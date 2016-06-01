package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class VTNCView extends JFrame{

	
private static final long serialVersionUID = 1L;
private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.png"));
private final InputStream FALLOUT_FONT = getClass().getResourceAsStream("/res/FalloutFont.ttf");
private final String INITIAL_TERMINAL_TEXT_AREA = "";
public static Font falloutFont = null;
private VTNCModel vtncModel;
private JPanel cPanel, textPanel;
private Button bExit, bHelp;
private HashMap<Integer, Button> buttons = new HashMap<Integer, Button>();
private JTextArea terminalTextArea;
private JWindow helpWindow;
private String terminalText;
private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
private final int szerokosc = (int) screenSize.getWidth();
private final int wysokosc = (int) screenSize.getHeight();
private int selectedOption = 0;
private int max_option;


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
		e.printStackTrace();
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
	
	bExit = new Button(ButtonTypes.BEXIT, "");
	bExit.setFont(falloutFont);
	bHelp = new Button(ButtonTypes.BHELP, "");
	bHelp.setFont(falloutFont);
	
	defineHelpWindow();
	
	bExit.setBounds(25, wysokosc - 90, 60, 60);
	cPanel.add(bExit);
	bHelp.setBounds(szerokosc - 95, wysokosc - 100, 60, 60);
	cPanel.add(bHelp);
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
	bExit.addActionListener(a);
	bHelp.addActionListener(a);
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
	case BEXIT: {
		return bExit;
	}
	case BHELP: {
		return bHelp;
	}
	default: return null;
	}
}

}