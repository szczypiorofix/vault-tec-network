package vaulttecnetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VTNCController {

private VTNCModel vtncModel;
private VTNCView vtncView;

public VTNCController(VTNCModel mModel, VTNCView mView)
{
	vtncModel = mModel;
	vtncView = mView;
	
	vtncView.addButtonListener(new ButtonListener());
	vtncView.addKeyboardListener(new MyKeyListener());
}


class ButtonListener implements ActionListener
{

@Override
public void actionPerformed(ActionEvent a) {
		
	if (a.getSource() == vtncView.whichButton(ButtonTypes.BEXIT))
	{
		vtncView.dispose();
		System.exit(0);
	}
	
	if (a.getSource() == vtncView.whichButton(ButtonTypes.BADD))
	{
		vtncModel.addText(vtncModel.getDefAddText());
		vtncView.setTerminalTextArea(vtncModel.getText());	
	}		
	}	
}


class MyKeyListener implements KeyListener
{

@Override
public void keyPressed(KeyEvent key) {
	
	switch (key.getKeyCode())
	{
	case KeyEvent.VK_ESCAPE: {
		System.exit(0);
	}
	case KeyEvent.VK_UP: {
		System.exit(0);
	}
	case KeyEvent.VK_DOWN: {
		System.exit(0);
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