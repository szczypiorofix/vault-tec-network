package vaulttecnetwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class VaultTecNetworkClient {

private final ImageIcon BACKGROUNDIMAGE = new ImageIcon(getClass().getResource("/res/terminal_background.jpg"));
private JFrame frame;
private JPanel cPanel;
private JButton bOK;
private int bOKx = 400, bOKy = 200;

public VaultTecNetworkClient()
{
	frame = new JFrame("Vault-Tec Network Customer");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(870, 495);
	frame.setLayout(new BorderLayout());
	frame.setLocationRelativeTo(null);
	
	cPanel = new JPanel()
	{
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(BACKGROUNDIMAGE.getImage(), -15, -15, null);
		}};
	cPanel.setLayout(null);
	bOK = new JButton("OK");
	
	bOK.setBounds(bOKx, bOKy, 60, 50);
	cPanel.add(bOK);
	
	frame.add(cPanel, BorderLayout.CENTER);
	
	frame.setVisible(true);
}


public static void main(String[] args) {

EventQueue.invokeLater(new Runnable()
{
	public void run()
	{
		new VaultTecNetworkClient();
	}
});
}

}


/**
 * 
 * 09:50 - GThoro: daj klientom taki obrazek http://orig13.deviantart.net/e329/f/2011/347/1/4/terminal_by_ashm909-d4j1hel.jpg :D �acza si� z serwerem i dostaj� dost�pne opcje, zapisane w bazie polecenia i opisy, i jakies tam operacje, moze dodaj osadnika, usun osadnika, zr�b sie� Vault-Tec :D
09:51 - GThoro: albo na razie zamiast zarzadzania osadnikami to po prostu taka jakby.. telegazeta, ze mozesz z poziomu klienta przegladac wpisy w bazie, pogrupowane po kategoriach
09:52 - GThoro: to by by�o w miare proste, 2 tabele w bazie, serwer obs�uguj�cy 2 polecenia
09:52 - GThoro: a raczej 3 polecenia
09:59 - GThoro: 1 tabela to jakies grupy, takie g��wne menu po odpaleniu terminala, i tam 3 kolumny: id, nazwa, kolejnosc, pierwsze polecenie wykonuje SELECT * FORM grupy ORDER BY kolejnosc; (dzi�ki kolejnosci mozesz sterowa� jak to si� pojawia)
10:00 - GThoro: 2 tabela to wpisy: id, id_grupy, nazwa, tresc; klient przysyla polecenie z id grupy i robisz SELECT * FROM wpisy WHERE id_grupy = ? ORDER BY nazwa (gdzie ? to warto�� od klienta)
10:00 - GThoro: 3 polecenie to id wpisu i zwracasz tresc danego wpisu

 * 
 */
