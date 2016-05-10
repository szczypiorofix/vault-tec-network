package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class VaultTecNetworkServer {

private JFrame frame;
	
public VaultTecNetworkServer()
{
	frame = new JFrame("Vault-Tec Network Server. RobCo Industries.");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(400, 500);
	frame.setLayout(new BorderLayout());
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);	
}
	
public static void main(String[] args) {

EventQueue.invokeLater(new Runnable()
{
	public void run()
	{
		new VaultTecNetworkServer();
	}
});
}

}