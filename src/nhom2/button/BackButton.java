package nhom2.button;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nhom2.button.AutoPlacementButton;
import nhom2.button.BFSButton;
import nhom2.button.CaptureGraphPanel;
import nhom2.button.CircularPlacementButton;
import nhom2.button.ColoringButton;
import nhom2.button.DFSButton;
import nhom2.button.AutoFindPaths;
import nhom2.button.FindPathButton;
import nhom2.button.InfoButton;
import nhom2.button.InputButton;
import nhom2.button.RandomPlacementButton;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.window.Main;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
public class BackButton extends Button {
	public Node gridBack;

	public Node getGridBack() {
		return gridBack;
	}

	public void setGridBack(Node gridBack) {
		this.gridBack = gridBack;
	}

	public BackButton(GridPane root) {
		this.setText("Back");
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//				System.out.println(gridBack);
				//				System.out.println(root.getChildren().get(2));
				root.getChildren().remove(2);
				root.add(gridBack, 1, 0);

			}
		});

	}

}