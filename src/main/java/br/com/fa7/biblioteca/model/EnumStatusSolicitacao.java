package br.com.fa7.biblioteca.model;

public enum EnumStatusSolicitacao {

	CRIADA("Criada"),
	REJEITADA("Rejeitada"),
	ACEITA("Aceita");
	
	private String statusSolicitacao;
	
	private EnumStatusSolicitacao(String statusSolicitacao) {
		this.setStatusSolicitacao(statusSolicitacao);
	}

	public String getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(String statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}
}
