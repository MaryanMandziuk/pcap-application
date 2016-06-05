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
            case "actual" :
                f = (d) -> d.getMsec();                
                
        }
    }
    
    
    public int comp(RecordFields d) {
        return f.get(d);
    }
}
