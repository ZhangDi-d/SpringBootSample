package com.ryze;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xueLai on 2019/8/1.
 */
public class LambdaExpressions {

    public void strSortJava7(List list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public void strSortJava8(List list){
        Collections.sort(list,(String a,String b)->{return a.compareTo(b);});
    }

    public void strSortJava8Simple(List<String> list){
        Collections.sort(list,(String a,String b)->a.compareTo(b));
    }

    public void strSortJava8SimpleMore(List<String> list){
        list.sort((a,b)->a.compareTo(b));
    }

}
