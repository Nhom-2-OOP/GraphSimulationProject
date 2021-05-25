package nhom2.button;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

public class InfoButton extends Button{
	public InfoButton() {
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				Pane mainPane = new Pane();
				
				mainPane.getStyleClass().add("paneInfo");
//				mainPane.setStyle("-fx-background-color: red;");
				mainPane.setStyle("-fx-background-color: rgb(67, 70, 83);" + 
						"-fx-padding: 0 10 20 20;");
				Text t = new Text();
				t.setFont(new Font(20));
//				t.setFill(Color.rgb(154, 168, 212));
				t.setFill(Color.WHITE);
//				t.setStyle("-fx-text-fill: white");
				t.setText("\n\n" + 
						"  Nhóm trưởng:   Phan Đức Anh - 20193981\n\n" + 
						"  Thành viên:    \n" + 
						"	   Đỗ Đức Anh              - 20193976\n" + 
						"	   Lê Doãn Biên            - 20193992\n" + 
						"	   Hoàng Khắc Đông         - 20194019\n" + 
						"	   Lê Huy Dương            - 20194032\n" + 
						"	   Phùng Bảo Hà            - 20190047\n" + 
						"	   Lê Trung Kiên           - 20194084\n" + 
						"	   Chu Nhật Minh           - 20194115\n" + 
						"	   Nguyễn Hải Minh         - 20194120\n" + 
						"	   Ngô Thành Nam           - 20194127\n" + 
						"	   Tạ Tiến Thành           - 20194176\n" +
						"      Github: https://github.com/Nhom-2-OOP/GraphSimulationProject");
				
				mainPane.getChildren().add(t);
				stage.setScene(new Scene(mainPane, 450, 400));
				stage.setTitle("Thông tin thành viên - Nhóm 2");
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

}
