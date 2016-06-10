package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;


public class VTNC_GUI extends JFrame {

private static final long serialVersionUID = 1L;
private final String INITIAL_TERMINAL_TEXT = "VAULT-TEC NETWORK CLIENT v.1.0\nEMPLOYEE ACCESS TERMINAL\n==========================================";
private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.png"));
private final ImageIcon CURSOR = new ImageIcon(getClass().getResource("/res/cursor1.png"));
private final URL soundFile1 = getClass().getResource("/res/sound1.wav");
private final URL soundFile2 = getClass().getResource("/res/sound2.wav");
private final LineBorder RED_BORDER = new LineBorder(Color.RED, 2, true);
private AudioInputStream beepStream;
private Clip beep;
private final InputStream FALLOUT_FONT = getClass().getResourceAsStream("/res/FalloutFont.ttf");
public static Font falloutFont = null;
private JPanel cPanel, textPanel;
private Button bPower, bHelp, bRefresh;
private HashMap<Integer, Button> buttons = new HashMap<Integer, Button>();
private JTextArea terminalTextArea;
private JWindow helpWindow;
private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
private final int szerokosc = (int) screenSize.getWidth();
private final int wysokosc = (int) screenSize.getHeight();
private int selectedOption = 0;
private int max_option;
private Date currentDate;
private SimpleDateFormat sdf;
private FileHandler fileHandler;
private final static Logger LOGGER = Logger.getLogger(VaultTecNetworkClientMain.class.getName());
private int selected = 1;
private boolean showHelp = false;
private Socket socket;
private ObjectInputStream ois;
private Cursor defaultCursor;
private HashMap<Integer, News> news;



	
public VTNC_GUI()
{
	super("Vault-Tec Unified Customer Network Communicator");	
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
	this.setLayout(new BorderLayout());
	this.setUndecorated(true);
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	this.setLocationRelativeTo(null);
	
	defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(CURSOR.getImage(), new Point(0, 0), "cursor");
	this.setCursor(defaultCursor);

	try {
		falloutFont = Font.createFont(Font.TRUETYPE_FONT, FALLOUT_FONT).deriveFont(26f);
	}
	catch (FontFormatException | IOException e)
	{
		zrzutLoga(e, true);
	} 
	
	cPanel = new JPanel(null)
	{
		private static final long serialVersionUID = 2L;
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(BACKGROUNDIMAGE.getImage(), 0, 0, getWidth(), getHeight(), this);
		}};
		
	terminalTextArea = new JTextArea();
	terminalTextArea.setFont(falloutFont);
	terminalTextArea.setForeground(new Color(041,225,140));
	terminalTextArea.setOpaque(false);
	terminalTextArea.setEditable(false);
	terminalTextArea.setFocusable(false);
	terminalTextArea.setText(INITIAL_TERMINAL_TEXT);

	textPanel = new JPanel(new BorderLayout());
	textPanel.setBounds(120, 200, 420, 80);
	textPanel.setOpaque(false);
	textPanel.setFocusable(false);
	textPanel.add(terminalTextArea);
	
	bPower = new Button(ButtonTypes.BPOWER, "");
	bPower.setFont(falloutFont);
	bPower.addFunctionButtonsMouseListner(new FunctionButtonsMouseListener());
	
	bHelp = new Button(ButtonTypes.BHELP, "");
	bHelp.setFont(falloutFont);
	bHelp.addFunctionButtonsMouseListner(new FunctionButtonsMouseListener());
	
	bRefresh = new Button(ButtonTypes.BREFRESH, "");
	bRefresh.addFunctionButtonsMouseListner(new FunctionButtonsMouseListener());
	
	defineHelpWindow();
	bPower.setBounds(25, wysokosc - 90, 60, 60);
	
	bHelp.setBounds(szerokosc - 95, wysokosc - 100, 60, 60);
	
	bRefresh.setBounds(szerokosc - 95, 10, 60, 60);
	cPanel.add(bPower);
	cPanel.add(bHelp);
	cPanel.add(bRefresh);
	cPanel.add(textPanel);
	news = new HashMap<Integer, News>();
	this.addKeyListener(new KeyboardListener());
	this.add(cPanel);
}

public void defineHelpWindow()
{
	helpWindow = new JWindow();
	helpWindow.setBounds(szerokosc - 350, wysokosc - 300, 300, 150);
	helpWindow.setBackground(Color.BLACK);
	helpWindow.setForeground(Color.WHITE);
	helpWindow.setCursor(defaultCursor);
	JTextArea helpWindowText = new JTextArea("  POMOC DLA PROGRAMU...\n\n  Strza³ki (góra/dó³) - poruszanie po menu\n  Enter - wybór opcji\n  Przycisk POWER - zamkniecie\n  Przycisk HELP (F1) - Pomoc");
	helpWindowText.setEditable(false);
	helpWindow.add(helpWindowText);
}

public void rysujButtony(HashMap<Integer, Button> b)
{
	
	for (int i = 1; i < buttons.size()+1; i++)
	{
		b.get(i).setBounds(115, 290 + (i*50), 300, 40);
		cPanel.add(b.get(i));
		b.get(i).addOptionButtonsMouseListener(new OptionButtonsMouseListener(buttons));
	}
}

public void messageSound(URL s)
{
	 try {
		 	beepStream = AudioSystem.getAudioInputStream((s));
	        beep = AudioSystem.getClip();
	        beep.open(beepStream);
	        beep.start();
	    } catch(Exception ex) {
	    	zrzutLoga(ex, true);
	    }
}

public void zrzutLoga(Exception e, Boolean closeProgram)
{
	currentDate = new Date();
	sdf = new SimpleDateFormat("YYYY.MM.dd-HH.mm.ss");
	try {
		fileHandler = new FileHandler("client_crash_" +sdf.format(currentDate) +".log", false);
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
	LOGGER.setUseParentHandlers(false);
	LOGGER.log(Level.WARNING, e.getMessage(), e);
	if (closeProgram) System.exit(-1);
}

public void showInfoPane(String head, String msg)
{
	JWindow d = new JWindow();
	d.setLayout(null);
	d.setSize(400, 400);
	d.setCursor(defaultCursor);
	d.setLocationRelativeTo(this);
	JTextField dHead = new JTextField(head);
	dHead.setEditable(false);
	dHead.setBounds(5,5, 380, 20);
	JTextArea dMsg = new JTextArea(msg);
	dMsg.setBounds(5,25,380, 330);
	dMsg.setEditable(false);
	JButton b1 = new JButton("Zamknij");
	b1.setBounds(10, 360, 100, 30);
	b1.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			d.dispose();
		}
	});
	d.add(dHead);
	d.add(dMsg);
	d.add(b1);
	d.setVisible(true);
}

public void showError(String errMsg)
{
	JOptionPane.showMessageDialog(this, errMsg, "ERROR!", JOptionPane.ERROR_MESSAGE);
}



public class KeyboardListener implements KeyListener
{
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_F1) {
			showHelp = !showHelp;
			helpWindow.setVisible(showHelp);
		}
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if ((selected >= 1))
			{
				if (selected == 1)
				{
					buttons.get(selected).deselectOption();
					selected=buttons.size();
					buttons.get(selected).selectOption();
					messageSound(soundFile1);
				}
				else {
					buttons.get(selected).deselectOption();
					selected--;
					buttons.get(selected).selectOption();
					messageSound(soundFile1);
				}
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if (selected <= buttons.size())
			{
				if (selected == buttons.size())
				{
					buttons.get(selected).deselectOption();
					selected=1;
					buttons.get(selected).selectOption();
					messageSound(soundFile1);
				}
				else {
					buttons.get(selected).deselectOption();
					selected++;
					buttons.get(selected).selectOption();
					messageSound(soundFile1);
				}
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			messageSound(soundFile1);
			showInfoPane(news.get(selected).getHeadline(), news.get(selected).getNewstText());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}



public class OptionButtonsMouseListener implements MouseListener
{
private HashMap<Integer, Button> btns;

public OptionButtonsMouseListener(HashMap<Integer, Button> b)
{
	btns = b;
}

	@Override
	public void mouseClicked(MouseEvent e) {
		showInfoPane(news.get(selected).getHeadline(), news.get(selected).getNewstText());
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = 1; i < buttons.size()+1; i++) btns.get(i).deselectOption();
		int i;
		for (i = 1; i < btns.size()+1; i++)
		{
			if (e.getComponent() == btns.get(i))
				break;
		}
		selected = i;
		btns.get(selected).selectOption();
		messageSound(soundFile1);
	}

	@Override
	public void mouseExited(MouseEvent e) {}
}


public class FunctionButtonsMouseListener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == bPower) System.exit(0);
		else if (e.getComponent() == bHelp) 
			{
				showHelp = !showHelp;
				helpWindow.setVisible(showHelp);
			}
		else if (e.getComponent() == bRefresh)
		{

	 		socket = new Socket();
	 		try {
	 			defaultCursor = getCursor();
	 			setCursor(new Cursor(Cursor.WAIT_CURSOR));
	 			socket.connect(new InetSocketAddress("127.0.0.1", 1201), 5000); // 5 sek. timeout
	 			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

	 			for (int i = 1; i < buttons.size(); i++) {
	 				buttons.get(i).deselectOption();
	 			}
	 			
	 			news = (HashMap<Integer, News>) ois.readObject();
	 			
	 			buttons = new HashMap<Integer, Button>();
	 			setCursor(defaultCursor);
	 				 			
	 			for (int i = 1; i < news.size()+1; i++)
	 			{
					buttons.put(i, new Button(ButtonTypes.BOPTION, "> " +news.get(i).getHeadline()));
	 			}

	 			
	 			//selected = 1;
	 			//buttons.get(selected).selectOption();
				rysujButtony(buttons);
				cPanel.repaint();
	 		}

	 		catch (IOException ioe)
	 		{
	 			ioe.printStackTrace();
	 			zrzutLoga(ioe, true);
 			}
	 		catch (ClassNotFoundException cnfe)
	 		{
	 			cnfe.printStackTrace();
	 			zrzutLoga(cnfe, true);
	 		}
 		}
 	}


	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getComponent() == bPower)
		{
			bPower.setBorder(RED_BORDER);
			messageSound(soundFile1);
		}
		else if (e.getComponent() == bHelp)
		{
			bHelp.setBorder(RED_BORDER);
			messageSound(soundFile1);
		}
		else if (e.getComponent() == bRefresh)
		{
			bRefresh.setBorder(RED_BORDER);
			messageSound(soundFile1);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getComponent() == bPower)
		{
			bPower.setBorder(null);
		}
		else if (e.getComponent() == bHelp)
		{
			bHelp.setBorder(null);
		}
		else if (e.getComponent() == bRefresh)
		{
			bRefresh.setBorder(null);
		}
	}
	
}
}