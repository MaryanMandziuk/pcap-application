package pcap.application;

/**
 * Representing logical operators
 * @author maryan
 */
public class Logical {
    
    private LogicalInt f;
    
    interface LogicalInt {
        boolean operation(boolean a, boolean b);
    }
    
    public Logical(String rel) {
        switch(rel) {
            case "&&": 
                f = (a, b) -> a && b;
                break;
            case "||":
                f = (a, b) -> a || b;
                break;       
        }    
    }
    
    public boolean compare(boolean a, boolean b) {
        return f.operation(a, b);
    }
}
