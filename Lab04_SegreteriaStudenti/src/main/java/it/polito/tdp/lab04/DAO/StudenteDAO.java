package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;


public class StudenteDAO {
	
	public Studente getStudente(int m) {
		
		String sql = "SELECT * FROM studente WHERE matricola=?";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);
			ResultSet rs = st.executeQuery();
			
			Studente s = null;
			
			if(rs.next()) 
				s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
			
			conn.close();
			return s;
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Corso> getCorsiStudente(int matr) {
		
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM iscrizione i, corso c " + 
				"WHERE c.codins=i.codins AND i.matricola=?";
		
		Studente tempS = this.getStudente(matr);
		List<Corso> corsi = new LinkedList<>();
		
		if(tempS!=null)
		{
			try {
				
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, matr);
				ResultSet rs = st.executeQuery();
				
				while(rs.next()) {
					Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
					corsi.add(c);
				}
					
				conn.close();
				
			} catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		return corsi;
		
	}

}
