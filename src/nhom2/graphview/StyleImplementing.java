package nhom2.graphview;

import javafx.scene.shape.Shape;

public class StyleImplementing implements StylableObject{
	
	private final Shape client;
	
	public StyleImplementing(Shape client) {
		this.client = client;
	}

	@Override
	public void setStyle(String css) {
		// TODO Auto-generated method stub
		client.setStyle(css);
	}

	@Override
	public void setStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		client.getStyleClass().clear();
        client.setStyle(null);
        client.getStyleClass().add(cssClass);
	}

	@Override
	public void addStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		client.getStyleClass().add(cssClass);
	}

	@Override
	public boolean removeStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		return client.getStyleClass().remove(cssClass);
	}

}
