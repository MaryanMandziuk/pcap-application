package pcap.application;

import java.util.ArrayList;
import java.util.List;



/**
 * Represent basic functionality for filter
 * @author maryan
 */
public class Filter {
    
    private final String[] s = {"saved", "==", "61", "||", "saved", "!=", "74", "&&", "saved", "<=", "100"};
    private final List<Condition> arrCon = new ArrayList<>();
    private final List<Logical> arrLog = new ArrayList<>();  
  
    public Filter() {   
        for(int i = 0; i < s.length; i+=4) {
            arrCon.add(new Condition(s[i], s[i+1], s[i+2]));
            if (i!=s.length-3)  {
                arrLog.add(new Logical(s[i+3]));
            }
        }
    }
    
    public boolean compare(RecordFields d) {
        boolean bool=false;
        if (arrLog.isEmpty()) {
            bool = arrCon.get(0).comp(d);
        } else {
            for(int i = 0; i < arrLog.size(); i++) {
                if (i == 0) {
                    bool = arrLog.get(i).compare(arrCon.get(i).comp(d), arrCon.get(i + 1).comp(d));
                } else {
                    bool = arrLog.get(i).compare(bool, arrCon.get(i + 1).comp(d));
                }
            }
        }
        return bool;
    }
}
