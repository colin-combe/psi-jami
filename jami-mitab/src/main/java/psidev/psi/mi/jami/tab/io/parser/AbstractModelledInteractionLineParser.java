package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * Abstract class for ModelledInteraction line parsers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/07/13</pre>
 */

public abstract class AbstractModelledInteractionLineParser<T extends ModelledInteraction> extends AbstractInteractionLineParser<T, ModelledParticipant, ModelledFeature>{

    public AbstractModelledInteractionLineParser(InputStream stream) {
        super(stream);
    }

    public AbstractModelledInteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public AbstractModelledInteractionLineParser(Reader stream) {
        super(stream);
    }

    public AbstractModelledInteractionLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    MitabModelledFeature createFeature(String type, Collection<Range> ranges, String text, int line, int column, int mitabColumn){
        MitabModelledFeature feature = new MitabModelledFeature(new DefaultCvTerm(type));
        feature.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        feature.getRanges().addAll(ranges);
        processTextFor(feature, text);

        return feature;
    }

    @Override
    MitabModelledParticipant finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<ModelledFeature> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod, int line, int column, int mitabColumn) {
        boolean hasParticipantFields = !bioRole.isEmpty() || !annot.isEmpty() || !feature.isEmpty() || !stc.isEmpty();
        // first identify interactor
        Interactor interactor = createInteractorFrom(uniqueId, altid, aliases, taxid, type, xref, checksum, line, column, mitabColumn);
        MitabModelledParticipant participant = null;
        boolean hasInteractorDetails = true;

        if (interactor == null && !hasParticipantFields){
            return participant;
        }
        else if (interactor == null){
            interactor = getInteractorFactory().createInteractor(MitabUtils.UNKNOWN_NAME, null);
            ((FileSourceContext)interactor).setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
            hasInteractorDetails = false;
        }

        if (hasParticipantFields){
            MitabCvTerm bioRoleTerm = null;
            // set biorole
            if (bioRole.size() > 1){
                if (getParserListener() != null){
                    getParserListener().onSeveralCvTermsFound(bioRole, bioRole.iterator().next(), bioRole.size() + " biological roles found in one participant. Only the first one will be loaded");
                }
                bioRoleTerm = bioRole.iterator().next();
            }
            else if (!bioRole.isEmpty()){
                bioRoleTerm = bioRole.iterator().next();
            }

            participant = new MitabModelledParticipant(interactor, bioRoleTerm);

            // add annotations
            participant.getAnnotations().addAll(annot);
            // add features
            participant.addAllFeatures(feature);
            // add stc
            if (stc.size() > 1){
                if (getParserListener() != null){
                    getParserListener().onSeveralStoichiometryFound(stc);
                }
                participant.setStoichiometry(stc.iterator().next());
            }
            else if (!stc.isEmpty()){
                participant.setStoichiometry(stc.iterator().next());
            }
            // add source locator
            participant.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        }
        else {
            participant = new MitabModelledParticipant(interactor);
            // add source locator
            participant.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        }

        if (!hasInteractorDetails && getParserListener() != null){
            getParserListener().onParticipantWithoutInteractor(participant, participant);
        }

        return participant;
    }

    @Override
    T finishInteraction(ModelledParticipant A, ModelledParticipant B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative, int line) {
        T interaction = null;
        boolean hasInteractionFields = !interactionType.isEmpty() || !source.isEmpty() || !interactionId.isEmpty() || !conf.isEmpty() || !expansion.isEmpty()
                || !xrefI.isEmpty() || !annotI.isEmpty() || !checksumI.isEmpty() || !params.isEmpty() || !created.isEmpty() || !update.isEmpty();

        if (A == null && B == null && !hasInteractionFields){
            return interaction;
        }

        // create interaction with participants
        interaction = createInteraction();

        // set interaction type
        if (interactionType.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermsFound(interactionType, interactionType.iterator().next(), interactionType.size() + " interaction types found. Only the first one will be loaded.");
            }
            interaction.setInteractionType(interactionType.iterator().next());
        }
        else if (!interactionType.isEmpty()){
            interaction.setInteractionType(interactionType.iterator().next());
        }
        // set identifiers
        initialiseInteractionIdentifiers(interactionId, interaction);
        // add confidences
        interaction.getModelledConfidences().addAll(conf);
        // set expansion method
        initialiseExpansionMethod(expansion, interaction);
        // add xrefs
        interaction.getXrefs().addAll(xrefI);
        // initialise annotations
        interaction.getAnnotations().addAll(annotI);
        // add params
        interaction.getModelledParameters().addAll(params);
        // created
        if (created.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCreatedDateFound(created);
            }
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        else if (!created.isEmpty()){
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        // update
        if (update.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralUpdatedDateFound(update);
            }
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        else if (!update.isEmpty()){
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        // checksum
        interaction.getChecksums().addAll(checksumI);

        // then initialise source
        if (source.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralSourceFound(source);
            }
            MitabSource firstSource = source.iterator().next();
            interaction.setSource(firstSource);
        }
        else if (!source.isEmpty()){
            MitabSource firstSource = source.iterator().next();
            interaction.setSource(firstSource);
        }

        if (A == null && B == null && getParserListener() != null){
            getParserListener().onInteractionWithoutParticipants(interaction, (FileSourceContext)interaction);
        }

        if (A != null){
            interaction.addParticipant(A);
        }
        if (B != null){
            interaction.addParticipant(B);
        }

        ((FileSourceContext)interaction).setSourceLocator(new MitabSourceLocator(line, 0, 0));

        return interaction;
    }
}
