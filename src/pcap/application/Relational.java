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
            case "<=" :
                f = (a, b) -> a <= b;
            case "==" :
                f = (a, b) -> a == b;
            case "!=" :
                f = (a, b) -> a != b;
        }
    }
    
    public boolean compare(int a, int b) {
        return f.operation(a, b);
    }
    
}
