package nhom2.button.input;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import nhom2.button.AddWeightButton;
import nhom2.button.InputButton;
import nhom2.graph.*;
import nhom2.window.*;
public class InputController  implements Initializable {
	@FXML
	private CheckBox yesBox;
	@FXML
	private CheckBox noBox;
	@FXML
	private Button Save;
	@FXML
	private Button FromExternal;
	@FXML
	private TextArea Input;
	@FXML 
	private DialogPane dialog;

	private static boolean IsDirected = false;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noBox.setSelected(true);
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
	@FXML
	private void inputFromExternal(ActionEvent e) {
		char [] data = new char [10000000];
		boolean isOpened = true;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Mở tệp đồ thị");
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
	@FXML 
	void handleYesBox(ActionEvent e) {
		if(yesBox.isSelected()) {
			noBox.setSelected(false);
			IsDirected = true;
		}
	}
	@FXML
	void handleNoBox(ActionEvent e) {
		if(noBox.isSelected()) {
			yesBox.setSelected(false);
			IsDirected = false;
		}
	}
	private void dataToGraph(String data) {
		 
	    ArrayList<String> rows = new ArrayList<String>();
	    String[] split = data.split("\n");
	    for(String s: split){
	    	rows.add(s);
	    }
	    
	    GraphEdgeList<String, String> g= new GraphEdgeList<String,String>(IsDirected);
	    for(String s: rows)  {
	    	String[] t = s.split(" ");
	    
	    	int size = t.length;
	    	for(int i=1;i<size;i++) { 
	    		g.insertEdge(t[0],t[i],t[0] + " " + t[i]);
	    	}
	    }
	    Main.setGraph(g);
	}
	private static void informSuccess() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		if( IsDirected ) inform.setHeaderText("Lưu đồ thị (có hướng) thành công!");
		else inform.setHeaderText("Lưu đồ thị (vô hướng) thành công!");
		inform.showAndWait();
		InputButton.stage.close();
	}
	private static void informNull() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Chưa mở file!");
		inform.showAndWait();
	}
//	
}
