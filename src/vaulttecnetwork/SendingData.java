package vaulttecnetwork;

import java.io.Serializable;

public class SendingData implements Serializable{

private static final long serialVersionUID = 1L;

private String headline;
private String message;


/** Podstawowa klasa danych przesy³anych pomiêdzy serwerem a u¿ytkownikami.
 * @param who
 * @param password
 * @param headline
 * @param message
 */
public SendingData(String headline, String message) {
	super();
	this.headline = headline;
	this.message = message;
}

public String getHeadline() {
	return headline;
}
public void setHeadline(String headline) {
	this.headline = headline;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}