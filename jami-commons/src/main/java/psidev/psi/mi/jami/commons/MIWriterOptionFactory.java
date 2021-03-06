package psidev.psi.mi.jami.commons;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.factory.options.MIDataSourceOptions;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabWriterOptions;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryLightIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.factory.options.PsiXmlWriterOptions;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

/**
 * The factory to get options for the InteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIWriterOptionFactory {

    private static final MIWriterOptionFactory instance = new MIWriterOptionFactory();

    private MIWriterOptionFactory() {
    }

    public static MIWriterOptionFactory getInstance() {
        return instance;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param outputFile : the file where to write the interactions
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(File outputFile) {
        Map<String, Object> options = getMitabOptions(outputFile, InteractionCategory.mixed, ComplexType.n_ary, null, true, MitabVersion.v2_7, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @param outputFile     : the file where to write the interactions
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionCategory objectCategory, ComplexType complexType, File outputFile) {
        Map<String, Object> options = getMitabOptions(outputFile, objectCategory, complexType, null, true, MitabVersion.v2_7, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param writeHeader : true if we want to write the header
     * @param version     : the MITAB version
     * @param outputFile  : the file where to write the interactions
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, File outputFile) {
        Map<String, Object> options = getMitabOptions(outputFile, InteractionCategory.mixed, ComplexType.n_ary, null, writeHeader, version, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param output : the output
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(OutputStream output) {
        Map<String, Object> options = getMitabOptions(output, InteractionCategory.mixed, ComplexType.n_ary, null, true, MitabVersion.v2_7, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @param output         : the output
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionCategory objectCategory, ComplexType complexType, OutputStream output) {
        Map<String, Object> options = getMitabOptions(output, objectCategory, complexType, null, true, MitabVersion.v2_7, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param writeHeader : true if we want to write the header
     * @param version     : the MITAB version
     * @param output      : the outputstream
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, OutputStream output) {
        Map<String, Object> options = getMitabOptions(output, InteractionCategory.mixed, ComplexType.n_ary, null, writeHeader, version, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param writer : the writer
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getDefaultMitabOptions(Writer writer) {
        Map<String, Object> options = getMitabOptions(writer, InteractionCategory.mixed, ComplexType.n_ary, null, true, MitabVersion.v2_7, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @param writer         : the writer
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(InteractionCategory objectCategory, ComplexType complexType, Writer writer) {
        Map<String, Object> options = getMitabOptions(writer, objectCategory, complexType, null, true, MitabVersion.v2_7, false);
        return options;
    }

    /**
     * Create the options for a MITAB interaction writer.
     *
     * @param writeHeader : true if we want to write the header
     * @param version     : the MITAB version
     * @param writer      : the writer
     * @return the options for a MITAB interaction writer.
     */
    public Map<String, Object> getMitabOptions(boolean writeHeader, MitabVersion version, Writer writer) {
        Map<String, Object> options = getMitabOptions(writer, InteractionCategory.mixed, ComplexType.n_ary, null, writeHeader, version, false);
        return options;
    }

    /**
     * Create the options for the MITAB InteractionWriter.
     *
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @param expansion      : the complex expansion method to use if we have n-ary interactions
     * @param writeHeader    : true if we want to write the header
     * @param version        : the MITAB version
     * @param extended       : true if all the aliases, features and confidences are pure mitab objects
     * @return the options for the MITAB InteractionWriter
     */
    public Map<String, Object> getMitabOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, ComplexExpansionMethod expansion, boolean writeHeader, MitabVersion version, boolean extended) {
        Map<String, Object> options = new HashMap<String, Object>(10);
        options.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, output);
        options.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIFileType.mitab.toString());
        options.put(MIDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY, objectCategory != null ? objectCategory : InteractionCategory.mixed);
        options.put(InteractionWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType != null ? complexType : ComplexType.n_ary);
        if (expansion != null) {
            options.put(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, expansion);
        }
        options.put(MitabWriterOptions.MITAB_HEADER_OPTION, writeHeader);
        options.put(MitabWriterOptions.MITAB_VERSION_OPTION, version != null ? version : MitabVersion.v2_7);
        options.put(MitabWriterOptions.MITAB_EXTENDED_OPTION, extended);
        return options;
    }

    /**
     * @param file : the output file
     * @return
     */
    public Map<String, Object> getDefaultXmlOptions(File file) {
        return getDefaultXmlOptions(file, InteractionCategory.mixed, ComplexType.n_ary, PsiXmlType.expanded, PsiXmlVersion.v2_5_4);
    }

    /**
     * @param output : the output stream
     * @return
     */
    public Map<String, Object> getDefaultXmlOptions(OutputStream output) {
        return getDefaultXmlOptions(output, InteractionCategory.mixed, ComplexType.n_ary, PsiXmlType.expanded, PsiXmlVersion.v2_5_4);
    }

    /**
     * @param writer : the output writer
     * @return
     */
    public Map<String, Object> getDefaultXmlOptions(Writer writer) {
        return getDefaultXmlOptions(writer, InteractionCategory.mixed, ComplexType.n_ary, PsiXmlType.expanded, PsiXmlVersion.v2_5_4);
    }

    /**
     * @param output          the output (file, outputfile, writer)
     * @param objectCategory: type of objects to write. It is mixed by default
     * @param complexType:    the kind of complex to write : n-ary or binary
     * @param type:           compact or expanded. It is expanded by default
     * @return
     */
    public Map<String, Object> getDefaultXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, PsiXmlType type,
                                                    PsiXmlVersion version) {
        if (type == null) {
            return getDefaultExpandedXmlOptions(output, objectCategory, complexType, version);
        }
        switch (type) {
            case compact:
                return getDefaultCompactXmlOptions(output, objectCategory, complexType, version);
            case expanded:
                return getDefaultExpandedXmlOptions(output, objectCategory, complexType, version);
            default:
                return getDefaultExpandedXmlOptions(output, objectCategory, complexType, version);
        }
    }

    /**
     * Create the options for the default expanded PSI-XML InteractionWriter.
     * The writer will not write extended XML objects and it will be based on an identity cache for generating ids
     * All sub complexes will be written as interactions.
     *
     * @param output         the output (file, outputfile, writer)
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @return the options for the PSI-XML  InteractionWriter
     */
    public Map<String, Object> getDefaultExpandedXmlOptions(Object output, InteractionCategory objectCategory,
                                                            ComplexType complexType, PsiXmlVersion version) {
        return getExpandedXmlOptions(output, objectCategory, complexType, null, null, null, version);
    }

    /**
     * Create the default options for the compact PSI-XML InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry
     * All complexes will be written as interactions.
     * It will not write PsiXml extended object features
     * It will use an identity cache to generate ids for the different interactions/experiments/interactors/participants/features
     *
     * @param output         the output (file, outputfile, writer)
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @param version:       Psi xml version
     * @return the options for the PSI-XM  InteractionWriter
     */
    public Map<String, Object> getDefaultCompactXmlOptions(Object output, InteractionCategory objectCategory,
                                                           ComplexType complexType, PsiXmlVersion version) {
        return getCompactXmlOptions(output, objectCategory, complexType, null, null, null, version);
    }

    /**
     * Create the options for the expanded PSI-XML InteractionWriter.
     * The writer will not write extended XML objects and it will be based on an identity cache for generating ids
     * All sub complexes will be written as interactions.
     *
     * @param output                  the output (file, outputfile, writer)
     * @param objectCategory          : the interaction object type to write
     * @param complexType:            the kind of complex to write : n-ary or binary
     * @param defaultSource           : default source if no source provided
     * @param defaultReleaseDate      : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @param version:                Psi xml version
     * @return the options for the PSI-XML InteractionWriter
     */
    public Map<String, Object> getExpandedXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, Source defaultSource,
                                                     XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                                     PsiXmlVersion version) {

        return getExpandedXmlOptions(output, objectCategory, complexType, new InMemoryLightIdentityObjectCache(),
                Collections.newSetFromMap(new IdentityHashMap<Interaction, Boolean>()),
                defaultSource, defaultReleaseDate, defaultEntryAnnotations, false, false, version);
    }

    /**
     * Create the options for the compact PSI-XML InteractionWriter.
     * The writer will not write extended XML objects and it will be based on an identity cache for generating ids
     *
     * @param output                  the output (file, outputfile, writer)
     * @param objectCategory          : the interaction object type to write
     * @param complexType:            the kind of complex to write : n-ary or binary
     * @param defaultSource           : default source if no source provided
     * @param defaultReleaseDate      : default release date in the entry source
     * @param defaultEntryAnnotations : annotations to write in the entry
     * @param version:                Psi xml version
     * @return the options for the PSI-XML InteractionWriter
     */
    public Map<String, Object> getCompactXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, Source defaultSource,
                                                    XMLGregorianCalendar defaultReleaseDate,
                                                    Collection<Annotation> defaultEntryAnnotations,
                                                    PsiXmlVersion version) {
        return getCompactXmlOptions(output, objectCategory, complexType, new InMemoryIdentityObjectCache(),
                Collections.newSetFromMap(new IdentityHashMap<Experiment, Boolean>()), Collections.newSetFromMap(new IdentityHashMap<Interactor, Boolean>()),
                new HashSet<String>(), Collections.newSetFromMap(new IdentityHashMap<Interaction, Boolean>()),
                defaultSource, defaultReleaseDate, defaultEntryAnnotations, false, false, version);
    }


    /**
     * Create the options for the expanded PSI-XML InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry.
     *
     * @param output         the output (file, outputfile, writer)
     * @param objectCategory : the interaction object type to write
     * @param complexType:   the kind of complex to write : n-ary or binary
     * @param elementCache:  the cache of objects necessary to assign ids to the different MI elements
     * @param interactionSet : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param version:       Psi xml version
     * @return the options for the PSI-XML  InteractionWriter
     */
    public Map<String, Object> getExpandedXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, PsiXmlObjectCache elementCache,
                                                     Set<Interaction> interactionSet, boolean writeComplexAsInteractors, boolean extended,
                                                     PsiXmlVersion version) {
        return getExpandedXmlOptions(output, objectCategory, complexType, elementCache, interactionSet,
                null, null, null, writeComplexAsInteractors, extended, version);
    }

    /**
     * Create the options for the compact PSI-XML InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry.
     *
     * @param output                    the output (file, outputfile, writer)
     * @param objectCategory            : the interaction object type to write
     * @param complexType:              the kind of complex to write : n-ary or binary
     * @param elementCache:             the cache of objects necessary to assign ids to the different MI elements
     * @param experimentSet             : the instance of set for the experiments that we want to use in compact mode
     * @param interactorSet             : the instance of set for the interactors that we want to use in compact mode
     * @param availabilitySet           : the instance of set for the availability that we want to use in compact mode
     * @param interactionSet            : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended                  : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml objects
     * @param version:                  Psi xml version
     * @return the options for the PSI-XML  InteractionWriter
     */
    public Map<String, Object> getCompactXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, PsiXmlObjectCache elementCache,
                                                    Set<Experiment> experimentSet, Set<Interactor> interactorSet, Set<String> availabilitySet,
                                                    Set<Interaction> interactionSet, boolean writeComplexAsInteractors, boolean extended,
                                                    PsiXmlVersion version) {
        return getCompactXmlOptions(output, objectCategory, complexType, elementCache, experimentSet, interactorSet, availabilitySet, interactionSet,
                null, null, null, writeComplexAsInteractors, extended, version);
    }


    /**
     * Create the options for the compact PSI-XML InteractionWriter.
     * This writer will write an empty source with just a release date and no attributes attached to the entry.
     * All sub complexes will be written as interactions.
     *
     * @param output                    the output (file, outputfile, writer)
     * @param objectCategory            : the interaction object type to write
     * @param complexType:              the kind of complex to write : n-ary or binary
     * @param elementCache:             the cache of objects necessary to assign ids to the different MI elements
     * @param interactionSet            : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param defaultSource             : default source if no source provided
     * @param defaultReleaseDate        : default release date in the entry source
     * @param defaultEntryAnnotations   : annotations to write in the entry
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended                  : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml objects
     * @param version:                  Psi xml version
     * @return the options for the PSI-XML  InteractionWriter
     */
    public Map<String, Object> getExpandedXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, PsiXmlObjectCache elementCache,
                                                     Set<Interaction> interactionSet, Source defaultSource,
                                                     XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                                     boolean writeComplexAsInteractors, boolean extended,
                                                     PsiXmlVersion version) {
        return getXmlOptions(output, objectCategory, complexType, PsiXmlType.expanded, elementCache, null, null, null, interactionSet, defaultSource,
                defaultReleaseDate, defaultEntryAnnotations, writeComplexAsInteractors, extended, version);
    }

    /**
     * Create the options for the compact PSI-XML InteractionWriter.
     *
     * @param output                    the output (file, outputfile, writer)
     * @param objectCategory            : the interaction object type to write
     * @param complexType:              the kind of complex to write : n-ary or binary
     * @param elementCache:             the cache of objects necessary to assign ids to the different MI elements
     * @param experimentSet             : the instance of set for the experiments that we want to use in compact mode
     * @param interactorSet             : the instance of set for the interactors that we want to use in compact mode
     * @param availabilitySet           : the instance of set for the availability that we want to use in compact mode
     * @param interactionSet            : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param defaultSource             : default source if no source provided
     * @param defaultReleaseDate        : default release date in the entry source
     * @param defaultEntryAnnotations   : annotations to write in the entry
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended                  : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml objects
     * @param version:                  Psi xml version
     * @return the options for the PSI-XML  InteractionWriter
     */
    public Map<String, Object> getCompactXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, PsiXmlObjectCache elementCache,
                                                    Set<Experiment> experimentSet, Set<Interactor> interactorSet, Set<String> availabilitySet,
                                                    Set<Interaction> interactionSet, Source defaultSource,
                                                    XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                                    boolean writeComplexAsInteractors, boolean extended,
                                                    PsiXmlVersion version) {
        return getXmlOptions(output, objectCategory, complexType, PsiXmlType.compact, elementCache,
                experimentSet, interactorSet, availabilitySet, interactionSet, defaultSource,
                defaultReleaseDate, defaultEntryAnnotations, writeComplexAsInteractors, extended, version);
    }

    /**
     * Create the options for the PSI-XML InteractionWriter.
     *
     * @param output                    the output (file, outputfile, writer)
     * @param objectCategory            : the interaction object type to write
     * @param complexType:              the kind of complex to write : n-ary or binary
     * @param xmlType:                  the type of xml (compact or expanded). The default type is expanded
     * @param elementCache:             the cache of objects necessary to assign ids to the different MI elements
     * @param experimentSet             : the instance of set for the experiments that we want to use in compact mode
     * @param interactorSet             : the instance of set for the interactors that we want to use in compact mode
     * @param availabilitySet           : the instance of set for the availability that we want to use in compact mode
     * @param interactionSet            : the instance of set for the interactions that we want to use in compact and expanded mode
     * @param defaultSource             : default source if no source provided
     * @param defaultReleaseDate        : default release date in the entry source
     * @param defaultEntryAnnotations   : annotations to write in the entry
     * @param writeComplexAsInteractors : true if we want to write all sub complexes as interactors instead of interactions
     * @param extended                  : true if all the interactions, participants, source, experiment, organism, cv terms and xrefs are pure xml objects
     * @param version:                  Psi xml version
     * @return the options for the PSI-XML  InteractionWriter
     */
    public Map<String, Object> getXmlOptions(Object output, InteractionCategory objectCategory, ComplexType complexType, PsiXmlType xmlType,
                                             PsiXmlObjectCache elementCache, Set<Experiment> experimentSet,
                                             Set<Interactor> interactorSet, Set<String> availabilitySet,
                                             Set<Interaction> interactionSet, Source defaultSource,
                                             XMLGregorianCalendar defaultReleaseDate, Collection<Annotation> defaultEntryAnnotations,
                                             boolean writeComplexAsInteractors, boolean extended,
                                             PsiXmlVersion version) {
        Map<String, Object> options = new HashMap<String, Object>(10);

        options.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, output);
        options.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIFileType.psimi_xml.toString());
        options.put(MIDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY, objectCategory != null ? objectCategory : InteractionCategory.mixed);
        options.put(InteractionWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType != null ? complexType : ComplexType.n_ary);
        options.put(PsiXmlWriterOptions.XML_TYPE_OPTION, xmlType != null ? xmlType : PsiXmlType.expanded);
        if (elementCache != null) {
            options.put(PsiXmlWriterOptions.ELEMENT_WITH_ID_CACHE_OPTION, elementCache);
        }
        if (experimentSet != null) {
            options.put(PsiXmlWriterOptions.COMPACT_XML_EXPERIMENT_SET_OPTION, experimentSet);
        }
        if (interactorSet != null) {
            options.put(PsiXmlWriterOptions.COMPACT_XML_INTERACTOR_SET_OPTION, interactorSet);
        }
        if (availabilitySet != null) {
            options.put(PsiXmlWriterOptions.COMPACT_XML_AVAILABILITY_SET_OPTION, availabilitySet);
        }
        if (interactionSet != null) {
            options.put(PsiXmlWriterOptions.XML_INTERACTION_SET_OPTION, interactionSet);
        }
        if (defaultSource != null) {
            options.put(PsiXmlWriterOptions.DEFAULT_SOURCE_OPTION, defaultSource);
        }
        if (defaultReleaseDate != null) {
            options.put(PsiXmlWriterOptions.DEFAULT_RELEASE_DATE_OPTION, defaultReleaseDate);
        } else {
            try {
                GregorianCalendar cal = new GregorianCalendar();
                XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH),
                        DatatypeConstants.FIELD_UNDEFINED);
                options.put(PsiXmlWriterOptions.DEFAULT_RELEASE_DATE_OPTION, xmlGregorianCalendar);
            } catch (DatatypeConfigurationException e) {
                System.out.println(e);
            }
        }
        if (defaultEntryAnnotations != null) {
            options.put(PsiXmlWriterOptions.XML_ENTRY_ATTRIBUTES_OPTION, defaultEntryAnnotations);
        }
        options.put(PsiXmlWriterOptions.WRITE_COMPLEX_AS_INTERACTOR_OPTION, writeComplexAsInteractors);
        options.put(PsiXmlWriterOptions.XML_EXTENDED_OPTION, extended);
        if (version != null) {
            options.put(PsiXmlWriterOptions.XML_VERSION_OPTION, version);
        } else {
            options.put(PsiXmlWriterOptions.XML_VERSION_OPTION, PsiXmlVersion.v2_5_4);
        }
        return options;
    }
}
