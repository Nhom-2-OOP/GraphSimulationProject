package nhom2.button.info;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Guide extends Button{

	public Guide(Stage infoStage) {
		this.setText("User Guide");
		Stage stage = new Stage();

		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				VBox root = new VBox();
				GridPane pane = new GridPane();
				pane.setHgap(20);
				//				root.setSpacing(10);
				root.setPadding(new Insets(15,20,10,10));

				Text text = new Text("");


				Button button1 = new Button("Zoom");
				pane.add(button1, 0, 0);
				button1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						text.setText("-Bạn có thể dùng con lăn chuột để zoom đồ thị");
						pane.add(text, 1, 0);
					}
				});

				Button button2 = new Button("Drag");
				pane.add(button2, 0, 1);
				button2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						text.setText("-Bạn có thể kéo thả để tùy ý thay đổi vị trí \n"
								+ "các đỉnh và hình dáng đồ thị ");
						pane.add(text, 1, 0, 1, 2);
					}
				});

				Button button3 = new Button("Scale");
				pane.add(button3, 0, 2);
				button3.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						text.setText("-Bạn có thể ấn vào icon số 1 ở góc trái màn hình \n"
								+ "để show tỉ lệ đồ thị so với màn hình");
						pane.add(text, 1, 0, 1, 2);
					}
				});

				Button button4 = new Button("MiniMap");
				pane.add(button4, 0, 3);
				button4.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						text.setText("-Bạn có thể ấn vào icon ở góc phải màn hình \n"
								+ "để xem minimap của đồ thị khi đang zoom");
						pane.add(text, 1, 0, 1, 2);
					}
				});

				Button button5 = new Button("Tabs");
				pane.add(button5, 0, 4);
				button5.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						text.setText("-Bạn có thể ấn vào dấu cộng trên góc trái màn \n"
								+ "hình để tạo đồ thị mới");
						pane.add(text, 1, 0, 1, 2);
					}
				});

				Button button6 = new Button("Mouse");
				pane.add(button6, 0, 5);
				button6.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						text.setText("-Bạn có thể ấn đúp chuột vào màn hình để \n"
								+ "thêm mới đỉnh hoặc bật/tắt trọng số đồ thị.\n"
								+ "-Bạn có thể ấn chuột phải vào đỉnh để xem \n"
								+ "thông tin đỉnh, xóa đỉnh hoặc thêm cạnh.\n"
								+ "-Bạn có thể ấn chuột phải vào cạnh để xóa \n"
								+ "cạnh hoặc thêm trọng số.");
						pane.add(text, 1, 0, 1, 6);
					}
				});

				root.getChildren().add(pane);



				stage.setScene(new Scene(root, 800, 600));
				stage.setTitle("Hướng dẫn sử dụng");
				infoStage.close();
				stage.show();
				if(stage.isShowing()) {
					stage.close();
					stage.show();

				}
				else {
					stage.show();
				}
			}
		});

	}

}