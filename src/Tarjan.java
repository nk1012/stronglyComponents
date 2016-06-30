import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class Tarjan {
    ArrayDeque<Integer> stack;
    HashMap<Integer, ArrayList<Integer>> adjacencyList;
    Integer nVertexes;
    Integer lowlinks[];
    Integer indeces[];
    Integer id;

    Tarjan(HashMap<Integer, ArrayList<Integer>> map) {
        id = 0;
        adjacencyList = map;
        nVertexes = map.size();
        lowlinks = new Integer[nVertexes];
        indeces = new Integer[nVertexes];
        stack = new ArrayDeque<>();
    }

    void setAdjacencyList(HashMap<Integer, ArrayList<Integer>> map) {
        adjacencyList = map;
        nVertexes = adjacencyList.size();
    }

    ObservableMap<Integer, ArrayList<Integer>> FindStronglyComponents()
    {
        id = 0;
        Arrays.fill(indeces, 0);
        Arrays.fill(lowlinks, nVertexes);
        ObservableMap<Integer, ArrayList<Integer>> resmap = FXCollections.observableHashMap();
        Boolean used[] = new Boolean[nVertexes];
        Arrays.fill(used, false);
        for (Integer i = 0; i < nVertexes; i++)
            if (!used[i])
                dfs(i, used, resmap);
        return resmap;
    }

    private void dfs(Integer v, Boolean used[], ObservableMap<Integer, ArrayList<Integer>> resMap)
    {
        used[v] = true;
        lowlinks[v] = id;
        indeces[v] = id++;
        stack.push(v);
        for (Integer val : adjacencyList.get(v)) {
            if (!used[val]) {
                dfs(val, used, resMap);
                lowlinks[v] = Integer.min(lowlinks[v], lowlinks[val]);
            } else if (stack.contains(val)) {
                lowlinks[v] = Integer.min(lowlinks[v], indeces[val]);
            }
        }
        ArrayList<Integer> resSet = new ArrayList<>();
        if (indeces[v] == lowlinks[v]) {
            while (stack.peek() != v) {
                resSet.add(stack.pop());
            }
            resSet.add(stack.pop());
            resMap.put(resMap.size() + 1, resSet);
        }
    }
}
