package vaulttecnetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class VTNCController {

private final URL soundFile1 = getClass().getResource("/res/sound1.wav");
private final URL soundFile2 = getClass().getResource("/res/sound2.wav");
private VTNCModel vtncModel;
private VTNCView vtncView;
private int selected;


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
			vtncView.selectPowerButton();
			vtncView.messageSound(soundFile2);
		}
		if (vtncView.getHelpButton() == e.getComponent())
		{
			vtncView.selectHelpButton();
			vtncView.messageSound(soundFile2);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if (vtncView.getPowerButton() == e.getComponent())
		{
			vtncView.deselectPowerButton();
		}
		if (vtncView.getHelpButton() == e.getComponent())
		{
			vtncView.deselectHelpButton();
		}
	}
}

class OptionButtonsMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(selected);
		System.exit(0);
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
		System.out.println(selected);
		System.exit(0);
		break;
	}
	case KeyEvent.VK_F1: {
		if (!vtncView.helpIsVisible()) vtncView.showHelp();
		else vtncView.helpVisible(false);
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