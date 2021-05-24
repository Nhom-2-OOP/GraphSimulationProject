package nhom2.button.addWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Vector;

import nhom2.button.InputButton;
import nhom2.graph.*;
import nhom2.graphview.GraphPanel;
import nhom2.window.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class AddWeightButtonController implements Initializable {
	private static GraphEdgeList<String, String> g;
	private static int n;
	@FXML 
	private TableView Table;
	@FXML
	private Text graph;
	@FXML
	private TextArea Input;
	@FXML
	private Button Save;
	@FXML
	private Button Random;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		g = Main.graphView.theGraph;
		n = g.NumOfVertex();
	}
	@FXML
	private void addRand(ActionEvent e) {
		g.setWeightedFeature();
		Main.setGraph(g);
		informSuccess();
	}
	@FXML 
	private void input(ActionEvent e) {
		String data = "";
		Input.commitValue();
		data = Input.getText();
	    Input.clear();
	    dataToGraph(data);
	    informSuccess();
	}
	private void dataToGraph(String data) {
		Vector<Vector <Integer> > w = new Vector<Vector <Integer> > ();
		String[] split = data.split("\n");
		ArrayList<String> rows = new ArrayList<String>();
		for(String s: split){
		   rows.add(s);
		}
		Vector<Integer> v = new Vector<Integer> () ;
		for(String s: rows)  {
			v.clear();
	    	String[] t = s.split(" ");
	    	
	    	int size = t.length;
	    	for(int i=0;i<size;i++) { 
	    		v.add(Integer.parseInt(t[i]));
	    	}
	    	w.add(v);
	    }
		g.setWeightedFeature(w);
		Main.setGraph(g);
	}
	private static void informSuccess() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Thêm trọng số thành công!");
		inform.showAndWait();
	}
	@FXML
	private void inputFromExternal(ActionEvent e) {
		char [] data = new char [10000000];
		boolean isOpened = true;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Mở tệp trọng số");
			File file = fileChooser.showOpenDialog(InputButton.stage);
			if(fileChooser != null) {
				isOpened = true;
				try (BufferedReader reader = new BufferedReader(new FileReader(file))){
					reader.read(data);
			    }
				catch (IOException ex) {
					ex.printStackTrace();
			    }
			}
			
		}
		catch (Exception ex){
			informNull();
			isOpened = false;
		}
		
		if(isOpened) {
			Input.setText(String.valueOf(data));
			String datas = "";
			Input.commitValue();
			datas = Input.getText();
		    Input.clear();
		    dataToGraph(datas);
			informSuccess();
		}
	}
	private static void informNull() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Chưa mở file!");
		inform.showAndWait();
	}
	
}
