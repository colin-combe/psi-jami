package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A basic minimal enricher for interactors.
 *
 * See description of minimal enrichment in AbstractInteractorEnricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalInteractorBaseEnricher<T extends Interactor> extends AbstractInteractorEnricher<T> {

    public MinimalInteractorBaseEnricher(){
        super();
    }

    public MinimalInteractorBaseEnricher(InteractorFetcher<T> fetcher){
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }

    @Override
    protected void onCompletedEnrichment(T objectToEnrich) {
        if(getListener() != null)
            getListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.SUCCESS , "The interactor has been successfully enriched.");
    }

    @Override
    protected void onInteractorCheckFailure(T objectToEnrich, T fetchedObject) throws EnricherException{
        // nothing to do here
    }

    @Override
    public T find(T objectToEnrich) throws EnricherException {
        T fetchInteractor = null;

        if (getInteractorFetcher() != null){
            if(!objectToEnrich.getIdentifiers().isEmpty()){
                Collection<String> ids = new ArrayList<String>(objectToEnrich.getIdentifiers().size());
                for (Xref id : objectToEnrich.getIdentifiers()){
                    ids.add(id.getId());
                }
                fetchInteractor = fetchInteractor(ids);
            }
        }
        return fetchInteractor;
    }

    private T fetchInteractor(Collection<String> ids) throws EnricherException {

        try {
            Collection<T> entities = getInteractorFetcher().fetchByIdentifiers(ids);
            return !entities.isEmpty() ? entities.iterator().next() : null;
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < getRetryCount()){
                try {
                    Collection<T> entities = getInteractorFetcher().fetchByIdentifiers(ids);
                    return !entities.isEmpty() ? entities.iterator().next() : null;
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ getRetryCount() +" times to fetch the Interactor but cannot connect to the fetcher.", e);
        }
    }
}
