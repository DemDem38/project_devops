package fr.uga.gap;

import java.util.ArrayList;

public class Series<T> {

    private ArrayList<T> list;
    private String typeArray;

    public Series(T[] l) {
        // Initialize array list
        list = new ArrayList<>();
        typeArray = "";
        initializeSeries(l);
    }

    public Series(T[] l, String type) {
        // Initialize array list
        list = new ArrayList<>();
        typeArray = type;
        initializeSeries(l);
    }

    private void initializeSeries(T[] l) {
        for (int i = 0; i < l.length; i++) {
            list.add(l[i]);
        }
    }

    // Getters and Setters
    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public String getTypeArray() { return typeArray; }

    public void setTypeArray(String typeArray) {
        this.typeArray = typeArray;
    }
}
