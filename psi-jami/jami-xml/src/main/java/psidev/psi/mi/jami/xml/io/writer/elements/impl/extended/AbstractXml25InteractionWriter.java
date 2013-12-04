package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.BibRef;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interaction;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25NamedInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Abstract class for extended interaction writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXml25InteractionWriter<I extends Interaction, P extends Participant> extends AbstractXml25NamedInteractionWriter<I,P> implements PsiXml25ExtendedInteractionWriter<I>{

    private PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter;

    public AbstractXml25InteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ParticipantWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.inferredInteractionWriter = new Xml25InferredInteractionWriter(writer, objectIndex);
    }

    public AbstractXml25InteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                          PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                          PsiXml25XrefWriter secondaryRefWriter, PsiXml25ExperimentWriter experimentWriter,
                                          PsiXml25ParticipantWriter<P> participantWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter1,
                                          PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                          PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter, new psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25InferredInteractionWriter(writer, objectIndex), interactionTypeWriter, attributeWriter,
                checksumWriter);
        inferredInteractionWriter = inferredInteractionWriter1;
    }

    @Override
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        return Collections.singletonList(getDefaultExperiment());
    }

    @Override
    protected void writeIntraMolecular(Interaction object) throws XMLStreamException {
        ExtendedPsi25Interaction xmlInteraction = (ExtendedPsi25Interaction)object;
        if (xmlInteraction.isIntraMolecular()){
            getStreamWriter().writeStartElement("intraMolecular");
            getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isIntraMolecular()));
            // write end intra molecular
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeInteractionType(Interaction object) throws XMLStreamException {
        ExtendedPsi25Interaction xmlInteraction = (ExtendedPsi25Interaction)object;
        if (!xmlInteraction.getInteractionTypes().isEmpty()){
            for (Object type : xmlInteraction.getInteractionTypes()){
                getInteractionTypeWriter().write((CvTerm)type);
            }
        }
    }

    @Override
    protected void writeInferredInteractions(Interaction object) throws XMLStreamException {
        ExtendedPsi25Interaction xmlInteraction = (ExtendedPsi25Interaction)object;
        if (!xmlInteraction.getInferredInteractions().isEmpty()){
            getStreamWriter().writeStartElement("inferredInteractionList");
            for (Object inferred : xmlInteraction.getInferredInteractions()){
                this.inferredInteractionWriter.write((InferredInteraction)inferred);
            }
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.setDefaultExperiment(new XmlExperiment(new BibRef("Mock publication for interactions that do not have experimental details.", (String) null, (Date) null)));
    }
}