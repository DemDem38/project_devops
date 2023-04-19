package fr.uga.gap;

import java.util.ArrayList;
import java.util.HashMap;

public class Dataframe<L> {
    
    private HashMap<L, Series<Object>> mapSeries;
    private ArrayList<Integer> index;

    public Dataframe(L[] ind, Object[][] o) {
        mapSeries = new HashMap<>();
        index = new ArrayList<>();

        for (int i = 0; i < o.length; i++) {
            Series<Object> s = new Series<>(o[i]);
            mapSeries.put(ind[i], s);
            index.add(i);
        }

    }

    // Getters and Setters
    public HashMap<L, Series<Object>> getMapSeries() {
        return mapSeries;
    }

    public void setMapSeries(HashMap<L, Series<Object>> mapSeries) {
        this.mapSeries = mapSeries;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Integer> index) {
        this.index = index;
    }

    // Affichages
    // All DataFrame
    @Override
    public String toString() {
        String s = "";
        for (L name: mapSeries.keySet()) {
            s += name.toString() + "|";
        }
        for (int i = 0; i < index.size(); i++) {
            s += "\n" + index.get(i) + "|";
            for (L name: mapSeries.keySet()) {
                s += mapSeries.get(name).getList().get(i) + "|";
            }
        }
        return s +"\n";
    }

    // Only First n lines
    public String printFirstLines(int n) {
        String s = "";
        for (L name: mapSeries.keySet()) {
            s += name.toString() + "|";
        }
        for (int i = 0; i < n; i++) {
            s += "\n" + index.get(i) + "|";
            for (L name: mapSeries.keySet()) {
                s += mapSeries.get(name).getList().get(i) + "|";
            }
        }
        return s +"\n";
    }

    // Only Last n lines
    public String printLastLines(int n) {
        String s = "";
        for (L name: mapSeries.keySet()) {
            s += name.toString() + "|";
        }
        for (int i = n-1; i < index.size(); i++) {
            s += "\n" + index.get(i) + "|";
            for (L name: mapSeries.keySet()) {
                s += mapSeries.get(name).getList().get(i) + "|";
            }
        }
        return s +"\n";
    }

    
}
