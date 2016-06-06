package vaulttecnetwork;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class VTNCController {

private final URL soundFile1 = getClass().getResource("/res/sound1.wav");
private final URL soundFile2 = getClass().getResource("/res/sound2.wav");
private VTNCModel vtncModel;
private VTNCView vtncView;
private int selected;
private Socket socket;
private ObjectInputStream ois;
private Cursor defaultCursor;
private HashMap<Integer, News> news;
private HashMap<Integer, Button> mButtons;


public VTNCController(VTNCModel mModel, VTNCView mView)
{
	vtncModel = mModel;
	vtncView = mView;
	mButtons = vtncView.getOptionButtons();
	vtncView.addButtonListener(new ButtonClickListener());
	vtncView.addKeyboardListener(new KeyPressedListener());
	vtncView.addFunctionButtonsMouseListener(new FuncionButtonsMouseListener());
	selected = vtncView.getSelectedOption();
	news = new HashMap<Integer, News>();
}


class FuncionButtonsMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		if (vtncView.getPowerButton() == e.getComponent())
		{
			vtncView.selectButton(vtncView.getPowerButton());
			vtncView.messageSound(soundFile2);
		}
		if (vtncView.getHelpButton() == e.getComponent())
		{
			vtncView.selectButton(vtncView.getHelpButton());
			vtncView.messageSound(soundFile2);
		}
		if (vtncView.getRefreshButton() == e.getComponent())
		{
			vtncView.selectButton(vtncView.getRefreshButton());
			vtncView.messageSound(soundFile2);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if (vtncView.getPowerButton() == e.getComponent())
		{
			vtncView.deselectButton(vtncView.getPowerButton());
		}
		if (vtncView.getHelpButton() == e.getComponent())
		{
			vtncView.deselectButton(vtncView.getHelpButton());
		}
		if (vtncView.getRefreshButton() == e.getComponent())
		{
			vtncView.deselectButton(vtncView.getRefreshButton());
		}
	}
}

class OptionButtonsMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e) {
		if (news.size() > 0) vtncView.showInfoPane(news.get(selected).getHeadline(), news.get(selected).getNewstText());
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		System.exit(0);
		vtncView.messageSound(soundFile1);
		vtncView.deselectOption(selected);
		int i;
		for (i = 0; i < vtncView.getOptionButtons().size(); i++)
		  if (vtncView.getOptionButtons().get(i) == e.getComponent())
		    break;
		selected = i;
		vtncView.selectOption(selected);
	}
	@Override
	public void mouseExited(MouseEvent e) {}
}


class ButtonClickListener implements ActionListener, MouseListener
{

@Override
public void actionPerformed(ActionEvent a) {
		
	if (a.getSource() == vtncView.whichButton(ButtonTypes.BPOWER))
	{
		vtncView.dispose();
		System.exit(0);
	}
	
	if (a.getSource() == vtncView.whichButton(ButtonTypes.BHELP))
	{
		if (!vtncView.helpIsVisible()) vtncView.showHelp();
		else vtncView.helpVisible(false);	
	}	
	
	if (a.getSource() == vtncView.whichButton(ButtonTypes.BREFRESH))
	{
		socket = new Socket();
		try {
			
			defaultCursor = vtncView.getCursor();
			vtncView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			socket.connect(new InetSocketAddress("127.0.0.1", 1201), 5000); // 5 sek. timeout
			
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			vtncView.getOptionButtons().clear();
			news = (HashMap<Integer, News>) ois.readObject();
			vtncView.setCursor(defaultCursor);
			
			for (int i = 1; i < vtncView.getOptionButtons().size(); i++) vtncView.deselectButton(vtncView.getOptionButtons().get(i));
			for (int i = 1; i < news.size()+1; i++)
			{
				//vtncView.getOptionButtons().put(i, new Button(ButtonTypes.BOPTION, "> " +news.get(i).getHeadline()));
				mButtons.put(i, new Button(ButtonTypes.BOPTION, "> " +news.get(i).getHeadline()));
			}

			mButtons.get(1).addMouseListener(this);
			
			
			for (int i = 1; i < mButtons.size(); i++) vtncView.deselectButton(mButtons.get(i));
			
			vtncView.setSelectedOption(1);
			vtncView.setMaxOption(mButtons.size());
			vtncView.rysujButtony(mButtons);
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(vtncView, "B³¹d po³¹czenia z serwerem! "+ioe.getMessage(), "B³¹d !!!", JOptionPane.ERROR_MESSAGE);
			vtncView.zrzutLoga(ioe, false);
			vtncView.setCursor(defaultCursor);
		}
		catch (ClassNotFoundException cnfe)
		{
			vtncView.zrzutLoga(cnfe, true);
		}
		finally
		{
			try {
			socket.close();
			}
			catch (IOException ioe)
			{
				vtncView.zrzutLoga(ioe, true);
			}
		}
	}
	}

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	System.exit(0);
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}	
}


class KeyPressedListener implements KeyListener
{

@Override
public void keyPressed(KeyEvent key) {
	
	switch (key.getKeyCode())
	{
	case KeyEvent.VK_ESCAPE: {
		//System.exit(0);
		break;
	}
	case KeyEvent.VK_UP: {
		vtncView.messageSound(soundFile1);
		if (selected >= 1) {
			vtncView.deselectOption(selected);
			if (selected == 1) {
				selected=vtncView.getMaxOption();
				vtncView.selectOption(selected);
			}
			else {
				selected--;
				vtncView.selectOption(selected);
			}
		}
		break;
	}
	case KeyEvent.VK_DOWN: {
		vtncView.messageSound(soundFile1);
		if (selected <= vtncView.getMaxOption()) {
			vtncView.deselectOption(selected);
			if (selected == vtncView.getMaxOption())
			{
				selected = 1;
				vtncView.selectOption(selected);
			}
			else {
				selected++;
				vtncView.selectOption(selected);
			}
		}
		break;
	}
	case KeyEvent.VK_ENTER: {
		//vtncView.showInfoPane("Wybrano: "+selected);
		if (news.size() > 0) vtncView.showInfoPane(news.get(selected).getHeadline(), news.get(selected).getNewstText());
		//System.exit(0);
		break;
	}
	case KeyEvent.VK_F1: {
		if (!vtncView.helpIsVisible()) vtncView.showHelp();
		else vtncView.helpVisible(false);
		vtncView.messageSound(soundFile1);
		break;
	}
	default: {
		break;
	}
	}
}

@Override
public void keyReleased(KeyEvent key) {}

@Override
public void keyTyped(KeyEvent key) {}
}

}