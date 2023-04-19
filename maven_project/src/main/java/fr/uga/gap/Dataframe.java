package fr.uga.gap;

import java.util.ArrayList;
import java.util.HashMap;

public class Dataframe<L> {
    
    HashMap<L, Series<Object>> mapSeries;
    ArrayList<Integer> index;

    public Dataframe(L[] ind, Object[][] o) {
        mapSeries = new HashMap<>();
        index = new ArrayList<>();

        for (int i = 0; i < o.length; i++) {
            Series<Object> s = new Series<>(o[i]);
            mapSeries.put(ind[i], s);
            index.add(i);
        }

        // for (String elem : nl) {
        //     nameLines.add((elem));
        // }

        // for (int i = 0; i < nc.length; i++) {
        //     columns.add(new Series(t[i], nc[i]));
        // }
    }
}
