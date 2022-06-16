package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteService {
	
	public List<Dipendente> listAllDipendenti();
	
	public Dipendente caricaSingoloDipendente(Long id);
	
	public void aggiorna(Dipendente instance);
	
	public void inserisciNuovo(Dipendente instance);
	
}
