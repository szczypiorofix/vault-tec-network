package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VTNCView extends JFrame{

	
private static final long serialVersionUID = 1L;
private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.jpg"));
private VTNCModel vtncModel;
private JPanel cPanel;
private JButton bOK;
private final int bOKx = 400, bOKy = 200;



public VTNCView(VTNCModel mModel)
{
	super("Vault-Tec Unified Customer Network Communicator");
	
	vtncModel = mModel;
	
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
	this.setLayout(new BorderLayout());
	this.setUndecorated(true);
	this.setLocationRelativeTo(null);

	cPanel = new JPanel()
	{
		private static final long serialVersionUID = 2L;
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(BACKGROUNDIMAGE.getImage(), 0, 0, getWidth(), getHeight(), this);
		}};
	
	cPanel.setLayout(null);
	bOK = new JButton("OK");
	bOK.setBounds(bOKx, bOKy, 60, 50);
	cPanel.add(bOK);
	this.add(cPanel, BorderLayout.CENTER);
}

public void addOKButtonListener(ActionListener a)
{
	bOK.addActionListener(a);
}

}