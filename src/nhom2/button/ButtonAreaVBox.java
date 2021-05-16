package nhom2.button;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

public class ButtonAreaVBox {

	public VBox area(GraphPanel graphView, SubScene subSceneGraphPanel, Stage stage, GridPane root) {
		int sizeButton = 55;
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

		return buttonArea;
	}

}