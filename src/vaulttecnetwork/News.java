package vaulttecnetwork;

import java.io.Serializable;

public class News implements Serializable{

private static final long serialVersionUID = 1L;

private int number;
private String headline;
private String newstText;

/**
 * @param number
 * @param headline
 * @param newstText
 */
public News(int number, String headline, String newstText) {
	super();
	this.number = number;
	this.headline = headline;
	this.newstText = newstText;
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
public String getHeadline() {
	return headline;
}
public void setHeadline(String headline) {
	this.headline = headline;
}
public String getNewstText() {
	return newstText;
}
public void setNewstText(String newstText) {
	this.newstText = newstText;
}

}
