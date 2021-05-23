package nhom2.button;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

public class ButtonAreaVBox {
	int sizeButton = 55;
	public GridPane area(GraphPanel<String, String> graphView, Stage stage, GridPane root) {
		GridPane buttonArea = new GridPane();

		//Tao nut Home
		HomeButton btnHome = new HomeButton(root);
		SubScene subSceneHome = new SubScene(btnHome, sizeButton, sizeButton);

		// Tao nut input do thi
		InputButton btnInput = new InputButton(stage);
		SubScene subSceneInput = new SubScene(btnInput,sizeButton,sizeButton);

		// Tao nut luu anh do thi
		CaptureGraphPanel btnCaptureGP = new CaptureGraphPanel(graphView, stage);
		SubScene subSceneCaptureGP = new SubScene(btnCaptureGP, sizeButton, sizeButton);
		
		
		// Tao nut tim dinh
		FindVertexButton FindingButton = new FindVertexButton(root, graphView);
		SubScene subSceneFinding = new SubScene(FindingButton, sizeButton, sizeButton);

		//Nut PlacementButon
		PlacementButton btnPla = new PlacementButton(root, graphView);
		SubScene subScenePla = new SubScene(btnPla, sizeButton, sizeButton);

		//Nut PathFindingButton
		PathFindingButton btnPathFinding = new PathFindingButton(root, stage, graphView);
		SubScene subScenePathFinding = new SubScene(btnPathFinding, sizeButton, sizeButton);

		// Tao nut to mau do thi
		ColoringButton ColoringButton = new ColoringButton(graphView);
		SubScene subSceneColoring = new SubScene(ColoringButton, sizeButton, sizeButton);


		// Tao nut thông tin thành viên
		Button InfoButton = new InfoButton();
		SubScene subSceneInfo = new SubScene(InfoButton,sizeButton,sizeButton);
		
		// Tao layout VBox
		VBox buttonAreaTop = new VBox(0);
		buttonAreaTop.getChildren().addAll(subSceneHome,subSceneInput, subSceneCaptureGP, subSceneFinding, subScenePla, subScenePathFinding, subSceneColoring);
		buttonAreaTop.setAlignment(Pos.TOP_CENTER);
		buttonArea.setVgrow(buttonAreaTop, Priority.ALWAYS);

		VBox buttonAreaBot = new VBox(0);
		buttonAreaBot.getChildren().add(subSceneInfo);
		buttonAreaBot.setAlignment(Pos.TOP_CENTER);
		
		buttonArea.add(buttonAreaTop, 0, 0);
		buttonArea.add(buttonAreaBot, 0, 1);
		buttonArea.setAlignment(Pos.BOTTOM_CENTER);
		
		buttonArea.getStyleClass().add("buttonArea");
		
		btnHome.getStyleClass().add("buttonOfButtonArea");
		btnInput.getStyleClass().add("buttonOfButtonArea");
		btnCaptureGP.getStyleClass().add("buttonOfButtonArea");
		btnPla.getStyleClass().add("buttonOfButtonArea");
		btnPathFinding.getStyleClass().add("buttonOfButtonArea");
		ColoringButton.getStyleClass().add("buttonOfButtonArea");
		FindingButton.getStyleClass().add("buttonOfButtonArea");
		InfoButton.getStyleClass().add("buttonOfButtonArea");
		
		btnHome.getStyleClass().add("btnHome");
		btnInput.getStyleClass().add("btnInput");
		btnCaptureGP.getStyleClass().add("btnCaptureGP");
		btnPla.getStyleClass().add("btnPla");
		btnPathFinding.getStyleClass().add("btnPathFinding");
		ColoringButton.getStyleClass().add("ColoringButton");
		FindingButton.getStyleClass().add("FindingButton");
		InfoButton.getStyleClass().add("InfoButton");
 		
		return buttonArea;
	}
	
	public VBox label() {
		VBox labelBtnArea = new VBox(0);
		
		Label labHome = new Label(" Home");
		labHome.getStyleClass().add("labelOfButtonArea");
		labHome.setPrefSize(245, sizeButton);
		Pane btnHome = new Pane(labHome);
		labelBtnArea.getChildren().add(btnHome);
		

		Label labInp = new Label(" Input");
		labInp.getStyleClass().add("labelOfButtonArea");
		labInp.setPrefSize(245, sizeButton);
		Pane btnInp = new Pane(labInp);
		labelBtnArea.getChildren().add(btnInp);
		
		Label labCap = new Label(" Save Image");
		labCap.getStyleClass().add("labelOfButtonArea");
		labCap.setPrefSize(245, sizeButton);
		Pane btnCaptureGP = new Pane(labCap);
		labelBtnArea.getChildren().add(btnCaptureGP);
		
		Label labFind= new Label(" Tìm đỉnh");
		labFind.getStyleClass().add("labelOfButtonArea");
		labFind.setPrefSize(245, sizeButton);
		Pane btnFind = new Pane(labFind);
		labelBtnArea.getChildren().add(btnFind);
		
		Label labPla = new Label(" Sắp xếp đỉnh");
		labPla.getStyleClass().add("labelOfButtonArea");
		labPla.setPrefSize(245, sizeButton);
		Pane btnPla = new Pane(labPla);
		labelBtnArea.getChildren().add(btnPla);

		Label labPathFind = new Label(" Tìm đường đi đồ thị");
		labPathFind.getStyleClass().add("labelOfButtonArea");
		labPathFind.setPrefSize(245, sizeButton);
		Pane btnPathFinding = new Pane(labPathFind);
		labelBtnArea.getChildren().add(btnPathFinding);

		Label labColor= new Label(" Tô màu đồ thị");
		labColor.getStyleClass().add("labelOfButtonArea");
		labColor.setPrefSize(245, sizeButton);
		Pane btnColor = new Pane(labColor);
		labelBtnArea.getChildren().add(btnColor);
		
		labelBtnArea.setPadding(new Insets(0, 0,0, 10));
		return labelBtnArea;
	}

}