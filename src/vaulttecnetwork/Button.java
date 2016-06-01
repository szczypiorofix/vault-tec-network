package vaulttecnetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Button extends JButton
{

private static final long serialVersionUID = 1L;

private final Color FONT_GREEN = new Color(041,225,140);
private final Color FONT_SELECT_GREEN = new Color(40, 180, 060);
private final Color FONT_DARK_GREEN = new Color(10, 80, 10);
private Image EXIT_BUTTONIMAGE;
private Image HELP_BUTTONIMAGE;
private ButtonTypes typ;
private String text;


public Button(ButtonTypes t, String txt, String name)
{
	super();
	setName(name);
	typ = t;
	text = txt;
	try {
	EXIT_BUTTONIMAGE = ImageIO.read(getClass().getResource("/res/power_button.png"));
	HELP_BUTTONIMAGE = ImageIO.read(getClass().getResource("/res/help_button.png"));
	}
	catch (IOException ioe)
	{
		
	}
	setOpaque(false);
	setContentAreaFilled(false);
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

public ButtonTypes typButtona()
{
	return typ;
}

@Override
protected void paintComponent(Graphics g)
{
	super.paintComponent(g);
	switch (typ)
	{
	case BPOWER: {
		g.drawImage(EXIT_BUTTONIMAGE, 0, 0, getWidth(), getHeight(), this);
		break;
	}
	case BHELP: {
		g.drawImage(HELP_BUTTONIMAGE, 0, 0, getWidth(), getHeight(), this);
		break;
	}
	case BOPTION:
		break;
	default:
		break;
	}
}
}