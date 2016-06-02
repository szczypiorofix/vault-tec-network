package vaulttecnetwork;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectors {

private int number;
private Socket socket;
private ObjectInputStream inputStream;
private ObjectOutputStream outputStream;
private char[] password;


/** Podstawowa klasa u¿ytkownika ³¹cz¹cego siê z serwerem.
 * @param number
 * @param password
 * @param socket
 * @param inputStream
 * @param outputStream
 */
public ServerConnectors(int number, char[] password, Socket socket, ObjectInputStream inputStream,
		ObjectOutputStream outputStream) {
	super();
	this.number = number;
	this.password = password;
	this.socket = socket;
	this.inputStream = inputStream;
	this.outputStream = outputStream;
}


public int getNumber() {
	return number;
}


public void setNumber(int number) {
	this.number = number;
}


public Socket getSocket() {
	return socket;
}


public void setSocket(Socket socket) {
	this.socket = socket;
}


public ObjectInputStream getInputStream() {
	return inputStream;
}


public void setInputStream(ObjectInputStream inputStream) {
	this.inputStream = inputStream;
}


public ObjectOutputStream getOutputStream() {
	return outputStream;
}


public void setOutputStream(ObjectOutputStream outputStream) {
	this.outputStream = outputStream;
}


public char[] getPassword() {
	return password;
}


public void setPassword(char[] password) {
	this.password = password;
}
}