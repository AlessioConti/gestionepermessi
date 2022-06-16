package it.prova.gestionepermessi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;

	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Backoffice User", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Backoffice User", "ROLE_BO_USER"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente User", "ROLE_DIPENDENTE_USER"));
		}

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", new Date());
			Dipendente adminDip = new Dipendente("Alessio", "Conti", "CNTLSS02E10H501D", "a.conti@prova.it", new Date(),
					new Date(), Sesso.MASCHIO);
			admin.setDipendente(adminDip);
			adminDip.setUtente(admin);
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.inserisciNuovoConDipendente(admin, adminDip);
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("bo") == null) {
			Utente backofficeUser = new Utente("bo", "bo", new Date());
			Dipendente backDip = new Dipendente("Luca", "Bianchi", "BNHLCA01T16H501V", "l.bianchi@prova.it", new Date(),
					new Date(), Sesso.MASCHIO);
			backofficeUser.setDipendente(backDip);
			backDip.setUtente(backofficeUser);
			backofficeUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Backoffice User", "ROLE_BO_USER"));
			utenteServiceInstance.inserisciNuovoConDipendente(backofficeUser, backDip);
			utenteServiceInstance.changeUserAbilitation(backofficeUser.getId());
		}

//		if (utenteServiceInstance.findByUsername("dipendente1") == null) {
//			Utente classicUser1 = new Utente("dipendente1", "dipendente1", new Date());
//			classicUser1.getRuoli()
//					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
//			utenteServiceInstance.inserisciNuovo(classicUser1);
//			utenteServiceInstance.changeUserAbilitation(classicUser1.getId());
//		}
//
//		if (utenteServiceInstance.findByUsername("dipendente2") == null) {
//			Utente classicUser2 = new Utente("dipendente2", "dipendente2", new Date());
//			classicUser2.getRuoli()
//					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
//			utenteServiceInstance.inserisciNuovo(classicUser2);
//			utenteServiceInstance.changeUserAbilitation(classicUser2.getId());
//		}

	}

}
