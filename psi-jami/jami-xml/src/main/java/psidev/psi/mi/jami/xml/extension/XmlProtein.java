package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

/**
 * Xml implementation of protein
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class XmlProtein extends XmlPolymer implements Protein{

    private Checksum rogid;

    public XmlProtein() {
    }

    public XmlProtein(String name, CvTerm type) {
        super(name, type);
    }

    public XmlProtein(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public XmlProtein(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public XmlProtein(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public XmlProtein(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public XmlProtein(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public XmlProtein(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public XmlProtein(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public XmlProtein(String name) {
        super(name);
    }

    public XmlProtein(String name, String fullName) {
        super(name, fullName);
    }

    public XmlProtein(String name, Organism organism) {
        super(name, organism);
    }

    public XmlProtein(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlProtein(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlProtein(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlProtein(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlProtein(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    @Override
    public void setXref(InteractorXrefContainer value) {
        if (value == null){
            this.xrefContainer = null;
        }
        else if (this.xrefContainer == null){
            this.xrefContainer = new ProteinXrefContainer();
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
        else {
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().clear();
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
    }

    @Override
    public void initialiseXrefContainer() {
        this.xrefContainer = new ProteinXrefContainer();
    }

    public String getUniprotkb() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((ProteinXrefContainer)xrefContainer).getUniprotkb();
    }

    public void setUniprotkb(String ac) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((ProteinXrefContainer)xrefContainer).setUniprotkb(ac);
    }

    public String getRefseq() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((ProteinXrefContainer)xrefContainer).getRefseq();
    }

    public void setRefseq(String ac) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((ProteinXrefContainer)xrefContainer).setRefseq(ac);
    }

    public String getGeneName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setGeneName(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRogid() {
        return this.rogid != null ? this.rogid.getValue() : null;
    }

    public void setRogid(String rogid) {
        Collection<Checksum> proteinChecksums = getChecksums();

        if (rogid != null){
            CvTerm rogidMethod = CvTermUtils.createRogid();
            // first remove old rogid
            if (this.rogid != null){
                proteinChecksums.remove(this.rogid);
            }
            this.rogid = new DefaultChecksum(rogidMethod, rogid);
            proteinChecksums.add(this.rogid);
        }
        // remove all smiles if the collection is not empty
        else if (!proteinChecksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(proteinChecksums, Checksum.ROGID_MI, Checksum.ROGID);
            this.rogid = null;
        }
    }


    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(Protein.PROTEIN, Protein.PROTEIN_MI));
    }

    protected void processAddedChecksumEvent(Checksum added) {
        if (rogid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.ROGID_MI, Checksum.ROGID)){
            // the rogid is not set, we can set the rogid
            rogid = added;
        }
    }

    protected void processRemovedChecksumEvent(Checksum removed) {
        if (rogid != null && rogid.equals(removed)){
            rogid = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.ROGID_MI, Checksum.ROGID);
        }
    }

    protected void clearPropertiesLinkedToChecksums() {
        rogid = null;
    }

    @Override
    protected void initialiseChecksums() {
        initialiseChecksumsWith(new ChecksumList());
    }

    private class ChecksumList extends AbstractListHavingProperties<Checksum> {
        public ChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {
            processAddedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToChecksums();
        }
    }
}
