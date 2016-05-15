package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class VaultTecNetworkServer {

private final URL FALLOUT_FONT = getClass().getResource("/res/FalloutFont.ttf");
private final ImageIcon TERMINALBACKGROUND = new ImageIcon(getClass().getResource("/res/terminal_background.jpg"));
private Font falloutFont;
private JFrame frame;
private Boolean fontIsLoaded;
private JTextArea info;
private String h = "\n                  ROBCO INDUSTRIES UNIFIED OPERATING SYSTEM\n                      "
		+ "COPYRIGHT 2075-2077 ROBCO INDUSTRIES\n                                 "
		+ "- Server 1 -\n\n"
		+ " - RobCo Communicator Management System -\n"
		+ " ==========================================\n\n";


	
public VaultTecNetworkServer()
{
	frame = new JFrame("Vault-Tec Network Server. RobCo Industries.");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(780, 497);
	frame.setResizable(false);
	frame.setLayout(new BorderLayout());
	frame.setLocationRelativeTo(null);
	
	
	
	
	fontIsLoaded = false;
	try {
		 falloutFont = Font.createFont(Font.TRUETYPE_FONT, new File(FALLOUT_FONT.toURI())).deriveFont(16f); 
	     fontIsLoaded = true;
	} catch (Exception e) {
	     e.printStackTrace();
	     fontIsLoaded = false;
	     System.exit(-1);
	}
	
	info = new JTextArea(h){
		private static final long serialVersionUID = 1L;
		Image image = TERMINALBACKGROUND.getImage();
	      {
	    	 setForeground(new Color(041,225,140));
	    	 //setBackground(new Color(014,046,032));
	    	 setOpaque(false);
	      }

	  public void paint(Graphics g) {
	    g.drawImage(image, -10, -5, this);
	    super.paint(g);
	  }
	};	
	
	if (fontIsLoaded) info.setFont(falloutFont);
	info.setEditable(false);
	info.setLineWrap(true);
	info.setWrapStyleWord(true);
	
	
	
	frame.add(info);
	
	frame.setVisible(true);	
}
	
public static void main(String[] args) {

EventQueue.invokeLater(new Runnable()
{
	public void run()
	{
		new VaultTecNetworkServer();
	}
});
}

}