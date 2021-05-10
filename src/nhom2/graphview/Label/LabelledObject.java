package nhom2.graphview.Label;

public interface LabelledObject {
	// Gan label vao object
	public void attachLabel(Label label);
	
	// Lay label tu object
	public Label getAttachedLabel();
}
