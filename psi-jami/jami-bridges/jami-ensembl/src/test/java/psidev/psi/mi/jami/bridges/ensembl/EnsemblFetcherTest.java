package psidev.psi.mi.jami.bridges.ensembl;


import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class EnsemblFetcherTest {

    @Test
    public void tests() throws Exception {
        EnsemblFetcher fetcher = new EnsemblFetcher();
                 //ENSG00000157764
        //fetcher.testA("ENSG00000157764");
        fetcher.testB(null, "ENSG00000157764");

    }


}
