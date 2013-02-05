package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExternalIdentifier;

/**
 * Factory for creating CvTerms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class CvTermFactory {

    public static CvTerm createPsiMiDatabaseNameOnly(){
        return new DefaultCvTerm(CvTerm.PSI_MI);
    }

    public static CvTerm createMICvTerm(String name, String MI){
        return new DefaultCvTerm(name, new DefaultExternalIdentifier(createPsiMiDatabaseNameOnly(), MI));
    }

    public static CvTerm createPsiMiDatabase(){
        return createMICvTerm(CvTerm.PSI_MI, CvTerm.PSI_MI_ID);
    }

    public static CvTerm createChebiDatabase(){
        return createMICvTerm(Xref.CHEBI, Xref.CHEBI_ID);
    }

    public static CvTerm createEnsemblDatabase(){
        return createMICvTerm(Xref.ENSEMBL, Xref.ENSEMBL_ID);
    }

    public static CvTerm createEnsemblGenomesDatabase(){
        return createMICvTerm(Xref.ENSEMBL_GENOMES, Xref.ENSEMBL_GENOMES_ID);
    }

    public static CvTerm createEntrezGeneIdDatabase(){
        return createMICvTerm(Xref.ENTREZ_GENE, Xref.ENTREZ_GENE_ID);
    }

    public static CvTerm createRefseqDatabase(){
        return createMICvTerm(Xref.REFSEQ, Xref.REFSEQ_ID);
    }

    public static CvTerm createDdbjEmblGenbankDatabase(){
        return createMICvTerm(Xref.DDBJ_EMBL_GENBANK, Xref.DDBJ_EMBL_GENBANK_ID);
    }

    public static CvTerm createUniprotkbDatabase(){
        return createMICvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_ID);
    }

    public static CvTerm createImexDatabase(){
        return createMICvTerm(Xref.IMEX, Xref.IMEX_ID);
    }

    public static CvTerm createSmile(){
        return createMICvTerm(Checksum.SMILE, Checksum.SMILE_ID);
    }

    public static CvTerm createStandardInchi(){
        return createMICvTerm(Checksum.INCHI, Checksum.INCHI_ID);
    }

    public static CvTerm createStandardInchiKey(){
        return createMICvTerm(Checksum.INCHI_KEY, Checksum.INCHI_KEY_ID);
    }

    public static CvTerm createRogid(){
        return createMICvTerm(Checksum.ROGID, Checksum.ROGID_ID);
    }

    public static CvTerm createUndeterminedStatus(){
        return createMICvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
    }

    public static CvTerm createNTerminalRangeStatus(){
        return createMICvTerm(Position.N_TERMINAL_RANGE, Position.N_TERMINAL_RANGE_MI);
    }

    public static CvTerm createCTerminalRangeStatus(){
        return createMICvTerm(Position.C_TERMINAL_RANGE, Position.C_TERMINAL_RANGE_MI);
    }

    public static CvTerm createNTerminalStatus(){
        return createMICvTerm(Position.N_TERMINAL, Position.N_TERMINAL_MI);
    }

    public static CvTerm createRaggedNTerminalStatus(){
        return createMICvTerm(Position.RAGGED_N_TERMINAL, Position.RAGGED_N_TERMINAL_MI);
    }

    public static CvTerm createGeneInteractorType(){
        return createMICvTerm(Gene.GENE, Gene.GENE_ID);
    }

    public static CvTerm createGeneNameAliasType(){
        return createMICvTerm(Alias.GENE_NAME, Alias.GENE_NAME_ID);
    }

    public static CvTerm createUnspecifiedRole(){
        return createMICvTerm(CvTerm.UNSPECIFIED_ROLE, CvTerm.UNSPECIFIED_ROLE_ID);
    }

    public static CvTerm createComplexPhysicalProperties(){
        return createMICvTerm(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_ID);
    }

    public static CvTerm createImexPrimaryQualifier(){
        return createMICvTerm(Xref.IMEX_PRIMARY, Xref.IMEX_PRIMARY_ID);
    }

    public static CvTerm createAllosteryCooperativeMechanism(){
        return createMICvTerm(CooperativeInteraction.ALLOSTERY, CooperativeInteraction.ALLOSTERY_ID);
    }
}
