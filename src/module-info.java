module GraphSimulationProject {
	requires javafx.controls;
	requires java.logging;
	requires javafx.swing;
	requires javafx.fxml;
	
	opens nhom2.window to javafx.graphics, javafx.fxml;
}
