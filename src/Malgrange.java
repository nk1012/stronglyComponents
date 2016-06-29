import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

class Malgrange {
    //http://www.intuit.ru/studies/courses/1033/241/lecture/6212?page=2    <====инфо о замыканиии
    HashMap<Integer, ArrayList<Integer>> ListContiguity;//список смежности
    Integer sizeV;//количество вершин
    HashSet<Integer> current_vertexes;//вершины,которые еще не включены не в одну из компонент
    HashSet<Integer> directT;//прямая транзитивность для некоторой вершины х
    HashSet<Integer> reverseT;//обратная транзитивность для некоторой вершины x

    Malgrange(HashMap<Integer, ArrayList<Integer>> listContiguity) {
        sizeV = listContiguity.size();
        ListContiguity = listContiguity; //список смежности
        current_vertexes = new HashSet<>();
        directT = new HashSet<>();
        reverseT = new HashSet<>();
    }

    void setListContiguity(HashMap<Integer, ArrayList<Integer>> map) {
        ListContiguity = map;
        sizeV = ListContiguity.size();
    }

    private void initCurrent_Vertexes(int size) {//изначально все вершины заносятся во множество незадействованных вершин
        for (int i = 0; i < size; i++) {
            current_vertexes.add(i);
        }
    }

    private void DirectTransitiveСlosure(Integer v, Boolean used[]) {//dfs находит прямое транзитивное замыкание для вершины v и вершины этого замыкания зприсываются в множество directT
        //идет проход от вешины v ко всем достижимым из нее
        directT.clear();
        Arrays.fill(used, false);
        DirecTrans(v, used);
        for (Integer i = 0; i < used.length; i++)
            if (used[i]) directT.add(i);
    }

    private void DirecTrans(int v, Boolean used[]) {//реурсивный поиск в глубину для прямого транзитивного замыкания
        used[v] = true;
        for (Integer i : ListContiguity.get(v))
            if (!used[i] && current_vertexes.contains(i))
                DirecTrans(i, used);
    }

    private void ReverseTransitiveСlosure(Integer v, Boolean used[]) {//dfs находит обратное транзитивное замыкание для вершины v и вершины этого замыкания зприсываются в множество  reverseT
        //идет проход от всех вершин к вершине v
        reverseT.clear();
        Arrays.fill(used, false);
        reverseTranse(v, used);
        for (Integer i = 0; i < used.length; i++)
            if (used[i]) reverseT.add(i);
    }

    private void reverseTranse(int v, Boolean used[]) {//реурсивный поиск в глубину для обратного транзитивного замыкания
        used[v] = true;
        for (Integer i : ListContiguity.keySet()) {
            if (!used[i] && ListContiguity.get(i).contains(v) && current_vertexes.contains(i))
                reverseTranse(i, used);
        }
    }

    ObservableMap<Integer, ArrayList<Integer>> FindStronglyComponents() {//находим сильные компоненты графа
        initCurrent_Vertexes(sizeV);
        ObservableMap<Integer, ArrayList<Integer>> mapres = FXCollections.observableHashMap();
        while (!current_vertexes.isEmpty()) {
            Boolean used1[] = new Boolean[sizeV];
            Boolean used2[] = new Boolean[sizeV];
            Integer v = current_vertexes.iterator().next();
            DirectTransitiveСlosure(v, used1);
            ReverseTransitiveСlosure(v, used2);
            ArrayList<Integer> res = new ArrayList<>();//хранится текущяя компонета
            for (int i = 0; i < sizeV; i++) {
                if (used1[i] && used2[i]) res.add(i);
            }
            current_vertexes.removeAll(res);
            mapres.put(mapres.size() + 1, res);
        }
        return mapres;
    }
}
