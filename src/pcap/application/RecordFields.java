package pcap.application;

/**
 * Represent record fields
 * @author maryan
 */
public class RecordFields {
    private final int seconds;
    private final int microseconds;
    private final int saved;
    private final int actual;
    

    public RecordFields(int sec, int msec, int saved, int actual) {
        this.seconds = sec;
        this.microseconds = msec;        
        this.saved = saved;
        this.actual = actual;
    }
    
    public int getMsec() {
        return this.microseconds;
    }
    
    public int getSec() {
        return this.seconds;
    }
    
    public int getSaved() {
        return this.saved;
    }
    
    public int getAcutal() {
        return this.actual;
    }
    
}
