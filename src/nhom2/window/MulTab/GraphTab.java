package nhom2.window.MulTab;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nhom2.button.ButtonAreaVBox;
import nhom2.graph.GraphEdgeList;
import nhom2.graphview.GraphPanel;
import nhom2.window.Main;

public class GraphTab extends Tab{
	public GraphPanel<String, String> graphView;
	GridPane buttonArea;
	public static GridPane root = Main.root;
	public static Stage stage = Main.stage;
	public GraphEdgeList<String, String> g;
	private StringProperty nameTab = new SimpleStringProperty();


	public GraphTab(GraphEdgeList<String, String> NewGraph) {
		this(NewGraph, "New Graph");
	}

	public GraphTab(GraphEdgeList<String, String> NewGraph, String name) {
		// Tao scene bieu dien do thi
		this.graphView = new GraphPanel<>(NewGraph);
		this.buttonArea = new ButtonAreaVBox().area(graphView, stage, root);
		PaneGraph graphPane = new PaneGraph(graphView);
		this.setContent(graphPane);

		nameTab.set(name);
	

		Label label = new Label();
		label.textProperty().bind(nameTab);
		
		setGraphic(label);

		TextField textField = new TextField();
		label.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2) {
				textField.setText(label.getText());
				setGraphic(textField);
				textField.selectAll();
				textField.requestFocus();
			}
		});
		textField.setOnAction(event -> {
			nameTab.setValue(textField.getText());
			setGraphic(label);
		});
		textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue) {
				nameTab.setValue(textField.getText());
				setGraphic(label);

			}
		});
	}

	public void setButtonArea () {
		root.getChildren().remove(2);
		root.getChildren().remove(1);

		Pane col1Pane = new Pane();
		VBox labelButton = new ButtonAreaVBox().label();
		col1Pane.getChildren().add(labelButton);

		root.add(buttonArea, 0, 0);
		root.add(col1Pane, 1, 0);
	}
}
