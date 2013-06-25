package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitabColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * The basic Mitab 2.6 writer for BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab26BinaryWriter extends AbstractMitab26BinaryWriter<BinaryInteraction, Participant>{

    private Mitab26ModelledBinaryWriter modelledBinaryWriter;
    private Mitab26BinaryEvidenceWriter binaryEvidenceWriter;

    public Mitab26BinaryWriter() {
        super();
    }

    public Mitab26BinaryWriter(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab26BinaryWriter(OutputStream output) {
        super(output);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab26BinaryWriter(Writer writer) {
        super(writer);
        initialiseSubWritersWith(writer);
    }

    @Override
    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }

    @Override
    public void flush() throws DataSourceWriterException {
        super.flush();
        this.binaryEvidenceWriter.flush();
        this.modelledBinaryWriter.flush();
    }

    @Override
    public void close() throws DataSourceWriterException {
        super.close();
    }

    @Override
    public void write(BinaryInteraction interaction) throws DataSourceWriterException {
        if (this.binaryEvidenceWriter == null || this.modelledBinaryWriter == null){
            throw new IllegalStateException("The Mitab25Writer has not been initialised with a map of options." +
                    "The options for the Mitab25Writer should contain at least "+ InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }
        // did not start yet so need to write the header if required
        else if (!hasWrittenHeader()){
            try{
                writeHeaderIfNotDone();
                this.binaryEvidenceWriter.setHasWrittenHeader(false);
                this.modelledBinaryWriter.setHasWrittenHeader(false);
            }
            catch (IOException e) {
                throw new DataSourceWriterException("Impossible to write MITAB header ", e);
            }
        }
        // we already started to write binary
        else{
            this.binaryEvidenceWriter.setHasWrittenHeader(true);
            this.modelledBinaryWriter.setHasWrittenHeader(true);
        }

        if (interaction instanceof BinaryInteractionEvidence){
            this.binaryEvidenceWriter.write((BinaryInteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledBinaryInteraction){
            this.modelledBinaryWriter.write((ModelledBinaryInteraction) interaction);
        }
        else {
            super.write(interaction);
        }
    }

    protected void initialiseSubWritersWith(Writer writer) {

        this.modelledBinaryWriter = new Mitab26ModelledBinaryWriter(writer);
        this.modelledBinaryWriter.setWriteHeader(false);
        this.binaryEvidenceWriter = new Mitab26BinaryEvidenceWriter(writer);
        this.binaryEvidenceWriter.setWriteHeader(false);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultMitabColumnFeeder(getWriter()));
    }

    @Override
    public void setWriteHeader(boolean writeHeader) {
        super.setWriteHeader(writeHeader);
        if (this.modelledBinaryWriter != null){
            this.modelledBinaryWriter.setWriteHeader(false);
        }
        if (this.binaryEvidenceWriter != null){
            this.binaryEvidenceWriter.setWriteHeader(false);
        }
    }
}
