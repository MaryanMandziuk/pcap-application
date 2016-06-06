package pcap.application;

/**
 * Retriving fields
 * @author maryan
 */
public class RefRecordFields {
    
    private RefInt f;
    
    interface RefInt {
        int get(RecordFields d);
    }
    
    public RefRecordFields(String rel) {
        switch(rel) {
            case "seconds" : 
                f = (d) -> d.getSec();
                break;
            case "microseconds" :
                f = (d) -> d.getMsec();
                break;
            case "saved" :
                f = (d) -> d.getSaved();
                break;
            case "actual" :
                f = (d) -> d.getMsec();                
                break;
        }
    }
    
    
    public int comp(RecordFields d) {
        return f.get(d);
    }
}
