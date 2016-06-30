import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class Init {
    static ListView<String> createListView(ObservableList<String> list, int CoordinateX, int CoordinateY, int sizeX, int sizeY) {
        ListView<String> lv = new ListView<>(list);
        lv.setLayoutX(CoordinateX);
        lv.setLayoutY(CoordinateY);
        lv.setPrefSize(sizeX, sizeY);
        return lv;
    }

    static HashMap<Integer, ArrayList<Integer>> readFile(String path) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        try (Scanner sc = new Scanner(new FileReader(path))) {
            Integer nVertexes = Integer.parseInt(sc.nextLine());
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                Integer key = Integer.parseInt(str.substring(0, str.indexOf(':')).trim());//вершина
                String values = str.substring(str.indexOf(':') + 1);
                StringTokenizer st = new StringTokenizer(values.trim());
                ArrayList<Integer> list = new ArrayList<>();
                while (st.hasMoreTokens())
                    list.add(Integer.parseInt(st.nextToken().trim()));
                map.put(key, list);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    static FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("/home/xubuntu/src/ProgramEngineering/src"));
        fileChooser.setTitle("Select a file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt files", "*.txt"));
        return fileChooser;
    }

    static Label createLabel(String name, int x, int y) {
        Label label = new Label(name);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    static void mapToList(ObservableMap<Integer, ArrayList<Integer>> map, ObservableList<String> list)
    {
        list.clear();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            String s = "";
            for (Integer i : entry.getValue()) {
                s += i + ", ";
            }
            list.add(s.substring(0, s.lastIndexOf(',')));
        }
    }

    static Button createButton(String name, int x, int y) {
        Button btn = new Button();
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setText(name);
        return btn;
    }
}
