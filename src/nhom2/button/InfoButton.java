package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import nhom2.button.info.Guide;
import nhom2.button.info.Member;

public class InfoButton extends Button{
	protected GridPane GridPane;

	public InfoButton() {
		this.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				
				HBox root = new HBox(20);
				
				Member memBut = new Member() ;
				Guide guideBut = new Guide();
				
				root.getChildren().add(memBut);
				root.getChildren().add(guideBut);

				root.setAlignment(Pos.CENTER);
				
				stage.setScene(new Scene(root, 800, 600));
//				stage.setTitle("Thông tin nhóm 2");
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