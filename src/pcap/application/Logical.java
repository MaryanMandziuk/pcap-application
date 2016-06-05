/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;

/**
 *
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
