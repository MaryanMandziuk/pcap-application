/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;

import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
    
    
    public PcapCommandLineParser(String[] args) {
        this.args = args;
        this.addOptions();
        
        try {
            this.commandLine = parser.parse(this.options, this.args);
            if (commandLine.hasOption("f")) {
                this.processFilesOption();
            }
             
        } catch (ParseException ex) {
            System.err.println("Fail, parse error: " + ex);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("pcap-application", this.options);
        }
    }

    
    private void addOptions() {
        this.filesOption();
    }
    
    
    private void filesOption() {
        options.addOption(Option.builder("f")
                .numberOfArgs(2)
                .argName("in-file out-file")
                .required()
                .desc("required two filenames")
                .build());
    }
    
    private void processFilesOption() {

        this.outFile = new File(commandLine.getOptionValues("f")[1]);
        this.inFile = new File(commandLine.getOptionValues("f")[0]);
        try {
            if(!this.outFile.exists()) {
                this.outFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("OutFile wasn't created" + e);
            exit(1);
        }
    }
    
    
    public File getInFile() {
        return this.inFile;
    }
    
    public File getOutFile() {
        return this.outFile;
    }
}
