package vaulttecnetwork;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Button extends JButton implements MouseListener{

private static final long serialVersionUID = 1L;
private Image EXIT_BUTTONIMAGE;
private Image ADD_BUTTONIMAGE;
private ButtonTypes typ;





public Button(ButtonTypes t)
{
	super();
	typ = t;
	try {
	EXIT_BUTTONIMAGE = ImageIO.read(getClass().getResource("/res/exit_button.png"));
	ADD_BUTTONIMAGE = ImageIO.read(getClass().getResource("/res/add_button.png"));
	}
	catch (IOException ioe)
	{
		
	}
	setOpaque(false);
	//setBorderPainted(false);
	setContentAreaFilled(false);
	addMouseListener(this);
	setBorder(new EmptyBorder(5,5,5,5));
	//this.setRolloverEnabled(true);
	//this.setRolloverIcon(new ImageIcon(ADD_BUTTONIMAGE));
}
	

@Override
protected void paintComponent(Graphics g)
{
	super.paintComponent(g);
	switch (typ)
	{
	case BEXIT: {
		g.drawImage(EXIT_BUTTONIMAGE, 0, 0, getWidth(), getHeight(), this);
		break;
	}
	case BADD: {
		g.drawImage(ADD_BUTTONIMAGE, 0, 0, getWidth(), getHeight(), this);
		break;
	}
	}
}


@Override
public void mouseClicked(MouseEvent arg0) {}

@Override
public void mouseEntered(MouseEvent arg0) {
	setBorder(new LineBorder(Color.RED, 2, true));
}

@Override
public void mouseExited(MouseEvent arg0) {
	setBorder(new EmptyBorder(5,5,5,5));
}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
}



