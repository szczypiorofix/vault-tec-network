package vaulttecnetwork;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectors {


private Socket socket;
private ObjectInputStream inputStream;
private ObjectOutputStream outputStream;


/** Podstawowa klasa użytkownika łączącego się z serwerem.

 * @param socket
 * @param inputStream
 * @param outputStream
 */
public ServerConnectors(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream)
{
	super();
	this.socket = socket;
	this.inputStream = inputStream;
	this.outputStream = outputStream;
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

}