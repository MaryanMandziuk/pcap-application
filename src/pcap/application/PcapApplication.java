/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;


import net.taunova.importer.PCapImportTask;
import net.taunova.importer.pcap.PCapHelper;

/**
 *
 * @author maryan
 */
public class PcapApplication {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        System.out.println("PCap application"); 
       
        String[] myArgs = {"-f","iperf-mptcp-0-0.pcap", "my.pcap"};
        PcapCommandLineParser parser = new PcapCommandLineParser(myArgs);
         
        
        ApplicationHandler handler = new ApplicationHandler(parser.getOutFile());
        PCapImportTask importTask = PCapHelper.createImportTask(parser.getInFile(), handler);
        importTask.init();

        while(!importTask.isFinished()) {
            importTask.processNext();
        }
        
        handler.showSavedPacketsTime();
    }
    
}
