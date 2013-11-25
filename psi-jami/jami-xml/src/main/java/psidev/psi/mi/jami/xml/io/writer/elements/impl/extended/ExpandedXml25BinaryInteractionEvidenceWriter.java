package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for an extended binary interaction evidence (having modelled, intramolecular properties, list
 * of experiments, list of interaction types, etc.).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXml25BinaryInteractionEvidenceWriter extends AbstractXml25InteractionEvidenceWriter<BinaryInteractionEvidence, ParticipantEvidence>
        implements ExpandedPsiXml25ElementWriter<BinaryInteractionEvidence> {
    public ExpandedXml25BinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXml25ParticipantEvidenceWriter(writer, objectIndex));
    }

    public ExpandedXml25BinaryInteractionEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ExperimentWriter experimentWriter, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter1, PsiXml25ElementWriter<String> availabilityWriter, PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter,
                                                        PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter != null ? participantWriter : new ExpandedXml25ParticipantEvidenceWriter(writer, objectIndex)
                , interactionTypeWriter, attributeWriter, experimentWriter, aliasWriter, inferredInteractionWriter1, availabilityWriter, confidenceWriter, parameterWriter,
                checksumWriter);
    }

    @Override
    protected void writeAvailability(BinaryInteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityDescription(object.getAvailability());
        }
    }

    @Override
    protected void writeExperiments(BinaryInteractionEvidence object) throws XMLStreamException {
        writeExperimentDescription(object);
    }
}