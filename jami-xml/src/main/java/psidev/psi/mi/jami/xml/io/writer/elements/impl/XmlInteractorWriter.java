package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Xml25 interactor writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlInteractorWriter implements PsiXmlElementWriter<Interactor> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter primaryRefWriter;
    private PsiXmlXrefWriter secondaryRefWriter;
    private PsiXmlElementWriter<CvTerm> interactorTypeWriter;
    private PsiXmlElementWriter<Organism> organismWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Checksum> checksumWriter;

    public XmlInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlInteractorWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlInteractorWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.aliasWriter = new XmlAliasWriter(writer);
        this.primaryRefWriter = new XmlPrimaryXrefWriter(writer);
        this.secondaryRefWriter = new XmlSecondaryXrefWriter(writer);
        this.interactorTypeWriter = new XmlInteractorTypeWriter(writer);
        this.organismWriter = new XmlOrganismWriter(writer);
        this.attributeWriter = new XmlAnnotationWriter(writer);
        this.checksumWriter = new XmlChecksumWriter(writer);
    }

    public XmlInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                               PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                               PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<CvTerm> interactorTypeWriter,
                               PsiXmlElementWriter<Organism> organismWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                               PsiXmlElementWriter<Checksum> checksumWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlInteractorWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlExperimentWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new XmlPrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new XmlSecondaryXrefWriter(writer);
        this.interactorTypeWriter = interactorTypeWriter != null ? interactorTypeWriter : new XmlInteractorTypeWriter(writer);
        this.organismWriter = organismWriter != null ? organismWriter : new XmlOrganismWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new XmlAnnotationWriter(writer);
        this.checksumWriter = checksumWriter != null ? checksumWriter : new XmlChecksumWriter(writer);
    }

    @Override
    public void write(Interactor object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("interactor");
            int id = this.objectIndex.extractIdForInteractor(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));

            // write names
            this.streamWriter.writeStartElement("names");
            // write shortname
            if (object.getShortName() != null){
                this.streamWriter.writeStartElement("shortLabel");
                this.streamWriter.writeCharacters(object.getShortName());
                this.streamWriter.writeEndElement();
            }
            // write fullname
            if (object.getFullName() != null){
                this.streamWriter.writeStartElement("fullName");
                this.streamWriter.writeCharacters(object.getFullName());
                this.streamWriter.writeEndElement();
            }
            // write aliases
            for (Alias alias : object.getAliases()){
                this.aliasWriter.write(alias);
            }
            // write end names
            this.streamWriter.writeEndElement();

            // write Xref
            if (!object.getIdentifiers().isEmpty()){
                writeXrefFromInteractorIdentifiers(object);
            }
            else if (!object.getXrefs().isEmpty()){
                writeXrefFromInteractorXrefs(object);
            }

            // write interactor type
            this.interactorTypeWriter.write(object.getInteractorType());

            // write organism
            if (object.getOrganism() != null){
                this.organismWriter.write(object.getOrganism());
            }

            // write sequence
            if (object instanceof Polymer){
                Polymer pol = (Polymer) object;
                if (pol.getSequence() != null){
                    this.streamWriter.writeStartElement("sequence");
                    this.streamWriter.writeCharacters(pol.getSequence());
                    this.streamWriter.writeEndElement();
                }
            }

            // write attributes
            if (!object.getAnnotations().isEmpty()){
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Annotation ann : object.getAnnotations()){
                    this.attributeWriter.write(ann);
                }
                for (Checksum c : object.getChecksums()){
                    this.checksumWriter.write(c);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
            }
            // write checksum
            else if (!object.getChecksums().isEmpty()){
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Checksum c : object.getChecksums()){
                    this.checksumWriter.write(c);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
            }

            // write end interactor
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the interactor : "+object.toString(), e);
        }
    }

    protected void writeXrefFromInteractorXrefs(Interactor object) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                this.primaryRefWriter.write(ref);
                index++;
            }
            // write secondaryref
            else{
                this.secondaryRefWriter.write(ref);
                index++;
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromInteractorIdentifiers(Interactor object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // all these xrefs are identity
        this.primaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.primaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);
        this.secondaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.secondaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);

        Xref primaryRef = object.getPreferredIdentifier();
        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (primaryRef != null){
            this.primaryRefWriter.write(primaryRef);
            hasWrittenPrimaryRef = true;
        }

        // write secondaryRefs (and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // ignore preferred identifier that is already written
            if (ref != primaryRef){
                if (!hasWrittenPrimaryRef){
                    hasWrittenPrimaryRef = true;
                    this.primaryRefWriter.write(ref);
                }
                else{
                    this.secondaryRefWriter.write(ref);
                }
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            this.secondaryRefWriter.setDefaultRefType(null);
            this.secondaryRefWriter.setDefaultRefTypeAc(null);
            for (Xref ref : object.getXrefs()){
                this.secondaryRefWriter.write(ref);
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }
}