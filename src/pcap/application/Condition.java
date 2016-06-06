package pcap.application;

/**
 * Representing condition
 * @author maryan
 */
public class Condition {
    
    private final ConditionInt f;
    
    interface ConditionInt {
        boolean get(RecordFields d);
    }
    
    public Condition(String rel, String rel1, String rel2) {
        f = (RecordFields d) ->  new Relational(rel1).compare(new RefRecordFields(rel).comp(d), 
                Integer.parseInt(rel2));
    }
      
    public boolean comp(RecordFields d) {
        return f.get(d);
    }
}
