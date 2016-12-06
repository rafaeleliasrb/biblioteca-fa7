package br.com.fa7.biblioteca.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_usuario")
public class Aluno extends Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String matricula;
	
	@ManyToMany(
	   fetch = FetchType.LAZY,	
	   targetEntity=br.com.fa7.biblioteca.model.Livro.class,
	   cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
	   name="reserva",
	   joinColumns=@JoinColumn(name="id_aluno"),
	   inverseJoinColumns=@JoinColumn(name="id_livro")	   
	)
	@OrderBy("titulo")
	private Set<Livro> livros;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Set<Livro> getLivros() {
		return livros;
	}
	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}
	public void adicionarLivros(List<Livro> novosLivros) {
		this.livros.addAll(novosLivros);
	}
}
