package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for BinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class DefaultBinaryInteractionEvidence extends AbstractBinaryInteraction<ParticipantEvidence> implements BinaryInteractionEvidence{

    private Xref imexId;
    private Experiment experiment;
    private String availability;
    private Collection<Parameter> parameters;
    private boolean isInferred = false;
    private Collection<Confidence> confidences;
    private boolean isNegative;
    private Collection<VariableParameterValueSet> variableParameterValueSets;

    public DefaultBinaryInteractionEvidence() {
        super();
    }

    public DefaultBinaryInteractionEvidence(String shortName) {
        super(shortName);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public DefaultBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(participantA, participantB);
    }

    public DefaultBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, participantA, participantB);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, type, participantA, participantB);
    }

    public DefaultBinaryInteractionEvidence(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public DefaultBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    protected void initialiseExperimentalConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseExperimentalConfidencesWith(Collection<Confidence> confidences){
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
        }
    }

    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseVariableParameterValueSetsWith(Collection<VariableParameterValueSet> variableValues){
        if (variableValues == null){
            this.variableParameterValueSets = Collections.EMPTY_LIST;
        }
        else {
            this.variableParameterValueSets = variableValues;
        }
    }

    protected void initialiseExperimentalParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseExperimentalParametersWith(Collection<Parameter> parameters){
        if (parameters == null){
            this.parameters = Collections.EMPTY_LIST;
        }
        else {
            this.parameters = parameters;
        }
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new ExperimentalBinaryInteractionXrefList());
    }

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        // add new imex if not null
        if (identifier != null){
            ExperimentalBinaryInteractionXrefList interactionXrefs = (ExperimentalBinaryInteractionXrefList) getXrefs();
            CvTerm imexDatabase = CvTermUtils.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermUtils.createImexPrimaryQualifier();
            // first remove old doi if not null
            if (this.imexId != null){
                interactionXrefs.removeOnly(this.imexId);
            }
            this.imexId = new DefaultXref(imexDatabase, identifier, imexPrimaryQualifier);
            interactionXrefs.addOnly(this.imexId);
        }
        else {
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
    }

    public Experiment getExperiment() {
        return this.experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        if (this.experiment != null){
            this.experiment.removeInteractionEvidence(this);
        }

        if (experiment != null){
            experiment.addInteractionEvidence(this);
        }
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {

        if (variableParameterValueSets == null){
            initialiseVariableParameterValueSets();
        }
        return this.variableParameterValueSets;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseExperimentalConfidences();
        }
        return this.confidences;
    }

    public String getAvailability() {
        return this.availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public boolean isNegative() {
        return this.isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseExperimentalParameters();
        }
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    protected void processAddedXrefEvent(Xref added) {

        // the added identifier is imex and the current imex is not set
        if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
            // the added xref is imex-primary
            if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                imexId = added;
            }
        }
    }

    protected void processRemovedXrefEvent(Xref removed) {
        // the removed identifier is pubmed
        if (imexId != null && imexId.equals(removed)){
            Collection<Xref> existingImex = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(getXrefs(), Xref.IMEX_MI, Xref.IMEX, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY);
            if (!existingImex.isEmpty()){
                imexId = existingImex.iterator().next();
            }
        }
    }

    protected void clearPropertiesLinkedToXrefs() {
        imexId = null;
    }

    @Override
    public String toString() {
        return imexId != null ? imexId.getId() : super.toString();
    }

    /**
     * Experimental interaction Xref list
     */
    private class ExperimentalBinaryInteractionXrefList extends AbstractListHavingProperties<Xref> {
        public ExperimentalBinaryInteractionXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            processAddedXrefEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedXrefEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToXrefs();
        }
    }
}
