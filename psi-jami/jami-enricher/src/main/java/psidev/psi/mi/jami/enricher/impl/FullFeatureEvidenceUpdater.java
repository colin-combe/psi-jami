package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullFeatureEvidenceUpdater extends MinimalFeatureEvidenceUpdater {

    /**
     * Processes the specific details of the feature which are not delegated to a subEnricher.
     * @param featureToEnrich       The feature being enriched.
     * @throws EnricherException    Thrown if problems are encountered in a fetcher.
     */
    @Override
    public void enrich(FeatureEvidence featureToEnrich)
            throws EnricherException {
        // DETECTION METHODS
        processDetectionMethods(featureToEnrich);
        super.enrich(featureToEnrich);
    }

    protected void processDetectionMethods(FeatureEvidence featureToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null)
            getCvTermEnricher().enrich(featureToEnrich.getDetectionMethods());
    }
}
