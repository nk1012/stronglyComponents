import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

class Malgrange {
    HashMap<Integer, ArrayList<Integer>> adjacencyList;
    Integer nVertexes;
    HashSet<Integer> current_vertexes;
    HashSet<Integer> directT;
    HashSet<Integer> reverseT;

    Malgrange(HashMap<Integer, ArrayList<Integer>> adjacencyList) {
        nVertexes = adjacencyList.size();
        this.adjacencyList = adjacencyList;
        current_vertexes = new HashSet<>();
        directT = new HashSet<>();
        reverseT = new HashSet<>();
    }

    void setAdjacencyList(HashMap<Integer, ArrayList<Integer>> map) {
        adjacencyList = map;
        nVertexes = adjacencyList.size();
    }

    private void initCurrent_Vertexes(int size) {
        for (int i = 0; i < size; i++) {
            current_vertexes.add(i);
        }
    }

    private void directTransitiveСlosure(Integer v, Boolean used[]) {
        directT.clear();
        Arrays.fill(used, false);
        directTrans(v, used);
        for (Integer i = 0; i < used.length; i++) {
            if (used[i]) directT.add(i);
        }
    }

    private void directTrans(int v, Boolean used[]) {
        used[v] = true;
        for (Integer i : adjacencyList.get(v))
            if (!used[i] && current_vertexes.contains(i))
                directTrans(i, used);
    }

    private void reverseTransitiveСlosure(Integer v, Boolean used[]) {
        reverseT.clear();
        Arrays.fill(used, false);
        reverseTranse(v, used);
        for (Integer i = 0; i < used.length; i++)
            if (used[i]) reverseT.add(i);
    }

    private void reverseTranse(int v, Boolean used[]) {
        used[v] = true;
        for (Integer i : adjacencyList.keySet()) {
            if (!used[i] && adjacencyList.get(i).contains(v) && current_vertexes.contains(i))
                reverseTranse(i, used);
        }
    }

    ObservableMap<Integer, ArrayList<Integer>> findStronglyComponents() {
        initCurrent_Vertexes(nVertexes);
        ObservableMap<Integer, ArrayList<Integer>> mapRes = FXCollections.observableHashMap();
        while (!current_vertexes.isEmpty()) {
            Boolean used1[] = new Boolean[nVertexes];
            Boolean used2[] = new Boolean[nVertexes];
            Integer v = current_vertexes.iterator().next();
            directTransitiveСlosure(v, used1);
            reverseTransitiveСlosure(v, used2);
            ArrayList<Integer> res = new ArrayList<>();
            for (int i = 0; i < nVertexes; i++) {
                if (used1[i] && used2[i]) res.add(i);
            }
            current_vertexes.removeAll(res);
            mapRes.put(mapRes.size() + 1, res);
        }
        return mapRes;
    }
}
