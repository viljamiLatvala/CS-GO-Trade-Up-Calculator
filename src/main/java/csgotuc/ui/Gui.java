/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.ui;

import csgotuc.dao.Database;
import csgotuc.dao.ItemDao;
import csgotuc.dao.ItemFetchingService;
import csgotuc.dao.SQLItemDao;
import csgotuc.domain.Item;
import csgotuc.domain.ItemService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author latvavil
 */
public class Gui extends Application {

    private ItemService itemService;
    private ObservableList<PieChart.Data> pieChartData;
    private ImageView itemPreview;
    private PieChart pieChart;

    @Override
    public void init() {
        try {
            //Path path = Paths.get(".", "/src/main/resources/object_data.csv");
            //ItemDao itemDao = new FileItemDao(path.normalize().toString());
            File database = new File("./database.db");
            if (!database.exists()) {
                FileUtils.copyURLToFile(
                        new URL("https://github.com/viljamiLatvala/ohjelmistotekniikka/raw/master/database.db"),
                        new File("./database.db"));
            }
            Database db = new Database("jdbc:sqlite:database.db");
            ItemDao itemDao = new SQLItemDao(db);
            if (itemDao.getAll().isEmpty()) {
                ItemFetchingService itemFetchingService = new ItemFetchingService();
                List<Item> items = itemFetchingService.fetchAllItems();
                for (Item item : items) {
                    itemDao.create(item);
                }
            }

            itemService = new ItemService(itemDao);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

        pieChartData = FXCollections.observableArrayList();
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        //Anchor pane ase rootPane
        this.itemPreview = new ImageView();
        AnchorPane rootPane = new AnchorPane();

        //Input-esittely
        TilePane tilePane = new TilePane();
        tilePane.setVgap(4);
        tilePane.setHgap(4);
        tilePane.setPrefColumns(4);
        for (int i = 0; i < 10; i++) {
            Group itemGroup = new Group(new Rectangle(100, 100, Color.GRAY));
            tilePane.getChildren().add(itemGroup);
        }

        //Presentation of the output
        pieChart = new PieChart();

        //New ListView menu
        //EventHandlers for ListCells
        ListView<Item> inputListView = new ListView<>();
        ObservableList<Item> testItems = FXCollections.observableArrayList();
        itemService.getPossibleInputs().forEach(item -> testItems.add(item));

        EventHandler<MouseEvent> mouseEnteredHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Object clickedObject = e.getSource();
                if (clickedObject instanceof ListCell) {
                    ListCell cell = (ListCell) clickedObject;
                    Item item = (Item) cell.getItem();
                    Image image;
                    try {
                        //Image to be replaced
                        image = new Image(new FileInputStream("./reference_item.png"));
                        itemPreview.setImage(image);
                        itemPreview.setX(e.getSceneX());
                        itemPreview.setY(e.getSceneY());
                        itemPreview.setFitHeight(100);
                        itemPreview.setPreserveRatio(true);

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        EventHandler<MouseEvent> mouseExitedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                itemPreview.setImage(null);
            }
        };

        EventHandler<MouseEvent> mouseClickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                    Object clickedObject = e.getSource();
                    if (clickedObject instanceof ListCell) {
                        ListCell cell = (ListCell) clickedObject;
                        Item item = (Item) cell.getItem();
                        itemService.addToInput(item);

                        formInputLoadout(tilePane);
                        System.out.println(itemService.getInput().size());
                        if (itemService.getInput().size() == 1) {
                            try {
                                formInputOptionList(inputListView, item.getGrade());
                            } catch (SQLException ex) {
                                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        try {
                            formChart();
                        } catch (SQLException ex) {
                            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        pieChart.setData(pieChartData);
                    }
                }
            }
        };

        inputListView.setCellFactory(item -> {
            return new ListCell<Item>() {
                @Override
                protected void updateItem(Item item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item.getName());
                        setOnMouseEntered(mouseEnteredHandler);
                        setOnMouseClicked(mouseClickedHandler);
                        setOnMouseExited(mouseExitedHandler);
                    }
                }
            };
        }
        );
        inputListView.setItems(testItems);

        //HBox to contain all 3 stationary menus
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(8);
        hbox.getChildren().add(inputListView);
        hbox.getChildren().add(tilePane);
        hbox.getChildren().add(pieChart);

        rootPane.getChildren().add(hbox);
        rootPane.getChildren().add(itemPreview);

        primaryStage.setScene(new Scene(rootPane));
        primaryStage.setTitle("CSGO Trade-Up Calculator");
        primaryStage.show();
    }

    public void formChart() throws SQLException {
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

        pieChartData = FXCollections.observableArrayList();
        for (Item item : outcomeDist.keySet()) {
            pieChartData.add(new PieChart.Data(item.getName(), outcomeDist.get(item)));
        }

    }

    public void formInputLoadout(TilePane tilePane) {

        List<Item> input = this.itemService.getInput();
        for (int i = 0; i < input.size(); i++) {
            int curIndex = i;
            Item curItem = input.get(i);

            if (this.itemService.getInputItem(i) != null) {
                Group newGroup = new Group(new Rectangle(100, 100, Color.DARKSEAGREEN));
                Image image = null;
                try {
                    image = new Image(new FileInputStream("./reference_item.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImageView img = new ImageView(image);
                img.setFitHeight(100);
                img.setFitWidth(100);
                img.setPreserveRatio(true);
                double actWidth = img.getBoundsInLocal().getWidth();
                double actHeight = img.getBoundsInLocal().getHeight();
                double xAlignment = newGroup.getLayoutX() + ((100 - actWidth) / 2);
                double yAlignment = newGroup.getLayoutY() + ((100 - actHeight) / 2);
                newGroup.getChildren().add(img);
                newGroup.getChildren().get(1).relocate(xAlignment, yAlignment);
                // create a menu 
                ContextMenu contextMenu = new ContextMenu();

                // create menuitems 
                MenuItem remove = new MenuItem("Remove");
                remove.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("REMOVE:" + curItem.getName());
                        itemService.removeFromInput(curIndex);
                        System.out.println("input size: " + itemService.getInput().size());
                        Group itemGroup = new Group(new Rectangle(100, 100, Color.GRAY));
                        tilePane.getChildren().remove(curIndex);
                        tilePane.getChildren().add(curIndex, itemGroup);
                        try {
                            formChart();
                            pieChart.setData(pieChartData);
                        } catch (SQLException ex) {
                            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                // add menu items to menu 
                contextMenu.getItems().add(remove);

                newGroup.setOnContextMenuRequested(e -> contextMenu.show(newGroup, e.getScreenX(), e.getScreenY()));

                tilePane.getChildren().set(i, newGroup);
            }

        }
    }

    public void formInputOptionList(ListView list, int grade) throws SQLException {
        ObservableList<Item> newListItems = FXCollections.observableArrayList();
        itemService.getByGrade(grade).forEach(item -> newListItems.add(item));
        list.getItems().setAll(newListItems);
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
