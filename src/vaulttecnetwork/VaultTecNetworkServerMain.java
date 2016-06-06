package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VaultTecNetworkServerMain implements WindowListener{

private final static Logger LOGGER = Logger.getLogger(VaultTecNetworkServerMain.class.getName());
private FileHandler fileHandler = null;
private final int SERVER_PORT = 1201;
private int count = 1;
private JFrame frame;
private JTextArea info;
private JPanel infoPane, buttonPane, newsPane;
private JButton addMessageButton, checkIPButton;
private JScrollPane infoScroll, newsScroll;
private ServerSocket serverSocket;
private Socket tempSocket;
private ObjectInputStream tempInputStream;
private ObjectOutputStream tempOutputStream;
private HashMap<Integer, ServerConnectors> users;
private Date currentDate;
private SimpleDateFormat sdf;
private HashMap<Integer, News> news;
private HashMap<Integer, Buttons> newsList;
private int numberOfNews = 0;
private JDialog newNewsDialog;
private JTextField newNewsDialogHeadline;
private JTextArea newNewsDialogMessage;
private JButton newNewsDialogAccept;
private JPanel newNewsDialogHeadlinePanel, newNewsDialogMessagePanel, newNewsDialogButtonPanel;
private JScrollPane newNewsDialogScrollPane;
private final JLabel newNewsDialogHeadlineTitle = new JLabel("Nag³ówek");


public void startGUI()
{
	frame = new JFrame("Vault-Tec Network Server. RobCo Industries.");
	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	frame.setSize(650, 500);
	frame.setResizable(false);
	frame.setLayout(new BorderLayout());
	frame.setLocationRelativeTo(null);
	infoPane = new JPanel(new BorderLayout());
	buttonPane = new JPanel();
	newsPane = new JPanel();
	newsPane.setPreferredSize(new Dimension(180, 80));
	
	newNewsDialogHeadline = new JTextField(20);
	newNewsDialogMessage = new JTextArea(25,30);
	newNewsDialogMessage.setWrapStyleWord(true);
	newNewsDialogMessage.setWrapStyleWord(true);
	newNewsDialogAccept = new JButton("Zapisz niusa");
	newNewsDialogHeadlinePanel = new JPanel(new FlowLayout()); 
	newNewsDialogMessagePanel = new JPanel(new BorderLayout());
	newNewsDialogButtonPanel = new JPanel(new FlowLayout());
	newNewsDialogScrollPane = new JScrollPane(newNewsDialogMessagePanel);
	
	newNewsDialog = new JDialog(frame, "Dodaj kolejnego niusa", true);
	newNewsDialog.setSize(420, 400);
	newNewsDialog.setLayout(new BorderLayout());
	newNewsDialog.setLocationRelativeTo(frame);
	//newNewsDialog.setResizable(false);
	
	newNewsDialogHeadlinePanel.add(newNewsDialogHeadlineTitle);
	newNewsDialogHeadlinePanel.add(newNewsDialogHeadline);
	newNewsDialogMessagePanel.add(newNewsDialogMessage, BorderLayout.CENTER);
	newNewsDialogButtonPanel.add(newNewsDialogAccept);
	newNewsDialog.add(newNewsDialogHeadlinePanel, BorderLayout.NORTH);
	newNewsDialog.add(newNewsDialogScrollPane, BorderLayout.CENTER);
	newNewsDialog.add(newNewsDialogButtonPanel, BorderLayout.SOUTH);
	
	addMessageButton = new JButton("Dodaj niusa");
	addMessageButton.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			newNewsDialogHeadline.setText("");
			newNewsDialogMessage.setText("");
			newNewsDialog.setVisible(true);
		}
	});
	
	newNewsDialogAccept.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			newNewsDialog.dispose();
			numberOfNews++;
			news.put(numberOfNews, new News(numberOfNews, newNewsDialogHeadline.getText().toString(), newNewsDialogMessage.getText().toString()));
			newsList.put(numberOfNews, new Buttons(news.get(numberOfNews).getHeadline(), numberOfNews));
			newsList.get(numberOfNews).addActionListener(new NewsButtonListener(numberOfNews));
			newsPane.setPreferredSize(new Dimension(180, (numberOfNews*60)));
			rysujListeNiusow(newsPane, newsList, news);
		}
	});
	
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
	newsScroll = new JScrollPane(newsPane);
	infoPane.add(infoScroll);
	buttonPane.add(addMessageButton);
	buttonPane.add(checkIPButton);
	frame.add(infoPane, BorderLayout.CENTER);
	frame.add(buttonPane, BorderLayout.SOUTH);
	frame.add(newsScroll, BorderLayout.EAST);
	frame.addWindowListener(this);
	frame.setVisible(true);
}

public VaultTecNetworkServerMain()
{	
	super();
}

public class NewsButtonListener implements ActionListener
{

private int number;	

public NewsButtonListener(int i)
{
	number = i;
}
	
@Override
public void actionPerformed(ActionEvent e)
{
	JFrame r = new JFrame(news.get(number).getHeadline());
	r.setSize(400, 400);
	r.setLocationRelativeTo(null);
	r.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	r.setLayout(new BorderLayout());
	JTextArea t = new JTextArea();
	t.setText(news.get(number).getNewstText());
	t.setEditable(false);
	r.add(t, BorderLayout.CENTER);
	r.setVisible(true);
}
	
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

public void rysujListeNiusow(JPanel p, HashMap<Integer, Buttons> l, HashMap<Integer, News> n)
{
	p.setLayout(null);
	for (int i = 0; i < l.size(); i++)
	{
		p.add(l.get(i+1));
	}
	newsScroll.repaint();
	newsScroll.revalidate();
}

public void serverStart()
{
	message("Server", " starting...");
	users = new HashMap<Integer, ServerConnectors>();
	news = new HashMap<Integer, News>();
	newsList = new HashMap<Integer, Buttons>();
	
	rysujListeNiusow(newsPane, newsList, news);
	
	try {
		serverSocket = new ServerSocket(SERVER_PORT);
		message("Serwer", "Adres lokalny serwera " +InetAddress.getLocalHost().toString());
		message("Serwer", "Oczekiwanie na u¿ytkowników ...");
		
		while (true)
		{
			tempSocket = serverSocket.accept();
			message("Serwer", "¯¹danie od klienta ...");
			tempOutputStream = new ObjectOutputStream(new BufferedOutputStream(tempSocket.getOutputStream()));
			users.put(count, new ServerConnectors(tempSocket, tempInputStream, tempOutputStream));

			if (news.size() > 0) {
				users.get(count).getOutputStream().writeObject(news);
				users.get(count).getOutputStream().flush();
			}
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

	
public static void main(String[] args) {

	VaultTecNetworkServerMain vtnsMain = new VaultTecNetworkServerMain();
	vtnsMain.startGUI();
	vtnsMain.serverStart();
}


private class Buttons extends JButton
{

private static final long serialVersionUID = 1L;

private Buttons(String s, int n)
{
	super(s);
	setBounds(10, -50 + (n*60), 120, 45);
}
}

}