package nhom2.button.placement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import nhom2.button.utilities.BackButton;
import nhom2.graphview.*;
import nhom2.window.Main;


public class PlacementButton extends Button{
	

	public PlacementButton( GridPane root, GraphPanel<String, String> graphView) {
		GridPane grid = new GridPane();

		//BackButton
		BackButton backBut = new BackButton(root);
		
		//Tap nut sap xep dinh ngau nhiÃªn
		RandomPlacementButton btnRanPla = new RandomPlacementButton(graphView);
		btnRanPla.setText("Sắp xếp đỉnh ngẫu nhiên");

		// Tao nut sap dinh theo vong tron
		CircularPlacementButton btnCircularPla = new CircularPlacementButton(graphView);
		btnCircularPla.setText("Sắp xếp đỉnh theo hình tròn");

		// Tao nut sap dinh tu dong
		AutoPlacementButton btnAutoPla = new AutoPlacementButton(graphView);
		btnAutoPla.setText("Sắp xếp đỉnh tự động");

		VBox placeList = new VBox();
		
		btnRanPla.getStyleClass().add("Col1ChooseButton");
		btnCircularPla.getStyleClass().add("Col1ChooseButton");
		btnAutoPla.getStyleClass().add("Col1ChooseButton");

		placeList.getChildren().addAll(btnRanPla, btnCircularPla, btnAutoPla);
		placeList.setPadding(new Insets(30, 0, 0, 10));
		grid.add(backBut, 0, 0);
		grid.add(placeList, 0, 1);	

		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				backBut.setGridBack(Main.getNodeCol1Start());
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("btnPlaEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

}
