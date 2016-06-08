package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
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
import javax.swing.JWindow;
import javax.swing.border.LineBorder;


public class VTNC_GUI extends JFrame{

private static final long serialVersionUID = 1L;
private final String INITIAL_TERMINAL_TEXT = "VAULT-TEC NETWORK CLIENT v.1.0\nEMPLOYEE ACCESS TERMINAL\n==========================================";
private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.png"));
private final URL soundFile1 = getClass().getResource("/res/sound1.wav");
private final URL soundFile2 = getClass().getResource("/res/sound2.wav");
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
private int selected;
private boolean help = false;
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
	textPanel.setBounds(120, 200, 420, 400);
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
	
	defineHelpWindow();
	
	bPower.setBounds(25, wysokosc - 90, 60, 60);
	cPanel.add(bPower);
	bHelp.setBounds(szerokosc - 95, wysokosc - 100, 60, 60);
	cPanel.add(bHelp);
	bRefresh.setBounds(szerokosc - 95, 10, 60, 60);
	cPanel.add(bRefresh);
	cPanel.add(textPanel);
	this.add(cPanel);
	news = new HashMap<Integer, News>();
	
	
	
	buttons.put(1, new Button(ButtonTypes.BOPTION, "AAAAAAAAA"));
	
	
	
	rysujButtony(buttons);
}

public void defineHelpWindow()
{
	helpWindow = new JWindow();
	helpWindow.setBounds(szerokosc - 350, wysokosc - 300, 300, 150);
	helpWindow.setBackground(Color.BLACK);
	helpWindow.setForeground(Color.WHITE);
	JTextArea helpWindowText = new JTextArea("  POMOC DLA PROGRAMU...\n\n  Strza³ki (góra/dó³) - poruszanie po menu\n  Enter - wybór opcji\n  Przycisk POWER - zamkniecie\n  Przycisk HELP (F1) - Pomoc");
	helpWindowText.setEditable(false);
	helpWindowText.setFocusable(false);
	helpWindow.add(helpWindowText);
}

public void showHelp(boolean b)
{
	helpWindow.setVisible(b);
	this.addKeyListener(new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) helpWindow.setVisible(false);
		}

		@Override
		public void keyReleased(KeyEvent e) {}	
	});
}

public void rysujButtony(HashMap<Integer, Button> b)
{
	for (int i = 1; i < buttons.size()+1; i++)
	{
		b.get(i).setBounds(110, 250 + (i*50), 300, 40);
		cPanel.add(b.get(i));
	}
	cPanel.revalidate();
	cPanel.repaint();
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
		fileHandler = new FileHandler("client" +sdf.format(currentDate) +".log", false);
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
	JDialog d = new JDialog(this, head, true);
	d.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	d.setSize(400, 400);
	d.setLocationRelativeTo(this);
	d.add(new JTextArea(msg));
	d.setVisible(true);
}

public void showError(String errMsg)
{
	JOptionPane.showMessageDialog(this, errMsg, "ERROR!", JOptionPane.ERROR_MESSAGE);
}


public class FunctionButtonsMouseListener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == bPower) System.exit(0);
		else if (e.getComponent() == bHelp) 
			{
				help = !help;
				showHelp(help);
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}


}