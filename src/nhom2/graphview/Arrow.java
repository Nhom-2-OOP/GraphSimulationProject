package nhom2.graphview;

import javafx.scene.shape.*;
import nhom2.graphview.Styling.StylableObject;
import nhom2.graphview.Styling.StyleImplementing;

public class Arrow extends Path implements StylableObject{
	private final StyleImplementing styleProxy;

	public Arrow() {
		getElements().add(new MoveTo(0, 0));  
        getElements().add(new LineTo(-5, 5));
        getElements().add(new MoveTo(0, 0));        
        getElements().add(new LineTo(-5, -5)); 
        
        styleProxy = new StyleImplementing(this);
        styleProxy.addStyleClass("arrow");
	}
	
	@Override
	public void setStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		styleProxy.setStyleClass(cssClass);
	}

	@Override
	public void addStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		styleProxy.addStyleClass(cssClass);
	}

	@Override
	public boolean removeStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		return styleProxy.removeStyleClass(cssClass);
	}

}
