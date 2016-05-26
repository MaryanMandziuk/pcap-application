/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;
import java.io.File;
/**
 *
 * @author maryan
 */
public class PcapInOutFiles {
    private final File inFile;
    private final File outFile;
    
    public PcapInOutFiles(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }
    
    public File getInFile() {
        return this.inFile;
    }
    
    public File getOutFile() {
        return this.outFile;
    }
}
