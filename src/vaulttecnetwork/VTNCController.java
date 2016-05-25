package vaulttecnetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VTNCController {

private VTNCModel vtncModel;
private VTNCView vtncView;

public VTNCController(VTNCModel mModel, VTNCView mView)
{
	vtncModel = mModel;
	vtncView = mView;
	
	
	vtncView.addOKButtonListener(new CloseListener());
	
}


class CloseListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		vtncView.dispose();
		System.exit(0);
	}
}

}