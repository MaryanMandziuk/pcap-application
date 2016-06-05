/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maryan
 */
public class PcapCommandLineParser {
    
    private final String[] args;
    private final CommandLineParser parser = new DefaultParser();
    private final Options options = new Options();
    private CommandLine commandLine;
    private File outFile;
    private File inFile;
    private final Logger logger = LoggerFactory.getLogger(PcapCommandLineParser.class);
    private Filter dataFilter = new Filter();
    
    public PcapCommandLineParser(String[] args) throws ParseException, IOException {
        this.args = args;
        this.addOptions();
        
        try {
            this.commandLine = parser.parse(this.options, this.args);
            if (commandLine.hasOption("f")) {
                this.processFilesOption();
            }
            if (commandLine.hasOption("CC")) {
                this.getSavedDataMore();
            }
            
             
        } catch (ParseException ex) {
            logger.error("Parse error: " + ex);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("pcap-application", this.options);
            throw ex;
        }
    }

    
    private void addOptions() {
        this.filesOption();
        this.moreLengthOption();
    }
    
    
    private void filesOption() {
        options.addOption(Option.builder("f")
                .numberOfArgs(2)
                .argName("in-file out-file")
                .required()
                .desc("required two filenames")
                .build());
    }
    
    private void moreLengthOption() {
        options.addOption(Option.builder("CC")
               .numberOfArgs(1)
               .argName("packetLength")
               .desc("save packets wich lenght more than")
               .build());
    }
    
    private void processFilesOption() throws IOException {

        this.outFile = new File(commandLine.getOptionValues("f")[1]);
        this.inFile = new File(commandLine.getOptionValues("f")[0]);
        
        try {
            if(!this.outFile.exists()) {
                this.outFile.createNewFile();
            }
        } catch (IOException ex) {
            logger.error("File creating error: " + ex);
            throw ex;
        }
    }
    
    private void getSavedDataMore() {
        this.dataFilter.savedData = Integer.parseInt(commandLine.getOptionValue("CC"));
        this.dataFilter.sMoreCommand = true;
    }
    
    public File getInFile() {
        return this.inFile;
    }
    
    public File getOutFile() {
        return this.outFile;
    }
    
    public Filter getFilter() {
        return this.dataFilter;
    }
}
