package nhom2.graphview;

public interface LabelledObject {
	// Gan label vao object
	public void attachLabel(Label label);
	
	// Lay label tu object
	public Label getAttachedLabel();
}
