module GraphSimulationProject {
	requires javafx.controls;
	requires java.logging;
	requires java.desktop;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.graphics;
	
	opens nhom2.window to javafx.graphics, javafx.fxml;
}
