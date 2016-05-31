package vaulttecnetwork;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Button extends JButton implements MouseListener{

private static final long serialVersionUID = 1L;

private final Color FONT_GREEN = new Color(041,225,140);
private final Color FONT_SELECT_GREEN = new Color(40, 180, 060);
private final Color FONT_DARK_GREEN = new Color(10, 80, 10);
private Image EXIT_BUTTONIMAGE;
private Image ADD_BUTTONIMAGE;
private ButtonTypes typ;
private String text;


public Button(ButtonTypes t, String txt)
{
	super();
	typ = t;
	text = txt;
	try {
	EXIT_BUTTONIMAGE = ImageIO.read(getClass().getResource("/res/power_button.png"));
	ADD_BUTTONIMAGE = ImageIO.read(getClass().getResource("/res/add_button.png"));
	}
	catch (IOException ioe)
	{
		
	}
	setOpaque(false);
	setContentAreaFilled(false);
	addMouseListener(this);
	setFocusable(false);
	setBorder(new EmptyBorder(5,5,5,5));	
	if (typ == ButtonTypes.BOPTION) {
		setHorizontalAlignment(SwingConstants.LEFT);
		setForeground(FONT_GREEN);
		setFont(VTNCView.falloutFont);
		setText(text);
	}
}

public void selectOption()
{
	setOpaque(true);
	setBackground(FONT_SELECT_GREEN);
	setForeground(FONT_DARK_GREEN);
}

public void deselectOption()
{
	setOpaque(false);
	setBackground(null);
	setForeground(FONT_GREEN);
}

public Button whichButton()
{
	return this;
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
	case BOPTION:
		break;
	default:
		break;
	}
}

@Override
public void mouseClicked(MouseEvent arg0) {}

@Override
public void mouseEntered(MouseEvent arg0) {
	switch (typ)
	{
	case BOPTION: {
		selectOption();
		break;
	}
	default : {
		setBorder(new LineBorder(Color.RED, 2, true));
		break;
	}
	}
}

@Override
public void mouseExited(MouseEvent arg0) {
	
	switch (typ)
	{
	case BOPTION: {
		deselectOption();
		break;
	}
	default : {
		setBorder(new EmptyBorder(5,5,5,5));
	}
	}
}

@Override
public void mousePressed(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent arg0) {}
}



