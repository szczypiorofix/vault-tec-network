package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VTNCView extends JFrame{

	
private static final long serialVersionUID = 1L;
private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.png"));
private final InputStream FALLOUT_FONT = getClass().getResourceAsStream("/res/FalloutFont.ttf");
private final String INITIAL_TERMINAL_TEXT_AREA = "";
public static Font falloutFont = null;
private VTNCModel vtncModel;
private JPanel cPanel, textPanel;
private Button bExit, bAdd;
private HashMap<Integer, Button> buttons = new HashMap<Integer, Button>();
private JTextArea terminalTextArea;
private String terminalText;
private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
private int szerokosc = (int) screenSize.getWidth();
private int wysokosc = (int) screenSize.getHeight();

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
		falloutFont = Font.createFont(Font.TRUETYPE_FONT, FALLOUT_FONT).deriveFont(22f);
	}
	catch (FontFormatException | IOException e)
	{
		e.printStackTrace();
	} 
	
	
	cPanel = new JPanel(null)
	{
		private static final long serialVersionUID = 2L;
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			screenSize = new Dimension(getWidth(), getHeight());
			g.drawImage(BACKGROUNDIMAGE.getImage(), 0, 0, getWidth(), getHeight(), this);
		}};
		
	terminalTextArea = new JTextArea();
	terminalTextArea.setFont(falloutFont);
	terminalTextArea.setForeground(new Color(041,225,140));
	terminalTextArea.setOpaque(false);
	terminalTextArea.setEditable(false);
	terminalTextArea.setText(vtncModel.getDefText());

	textPanel = new JPanel(new BorderLayout());
	textPanel.setBounds(400, 200, 400, 400);
	textPanel.setOpaque(false);
	textPanel.add(terminalTextArea);
	
	buttons.put(1, new Button(ButtonTypes.BOPTION, "> OPCJA 1"));
	buttons.put(2, new Button(ButtonTypes.BOPTION, "> OPCJA 2"));
	buttons.put(3, new Button(ButtonTypes.BOPTION, "> OPCJA 3"));
	
	buttons.get(1).setBounds(390, 250, 300, 50);
	buttons.get(2).setBounds(390, 300, 300, 50);
	buttons.get(3).setBounds(390, 350, 300, 50);

	cPanel.add(buttons.get(1));
	cPanel.add(buttons.get(2));
	cPanel.add(buttons.get(3));
	
	bExit = new Button(ButtonTypes.BEXIT, "");
	bExit.setFont(falloutFont);
	bAdd = new Button(ButtonTypes.BADD, "");
	bAdd.setFont(falloutFont);
	
	bExit.setBounds(25, wysokosc - 90, 60, 60);
	cPanel.add(bExit);
	bAdd.setBounds(szerokosc - 95, wysokosc - 100, 60, 60);
	cPanel.add(bAdd);
	
	cPanel.add(textPanel);

	this.add(cPanel);
}

public void addButtonListener(ActionListener a)
{
	bExit.addActionListener(a);
	bAdd.addActionListener(a);
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

public JButton whichButton (ButtonTypes b)
{
	switch (b)
	{
	case BEXIT: {
		return bExit;
	}
	case BADD: {
		return bAdd;
	}
	default: return null;
	}
}

}