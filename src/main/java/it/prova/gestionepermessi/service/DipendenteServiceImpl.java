package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
@Service
public class DipendenteServiceImpl implements DipendenteService {
	
	@Autowired
	private DipendenteRepository repository;
	
	@Transactional(readOnly = true)
	public List<Dipendente> listAllDipendenti() {
		return (List<Dipendente>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Dipendente instance) {
		repository.save(instance);
	}

	@Transactional
	public void inserisciNuovo(Dipendente instance) {
		repository.save(instance);
	}

}
