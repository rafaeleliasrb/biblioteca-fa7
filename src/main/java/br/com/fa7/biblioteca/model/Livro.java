package br.com.fa7.biblioteca.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Livro extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titulo;
	private String autor;
	private Integer quantidade;
	
	@ManyToMany(
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		mappedBy = "livros",
		fetch = FetchType.LAZY,
		targetEntity = Aluno.class
	)
	private Set<Aluno> alunos;	
	
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Set<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void diminurDisponiveis() {
		if(quantidade > 0) {
			this.quantidade--;
		}
		else {
			throw new RuntimeException("Livro não pode ser reservado pois não há mais disponíveis");
		}
	}
	public void gerarLivroDeSolicitacao(SolicitacaoLivro solicitacaoLivro) {
		this.titulo = solicitacaoLivro.getTitulo();
		this.autor = solicitacaoLivro.getAutor();
		this.quantidade = solicitacaoLivro.getQuantidade();
	}
}
