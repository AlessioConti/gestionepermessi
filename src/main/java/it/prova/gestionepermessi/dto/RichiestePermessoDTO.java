package it.prova.gestionepermessi.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import it.prova.gestionepermessi.model.RichiestePermesso;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestePermessoDTO {
	private Long id;
	@NotNull(message = "{dataInizio.notnull}")
	private Date dataInizio;
	@NotNull(message = "{dataFine.notnull}")
	private Date dataFine;
	private boolean approvato;
	private String codiceCertificato;
	private String note;
	@NotNull(message = "{tipoPermesso.notnull}")
	private TipoPermesso tipoPermesso;

	public RichiestePermessoDTO() {
	}

	public RichiestePermessoDTO(Long id, Date dataInizio, Date dataFine, boolean approvato, String codiceCertificato,
			String note, TipoPermesso tipoPermesso) {
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.tipoPermesso = tipoPermesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public boolean isApprovato() {
		return approvato;
	}

	public void setApprovato(boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TipoPermesso getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(TipoPermesso tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public static RichiestePermessoDTO buildRichiestaPermessoDTOFromModel(RichiestePermesso richiestePermessoModel) {
		return new RichiestePermessoDTO(richiestePermessoModel.getId(), richiestePermessoModel.getDataInizio(),
				richiestePermessoModel.getDataFine(), richiestePermessoModel.isApprovato(),
				richiestePermessoModel.getCodiceCertificato(), richiestePermessoModel.getNote(), richiestePermessoModel.getTipoPermesso());
	}
}
