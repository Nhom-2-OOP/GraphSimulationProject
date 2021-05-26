module GraphSimulationProject {
	requires javafx.controls;
	requires java.logging;
	requires javafx.swing;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens nhom2.window to javafx.graphics, javafx.fxml;
	opens nhom2.button.input to javafx.graphics, javafx.fxml;
}
