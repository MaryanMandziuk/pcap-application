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
import java.util.Arrays;
import net.taunova.importer.pcap.exception.PCapInvalidFormat;
import net.taunova.importer.pcap.exception.PCapSourceNotFound;
import org.apache.commons.cli.ParseException;

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
     * Create temporary pcap file with little endian data
     * @param name
     * @return File
     * @throws IOException
     */
    public File createTempPCapLittleEndian(String name) throws IOException {
        final File testPCap = tempFolder.newFile(name + PCAP_EX);
        DataOutputStream os = new DataOutputStream(new FileOutputStream(testPCap));
        try {
            long[] bin = {0xd4c3b2a102000400L, 0x0000000000000000L,
                0xffff000001000000L, 0x7934c242cd710800L,
                0x080000004b000000L, 0x1234ffff0000121aL};
            for (int i = 0; i < bin.length; i++) {
                os.writeLong(bin[i]);
            }

        } finally {
            os.close();
        }
        return testPCap;
    }
    
    /**
     * Create temporary pcap file with big endian data
     * @param name
     * @return File
     * @throws IOException
     */
    public File createTempPCapBigEndian(String name) throws IOException {
        final File testPCap = tempFolder.newFile(name + PCAP_EX);
        DataOutputStream os = new DataOutputStream(new FileOutputStream(testPCap));
        try {
            long[] bin = {0xa1b2c3d400020004L, 0x0000000000000000L,
                0x0000ffff00000001L, 0x42c23479000871cdL,
                0x000000080000004bL, 0x1234ffff0000121aL};
            for (int i = 0; i < bin.length; i++) {
                os.writeLong(bin[i]);
            }

        } finally {
            os.close();
        }
        return testPCap;
    }
    
    /**
     * Test PcapApplication with little endian on entrency.
     * @throws java.lang.Exception
     */
    @Test
    public void testPcapApplicationLittleEndian() throws Exception {
        System.out.println("Test resulting pcap file with little endian");
        String pcapName = "testPcap" + PCAP_EX;
        String resultingPcapName = "test" + PCAP_EX;
        
        File pcap = createTempPCapLittleEndian(pcapName);
        
        final File resultingPcap = tempFolder.newFile(resultingPcapName);
        
        String[] myArgs = {OPTION_F, pcap.getAbsolutePath(), resultingPcap.getAbsolutePath()};
        
        PcapCommandLineParser parser = new PcapCommandLineParser(myArgs);
        
        ApplicationHandler handler = new ApplicationHandler(parser.getOutFile());
        PCapImportTask importTask = PCapHelper.createImportTask(parser.getInFile(), handler);
        importTask.init();

        while( !importTask.isFinished() ) {
            importTask.processNext();
        }
        
        long[] expBin = {0xa1b2c3d400020004L, 0x0000000000000000L,
                0x0000ffff00000001L, 0x42c23479000871cdL,
                0x000000080000004bL, 0x1234ffff0000121aL};
        

        DataInputStream inputStream = new DataInputStream(new FileInputStream(resultingPcap));
        int count = inputStream.available()/8;
        long[] b = new long[count];
        
        for (int i = 0; i < count; i++ ) {
            b[i] = inputStream.readLong();
        }

        assertTrue(Arrays.equals(b, expBin));
        
    }
    
    /**
     * Test PcapApplication with big endian on entrency.
     * @throws Exception 
     */
    @Test
    public void testPcapApplicationBigEndian() throws Exception {
        System.out.println("Test resulting pcap file with big endian");
        String pcapName = "testPcap" + PCAP_EX;
        String resultingPcapName = "test" + PCAP_EX;
        
        File pcap = createTempPCapBigEndian(pcapName);
        
        final File resultingPcap = tempFolder.newFile(resultingPcapName);
        
        String[] myArgs = {OPTION_F, pcap.getAbsolutePath(), resultingPcap.getAbsolutePath()};
        
        PcapCommandLineParser parser = new PcapCommandLineParser(myArgs);
        
        ApplicationHandler handler = new ApplicationHandler(parser.getOutFile());
        PCapImportTask importTask = PCapHelper.createImportTask(parser.getInFile(), handler);
        importTask.init();

        while( !importTask.isFinished() ) {
            importTask.processNext();
        }
        
        long[] expBin = {0xa1b2c3d400020004L, 0x0000000000000000L,
                0x0000ffff00000001L, 0x42c23479000871cdL,
                0x000000080000004bL, 0x1234ffff0000121aL};
        

        DataInputStream inputStream = new DataInputStream(new FileInputStream(resultingPcap));
        int count = inputStream.available()/8;
        long[] b = new long[count];
        
        for (int i = 0; i < count; i++ ) {
            b[i] = inputStream.readLong();
            System.out.println(Long.toHexString(b[i]));
        }

        assertTrue(Arrays.equals(b, expBin));

    }
    
    /**
     * Test application with incorrect comnand
     * @throws IOException
     * @throws ParseException 
     */
    @Test(expected = ParseException.class)
    public void testCommandLine() throws IOException, ParseException {
        System.out.println("testing command line option -f");
        String pcapName = "testPcap" + PCAP_EX;
        String resultingPcapName = "test" + PCAP_EX;
        
        File pcap = createTempPCapLittleEndian(pcapName);
        
        final File resultingPcap = tempFolder.newFile(resultingPcapName);
        
        String[] myArgs = {"f", pcap.getAbsolutePath(), resultingPcap.getAbsolutePath()};
        
        new PcapCommandLineParser(myArgs);
    }
    
    /**
     * Test command line with missed argument
     * @throws IOException
     * @throws ParseException 
     */
    @Test(expected = ParseException.class)
    public void testCommandLineArgument() throws IOException, ParseException {
        System.out.println("testing command line with missed argument");
        String pcapName = "testPcap" + PCAP_EX;
        
        File pcap = createTempPCapLittleEndian(pcapName);
        
        String[] myArgs = {OPTION_F, pcap.getAbsolutePath(), };
        
        new PcapCommandLineParser(myArgs);
    }
    
    /**
     * Test for creating resulting file
     * @throws IOException
     * @throws ParseException
     * @throws PCapSourceNotFound
     * @throws PCapInvalidFormat 
     */
    @Test
    public void testResultingPcap() throws IOException, ParseException, PCapSourceNotFound, PCapInvalidFormat {
        System.out.println("testing for creating resulting file");
        String pcapName = "testPcap" + PCAP_EX;
        String resultingPcapName = "my" + PCAP_EX;
        
        File pcap = createTempPCapLittleEndian(pcapName);
        
        String[] myArgs = {OPTION_F, pcap.getAbsolutePath(), resultingPcapName};
        
        new PcapCommandLineParser(myArgs);
        
        assertTrue(new File(resultingPcapName).exists());
    }
    
}
