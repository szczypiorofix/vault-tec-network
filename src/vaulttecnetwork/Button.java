package vaulttecnetwork;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{

private static final long serialVersionUID = 1L;
private final ImageIcon BUTTONIMAGE = new ImageIcon(getClass().getResource("/res/button.png"));
private ButtonTypes typ;





public Button(ButtonTypes t)
{
	super();
	setOpaque(false);
	typ = t;
}
	
@Override
protected void paintComponent(Graphics g)
{
	super.paintComponent(g);
	switch (typ)
	{
	case BEXIT: {
		g.drawImage(BUTTONIMAGE.getImage(), 0, 0, this);
	}
	case BADD: {
		g.drawImage(BUTTONIMAGE.getImage(), 0, 0, 20, 20, this);
	}
	default: {
		g.drawImage(BUTTONIMAGE.getImage(), 0, 0, 20, 20, this);
	}
	}
}
}



