package vaulttecnetwork;

import java.io.Serializable;

public class SendingData implements Serializable{

private static final long serialVersionUID = 1L;

private int who;
private char[] password;
private String headline;
private String message;


/** Podstawowa klasa danych przesy³anych pomiêdzy serwerem a u¿ytkownikami.
 * @param who
 * @param password
 * @param headline
 * @param message
 */
public SendingData(int who, char[] password, String headline, String message) {
	super();
	this.who = who;
	this.password = password;
	this.headline = headline;
	this.message = message;
}


public int getWho() {
	return who;
}
public void setWho(int kto) {
	this.who = kto;
}
public char[] getPassword() {
	return password;
}
public void setPassword(char[] password) {
	this.password = password;
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