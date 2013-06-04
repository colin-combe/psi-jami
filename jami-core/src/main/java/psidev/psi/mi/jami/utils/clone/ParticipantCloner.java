package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Utility class for cloning a participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class ParticipantCloner {

    /***
     * This method will copy properties of participant source in participant target and will override all the other properties of Target participant.
     * This method will ignore interaction
     * @param source
     * @param target
     */
    public static void copyAndOverrideParticipantEvidenceProperties(ParticipantEvidence source, ParticipantEvidence target){
        if (source != null && target != null){
            target.setExperimentalRole(source.getExperimentalRole());
            target.setExpressedInOrganism(source.getExpressedInOrganism());
            target.setBiologicalRole(source.getBiologicalRole());
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());
            target.setCausalRelationship(source.getCausalRelationship());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getFeatures().clear();
            target.getFeatures().addAll(source.getFeatures());
            target.getConfidences().clear();
            target.getConfidences().addAll(source.getConfidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());
            target.getExperimentalPreparations().clear();
            target.getExperimentalPreparations().addAll(source.getExperimentalPreparations());
            target.getParameters().clear();
            target.getParameters().addAll(source.getParameters());
            target.getIdentificationMethods().clear();
            target.getIdentificationMethods().addAll(source.getIdentificationMethods());
        }
    }

    /***
     * This method will copy properties of component source in component target and will override all the other properties of Target component.
     * This method will ignore interaction
     * @param source
     * @param target
     */
    public static void copyAndOverrideComponentProperties(ModelledParticipant source, ModelledParticipant target){
        if (source != null && target != null){
            target.setBiologicalRole(source.getBiologicalRole());
            target.setInteractor(source.getInteractor());
            target.setStoichiometry(source.getStoichiometry());
            target.setCausalRelationship(source.getCausalRelationship());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getFeatures().clear();
            target.addAllModelledFeatures(source.getFeatures());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getAliases().clear();
            target.getAliases().addAll(source.getAliases());
        }
    }
}
