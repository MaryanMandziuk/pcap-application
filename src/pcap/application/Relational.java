package pcap.application;

/**
 * Representing relations
 * @author maryan
 */
public class Relational {
   
    private RelationInt f;
    
    interface RelationInt {
        boolean operation(int a, int b);   
    }

    public Relational(String rel) {
        switch(rel) {
            case ">" :
                f = (a, b) -> a > b;
                break;
            case "<" :
                f = (a, b) -> a < b;
                break;
            case ">=" :
                f = (a, b) -> a >= b;
                break;
            case "<=" :
                f = (a, b) -> a <= b;
                break;
            case "==" :
                f = (a, b) -> a == b;
                break;
            case "!=" :
                f = (a, b) -> a != b;
                break;
        }
    }
    
    public boolean compare(int a, int b) {
        return f.operation(a, b);
    }
    
}
