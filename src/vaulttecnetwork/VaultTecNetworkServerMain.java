package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VaultTecNetworkServerMain implements WindowListener{

private final static Logger LOGGER = Logger.getLogger(VaultTecNetworkServerMain.class.getName());
private FileHandler fileHandler = null;
private final int SERVER_PORT = 1201;
private int count = 1;
private JFrame frame;
private JTextArea info;
private JPanel infoPane, buttonPane;
private JButton addMessageButton, checkIPButton;
private JScrollPane infoScroll;
private ServerSocket serverSocket;
private Socket tempSocket;
private ObjectInputStream tempInputStream;
private ObjectOutputStream tempOutputStream;
private HashMap<Integer, ServerConnectors> users;
private Date currentDate;
private SimpleDateFormat sdf;
private SendingData data;



public VaultTecNetworkServerMain()
{	
	frame = new JFrame("Vault-Tec Network Server. RobCo Industries.");
	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	frame.setSize(450, 500);
	frame.setResizable(false);
	frame.setLayout(new BorderLayout());
	frame.setLocationRelativeTo(null);
	infoPane = new JPanel(new BorderLayout());
	buttonPane = new JPanel();
	addMessageButton = new JButton("Dodaj wiadomoœæ");
	checkIPButton = new JButton("Moje IP");
	checkIPButton.addActionListener(new ActionListener()
	{
		private URL whatismyip;
		private BufferedReader readerIP;
		private String externalIP;
		private Boolean checkIP;
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			checkIP = true;			
			try {
				whatismyip = new URL("http://checkip.amazonaws.com");
				readerIP = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				externalIP = readerIP.readLine();
			}
			catch (Exception uhe)
			{
				checkIP = false;
				checkIPButton.setText("B³¹d po³¹czenia!");
			}
			finally
			{
				if (readerIP != null) 
					{
						try {
						readerIP.close();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
							System.exit(-1);
						}
					}
			}
			if (checkIP) checkIPButton.setText("Moje IP: " +externalIP);
		}
	});
	info = new JTextArea();
	info.setEditable(false);
	info.setLineWrap(true);
	info.setWrapStyleWord(true);
	infoScroll = new JScrollPane(info);
	infoPane.add(infoScroll);
	buttonPane.add(addMessageButton);
	buttonPane.add(checkIPButton);
	frame.add(infoPane, BorderLayout.CENTER);
	frame.add(buttonPane, BorderLayout.SOUTH);
	frame.addWindowListener(this);
	frame.setVisible(true);
	new Thread(new Runnable()
		{
			public void run()
			{
				serverStart();
			}
		}).start();
}

public void zrzutLoga(Exception e)
{
	currentDate = new Date();
	sdf = new SimpleDateFormat("YYYY.MM.dd-HH.mm.ss");
	try {
		fileHandler = new FileHandler("server" +sdf.format(currentDate) +".log", false);
	} catch (SecurityException se) {
		se.printStackTrace();
		System.exit(-1);
	} catch (IOException ioe) {
		ioe.printStackTrace();
		System.exit(-1);
	}
	fileHandler.setFormatter(new SimpleFormatter());
	fileHandler.setLevel(Level.ALL);
	LOGGER.addHandler(fileHandler);
	LOGGER.log(Level.WARNING, e.getMessage(), e);
	System.exit(-1);
}

public void message(String name, String msg)
{
	currentDate = new Date();
	sdf = new SimpleDateFormat("HH:mm:ss");
	info.append(" "+sdf.format(currentDate) +" "+name +": " +msg +"\n");
}

public void serverStart()
{
	message("Server", "Hello Staffi!");
	users = new HashMap<Integer, ServerConnectors>();
	
	try {
		serverSocket = new ServerSocket(SERVER_PORT);
		message("Serwer", "Adres lokalny serwera " +InetAddress.getLocalHost().toString());
		message("Serwer", "Oczekiwanie na u¿ytkowników ...");
		
		while (true)
		{
			tempSocket = serverSocket.accept();
			tempInputStream = new ObjectInputStream(new BufferedInputStream(tempSocket.getInputStream()));
			tempOutputStream = new ObjectOutputStream(new BufferedOutputStream(tempSocket.getOutputStream()));
			users.put(count, new ServerConnectors(count, new char[0], tempSocket, tempInputStream, tempOutputStream));
			try {
			data = (SendingData) tempInputStream.readObject();
			message("Serwer", data.getWho() +" " +data.getHeadline() +" " +data.getMessage());
			}
			catch (ClassNotFoundException cnfe)
			{
				cnfe.printStackTrace();
				zrzutLoga(cnfe);
			}
			data.setMessage("NEW MESSAGE!");
			users.get(count).getOutputStream().writeObject(data);
			users.get(count).getOutputStream().flush();
			
			new Thread(new ServerThread(users, count)).start();
			count++;
		}
	}
	catch (SocketException se)
	{
		zrzutLoga(se);
	}
	catch (IOException ioe)
	{
		zrzutLoga(ioe);
	}
	finally {
		try {
			serverSocket.close();
		} catch (IOException e) {
			zrzutLoga(e);
		}
	}
}

public class ServerThread implements Runnable
{

private HashMap<Integer, ServerConnectors> connectors;
private int number;
private SendingData threadData;


public ServerThread(HashMap<Integer, ServerConnectors> c, int i)
{
	connectors = c;
	number = i;
}
	
public void run()
{
	while (true)
	{
		message("OK", "dzia³a w¹tek!");
		try {
		threadData = (SendingData) connectors.get(number).getInputStream().readObject();
		message("OK", threadData.getHeadline() +" " +threadData.getMessage());
		}
		catch (ClassNotFoundException | IOException e)
		{
			zrzutLoga(e);
		}	
	}
}
}

public static void main(String[] args) {

EventQueue.invokeLater(new Runnable()
{
	public void run()
	{
		new VaultTecNetworkServerMain();
	}
});
}

@Override
public void windowOpened(WindowEvent e) {}

@Override
public void windowClosing(WindowEvent e) {
	System.exit(0);
}

@Override
public void windowClosed(WindowEvent e) {}

@Override
public void windowIconified(WindowEvent e) {}

@Override
public void windowDeiconified(WindowEvent e) {}

@Override
public void windowActivated(WindowEvent e) {}

@Override
public void windowDeactivated(WindowEvent e) {}

}