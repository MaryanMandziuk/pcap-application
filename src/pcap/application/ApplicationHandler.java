package pcap.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.taunova.importer.pcap.PCapDatalink;
import net.taunova.importer.pcap.PCapEventHandler;
import net.taunova.importer.pcap.PCapInfo;
import net.taunova.importer.pcap.PCapConst;

/**
 * Pcap application handler
 * @author maryan
 */
public class ApplicationHandler implements PCapEventHandler {
    
    public DataOutputStream outStream;
    
    private long time;
    private int packetCount;
    private final Logger logger = LoggerFactory.getLogger(ApplicationHandler.class);
    private final Filter filter;
    
    public ApplicationHandler(File file, Filter dataFilter) throws FileNotFoundException {
        this.outStream = new DataOutputStream(new FileOutputStream(file));
        this.filter = dataFilter;
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
    public void handleInfo(PCapInfo info, PCapDatalink type) {
        System.out.println("Info: " + info + " type: " + type);
        try {
            outStream.writeInt(PCapConst.BE_MAGIC);
            outStream.writeShort(info.getMajor());
            outStream.writeShort(info.getMinor());
            outStream.writeInt(info.getZone());
            outStream.writeInt(info.getAccuracy());
            outStream.writeInt(info.getSnapshotLenght());
            outStream.writeInt(type.getType());
        } catch (IOException ex) {
            logger.error("Global header - write error: " + ex);
        }
    }

    @Override
    public void handleEntity(int saved, int actual, long timestamp, DataInputStream stream) throws IOException {
        int seconds = (int) (timestamp / 1000000);    
        int microseconds = (int) (timestamp % 1000000); 
        RecordFields record = new RecordFields(seconds, microseconds, saved, actual);
        
        if (filter.compare(record)) {
            System.out.println("  Packet # saved: " + saved + " timestamp: " + timestamp);
            byte[] packet = new byte[saved];
            try {
                stream.read(packet);
            } catch ( IOException ex ) {
                logger.error("Packet read error: " + ex);
            }

            try {
                long startTime = System.currentTimeMillis();
                outStream.writeInt(seconds);
                outStream.writeInt(microseconds);
                outStream.writeInt(saved);
                outStream.writeInt(actual);
                outStream.write(packet);
                long endTime = System.currentTimeMillis();
                time += (endTime - startTime);
                packetCount++;
            } catch (IOException ex) {
                logger.error("Record header - write error: " + ex);
            } 
        } else {
            stream.skip(saved);
        }
    }
    
    public void showSavedPacketsTime() {
        System.out.println("Number of packets: " + packetCount + " saved for: " + time + " ms");
    }
    
}
