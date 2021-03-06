package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlExperiment;
import psidev.psi.mi.jami.xml.model.extension.BibRef;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlLocator;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml 3.0 implementation of experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlRootElement(name = "experimentDescription", namespace = "http://psi.hupo.org/mi/mif300")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlExperiment extends AbstractXmlExperiment {

    private JAXBVariableParameterWrapper jaxbVariableParameterWrapper;

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

    @Override
    public Collection<VariableParameter> getVariableParameters() {
        if (this.jaxbVariableParameterWrapper == null){
            this.jaxbVariableParameterWrapper = new JAXBVariableParameterWrapper();
        }
        return this.jaxbVariableParameterWrapper.variableParameters;
    }

    @XmlElement(name = "variableParameterList")
    public void setJAXBVariableParameterValuesWrapper(JAXBVariableParameterWrapper jaxbVariableParameterList) {
        this.jaxbVariableParameterWrapper = jaxbVariableParameterList;
        // initialise all variable parameters because of back references
        if (this.jaxbVariableParameterWrapper != null && !this.jaxbVariableParameterWrapper.variableParameters.isEmpty()){
            for (VariableParameter param : this.jaxbVariableParameterWrapper.variableParameters){
                param.setExperiment(this);
            }
        }
    }

    @Override
    protected void initialiseFullNameFromPublication(BibRef publication) {
        // does nothing
    }

    //////////////////////////////////////////////////

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="variableParametersWrapper")
    public static class JAXBVariableParameterWrapper implements Locatable, FileSourceContext {
        private PsiXmlLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<VariableParameter> variableParameters;

        public JAXBVariableParameterWrapper(){
            initialiseVariables();
        }

        public JAXBVariableParameterWrapper(List<VariableParameter> parameters){
            this.variableParameters = parameters;
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseVariables(){
            this.variableParameters = new ArrayList<VariableParameter>();
        }

        @XmlElement(type=XmlVariableParameter.class, name="variableParameter", required = true)
        public List<VariableParameter> getJAXBVariableParameters() {
            return this.variableParameters;
        }

        @Override
        public String toString() {
            return "Variable parameter values List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
