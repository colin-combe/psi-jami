package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30;

import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlNamedExperimentWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 3.0 writer for a modelled interaction (ignore experimental details) which have a fullname and aliases in
 * addition to the shortname.
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class XmlNamedModelledInteractionWriter extends XmlModelledInteractionWriter{

    public XmlNamedModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseExperimentWriter() {
        super.setExperimentWriter(new XmlNamedExperimentWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseParticipantWriter() {
        super.setParticipantWriter(new XmlNamedModelledParticipantWriter(getStreamWriter(), getObjectIndex()));
    }
}
