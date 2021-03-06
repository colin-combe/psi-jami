package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalParticipantPool;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureEvidenceComparator;
import psidev.psi.mi.jami.utils.comparator.organism.UnambiguousOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.UnambiguousParameterComparator;

/**
 * Unambiguous exact Experimental participant pool comparator.
 *
 * It will first compares experimental roles using UnambiguousCvTermComparator. If both experimental roles are equals, it
 * will look at the identification methods using UnambiguousCvTermComparator. If both identification methods are equals, it will
 * look at the experimental preparations using UnambiguousCvTermComparator. If both experimental preparations are equals, it will
 * look at the expressed in organisms using UnambiguousOrganismComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactExperimentalParticipantPoolComparator extends ExperimentalParticipantPoolComparator {

    private static UnambiguousExactExperimentalParticipantPoolComparator unambiguousExactExperimentalParticipantComparator;

    /**
     * Creates a new UnambiguousExactExperimentalParticipantPoolComparator. It will use a UnambiguousExactParticipantBaseComparator to compare
     * the basic properties of a participant, a UnambiguousCvTermComparator to compare experimental roles, preparations and identification methods
     * and a UnambiguousOrganismComparator to compare expressed in organisms
     */
    public UnambiguousExactExperimentalParticipantPoolComparator() {
        super(new UnambiguousExactParticipantBaseComparator(),
                new UnambiguousOrganismComparator(),
                new UnambiguousParameterComparator(),
                new UnambiguousFeatureEvidenceComparator(),
                new UnambiguousExactExperimentalEntityComparator());
    }

    @Override
    public UnambiguousExactParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousExactParticipantBaseComparator) super.getParticipantBaseComparator();
    }

    @Override
    public UnambiguousOrganismComparator getOrganismComparator() {
        return (UnambiguousOrganismComparator) super.getOrganismComparator();
    }

    @Override
    public UnambiguousExactExperimentalEntityComparator getExperimentalEntityComparator() {
        return (UnambiguousExactExperimentalEntityComparator) super.getExperimentalEntityComparator();
    }

    @Override
    /**
     * It will first compares experimental roles using UnambiguousCvTermComparator. If both experimental roles are equals, it
     * will look at the identification methods using UnambiguousCvTermComparator. If both identification methods are equals, it will
     * look at the experimental preparations using UnambiguousCvTermComparator. If both experimental preparations are equals, it will
     * look at the expressed in organisms using UnambiguousOrganismComparator.
     */
    public int compare(ExperimentalParticipantPool experimentalParticipant1, ExperimentalParticipantPool experimentalParticipant2) {
        return super.compare(experimentalParticipant1, experimentalParticipant2);
    }

    /**
     * Use UnambiguousExactParticipantEvidenceComparator to know if two experimental participants are equals.
     * @param experimentalParticipant1
     * @param component2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ExperimentalParticipantPool experimentalParticipant1, ExperimentalParticipantPool component2){
        if (unambiguousExactExperimentalParticipantComparator == null){
            unambiguousExactExperimentalParticipantComparator = new UnambiguousExactExperimentalParticipantPoolComparator();
        }

        return unambiguousExactExperimentalParticipantComparator.compare(experimentalParticipant1, component2) == 0;
    }
}
