package nhom2.button;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
 
public class ContextMenuDemo2 extends Application {
 
    @Override
    public void start(Stage stage) {
 
        Circle circle = new Circle();
        circle.setRadius(80);
        circle.setFill(Color.AQUA);
 
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.setSpacing(5);
 
        root.getChildren().addAll(circle);
 
        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
 
        MenuItem menuItem = new MenuItem("Menu Item");
 
        // Set accelerator to menuItem.
        menuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
 
        Menu parentMenu = new Menu("Parent");
        MenuItem childMenuItem1 = new MenuItem("Child 1");
        MenuItem childMenuItem2 = new MenuItem("Child 2");
        parentMenu.getItems().addAll(childMenuItem1, childMenuItem2);
 
        CheckMenuItem checkMenuItem = new CheckMenuItem("Check Menu Item");
        checkMenuItem.setSelected(true);
 
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
 
        RadioMenuItem radioMenuItem1 = new RadioMenuItem("Radio - Option 1");
        RadioMenuItem radioMenuItem2 = new RadioMenuItem("Radio - Option 2");
        ToggleGroup group = new ToggleGroup();
 
        radioMenuItem1.setToggleGroup(group);
        radioMenuItem2.setToggleGroup(group);
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(menuItem, parentMenu, checkMenuItem, //
                separatorMenuItem, radioMenuItem1, radioMenuItem2);
 
        // When user right-click on Circle
        circle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
 
                contextMenu.show(circle, event.getScreenX(), event.getScreenY());
            }
        });
 
        Scene scene = new Scene(root, 400, 200);
 
        stage.setTitle("JavaFX ContextMenu (o7planning.org)");
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }
 
}
