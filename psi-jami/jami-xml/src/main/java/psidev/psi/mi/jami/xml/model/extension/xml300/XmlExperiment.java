package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlExperiment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlRootElement(name = "experimentDescription", namespace = "http://psi.hupo.org/mi/mif300")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlExperiment extends AbstractXmlExperiment{

    public XmlExperiment() {
    }

    public XmlExperiment(Publication publication) {
        super(publication);
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod) {
        super(publication, interactionDetectionMethod);
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism) {
        super(publication, interactionDetectionMethod, organism);
    }
}