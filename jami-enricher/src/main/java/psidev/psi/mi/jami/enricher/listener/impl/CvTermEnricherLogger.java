package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * A logging listener. It will display a message when each event is fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class CvTermEnricherLogger implements CvTermEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(CvTermEnricherLogger.class.getName());

    public void onEnrichmentComplete(CvTerm cvTerm, EnrichmentStatus status, String message) {
        log.info(cvTerm.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(CvTerm object, String message, Exception e) {
        log.error(object.toString()+" enrichment error, message: "+message, e);
    }

    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        log.info(cv+" has old shortname "+oldShortName+" new shortname "+cv.getShortName());
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        log.info(cv+" has old full name "+oldFullName+" new full name "+cv.getFullName());
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        log.info(cv+" has old MI "+oldMI+" new MI "+cv.getMIIdentifier());
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        log.info(cv+" has old MOD "+oldMOD+" new old MOD "+cv.getMODIdentifier());
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        log.info(cv+" has old PAR "+oldPAR+" new PAR "+cv.getPARIdentifier());
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        log.info(cv+" has added ID "+added.toString());
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        log.info(cv+" has removed ID "+removed.toString());
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        log.info(cv+" has added XREF "+added.toString()) ;
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        log.info(cv+" has removed xref "+removed.toString());
    }

    public void onAddedAlias(CvTerm cv, Alias added) {
        log.info(cv+" has added synonym "+added.toString());
    }

    public void onRemovedAlias(CvTerm cv, Alias removed) {
        log.info(cv+" has removed synonym "+removed.toString());
    }

    public void onAddedAnnotation(CvTerm o, Annotation added) {
        log.info(o.toString()+" has annotation added "+added.toString());    }

    public void onRemovedAnnotation(CvTerm o, Annotation removed) {
        log.info(o.toString()+" has annotation removed "+removed.toString());
    }
}
