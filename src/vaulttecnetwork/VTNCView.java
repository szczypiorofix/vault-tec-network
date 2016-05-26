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
private Font falloutFont;
private VTNCModel vtncModel;
private JPanel cPanel, panelC, panelW, panelE, panelN, panelS;
private Button bExit, bAdd;
private JTextArea terminalTextArea;
private String terminalText;
private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


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
	
	
	cPanel = new JPanel(new BorderLayout())
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
	terminalTextArea.setText(vtncModel.getDefText());

	bExit = new Button("EXIT");
	bExit.setFont(falloutFont);
	bAdd = new Button("Dodaj conieco...");
	bAdd.setFont(falloutFont);
	
	panelN = new JPanel(new BorderLayout());
	panelW = new JPanel(new BorderLayout());
	panelS = new JPanel(new BorderLayout());
	panelC = new JPanel(new BorderLayout());
	panelE = new JPanel(new BorderLayout());
	panelN.setOpaque(false);
	panelS.setOpaque(false);
	panelE.setOpaque(false);
	panelW.setOpaque(false);
	panelC.setOpaque(false);
	
	panelS.add(bExit, BorderLayout.WEST);
	panelN.add(bAdd, BorderLayout.EAST);
	panelC.add(terminalTextArea, BorderLayout.CENTER);
	
	cPanel.add(panelN, BorderLayout.NORTH);
	cPanel.add(panelS, BorderLayout.SOUTH);
	cPanel.add(panelW, BorderLayout.WEST);
	cPanel.add(panelE, BorderLayout.EAST);
	cPanel.add(panelC, BorderLayout.CENTER);
	this.add(cPanel, BorderLayout.CENTER);
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