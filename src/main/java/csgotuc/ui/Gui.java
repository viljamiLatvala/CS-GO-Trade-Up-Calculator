/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.ui;

import csgotuc.dao.FileItemDao;
import csgotuc.dao.ItemDao;
import csgotuc.domain.Item;
import csgotuc.domain.ItemService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author latvavil
 */
public class Gui extends Application {

    private ItemService itemService;
    private ObservableList<PieChart.Data> pieChartData;

    @Override
    public void init() {
        try {
            Path path = Paths.get(".", "/src/main/resources/object_data.csv");
            ItemDao itemDao = new FileItemDao(path.normalize().toString());
            itemService = new ItemService(itemDao);
        } catch (Exception e) {
            System.out.println(e);
        }

        pieChartData = FXCollections.observableArrayList();
    }

    @Override
    public void start(Stage primaryStage) {
        //Input-esittely
        TilePane tilePane = new TilePane();
        tilePane.setVgap(4);
        tilePane.setHgap(4);
        tilePane.setPrefColumns(4);
        for (int i = 0; i < 10; i++) {
            tilePane.getChildren().add(new Rectangle(100, 100));
        }

        //Output-esittely
        PieChart pieChart = new PieChart();

        //Input-valinta
        ListView<String> list = new ListView<String>();

        ObservableMap<String, Item> map = FXCollections.observableHashMap();
        for (Item item : itemService.getAll()) {
            map.put(item.getName(), item);
        }

        list.getItems().setAll(map.keySet());
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    String clicked = list.getSelectionModel().getSelectedItem();
                    try {
                        itemService.addToInput(map.get(clicked));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    formInputLoadout(tilePane);
                    formInputOptionList(list, map.get(clicked).getGrade());
                    formChart();
                    pieChart.setData(pieChartData);
                }
            }
        });

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(8);
        hbox.getChildren().add(list);
        hbox.getChildren().add(tilePane);
        hbox.getChildren().add(pieChart);

        primaryStage.setScene(new Scene(hbox));
        primaryStage.setTitle("CSGO Trade-Up Calculator");
        primaryStage.show();
    }

    public void formChart() {
        pieChartData = FXCollections.observableArrayList();
        List<Item> outcomePool = this.itemService.calculateTradeUp();
        int poolSize = outcomePool.size();
        Map<Item, Integer> outcomeDist = new HashMap<>();

        for (int i = 0; i < poolSize; i++) {
            Item curItem = outcomePool.get(i);
            if (outcomeDist.containsKey(curItem)) {
                outcomeDist.put(curItem, outcomeDist.get(curItem) + 1);
            } else {
                outcomeDist.put(curItem, 1);
            }
        }
        for (Item item : outcomeDist.keySet()) {
            pieChartData.add(new PieChart.Data(item.getName(), outcomeDist.get(item)));
        }

    }

    public void formInputLoadout(TilePane tilePane) {
        for (int i = 0; i < this.itemService.getInput().size(); i++) {
            tilePane.getChildren().set(i, new Rectangle(100, 100, Color.GREEN));
        }
    }
    
    public void formInputOptionList(ListView list, int grade) {
        ObservableMap<String, Item> map = FXCollections.observableHashMap();
        for (Item item : itemService.getByGrade(grade)) {
            map.put(item.getName(), item);
        }
        list.getItems().setAll(map.keySet());
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
