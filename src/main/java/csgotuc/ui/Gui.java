/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csgotuc.ui;

import csgotuc.dao.Database;
import csgotuc.dao.ItemDao;
import csgotuc.dao.SQLItemDao;
import csgotuc.domain.Item;
import csgotuc.domain.ItemService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author latvavil
 */
public class Gui extends Application {

    private ItemService itemService;
    private ObservableList<PieChart.Data> pieChartData;
    private Group previewGroup;
    private PieChart pieChart;
    private AnchorPane rootPane;

    @Override
    public void init() {
        try {
            File database = new File("./database.db");
            if (!database.exists()) {
                FileUtils.copyURLToFile(
                        new URL("https://github.com/viljamiLatvala/ohjelmistotekniikka/raw/master/database.db"),
                        new File("./database.db"));
            }
            Database db = new Database("jdbc:sqlite:database.db");
            ItemDao itemDao = new SQLItemDao(db);
            itemService = new ItemService(itemDao);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }

        pieChartData = FXCollections.observableArrayList();
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        this.previewGroup = new Group();
        rootPane = new AnchorPane();

        TilePane tilePane = new TilePane(4, 4);
        tilePane.setPrefColumns(4);
        for (int i = 0; i < 10; i++) {
            Group itemGroup = new Group(new Rectangle(100, 100, Color.GRAY));
            tilePane.getChildren().add(itemGroup);
        }

        pieChart = new PieChart();

        ListView<Item> inputListView = new ListView<>();
        ObservableList<Item> inputItems = FXCollections.observableArrayList(itemService.getPossibleInputs());

        EventHandler<MouseEvent> mouseEnteredHandler = (MouseEvent e) -> {
            Object clickedObject = e.getSource();
            Item item = (Item) ((ListCell) clickedObject).getItem();
            formPreview(item.getImage(), Arrays.asList(item.getName()));
            previewGroup.relocate(e.getSceneX() + 10, e.getSceneY() + 10);
            rootPane.getChildren().add(previewGroup);
        };

        EventHandler<MouseEvent> mouseExitedHandler = (MouseEvent e) -> {
            rootPane.getChildren().remove(previewGroup);
        };

        EventHandler<MouseEvent> mouseClickedHandler = (MouseEvent e) -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Object clickedObject = e.getSource();
                Item item = (Item) ((ListCell) clickedObject).getItem();
                if(itemService.getInput().size()<10) {
                    itemService.addToInput(new Item(item));
                }
                

                formInputLoadout(tilePane);
                if (itemService.getInput().size() == 1) {
                    formInputOptionList(inputListView, item.getGrade());
                }

                try {
                    formChart();
                } catch (SQLException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
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
        inputListView.setItems(inputItems);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(8);
        hbox.getChildren().add(inputListView);
        hbox.getChildren().add(tilePane);
        hbox.getChildren().add(pieChart);

        rootPane.getChildren().add(hbox);
        primaryStage.setScene(new Scene(rootPane));
        primaryStage.setTitle("CSGO Trade-Up Calculator");
        primaryStage.show();
    }

    /**
     *
     * @throws SQLException
     */
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
        outcomeDist.keySet().forEach((item) -> {
            pieChartData.add(new PieChart.Data(item.getName(), outcomeDist.get(item)));
        });

        pieChart.setData(pieChartData);

        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().setOnMouseEntered((MouseEvent e) -> {
                Item item = itemService.findByName(data.getName());
                outcomeDist.keySet().forEach(key -> {
                    if (key.getName().equals(data.getName())) {
                        item.setFloatValue(key.getFloatValue());
                    }
                });
                String dropChance = data.getPieValue() / pieChart.getData().size() * 100 + "%";
                formPreview(item.getImage(), Arrays.asList(item.getName(), "chance: " + dropChance, "Float Value: " + item.getFloatValue(), "Condition: " + item.getCondition()));
                previewGroup.setLayoutX(e.getSceneX() + 5);
                previewGroup.setLayoutY(e.getSceneY() + 5);
                rootPane.getChildren().add(previewGroup);
            });
            data.getNode().setOnMouseExited((MouseEvent e) -> {
                rootPane.getChildren().remove(previewGroup);
            });
        }

    }

    /**
     *
     * @param tilePane
     */
    public void formInputLoadout(TilePane tilePane) {

        List<Item> input = this.itemService.getInput();
        for (int i = 0; i < input.size(); i++) {
            int curIndex = i;
            Item inputItem = this.itemService.getInputItem(i);

            if (inputItem == null) {
                continue;
            }
            Group newGroup = new Group(new Rectangle(100, 100, Color.DARKSEAGREEN));
            Image image = null;
            image = new Image(new ByteArrayInputStream(inputItem.getImage()));
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

            ContextMenu contextMenu = new ContextMenu();

            MenuItem remove = new MenuItem("Remove");
            remove.setOnAction((ActionEvent event) -> {
                itemService.removeFromInput(curIndex);
                Group itemGroup = new Group(new Rectangle(100, 100, Color.GRAY));
                tilePane.getChildren().remove(curIndex);
                tilePane.getChildren().add(curIndex, itemGroup);
                try {
                    formChart();
                } catch (SQLException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            MenuItem floatValue = new MenuItem("Set float value");
            floatValue.setOnAction((ActionEvent event) -> {
                Item item = itemService.getInputItem(curIndex);
                TextInputDialog setFloat = new TextInputDialog(Double.toString(item.getFloatValue()));
                setFloat.setTitle("Enter new float value");
                setFloat.setHeaderText(null);
                Optional<String> result = setFloat.showAndWait();

                result.ifPresent(newFloat -> {
                    try {
                        item.setFloatValue(Double.parseDouble(newFloat));
                        try {
                            formChart();
                        } catch (SQLException ex) {
                            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(AlertType.WARNING, "Input must be parseable to a Double", ButtonType.OK);
                        alert.show();
                    }

                });
            });
            contextMenu.getItems().add(remove);
            contextMenu.getItems().add(floatValue);
            newGroup.setOnContextMenuRequested(e -> contextMenu.show(newGroup, e.getScreenX(), e.getScreenY()));
            tilePane.getChildren().set(i, newGroup);

        }
    }

    /**
     *
     * @param list
     * @param grade
     */
    public void formInputOptionList(ListView list, int grade) {
        ObservableList<Item> newListItems = FXCollections.observableArrayList();
        itemService.getByGrade(grade).forEach(item -> newListItems.add(item));
        list.getItems().setAll(newListItems);
    }

    public void formPreview(byte[] imageAsByteArray, List<String> labels) {
        Group newPreviewGroup = new Group();

        Rectangle backdrop = new Rectangle(220, 240, Color.GRAY);
        newPreviewGroup.getChildren().add(backdrop);

        ImageView previewImageView = new ImageView(new Image(new ByteArrayInputStream(imageAsByteArray)));
        previewImageView.setFitWidth(180);
        previewImageView.setPreserveRatio(true);
        double actWidth = previewImageView.getBoundsInLocal().getWidth();
        double actHeight = previewImageView.getBoundsInLocal().getHeight();
        double xAlignment = newPreviewGroup.getLayoutX() + ((backdrop.getWidth() - actWidth) / 2);
        double yAlignment = 30 + newPreviewGroup.getLayoutY() + ((backdrop.getHeight() / 2 - actHeight) / 2);
        newPreviewGroup.getChildren().add(previewImageView);
        newPreviewGroup.getChildren().get(1).relocate(xAlignment, yAlignment);

        double labelYPos = yAlignment + 180;

        for (String labelText : labels) {
            Label newLabel = new Label(labelText);
            newLabel.setLayoutX(newPreviewGroup.getLayoutX() + 10);
            newLabel.setLayoutY(labelYPos);
            labelYPos += 15;
            newPreviewGroup.getChildren().add(newLabel);
        }
        backdrop.setHeight(labelYPos + 30);

        previewGroup = newPreviewGroup;
    }

    @Override
    public void stop() {
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
