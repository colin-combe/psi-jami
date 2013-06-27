package psidev.psi.mi.jami.enricher.impl.feature;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:54
 */
public abstract class AbstractFeatureEnricher
        implements FeatureEnricher{


    protected FeatureEnricherListener listener;




    public boolean enrichFeature(Feature featureToEnrich, String sequenceOld, String sequenceNew){

        if(featureToEnrich == null) throw new IllegalArgumentException("Feature enricher was passed a null feature.");


        /*
        if (getOrganismEnricher().getFetcher() instanceof MockOrganismFetcher){
            MockOrganismFetcher organismFetcher = (MockOrganismFetcher)getOrganismEnricher().getFetcher();
            organismFetcher.clearOrganisms();
            organismFetcher.addNewOrganism(""+proteinToEnrich.getOrganism().getTaxId(), proteinFetched.getOrganism());
        }

        getOrganismEnricher().enrichOrganism(proteinToEnrich.getOrganism());
         */
        processFeature(featureToEnrich);

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, "Success. Feature enriched.");
        return true;
    }

    public abstract boolean processFeature(Feature featureToEnrich);

    public Feature fetchFeature(Feature featureToEnrich) {
        //if(getFetcher() == null) throw new IllegalStateException("FeatureFetcher has not been provided.");
        if(featureToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null feature.");

        if(listener != null) listener.onFeatureEnriched(featureToEnrich,
                "Failed. No feature fetcher has been implemented.");
        return null;
    }

    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener) {
        this.listener = featureEnricherListener;
    }

    public FeatureEnricherListener getFeatureEnricherListener() {
        return listener;
    }
}
