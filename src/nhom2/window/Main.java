package nhom2.window;

import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nhom2.button.AutoPlacementButton;
import nhom2.button.BFSButton;
import nhom2.button.CaptureGraphPanel;
import nhom2.button.CircularPlacementButton;
import nhom2.button.ColoringButton;
import nhom2.button.DFSButton;
import nhom2.button.AutoFindPaths;
import nhom2.button.FindPathButton;
import nhom2.button.InfoButton;
import nhom2.button.InputButton;
import nhom2.button.RandomPlacementButton;
import nhom2.graph.*;
import nhom2.graphview.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;


public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;

	@Override
	public void start(Stage stage) {

		// Tao scene bieu dien do thi
    	graphView = new GraphPanel<>(g);
    	SubScene subSceneGraphPanel = new SubScene(graphView,800,600);
    	graphView.init();
        //graphView.start_automatic_layout();
        
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
    	DFSButton<String, String> DFSButton = new DFSButton(stage, graphView);
    	DFSButton.setText("Tìm đường DFS");
    	SubScene subSceneDFS = new SubScene(DFSButton,150,30);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	// Tao nut to mau do thi
    	BFSButton<String, String> BFSButton = new BFSButton(stage, graphView);
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

		GridPane root = new GridPane();

		//    	root.setHgap(20);
		root.setPadding(new Insets(10, 10, 10, 10));

		//row
		int numRows = 1 ;
		for (int i = 0 ; i < numRows ; i++) {
			RowConstraints r = new RowConstraints();
			r.setPercentHeight(100.0 / numRows);
			r.setValignment(VPos.CENTER);
			root.getRowConstraints().add(r);
		}

		// col 1
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(15);
		c.setHalignment(HPos.CENTER);
		root.getColumnConstraints().add(c);
		root.add(buttonArea, 0, 0);

		//col 2
		c = new ColumnConstraints();
		c.setPercentWidth(85);
		c.setHalignment(HPos.CENTER);
		root.getColumnConstraints().add(c);
		Pane test = new Pane(); 
		root.add(test, 1, 0);
		test.setPrefSize(500, 500);
		test.getChildren().add(subSceneGraphPanel);
		subSceneGraphPanel.heightProperty().bind(test.heightProperty());
		subSceneGraphPanel.widthProperty().bind(test.widthProperty());

		Scene scene = new Scene(root);
		stage = new Stage();
		stage.setTitle("Nhóm 2 - OOP - Graph Visualization");
		stage.setMinWidth(800);
		stage.setMinHeight(700);
		stage.setScene(scene);
		stage.show();  

	}

	public static void main(String[] args) {
		launch(args);
	}

	private static GraphEdgeList<String, String> build_sample_digraph() {

		GraphEdgeList<String,String> g = new GraphEdgeList<String,String>(true);

		g.insertEdge("A", "B", "AB1");
		g.insertEdge("A", "C", "AC");
		g.insertEdge("A", "G", "AG");
		g.insertEdge("A", "H", "AH");    
		g.insertEdge("A", "D", "AD");

		g.insertEdge("D", "E", "DE");
		g.insertEdge("D", "F", "DF");
		g.insertEdge("D", "I", "DI");
		g.insertEdge("D", "J", "DJ");

		//      g.insertEdge("AA", "BB", "AB1");
		g.insertEdge("AA", "CC", "AC1");
		g.insertEdge("AA", "GG", "AG1");
		g.insertEdge("AA", "HH", "AH1");

		g.insertEdge("AA", "DD", "AD1");

		g.insertEdge("DD", "EE", "DE1");
		g.insertEdge("DD", "FF", "DF1");
		g.insertEdge("DD", "II", "DI1");
		g.insertEdge("DD", "JJ", "DJ1");


		g.insertEdge("BB", "B", "BBB1");
		g.insertEdge("BB", "B", "BBB2");
		g.insertEdge("B", "BB", "BBB3");
		g.insertEdge("I", "BB", "ADD1");
		g.insertEdge("I", "H", "HII");
		g.insertEdge("C", "H", "HCII");
		g.insertEdge("BB", "H", "BHBB");
		g.insertEdge("DD", "H", "1");
		return g;
	}
	public static void setGraph(GraphEdgeList<String, String> NewGraph) {
		g = NewGraph;
		graphView.Renew(NewGraph, true); 
	}
}

