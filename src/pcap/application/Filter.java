/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcap.application;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author maryan
 */
public class Filter {
    
    String[] s = {"a", ">", "1", "&&", "b", "<", "11"};
    boolean bbo;
    List<Condition> arrCon = new ArrayList<>();
    List<Logical> arrLog = new ArrayList<>();
//    private MyInter ff;
    
//    interface MyInter {
//        boolean op(RecordFields d);
//    }
    
  
    public Filter(){
        
        for(int i = 0; i < s.length; i+=4) {
            arrCon.add(new Condition(s[i], s[i+1], s[i+2]));
            if(i!=s.length-3){
                arrLog.add(new Logical(s[i+3]));
            }
        }
       
//        System.out.println("Show it once");
//        Ref f = new Ref(s[0]);
//        Relation obj1 = new Relation(s[1]);
//        
//        Ref f2 = new Ref(s[4]);
//        Relation obj2 = new Relation(s[5]);
//        
//        Logic log1 = new Logic(s[3]);
//        int i1 = Integer.parseInt(s[2]);
//        int i2 = Integer.parseInt(s[6]);
//        
//        
//        ff= (Data d) ->  log1.comp(obj1.comp(f.comp(d), i1), obj2.comp(f2.comp(d), i2));
    }
    
    public boolean compare(RecordFields d) {
        boolean bool=false;
         for(int i = 0; i < arrLog.size(); i++) {
            
            if (i == 0) {
                bool = arrLog.get(i).compare(arrCon.get(i).comp(d), arrCon.get(i+1).comp(d));
            } else {
                bool = arrLog.get(i).compare(bool, arrCon.get(i+1).comp(d));
            }
        }
         return bool;
    }
}
