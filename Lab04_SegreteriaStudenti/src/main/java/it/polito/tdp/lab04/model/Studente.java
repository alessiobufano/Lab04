package it.polito.tdp.lab04.model;

public class Studente {
	
	private int matricola;
	private String cognome;
	private String nome;
	private String cds;
	
	public Studente(int matricola, String cognome, String nome, String cds) {
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		this.cds = cds;
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCds() {
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricola;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		if (matricola != other.matricola)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Studente "+cognome+" "+nome+" [matricola=" + matricola + ", cds=" + cds + "]";
	}

}
