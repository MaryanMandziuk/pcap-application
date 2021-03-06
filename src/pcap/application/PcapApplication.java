package pcap.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.taunova.importer.PCapImportTask;
import net.taunova.importer.pcap.PCapHelper;
import net.taunova.importer.pcap.exception.PCapInvalidFormat;
import net.taunova.importer.pcap.exception.PCapSourceNotFound;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maryan
 */
public class PcapApplication {

    /** 
     * Pcap application for filtering packets
     * @param args the command line arguments
     * @throws org.apache.commons.cli.ParseException
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws net.taunova.importer.pcap.exception.PCapSourceNotFound
     * @throws net.taunova.importer.pcap.exception.PCapInvalidFormat
     */
    public static void main(String[] args) throws ParseException, IOException, FileNotFoundException, PCapSourceNotFound, PCapInvalidFormat {
        
        System.out.println("PCap application"); 
        final Logger logger = LoggerFactory.getLogger(PcapApplication.class);
        String[] myArgs = {"-f","iperf-mptcp-0-0.pcap", "my.pcap"};
        PcapCommandLineParser parser = new PcapCommandLineParser(myArgs);
         
        try {
            ApplicationHandler handler = new ApplicationHandler(parser.getOutFile(), parser.getFilter());
            PCapImportTask importTask = PCapHelper.createImportTask(parser.getInFile(), handler);
            importTask.init();

            while( !importTask.isFinished() ) {
                importTask.processNext();
            }

            handler.showSavedPacketsTime();
        } catch ( FileNotFoundException | PCapSourceNotFound | PCapInvalidFormat ex ) {
            logger.error("Error : " + ex);
            throw ex;
        }
    }
    
}
