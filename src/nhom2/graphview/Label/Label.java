package nhom2.graphview.Label;

import javafx.scene.text.Text;
import nhom2.graphview.Styling.StylableObject;
import nhom2.graphview.Styling.StyleImplementing;

public class Label extends Text implements StylableObject{
	private final StyleImplementing style;
	
	public Label() {
        this(0,0,"");
    }
	
    public Label(String text) {
        this(0, 0, text);
    }
    
    public Label(double x, double y, String text) {
    	super(x, y, text);
    	style = new StyleImplementing(this);
    }

	@Override
	public void setStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		style.setStyleClass(cssClass);
	}

	@Override
	public void addStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		style.addStyleClass(cssClass);
	}

	@Override
	public boolean removeStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		return style.removeStyleClass(cssClass);
	}
}
