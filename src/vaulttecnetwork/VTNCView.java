package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

public class VTNCView extends JFrame{

	
private static final long serialVersionUID = 1L;
private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.png"));
private AudioInputStream beepStream;
private Clip beep;
private final InputStream FALLOUT_FONT = getClass().getResourceAsStream("/res/FalloutFont.ttf");
private final String INITIAL_TERMINAL_TEXT_AREA = "";
public static Font falloutFont = null;
private VTNCModel vtncModel;
private JPanel cPanel, textPanel;
private Button bPower, bHelp, bRefresh;
private HashMap<Integer, Button> buttons = new HashMap<Integer, Button>();
private JTextArea terminalTextArea;
private JWindow helpWindow;
private String terminalText;
private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
private final int szerokosc = (int) screenSize.getWidth();
private final int wysokosc = (int) screenSize.getHeight();
private int selectedOption = 0;
private int max_option;
private Date currentDate;
private SimpleDateFormat sdf;
private FileHandler fileHandler;
private final static Logger LOGGER = Logger.getLogger(VaultTecNetworkClientMain.class.getName());


public VTNCView(VTNCModel mModel)
{
	super("Vault-Tec Unified Customer Network Communicator");
	vtncModel = mModel;
	
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
	terminalTextArea.setText(vtncModel.getDefText());

	textPanel = new JPanel(new BorderLayout());
	textPanel.setBounds(120, 200, 420, 400);
	textPanel.setOpaque(false);
	textPanel.setFocusable(false);
	textPanel.add(terminalTextArea);
	
	buttons.put(1, new Button(ButtonTypes.BOPTION, "> OPTION 1"));
	buttons.put(2, new Button(ButtonTypes.BOPTION, "> OPTION 2"));
	buttons.put(3, new Button(ButtonTypes.BOPTION, "> OPTION 3"));
	buttons.put(4, new Button(ButtonTypes.BOPTION, "> OPTION 4"));
	buttons.put(5, new Button(ButtonTypes.BOPTION, "> OPTION 5"));
	buttons.put(6, new Button(ButtonTypes.BOPTION, "> OPTION 6"));
	
	rysujButtony(buttons);
		
	setMaxOption(buttons.size());
	setSelectedOption(1);
	selectOption(getSelectedOption());
	
	bPower = new Button(ButtonTypes.BPOWER, "");
	bPower.setFont(falloutFont);
	bHelp = new Button(ButtonTypes.BHELP, "");
	bHelp.setFont(falloutFont);
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

public void showHelp()
{
	helpWindow.setVisible(true);
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
		buttons.get(i).setBounds(110, 250 + (i*50), 300, 40);
		cPanel.add(buttons.get(i));
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

public void selectButton(Button b)
{
	b.setBorder(new LineBorder(Color.RED, 2, true));
}

public void deselectButton(Button b)
{
	b.setBorder(null);
}

public ButtonTypes buttonType(Button b)
{
	return b.typButtona();
}

public Button getPowerButton()
{
	return bPower;
}

public Button getHelpButton()
{
	return bHelp;
}

public Button getRefreshButton()
{
	return bRefresh;
}

public HashMap<Integer, Button> getOptionButtons()
{
	return buttons;
}

public boolean helpIsVisible()
{
	return helpWindow.isVisible();
}

public void helpVisible(boolean b)
{
	helpWindow.setVisible(b);
}

public void addButtonListener(ActionListener a)
{
	bPower.addActionListener(a);
	bHelp.addActionListener(a);
	bRefresh.addActionListener(a);
}

public void addOptionButtonsMouseListener(MouseListener m)
{
	for (int i = 1; i < buttons.size()+1; i++) buttons.get(i).addMouseListener(m);
}

public void addFunctionButtonsMouseListener(MouseListener m)
{
	bPower.addMouseListener(m);
	bHelp.addMouseListener(m);
	bRefresh.addMouseListener(m);
}

public void addKeyboardListener(KeyListener k)
{
	this.addKeyListener(k);
}

public void resetTerminalTextArea()
{
	terminalTextArea.setText(INITIAL_TERMINAL_TEXT_AREA);
}

public void setTerminalTextArea(String s)
{
	terminalTextArea.setText(s);
}

public void setSelectedOption(int s)
{
	selectedOption = s;
}

public int getSelectedOption()
{
	return selectedOption;
}

public void selectOption(int i)
{
	buttons.get(i).selectOption();
}

public void deselectOption(int i)
{
	buttons.get(i).deselectOption();
}

public int getMaxOption()
{
	return max_option;
}

public void setMaxOption(int m)
{
	max_option = m;
}

public JButton whichButton (ButtonTypes b)
{
	switch (b)
	{
	case BPOWER: {
		return bPower;
	}
	case BHELP: {
		return bHelp;
	}
	case BREFRESH: {
		return bRefresh;
	}
	default: return null;
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

public void showInfoPane(String msg)
{
	JOptionPane.showMessageDialog(this, msg, "", JOptionPane.INFORMATION_MESSAGE);
}

public void showError(String errMsg)
{
	JOptionPane.showMessageDialog(this, errMsg, "ERROR!", JOptionPane.ERROR_MESSAGE);
}

}