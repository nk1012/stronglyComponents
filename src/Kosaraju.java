import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class Kosaraju {
    HashMap<Integer, ArrayList<Integer>> adjacencyList;
    HashMap<Integer, ArrayList<Integer>> reverseAdjacencyList;
    ArrayList<Integer> reverseOrder;
    Integer nVertexes;

    Kosaraju(HashMap<Integer, ArrayList<Integer>> map) {
        adjacencyList = map;
        nVertexes = adjacencyList.size();
        reverseAdjacencyList = invertGraph();
        reverseOrder = new ArrayList<>();
    }

    void setAdjacencyList(HashMap<Integer, ArrayList<Integer>> map) {
        adjacencyList = map;
        nVertexes = adjacencyList.size();
        reverseAdjacencyList = invertGraph();
    }

    private void addInMap(HashMap<Integer, ArrayList<Integer>> map, Integer key, Integer val)
    {
        ArrayList<Integer> list = map.get(key);
        if (list != null) {
            list.add(val);
        } else {
            list = new ArrayList<>();
            list.add(val);
            map.put(key, list);
        }
    }

    private HashMap<Integer, ArrayList<Integer>> invertGraph()
    {
        HashMap<Integer, ArrayList<Integer>> invertlist = new HashMap<>();
        for (Integer key : adjacencyList.keySet()) {
            for (Integer val : adjacencyList.get(key))
                addInMap(invertlist, val, key);
        }
        return invertlist;
    }


    private void dfsInverted()
    {
        Boolean used[] = new Boolean[nVertexes];
        reverseOrder.clear();
        Arrays.fill(used, false);
        for (int i = 0; i < nVertexes; i++)
            dfs(i, used);
    }

    private void dfs(Integer v, Boolean used[])
    {
        used[v] = true;
        for (Integer i : reverseAdjacencyList.get(v)) {
            if (!used[i]) {
                dfs(i, used);
            }
        }
        if (!reverseOrder.contains(v)) {
            reverseOrder.add(v);
        }
    }


    private void dfs_source(int v, Boolean used[], ArrayList<Integer> res)
    {
        used[v] = true;
        for (Integer i : adjacencyList.get(v))
            if (!used[i]) {
                dfs_source(i, used, res);
            }
        if (!res.contains(v)) res.add(v);
    }

    ObservableMap<Integer, ArrayList<Integer>> FindStronglyComponents()
    {
        ObservableMap<Integer, ArrayList<Integer>> resMap = FXCollections.observableHashMap();
        dfsInverted();
        Boolean used[] = new Boolean[nVertexes];
        Arrays.fill(used, false);
        for (Integer i = reverseOrder.size() - 1; i >= 0; i--) {
            if (!used[reverseOrder.get(i)]) {
                ArrayList<Integer> res = new ArrayList<>();
                dfs_source(reverseOrder.get(i), used, res);
                resMap.put(resMap.size() + 1, res);
            }
        }
        return resMap;
    }
}
