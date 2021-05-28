package nhom2.button.info;

import java.io.InputStream;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nhom2.window.Main;

public class Member extends Button {

	public Member(Stage infoStage) {
		this.setText("Member Information");
		Stage stage = new Stage();
		VBox rootMem = new VBox(20);
		
		InputStream input = this.getClass().getResourceAsStream("/nhom2/window/design/team.png");
		Image image = new Image(input);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(300);
		imageView.setFitWidth(300);
		
		rootMem.getChildren().add(imageView);	
		rootMem.setStyle("-fx-background-color: WHITE");
		
		GridPane mem = new GridPane();
		rootMem.getChildren().add(mem);

		mem.add(new TextMem("Phan Đức Anh"), 0, 0);
		mem.add(new TextMem(" 20193981 "), 1, 0);

		mem.add(new TextMem("Đỗ Đức Anh"), 0, 1);
		mem.add(new TextMem(" 20193976 "), 1, 1);

		mem.add(new TextMem("Lê Doãn Biên"), 0, 2);
		mem.add(new TextMem(" 20193992 "), 1, 2);

		mem.add(new TextMem("Hoàng Khắc Đông"), 0, 3);
		mem.add(new TextMem(" 20194019 "), 1, 3);

		mem.add(new TextMem("Lê Huy Dương"), 0, 4);
		mem.add(new TextMem(" 20194032 "), 1, 4);

		mem.add(new TextMem("Phùng Bảo Hà"), 0, 5);
		mem.add(new TextMem(" 20190047 "), 1, 5);

		mem.add(new TextMem("Lê Trung Kiên"), 0, 6);
		mem.add(new TextMem(" 20194084 "), 1, 6);

		mem.add(new TextMem("Chu Nhật Minh"), 0, 7);
		mem.add(new TextMem(" 20194115 "), 1, 7);

		mem.add(new TextMem("Nguyễn Hải Minh"), 0, 8);
		mem.add(new TextMem(" 20194120 "), 1, 8);

		mem.add(new TextMem("Ngô Thành Nam"), 0, 9);
		mem.add(new TextMem(" 20194127 "), 1, 9);

		mem.add(new TextMem("Tạ Tiến Thành"), 0, 10);
		mem.add(new TextMem(" 20194176 "), 1, 10);

		ColumnConstraints c = new ColumnConstraints();
		c.setPrefWidth(250);
		mem.getColumnConstraints().add(c);
		
		for(int i = 0; i <= 11; i++) {
			RowConstraints r = new RowConstraints();
			r.setMinHeight(40);
			mem.getRowConstraints().add(r);
		}
		
		mem.setAlignment(Pos.CENTER);

		rootMem.setAlignment(Pos.CENTER);

		Screen screen = Screen.getPrimary();

		stage.setScene(new Scene(rootMem, 550, 830));
		stage.setTitle("Thông tin nhóm 2");
		stage.setResizable(false);
		
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				infoStage.close();
				
				stage.show();
//				if(stage.isShowing()) {
//					stage.close();
//				}
//				else {
//					stage.show();
//				}
			}
		});
	}

}
