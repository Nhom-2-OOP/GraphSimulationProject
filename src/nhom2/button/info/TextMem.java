package nhom2.button.info;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextMem extends Text{

	public TextMem(String t) {
		this.setText(t);
		this.setFont(new Font(20));
		this.setFill(Color.BLACK);
	}

}
