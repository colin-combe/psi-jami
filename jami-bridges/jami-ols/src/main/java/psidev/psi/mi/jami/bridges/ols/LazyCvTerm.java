package psidev.psi.mi.jami.bridges.ols;

import com.sun.xml.internal.ws.addressing.model.ActionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/08/13
 */
public class LazyCvTerm extends DefaultCvTerm {

    protected final Logger log = LoggerFactory.getLogger(LazyCvTerm.class.getName());

    private Query queryService;

    private boolean hasShortName = false;
    private boolean hasXrefs  = false;
    private boolean hasAnnotations = false;

    public LazyCvTerm(Query queryService, String fullName, Xref identityRef) {
        super("");
        if (queryService == null){
            throw new IllegalArgumentException("The lazy cv term needs the Ols query service which cannot be null.");
        }
        this.queryService = queryService;

        setFullName(fullName);

        if (identityRef != null){
            getIdentifiers().add(identityRef);
        }
    }

    /**
     * If the shortName is not yet known, a query will be made to OLS.
     * If no shortName is found, the fullName is used instead.
     * @return  The shortName of the cvTerm.
     */
    @Override
    public String getShortName() {
        if (!hasShortName){
            initialiseShortNameAndSynonyms();
            hasShortName = true;
        }
        return super.getShortName();
    }

    public void setShortName(String name) {
        if (name != null){
            hasShortName = true;
        }
        super.setShortName(name);
    }

    public Collection<Xref> getXrefs() {
        if (!hasXrefs){
            initialiseOlsXrefs();
            hasXrefs = true;
        }
        return super.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (!hasAnnotations){
            initialiseOlsAnnotations();
            hasAnnotations = true;
        }
        return super.getAnnotations();
    }

    public Collection<Alias> getSynonyms() {
        if (!hasShortName) {

            initialiseOlsSynonyms();
            hasShortName = true;
        }

        return super.getSynonyms();
    }

    @Override
    public String toString() {
        return (getMIIdentifier() != null ? getMIIdentifier() : (getMODIdentifier() != null ? getMODIdentifier() : (getPARIdentifier() != null ? getPARIdentifier() : "-"))) + " ("+getFullName()+")";
    }

    // == QUERY METHODS =======================================================================

    /**
     * Retrieve the metadata for an entry.
     * <p>
     * The identifier is used to find the metadata
     * which can be used to find the identifier phrases for short labels and synonyms.
     *
     * @param identifier    The identifier that is being used.
     * @throws BridgeFailedException
     */
    private void fetchMetaDataByIdentifier(String identifier){
        try{
            Map<String,String> metaDataMap = queryService.getTermMetadata(identifier, null);
            if(primaryIdentifier == null)
                log.info("null primary");
            if(primaryIdentifier.getDatabase() == null)
                log.info("null database");
            log.info("using "+primaryIdentifier.getDatabase().getShortName());
            shortName = extractShortNameFromMetaData(metaDataMap , primaryIdentifier.getDatabase().getShortName());
            synonyms = extractSynonymsFromMetaData(metaDataMap , primaryIdentifier.getDatabase().getShortName());
            if(synonyms.isEmpty())
                initialiseSynonyms();
        }catch (RemoteException e) {
            log.warn("LazyCvTerm "+toString()+" failed whilst attempting to access metaData.",e);
        }
    }

    /**
     * Scans the meta data to find the short name if one is present.
     * @param metaDataMap           The map of data to search in.
     * @param miTermOntologyName    The name used for the ontology in MI.
     * @return                      The short name if one is found.
     */
    private String extractShortNameFromMetaData(Map metaDataMap, String miTermOntologyName){

        String META_DATA_SEPARATOR = "_";
        String SHORTLABEL_IDENTIFIER;
        if(miTermOntologyName == null) return null;
        else if(miTermOntologyName.equals(PSI_MI)) SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
        else if(miTermOntologyName.equals(PSI_MOD)) SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
        else return null;

        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SHORTLABEL_IDENTIFIER + META_DATA_SEPARATOR)){
                    return (String)metaDataMap.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Scans the meta data to find the description if one is present.
     * @param metaDataMap   The map of metaData to scrape for a definition
     * @return              The definition for the term if it is known.
     */
    private String extractDescriptionFromMetaData(Map metaDataMap){
        String DEFINITION_KEY = "definition";
        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                    return (String) metaDataMap.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Scans the meta data to find the synonyms if any are present.
     * @param metaDataMap           The map of meatData to scrape for synonyms.
     * @param miTermOntologyName    The name of the ontology used in psi-mi
     * @return                      The synonyms. Or an empty collection if none are found.
     */
    private Collection<Alias> extractSynonymsFromMetaData(Map metaDataMap, String miTermOntologyName){

        String META_DATA_SEPARATOR = "_";
        String SYNONYM_IDENTIFIER;
        String EXACT_SYNONYM_KEY = "exact_synonym";
        if(miTermOntologyName == null) return Collections.EMPTY_LIST;
        else if(miTermOntologyName.equalsIgnoreCase(PSI_MI)) SYNONYM_IDENTIFIER = "Alternate label curated by PSI-MI";
        else if(miTermOntologyName.equalsIgnoreCase(PSI_MOD)) SYNONYM_IDENTIFIER = "Alternate name curated by PSI-MOD";
        else return Collections.EMPTY_LIST;

        if (metaDataMap != null) {
            Collection<Alias> synonyms = new ArrayList<Alias>();
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SYNONYM_IDENTIFIER + META_DATA_SEPARATOR)){
                    synonyms.add(AliasUtils.createAlias(
                            "synonym", "MI:1041", (String) metaDataMap.get(key)));
                }else if (keyName.startsWith(EXACT_SYNONYM_KEY)){
                    synonyms.add(AliasUtils.createAlias(
                            "synonym", "MI:1041", (String) metaDataMap.get(key)));
                }
            }
            return synonyms;
        }else{
            return Collections.EMPTY_LIST;
        }
    }
}