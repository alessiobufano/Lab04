package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);

			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<String> getNomiCorsi() {

		String sql = "SELECT nome FROM corso";

		List<String> corsi = new LinkedList<String>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String nome = rs.getString("nome");
				corsi.add(nome);
			}

			conn.close();
			
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {
	
		String sql = "SELECT * FROM corso WHERE codins=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			Corso c = null;
			
			if(rs.next()) 
				c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			
			conn.close();
			return c;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Corso getCorsoDaNome(String nome) {
		
		String sql = "SELECT * FROM corso WHERE nome=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();
			
			Corso c = null;
			
			if(rs.next()) 
				c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			
			conn.close();
			return c;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(String nome) {
		
		Corso tempC = this.getCorsoDaNome(nome);
		List<Studente> studenti = new LinkedList<>();
		
		if(tempC!=null)
		{
			String codins = tempC.getCodins();
			String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS " + 
					"FROM studente s, iscrizione i, corso c " + 
					"WHERE s.matricola=i.matricola AND c.codins=i.codins AND c.codins=?";
			
			try {
				
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, codins);
				ResultSet rs = st.executeQuery();
				
				while(rs.next()) {
					Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
					studenti.add(s);
				}
				
				conn.close();
				
			} catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return studenti;
	}
	
	public boolean ricercaIscrizione(Studente studente, Corso corso) {
		
		String sql = "SELECT COUNT(*) AS pres " + 
				"FROM iscrizione i " + 
				"WHERE i.codins=? AND i.matricola=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			ResultSet rs = st.executeQuery();
			
			int trovato = 0;
			if(rs.next()) 
				trovato = rs.getInt("pres");
			
			conn.close();
			
			if(trovato==1)
				return true;
			else
				return false;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		if(!this.ricercaIscrizione(studente, corso))
		{
			String sql = "INSERT INTO `iscrizione` (`matricola`, `codins`) VALUES (?, ?)";
			
			try {
				
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, studente.getMatricola());
				st.setString(2, corso.getCodins());
				
				st.executeUpdate();
				
				conn.close();
				return true;
				
			} catch(SQLException e) {
				throw new RuntimeException(e);
			}
			
		}
		return false;
	}

}
