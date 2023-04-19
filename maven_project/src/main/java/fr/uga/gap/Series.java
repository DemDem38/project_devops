package fr.uga.gap;

import java.util.ArrayList;

public class Series<T> {

    ArrayList<T> list;

    
    public Series(T[] l) {
        list = new ArrayList<T>();

        for (int i = 0; i < l.length; i++) {
            list.add(l[i]);
        }
    }
}
