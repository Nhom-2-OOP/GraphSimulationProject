package nhom2.graphview.Styling;

// Doi tuong nao la stylabe thi se co file css properties theo runtime

public interface StylableObject {
	public void setStyle(String css);
	public void setStyleClass(String cssClass);
	public void addStyleClass(String cssClass);
	public boolean removeStyleClass(String cssClass);
}
