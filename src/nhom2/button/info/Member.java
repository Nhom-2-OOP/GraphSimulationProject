package nhom2.button.info;

import java.io.File;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nhom2.window.Main;

public class Member extends Button {

	public Member() {
		this.setText("Member Information");
		
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				VBox root = new VBox();
				//				root.setSpacing(10);
				root.setPadding(new Insets(15,20,10,10));
				
				Class<?> clazz = this.getClass();



//				Button imgTeamBut = new Button();
//				SubScene subImg = new SubScene(imgTeamBut, 500, 500);
//				imgTeamBut.getStyleClass().add("imgTeamBut");
				
				InputStream input = clazz.getResourceAsStream("/nhom2/window/design/team.png");
				Image image = new Image(input);
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(300);
				imageView.setFitWidth(300);
				
				root.getChildren().add(imageView);


				GridPane phanh = new GridPane();
				phanh.add(new Text("Phan Đức Anh"), 0, 0);
				phanh.add(new Text(" 20193981 "), 1, 0);
				phanh.setGridLinesVisible(true);
				root.getChildren().add(phanh);

				GridPane danh = new GridPane();
				danh.add(new Text("Đỗ Đức Anh"), 0, 0);
				danh.add(new Text(" 20193976 "), 1, 0);
				danh.setGridLinesVisible(true);
				root.getChildren().add(danh);

				GridPane bien = new GridPane();
				bien.add(new Text("Lê Doãn Biên"), 0, 0);
				bien.add(new Text(" 20193992 "), 1, 0);
				bien.setGridLinesVisible(true);
				root.getChildren().add(bien);

				GridPane dong = new GridPane();
				dong.add(new Text("Hoàng Khắc Đông"), 0, 0);
				dong.add(new Text(" 20194019 "), 1, 0);
				dong.setGridLinesVisible(true);
				root.getChildren().add(dong);

				GridPane duong = new GridPane();
				duong.add(new Text("Lê Huy Dương"), 0, 0);
				duong.add(new Text(" 20194032 "), 1, 0);
				duong.setGridLinesVisible(true);
				root.getChildren().add(duong);

				GridPane ha = new GridPane();
				ha.add(new Text("Phùng Bảo Hà"), 0, 0);
				ha.add(new Text(" 20190047 "), 1, 0);
				ha.setGridLinesVisible(true);
				root.getChildren().add(ha);

				GridPane kien = new GridPane();
				kien.add(new Text("Lê Trung Kiên"), 0, 0);
				kien.add(new Text(" 20194084 "), 1, 0);
				kien.setGridLinesVisible(true);
				root.getChildren().add(kien);

				GridPane minh1 = new GridPane();
				minh1.add(new Text("Chu Nhật Minh"), 0, 0);
				minh1.add(new Text(" 20194115 "), 1, 0);
				minh1.setGridLinesVisible(true);
				root.getChildren().add(minh1);

				GridPane minh2 = new GridPane();
				minh2.add(new Text("Nguyễn Hải Minh"), 0, 0);
				minh2.add(new Text(" 20194120 "), 1, 0);
				minh2.setGridLinesVisible(true);
				root.getChildren().add(minh2);

				GridPane nam = new GridPane();
				nam.add(new Text("Ngô Thành Nam"), 0, 0);
				nam.add(new Text(" 20194127 "), 1, 0);
				nam.setGridLinesVisible(true);
				root.getChildren().add(nam);

				GridPane thanh = new GridPane();
				thanh.add(new Text("Tạ Tiến Thành"), 0, 0);
				thanh.add(new Text(" 20194176 "), 1, 0);
				thanh.setGridLinesVisible(true);
				root.getChildren().add(thanh);

				RowConstraints r = new RowConstraints();
				r.setPrefHeight(30);
				ColumnConstraints c = new ColumnConstraints();
				c.setPrefWidth(150);
				phanh.getRowConstraints().add(r);
				phanh.getColumnConstraints().add(c);

				danh.getRowConstraints().add(r);
				danh.getColumnConstraints().add(c);

				bien.getRowConstraints().add(r);
				bien.getColumnConstraints().add(c);

				dong.getRowConstraints().add(r);
				dong.getColumnConstraints().add(c);

				duong.getRowConstraints().add(r);
				duong.getColumnConstraints().add(c);

				ha.getRowConstraints().add(r);
				ha.getColumnConstraints().add(c);

				kien.getRowConstraints().add(r);
				kien.getColumnConstraints().add(c);

				minh1.getRowConstraints().add(r);
				minh1.getColumnConstraints().add(c);

				minh2.getRowConstraints().add(r);
				minh2.getColumnConstraints().add(c);

				nam.getRowConstraints().add(r);
				nam.getColumnConstraints().add(c);

				thanh.getRowConstraints().add(r);
				thanh.getColumnConstraints().add(c);

				root.setAlignment(Pos.CENTER);

				stage.setScene(new Scene(root, 800, 600));
				stage.setTitle("Thông tin nhóm 2");
				stage.show();
			}
		});
	}

}
