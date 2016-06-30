import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StrongComponent extends Application {
    Tarjan tarjan;
    Kosaraju kosaraju;
    Malgrange malgrange;
    File inFile;
    File outFile;
    HashMap<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();
    Integer nVertexes;

    Button btn_txt = Init.createButton("Select a file", 20, 10);
    Button btn_txtSave = Init.createButton("Save results", 280, 300);
    Button btn_execute = Init.createButton("Get results", 20, 50);
    FileChooser fileChooserTxt = Init.createFileChooser();
    FileChooser fileSaverTxt = Init.createFileChooser();

    Label labelMalgrange = Init.createLabel("Malgrange", 100, 107);
    Label labelKosaraju = Init.createLabel("Kosaraju", 300, 107);
    Label LabelTarjan = Init.createLabel("Tarjan", 500, 107);
    Label messageInfo = Init.createLabel("Connected component list", 250, 50);
    Label notSelectedError = Init.createLabel("Please select an input file", 180, 54);

    ObservableList<String> TarjanList = FXCollections.observableArrayList();
    ObservableList<String> KosarajuList = FXCollections.observableArrayList();
    ObservableList<String> MalgrangeList = FXCollections.observableArrayList();

    ListView<String> MalgrangeListView = Init.createListView(MalgrangeList, 100, 130, 100, 100);
    ListView<String> KosarajuListView = Init.createListView(KosarajuList, 300, 130, 100, 100);
    ListView<String> TarjaneListView = Init.createListView(TarjanList, 500, 130, 100, 100);

    public static void main(String args[]) {
        launch();
    }

    void readFile() {
        try (Scanner sc = new Scanner(new FileReader(inFile))) {
            adjacencyList.clear();
            nVertexes = Integer.parseInt(sc.nextLine());
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                Integer key = Integer.parseInt(str.substring(0, str.indexOf(':')).trim());//вершина
                String values = str.substring(str.indexOf(':') + 1);
                StringTokenizer st = new StringTokenizer(values.trim());
                ArrayList<Integer> list = new ArrayList<>();
                while (st.hasMoreTokens()) {
                    list.add(Integer.parseInt(st.nextToken().trim()));
                }
                adjacencyList.put(key, list);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void writeFile(File f) {
        try (FileOutputStream fos = new FileOutputStream(f)) {
            String str = "Result format: 'component # : vertexes' \n" + "Malgrange algorithm: \n";
            fos.write(str.getBytes());
            for (int i = 0; i < MalgrangeList.size(); i++) {
                str = (i + 1) + " : " + MalgrangeList.get(i) + "\n";
                fos.write(str.getBytes());
            }
            str = "Kosaraju algorithm: \n";
            fos.write(str.getBytes());
            for (int i = 0; i < KosarajuList.size(); i++) {
                str = (i + 1) + " : " + KosarajuList.get(i) + "\n";
                fos.write(str.getBytes());
            }
            str = "Tarjan algorithm: \n";
            fos.write(str.getBytes());

            for (int i = 0; i < TarjanList.size(); i++) {
                str = (i + 1) + " : " + TarjanList.get(i) + "\n";
                fos.write(str.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 700, 400);
        btn_txt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inFile = fileChooserTxt.showOpenDialog(primaryStage);
                if (inFile != null) {
                    outFile = null;
                    root.getChildren().removeAll(labelMalgrange, MalgrangeListView, labelKosaraju, KosarajuListView);
                    root.getChildren().removeAll(LabelTarjan, TarjaneListView, notSelectedError, messageInfo, btn_txtSave);
                    readFile();
                    String filename = inFile.getName();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText(filename + " loaded");
                    alert.showAndWait();
                }
            }
        });
        btn_txtSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                outFile = fileSaverTxt.showSaveDialog(primaryStage);
                if (outFile != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText(null);
                    alert.setContentText("Result written in  " + outFile.getName());
                    alert.showAndWait();
                    writeFile(outFile);
                }
                root.getChildren().addAll(btn_txt, btn_execute);
                primaryStage.setHeight(120);
                primaryStage.setWidth(150);
            }
        });
        btn_execute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setHeight(400);
                primaryStage.setWidth(650);
                root.getChildren().removeAll(btn_txt, btn_execute);
                if (inFile != null) {
                    root.getChildren().remove(notSelectedError);
                    if (malgrange != null) {
                        malgrange.setAdjacencyList(adjacencyList);
                        kosaraju.setAdjacencyList(adjacencyList);
                        tarjan.setAdjacencyList(adjacencyList);
                    } else {
                        malgrange = new Malgrange(adjacencyList);
                        kosaraju = new Kosaraju(adjacencyList);
                        tarjan = new Tarjan(adjacencyList);
                    }
                    Init.mapToList(malgrange.findStronglyComponents(), MalgrangeList);
                    Init.mapToList(kosaraju.FindStronglyComponents(), KosarajuList);
                    Init.mapToList(tarjan.FindStronglyComponents(), TarjanList);
                    if (!root.getChildren().contains(MalgrangeListView))
                        root.getChildren().addAll(MalgrangeListView, KosarajuListView, TarjaneListView, labelMalgrange, labelKosaraju, LabelTarjan, messageInfo, btn_txtSave);
                } else {
                    if (!root.getChildren().contains(notSelectedError))
                        root.getChildren().add(notSelectedError);
                }
            }
        });
        primaryStage.setTitle("Find strongly connected components");
        primaryStage.setHeight(120);
        primaryStage.setWidth(150);
        primaryStage.setScene(scene);
        root.getChildren().addAll(btn_txt, btn_execute);
        primaryStage.show();
    }
}
