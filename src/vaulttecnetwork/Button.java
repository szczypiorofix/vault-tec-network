package vaulttecnetwork;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{

private static final long serialVersionUID = 1L;
private final ImageIcon BUTTONIMAGE = new ImageIcon(getClass().getResource("/res/button.png"));






public Button(String title)
{
	super();
	
	//setBorderPainted(false);
	setText(title);
	setOpaque(false);
}
	
@Override
protected void paintComponent(Graphics g)
{
	super.paintComponent(g);
	g.drawImage(BUTTONIMAGE.getImage(), 0, 0, getWidth(), getHeight(), this);
}};



