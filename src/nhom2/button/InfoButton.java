package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Pos;
import nhom2.button.info.Guide;
import nhom2.button.info.Member;

public class InfoButton extends Button{
	protected GridPane GridPane;

	public InfoButton() {
		
		Stage stage = new Stage();
		
		HBox root = new HBox(20);
		
		Guide guideBut = new Guide();
		SubScene subSceneGuide = new SubScene (guideBut, 200, 50);
		
		Member memBut = new Member(stage) ;
		SubScene subSceneMem = new SubScene (memBut, 200, 50);
		
		root.getChildren().add(subSceneGuide);
		root.getChildren().add(subSceneMem);

		root.setAlignment(Pos.CENTER);
						
		stage.setScene(new Scene(root, 450, 70));
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		
		this.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				stage.show();
			}
		});
		
		
		
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("InfoButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

	protected Object getRowConstraints() {
		// TODO Auto-generated method stub
		return null;
	}
	
}