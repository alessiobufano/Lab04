package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	CorsoDAO cDAO;
	StudenteDAO sDAO;
	
	public Model() {
		this.cDAO = new CorsoDAO();
		this.sDAO = new StudenteDAO();
	}

	public List<String> getNomiCorsi() {
		
		return cDAO.getNomiCorsi();
	}
	
	public Studente getStudente(int m) {
		
		return sDAO.getStudente(m);
	}
	
	public Corso getCorso(String codins) {
		
		return cDAO.getCorso(codins);
	}
	
	public Corso getCorsoDaNome(String nome) {
		
		return cDAO.getCorsoDaNome(nome);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(String nome) {
		
		return cDAO.getStudentiIscrittiAlCorso(nome);
	}
	
	public List<Corso> getCorsiStudente(int matr) {
		
		return sDAO.getCorsiStudente(matr);
	}
	
	public boolean ricercaIscrizione(Studente studente, Corso corso) {
		
		return cDAO.ricercaIscrizione(studente, corso);
	}
	
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		
		return cDAO.iscriviStudenteACorso(studente, corso);
	}
	
}
