package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nhom2.button.utilities.BackButton;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Vertex.VertexNode;
import nhom2.window.Main;


public class FindVertexButton<V,E> extends Button{
	

	public FindVertexButton( GridPane root, GraphPanel<V,E> graphView) {
		GridPane grid = new GridPane();

		//BackButton
		BackButton backBut = new BackButton(root);

		GridPane gridChild = new GridPane();
		Label lbStartVertex = new Label("Nhập tên đỉnh:");
		TextField tfStartVertex = new TextField();
		Button find = new Button("Tìm");
				
		tfStartVertex.setPromptText("Tên đỉnh");
		
		gridChild.setVgap(30);
		
		gridChild.add(lbStartVertex, 0, 0);
		gridChild.add(tfStartVertex, 0, 1);	
		HBox likeNexResBox = new HBox(60);
		likeNexResBox.getChildren().add(find);
		gridChild.add(likeNexResBox, 0, 2);
		
		grid.add(backBut, 0, 0);
		grid.add(gridChild, 0, 1);
		
		gridChild.setPadding(new Insets(30, 10, 0, 10));
		gridChild.setHalignment(tfStartVertex, HPos.RIGHT);
		
		gridChild.prefWidthProperty().bind(grid.widthProperty());
		
		lbStartVertex.getStyleClass().add("lbStartVerTexFS");
		tfStartVertex.getStyleClass().add("tfStartVertexFS");
		find.getStyleClass().add("FSNextReset");
		likeNexResBox.getStyleClass().add("likeNexResBox");


		EventHandler<ActionEvent> current = backBut.getOnAction();
		
		backBut.setOnAction(e -> {
	        current.handle(e);
	        for (VertexNode<V> tmp : graphView.vertexNodes.values())
				tmp.setStyle("-fx-fill: #96d1cd");
	    });

		find.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
	
				tfStartVertex.commitValue();
				String dataStart = tfStartVertex.getText();
				Vertex<V> startVertex = graphView.theGraph.vertices.get(dataStart);
				if (startVertex == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}
				graphView.vertexNodes.get(startVertex).setStyle("-fx-fill: red");
			}
		});
		
		find.setOnMouseEntered(mouseEvent -> {
			find.getStyleClass().add("FSNextResetEntered");
		});
		find.setOnMouseExited(mouseEvent -> {
			find.getStyleClass().remove(2);
		});

		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				backBut.setGridBack(Main.getNodeCol1Start());
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("FindingButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

}
