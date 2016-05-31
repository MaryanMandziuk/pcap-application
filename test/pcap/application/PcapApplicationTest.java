/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import net.taunova.importer.PCapImportTask;
import net.taunova.importer.pcap.PCapHelper;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.io.DataInputStream;
import java.io.FileInputStream;
/**
 *
 * @author maryan
 */
public class PcapApplicationTest {
    
    static final String PCAP_EX = ".pcap";
    static final String OPTION_F = "-f";
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    
    /**
     * Create temporary pcap file with data
     * @param name
     * @return
     * @throws IOException
     */
    public File createTempPCap(String name) throws IOException {
        final File testPCap = tempFolder.newFile(name + PCAP_EX);
        DataOutputStream os = new DataOutputStream(new FileOutputStream(testPCap));
        try {
            long[] bin = {0xd4c3b2a102000400L, 0x0000000000000000L,
                0xffff000001000000L, 0x7934c242cd710800L,
                0x4b0000004b000000L,};
            for (int i = 0; i < bin.length; i++) {
                os.writeLong(bin[i]);
            }

        } finally {
            os.close();
        }
        return testPCap;
    }
    
    /**
     * Test of main method, of class PcapApplication.
     * @throws java.lang.Exception
     */
    @Test
    public void testPcapApplication() throws Exception {
        System.out.println("main");
        String pcapName = "testPcap.pcap";
        String resultingPcap = "my.pcap";
        File pcap = createTempPCap(pcapName);
        
        String[] myArgs = {"-f", pcap.getAbsolutePath(), resultingPcap};
        
        PcapCommandLineParser parser = new PcapCommandLineParser(myArgs);
        
        ApplicationHandler handler = new ApplicationHandler(parser.getOutFile());
        PCapImportTask importTask = PCapHelper.createImportTask(parser.getInFile(), handler);
        importTask.init();

        while( !importTask.isFinished() ) {
            importTask.processNext();
        }

        DataInputStream inputStream = new DataInputStream(new FileInputStream(resultingPcap));
        long[] bin = {0xd4c3b2a102000400L, 0x0000000000000000L,
                0xffff000001000000L, 0x7934c242cd710800L,
                0x4b0000004b000000L,};
        byte[] b = new byte[inputStream.available()];
        int coutn = inputStream.available();
        inputStream.read(b);
        System.out.println(coutn);
        
        
        for (int j = 0; j < coutn; j++)  {
            System.out.print((b[j]));
        }
    }
    
}
