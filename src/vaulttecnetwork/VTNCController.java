package vaulttecnetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JWindow;

public class VTNCController {

private VTNCModel vtncModel;
private VTNCView vtncView;
private int selected;

public VTNCController(VTNCModel mModel, VTNCView mView)
{
	vtncModel = mModel;
	vtncView = mView;
	
	vtncView.addButtonListener(new ButtonListener());
	vtncView.addKeyboardListener(new MyKeyListener());
	selected = vtncView.getSelectedOption();
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
		vtncView.showHelp();
		
		
		//vtncModel.addText(vtncModel.getDefAddText());
		//vtncView.setTerminalTextArea(vtncModel.getText());	
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