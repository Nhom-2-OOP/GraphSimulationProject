module GraphSimulationProject {
	requires javafx.controls;
	requires java.logging;
	requires javafx.swing;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens nhom2.window to javafx.graphics, javafx.fxml;
	opens nhom2.button.input to javafx.fxml;
	opens nhom2.button.addWeight to javafx.fxml;
	opens nhom2.button.D_TableButton to javafx.fxml;
}
