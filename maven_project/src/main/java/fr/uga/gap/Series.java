package fr.uga.gap;

import java.util.HashMap;

public class Series {

    private HashMap<Object, Object> mapData;
    private String typeArray;

    public Series(Object[] l) {
        // Initialize array list
        mapData = new HashMap<>();
        typeArray = null;

        initializeSeries(l);
    }

    public Series(Object[] l, Object[] ind) {
        // Initialize array list
        mapData = new HashMap<>();
        typeArray = null;

        initializeSeries(l, ind);
    }

    public Series(Object[] l, String type) {
        // Initialize array list
        mapData = new HashMap<>();
        typeArray = type;

        initializeSeries(l);
    }

    public Series(Object[] l, Object[] ind, String type) {
        // Initialize array list
        mapData = new HashMap<>();
        typeArray = type;
        initializeSeries(l, ind);
    }

    private void initializeSeries(Object[] l) {
        // If the user don't give an array of index.
        // The index are 0, 1, 2, etc...
        for (int i = 0; i < l.length; i++) {
            mapData.put(i, l[i]);
        }
    }

    private void initializeSeries(Object[] l, Object[] ind) {
        for (int i = 0; i < l.length; i++) {
            mapData.put(ind[i], l[i]);
        }
    }

    public Object getData(Object label) {
        return mapData.get(label);
    }


    // Getters
    public HashMap<Object, Object> getMapData() {
        return mapData;
    }

    public String getTypeArray() { return typeArray; }
}
