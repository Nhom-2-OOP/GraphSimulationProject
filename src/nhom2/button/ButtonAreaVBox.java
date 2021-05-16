package nhom2.button;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

public class ButtonAreaVBox {
	int sizeButton = 55;
	public VBox area(GraphPanel graphView, SubScene subSceneGraphPanel, Stage stage, GridPane root) {
		//Tao nut Home
		HomeButton btnHome = new HomeButton(root);
		SubScene subSceneHome = new SubScene(btnHome, sizeButton, sizeButton);

		// Tao nut input do thi
		InputButton btnInput = new InputButton(stage);
		btnInput.setText("Input đồ thị");
		SubScene subSceneInput = new SubScene(btnInput,sizeButton,sizeButton);

		// Tao nut luu anh do thi
		CaptureGraphPanel btnCaptureGP = new CaptureGraphPanel(subSceneGraphPanel, stage);
		btnCaptureGP.setText("Save");
		SubScene subSceneCaptureGP = new SubScene(btnCaptureGP, sizeButton, sizeButton);


		//Nut PlacementButon
		PlacementButton btnPla = new PlacementButton(root, graphView);
		SubScene subScenePla = new SubScene(btnPla, sizeButton, sizeButton);

		//Nut PathFindingButton
		PathFindingButton btnPathFinding = new PathFindingButton(root, stage, graphView);
		SubScene subScenePathFinding = new SubScene(btnPathFinding, sizeButton, sizeButton);

		// Tao nut to mau do thi
		ColoringButton ColoringButton = new ColoringButton(graphView);
		ColoringButton.setText("Color Graph");
		SubScene subSceneColoring = new SubScene(ColoringButton, sizeButton, sizeButton);

		// Tao nut thông tin thành viên
		Button InfoButton = new InfoButton();
		InfoButton.setText("Thông tin nhóm");
		SubScene subSceneInfo = new SubScene(InfoButton,sizeButton,sizeButton);


		
		// Tao layout VBox
		VBox buttonArea = new VBox(0);
		buttonArea.getChildren().addAll(subSceneHome,subSceneInput, subSceneCaptureGP, subScenePla, subScenePathFinding, subSceneColoring, subSceneInfo);
		buttonArea.setAlignment(Pos.TOP_CENTER);

		buttonArea.getStyleClass().add("buttonArea");
		btnHome.getStyleClass().add("buttonOfButtonArea");
		btnInput.getStyleClass().add("buttonOfButtonArea");
		btnCaptureGP.getStyleClass().add("buttonOfButtonArea");
		btnPla.getStyleClass().add("buttonOfButtonArea");
		btnPathFinding.getStyleClass().add("buttonOfButtonArea");
		ColoringButton.getStyleClass().add("buttonOfButtonArea");
		InfoButton.getStyleClass().add("buttonOfButtonArea");
		 
		return buttonArea;
	}
	
	public VBox label() {
		VBox labelBtnArea = new VBox(0);
		
		Label labHome = new Label("Home");
		labHome.getStyleClass().add("labelOfButtonArea");
		labHome.setPrefSize(245, sizeButton);
		Pane btnHome = new Pane(labHome);
		labelBtnArea.getChildren().add(btnHome);
		

		Label labInp = new Label("Input");
		labInp.getStyleClass().add("labelOfButtonArea");
		labInp.setPrefSize(245, sizeButton);
		Pane btnInp = new Pane(labInp);
		labelBtnArea.getChildren().add(btnInp);
		
		Label labCap = new Label("Save Image");
		labCap.getStyleClass().add("labelOfButtonArea");
		labCap.setPrefSize(245, sizeButton);
		Pane btnCaptureGP = new Pane(labCap);
		labelBtnArea.getChildren().add(btnCaptureGP);
		
		
		Label labPla = new Label("Sắp xếp đỉnh");
		labPla.getStyleClass().add("labelOfButtonArea");
		labPla.setPrefSize(245, sizeButton);
		Pane btnPla = new Pane(labPla);
		labelBtnArea.getChildren().add(btnPla);

		Label labPathFind = new Label("Tìm đường đi đồ thị");
		labPathFind.getStyleClass().add("labelOfButtonArea");
		labPathFind.setPrefSize(245, sizeButton);
		Pane btnPathFinding = new Pane(labPathFind);
		labelBtnArea.getChildren().add(btnPathFinding);

		Label labColor= new Label("Tô màu đồ thị");
		labColor.getStyleClass().add("labelOfButtonArea");
		labColor.setPrefSize(245, sizeButton);
		Pane btnColor = new Pane(labColor);
		labelBtnArea.getChildren().add(btnColor);
		
		Label labInfo= new Label("Infor");
		labInfo.getStyleClass().add("labelOfButtonArea");
		labInfo.setPrefSize(245, sizeButton);
		Pane btnInfo  = new Pane(labInfo);
		labelBtnArea.getChildren().add(btnInfo);
		
		System.out.println(labelBtnArea.getChildren());
		return labelBtnArea;
	}

}