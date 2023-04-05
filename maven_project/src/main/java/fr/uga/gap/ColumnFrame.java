package fr.uga.gap;

import java.util.ArrayList;

public class ColumnFrame {

    ArrayList<Integer> list;
    String name;
    
    public ColumnFrame(int[] l, String n) {
        name = n;
        list = new ArrayList<Integer>();

        for (int i = 0; i < l.length; i++) {
            list.add(l[i]);
        }
    }


}
