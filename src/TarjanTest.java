import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TarjanTest {
    @Test
    public void testFindStronglyComponents() {
        HashMap<Integer, ArrayList<Integer>> sourceMap = Init.readFile("/home/xubuntu/src/ProgramEngineering/src/input.txt");
        Tarjan test = new Tarjan(sourceMap);
        ObservableMap<Integer, ArrayList<Integer>> resT = test.FindStronglyComponents();
        ObservableMap<Integer, ArrayList<Integer>> res = FXCollections.observableHashMap();

        Integer vertex = 1;
        List<Integer> list = Arrays.asList(2);
        res.put(vertex++, new ArrayList<>(list));

        list = Arrays.asList(3);
        res.put(vertex++, new ArrayList<>(list));

        list = Arrays.asList(4, 1, 0);
        res.put(vertex++, new ArrayList<>(list));

        list = Arrays.asList(5);
        res.put(vertex++, new ArrayList<>(list));

        Assert.assertEquals(res, resT);
    }
}
