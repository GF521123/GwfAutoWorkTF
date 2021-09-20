package com.gwf.utils.Imp;
import java.util.Comparator;
/**
 * @author gwf
 * @version 1.0
 * @date 2021/9/14 14:18
 */

public class SortByLengthComparator implements Comparator<String> {

    @Override
    public int compare(String var1, String var2) {
        if (var1.length() > var2.length()) {
            return 1;
        } else if (var1.length() == var2.length()) {
            return 0;
        } else {
            return -1;
        }
    }
}