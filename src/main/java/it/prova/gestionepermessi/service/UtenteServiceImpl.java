package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.DipendenteRepository;
import it.prova.gestionepermessi.repository.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		return (List<Utente>) utenteRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return utenteRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return utenteRepository.findByIdConRuoli(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Utente utenteInstance) {
		Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
		if(utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		utenteRepository.save(utenteReloaded);
	}

	@Transactional
	public void inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword())); 
		utenteInstance.setDateCreated(new Date());
		utenteRepository.save(utenteInstance);
	}
	
	@Transactional
	public void inserisciNuovoConDipendente(Utente utenteInstance, Dipendente dipendenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword())); 
		utenteInstance.setDateCreated(new Date());
		utenteRepository.save(utenteInstance);
		dipendenteRepository.save(dipendenteInstance);

	}

	@Transactional
	public void rimuovi(Utente utenteInstance) {
		utenteRepository.delete(utenteInstance);
	}

	@Transactional(readOnly = true)
	public Page<Utente> findByExample(Utente example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getUsername()))
				predicates
						.add(cb.like(cb.upper(root.get("username")), "%" + example.getUsername().toUpperCase() + "%"));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDateCreated() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), example.getDateCreated()));

			if (!example.getRuoli().isEmpty()) {
				predicates.add(root.join("ruoli").in(example.getRuoli()));
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return utenteRepository.findAll(specificationCriteria, paging);
	}

	@Transactional(readOnly = true)
	public Utente findByUsernameAndPassword(String username, String password) {
		return utenteRepository.findByUsernameAndPassword(username, password);
	}

	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return utenteRepository.findByUsernameAndPasswordAndStato(username, password,StatoUtente.ATTIVO);
	}

	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if(utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");
		
		if(utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if(utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if(utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsername(String username) {
		return utenteRepository.findByUsername(username).orElse(null);
	}

	@Override
	public void changeUserPassword(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if(utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");
		
		if(passwordEncoder.matches("Password@01", utenteInstance.getPassword()))
			throw new RuntimeException("La password di questo utente ?? gia stata resettata di recente");
		
		utenteInstance.setPassword(passwordEncoder.encode("Password@01"));
	}

}
