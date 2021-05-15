package nhom2.button;

import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

public class buttonAreaVBox {

	public VBox area(GraphPanel graphView, SubScene subSceneGraphPanel, Stage stage, GridPane root) {
		//Tap nut sap xep dinh ngau nhien
		RandomPlacementButton btnRanPla = new RandomPlacementButton(graphView);
		btnRanPla.setText("Sắp đỉnh ngẫu nhiên");
		SubScene subSceneRanPla = new SubScene(btnRanPla,150,30);

		// Tao nut sap dinh theo vong tron
		CircularPlacementButton btnCircularPla = new CircularPlacementButton(graphView);
		btnCircularPla.setText("Sắp đỉnh theo vòng tròn");
		SubScene subSceneCircularPla = new SubScene(btnCircularPla,150,30);

		// Tao nut sap dinh tu dong
		AutoPlacementButton btnAutoPla = new AutoPlacementButton(graphView);
		btnAutoPla.setText("Sắp đỉnh tự động");
		SubScene subSceneAutoPla = new SubScene(btnAutoPla,150,30);

		// Tao nut luu anh do thi
		CaptureGraphPanel btnCaptureGP = new CaptureGraphPanel(subSceneGraphPanel, stage);
		btnCaptureGP.setText("Lưu ảnh đồ thị");
		SubScene subSceneCaptureGP = new SubScene(btnCaptureGP,150,30);

		// Tao nut input do thi
		InputButton btnInput = new InputButton(stage);
		btnInput.setText("Input đồ thị");
		SubScene subSceneInput = new SubScene(btnInput,150,30);

		// Tao nut tu tim duong di
		FindPathButton<String, String> btnFindPath = new FindPathButton(stage, graphView);
		btnFindPath.setText("Tự tìm đường đi");
		SubScene subSceneFindPath = new SubScene(btnFindPath,150,30);

		// Tao nut tu dong tim duong di
		Button btnAutoFindPath = new AutoFindPaths<String, String>(graphView);
		btnAutoFindPath.setText("Tự động tìm đường đi");
		SubScene subSceneAutoFindPath = new SubScene(btnAutoFindPath,150,30);

		// Tao nut to mau do thi
		ColoringButton ColoringButton = new ColoringButton(graphView);
		ColoringButton.setText("Tô màu đồ thị");
		SubScene subSceneColoring = new SubScene(ColoringButton,150,30);

		// Tao nut to mau do thi
		DFSButton<String, String> DFSButton = new DFSButton(root, graphView);
		DFSButton.setText("Tìm đường DFS");
		SubScene subSceneDFS = new SubScene(DFSButton,150,30);

		// Tao nut BFS
		BFSButton<String, String> BFSButton = new BFSButton(root, graphView);
		BFSButton.setText("Tìm đường BFS");
		SubScene subSceneBFS = new SubScene(BFSButton,150,30);

		// Tao nut thông tin đồ thị
		Button InfoButton = new InfoButton();
		InfoButton.setText("Thông tin nhóm");
		SubScene subSceneInfo = new SubScene(InfoButton,150,30);

		// Tao layout VBox
		VBox buttonArea = new VBox(10);
		buttonArea.getChildren().addAll(subSceneInput, subSceneCaptureGP, subSceneRanPla, subSceneAutoPla, subSceneCircularPla, subSceneFindPath, subSceneAutoFindPath, subSceneColoring, subSceneDFS, subSceneBFS, subSceneInfo);
		buttonArea.setAlignment(Pos.CENTER);

		return buttonArea;
	}

}
