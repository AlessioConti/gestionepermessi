package it.prova.gestionepermessi.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import it.prova.gestionepermessi.model.Messaggio;

public class MessaggioDTO {
	private Long id;
	@NotBlank(message = "{testo.notblank}")
	private String testo;
	@NotBlank(message = "{oggetto.notblank}")
	private String oggetto;
	private boolean letto;
	private Date dataInserimento;
	private Date dataLettura;

	public MessaggioDTO() {
	}

	public MessaggioDTO(Long id, String testo, String oggetto, boolean letto, Date dataInserimento, Date dataLettura) {
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public boolean isLetto() {
		return letto;
	}

	public void setLetto(boolean letto) {
		this.letto = letto;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataLettura() {
		return dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public static MessaggioDTO buildMessaggioDTOFromModel(Messaggio messaggioModel) {
		return new MessaggioDTO(messaggioModel.getId(), messaggioModel.getTesto(), messaggioModel.getOggetto(),
				messaggioModel.isLetto(), messaggioModel.getDataInserimento(), messaggioModel.getDataLettura());
	}
}
