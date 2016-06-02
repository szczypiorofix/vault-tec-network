package vaulttecnetwork;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import javax.swing.JOptionPane;

public class VTNCController {

private final URL soundFile1 = getClass().getResource("/res/sound1.wav");
private final URL soundFile2 = getClass().getResource("/res/sound2.wav");
private VTNCModel vtncModel;
private VTNCView vtncView;
private int selected;
private Socket socket;
private ObjectOutputStream oos;
private ObjectInputStream ois;
private SendingData data;
private Cursor defaultCursor;


public VTNCController(VTNCModel mModel, VTNCView mView)
{
	vtncModel = mModel;
	vtncView = mView;
	vtncView.addButtonListener(new ButtonClickListener());
	vtncView.addKeyboardListener(new KeyPressedListener());
	vtncView.addOptionButtonsMouseListener(new OptionButtonsMouseListener());
	vtncView.addFunctionButtonsMouseListener(new FuncionButtonsMouseListener());
	selected = vtncView.getSelectedOption();
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
		vtncView.showInfoPane("Wybrano: "+selected);
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
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


class ButtonClickListener implements ActionListener
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
			socket.connect(new InetSocketAddress("10.10.0.131", 1201), 5000); // 5 sek. timeout
			if (oos == null) oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			
			data = new SendingData(1, new char[0], "Headline", "message");
			oos.writeObject(data);
			oos.flush();
			
			if (ois == null) ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			data = (SendingData) ois.readObject();
			vtncView.setCursor(defaultCursor);
			vtncView.showInfoPane(data.getHeadline() + " "+data.getMessage());
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
		vtncView.showInfoPane("Wybrano: "+selected);
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