package vaulttecnetwork;


public class VaultTecNetworkClientMain {

public VaultTecNetworkClientMain()
{
	VTNCModel vtncModel = new VTNCModel();
	VTNCView vtncView = new VTNCView(vtncModel);
	VTNCController vtncController = new VTNCController(vtncModel, vtncView);
	vtncView.setVisible(true);
	vtncController.toString();
}


public static void main(String[] args) {

	new VaultTecNetworkClientMain();

}
}



/**
 * 
09:50 - GThoro: daj klientom taki obrazek http://orig13.deviantart.net/e329/f/2011/347/1/4/terminal_by_ashm909-d4j1hel.jpg :D �acza si� z serwerem i dostaj� dost�pne opcje, zapisane w bazie polecenia i opisy, i jakies tam operacje, moze dodaj osadnika, usun osadnika, zr�b sie� Vault-Tec :D
09:51 - GThoro: albo na razie zamiast zarzadzania osadnikami to po prostu taka jakby.. telegazeta, ze mozesz z poziomu klienta przegladac wpisy w bazie, pogrupowane po kategoriach
09:52 - GThoro: to by by�o w miare proste, 2 tabele w bazie, serwer obs�uguj�cy 2 polecenia
09:52 - GThoro: a raczej 3 polecenia
09:59 - GThoro: 1 tabela to jakies grupy, takie g��wne menu po odpaleniu terminala, i tam 3 kolumny: id, nazwa, kolejnosc, pierwsze polecenie wykonuje SELECT * FORM grupy ORDER BY kolejnosc; (dzi�ki kolejnosci mozesz sterowa� jak to si� pojawia)
10:00 - GThoro: 2 tabela to wpisy: id, id_grupy, nazwa, tresc; klient przysyla polecenie z id grupy i robisz SELECT * FROM wpisy WHERE id_grupy = ? ORDER BY nazwa (gdzie ? to warto�� od klienta)
10:00 - GThoro: 3 polecenie to id wpisu i zwracasz tresc danego wpisu

 * 
 */
