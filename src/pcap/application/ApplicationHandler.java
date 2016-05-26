/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.taunova.importer.pcap.PCapDatalink;
import net.taunova.importer.pcap.PCapEventHandler;
import net.taunova.importer.pcap.PCapVersion;
/**
 *
 * @author maryan
 */
public class ApplicationHandler implements PCapEventHandler{
    
    public DataOutputStream outStream;
    
    public ApplicationHandler(File file) throws FileNotFoundException {
        this.outStream = new DataOutputStream(new FileOutputStream(file));
    }
    
    @Override
    public void onImportStart() {
        System.out.println("Import start");
    }

    @Override
    public void onImportFinish() {
        System.out.println("Import end");
    }

    @Override
    public void onImportFailed() {
        System.out.println("Import failed");
    }

    @Override
    public void handleInfo(PCapVersion version, PCapDatalink type) {
        final int BE_MAGIC = 0xa1b2c3d4;
        try {
            System.out.println("Info: " + version + " type: " + type);
            outStream.writeInt(BE_MAGIC);
            outStream.writeShort(version.getMajor());
            outStream.writeShort(version.getMinor());
            outStream.writeInt(type.getType());
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
    }

    @Override
    public void handleEntity(int saved, int actual, long timestamp, DataInputStream stream) throws IOException {
        System.out.println("  Packet # saved: " + saved + " timestamp: " + timestamp);
        
        byte[] b = new byte[saved];
        int seconds = (int) (timestamp / 1000000);    
        int microseconds = (int) (timestamp % 1000000); 

        stream.read(b);
        try {
            
            outStream.writeInt(seconds);
            outStream.writeInt(microseconds);
            outStream.writeInt(saved);
            outStream.writeInt(actual);
            outStream.write(b);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
}
