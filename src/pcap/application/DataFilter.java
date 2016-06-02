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
public class DataFilter {
    
    int savedData;
    boolean sLessCommand;
    boolean sMoreCommand;
    
  
    
    
    public boolean checkData(int saved, int actual, long timestamp) {
        
        if (this.sMoreCommand) {
            return this.savedData > saved;
        }
        
        else if (this.sLessCommand) {
            return this.savedData < saved;     
        } 
        
        return true;  
    }
}
