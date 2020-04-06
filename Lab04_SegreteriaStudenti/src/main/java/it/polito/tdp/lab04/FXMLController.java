package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private ObservableList<String> listCorsi = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> boxCorsi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button doCercaCorsi;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	
    	this.txtRisultato.clear();
    	String nome = this.boxCorsi.getValue();
    	
    	if(nome.equals("")) {
    		this.txtRisultato.setText("Errore! Scegliere un corso!\n");
    		return;
    	}
    	
    	List<Studente> studenti = this.model.getStudentiIscrittiAlCorso(nome);
    	if(studenti.size()==0)
    		this.txtRisultato.setText("Il corso "+nome+" non ha studenti iscritti\n");
    	else
    	{
    		this.txtRisultato.setText("Gli studenti iscritti al corso "+nome+" sono:\n");
    		for(Studente s : studenti)
    			this.txtRisultato.appendText(s.toString()+"\n");
    	}
    	
    }

    @FXML
    void doInfoStudente(ActionEvent event) {
    	
    	this.txtRisultato.clear();
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	
    	String matr = this.txtMatricola.getText();
    	int m;
    	
    	try {
    		
    		m = Integer.parseInt(matr);
    		Studente s = this.model.getStudente(m);
    		
    		if(s==null)
    			this.txtRisultato.setText("Errore! La matricola inserita non corrisponde ad alcuno studente\n");
    		else
    		{
    			this.txtCognome.setText(s.getCognome());
    			this.txtNome.setText(s.getNome());
    		}
    		
    	} catch(NumberFormatException e) {
    		this.txtRisultato.setText("Errore! La matricola dello studente deve essere un valore numerico intero di 6 cifre\n");
    		return;
    	}

    }
    
    @FXML
    void doCercaCorsi(ActionEvent event) {
    
    	this.doInfoStudente(event);
    	
    	if(!this.txtCognome.getText().equals("") && !this.txtNome.getText().equals(""))
    	{
    		int matr = Integer.parseInt(this.txtMatricola.getText());
    		List<Corso> corsi = this.model.getCorsiStudente(matr);
    		
    		if(corsi.size()==0)
        		this.txtRisultato.setText("Lo studente "+this.txtCognome.getText()+" "+this.txtNome.getText()+" non è iscritto ad alcun corso\n");
        	else
        	{
        		this.txtRisultato.setText("Lo studente "+this.txtCognome.getText()+" "+this.txtNome.getText()+" è iscritto ad i corsi:\n");
        		for(Corso c : corsi)
        			this.txtRisultato.appendText(c.toString()+"\n");
        	}
    	}
    	else
    		return;
    	
    }

    @FXML
    void doIscriviStudente(ActionEvent event) {
    	
    	this.doInfoStudente(event);
    	if(!this.txtCognome.getText().equals("") && !this.txtNome.getText().equals(""))
    	{
    		if(this.boxCorsi.getValue().equals("")) {
    			this.txtRisultato.setText("Errore! Scegliere un corso!\n");
        		return;
    		}
    		else
    		{
    			int matr = Integer.parseInt(this.txtMatricola.getText());
    			String nomeC = this.boxCorsi.getValue();
    			Studente s = this.model.getStudente(matr);
    			Corso c = this.model.getCorsoDaNome(nomeC);
    			
    			if(this.model.iscriviStudenteACorso(s, c))
    				this.txtRisultato.setText("Complimenti! Lo "+s.toString()+" è stato correttamente iscritto al "+c.toString()+"\n");
    			else
    				this.txtRisultato.setText("Lo "+s.toString()+" era già precedentemente iscritto al "+c.toString()+"\n");
    		}
    	}

    }

    @FXML
    void doReset(ActionEvent event) {

    	this.boxCorsi.setValue("");
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    	this.txtRisultato.clear();
    }

    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert doCercaCorsi != null : "fx:id=\"doCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }

	public void setModel(Model model) {
		this.model = model;
        this.listCorsi.addAll(this.model.getNomiCorsi());  
        this.boxCorsi.setItems(listCorsi);
        this.boxCorsi.setValue("");
      //conviene mettere direttamente lista di corsi (e non di stringhe nomeCorso) e poi nella tendina uscirà il toString() di Corso
	}
}