package Database_package;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(
		name = "get_rounds_from_to",
	 	query = "select r from Runde r where r.nummer between :nummer1 and :nummer2"
	)
			
})

@Entity
@Table(name = "Runde")
public class Runde {
	
	@Id
	@Column(name = "Nummer")
	private Integer nummer;
	
	@Column(name = "Spieler")
	private String spieler;
	
	@Column(name = "letzte_Position")
	private Integer letzte_Position;
	
	@Column(name = "aktuelle_Position")
	private Integer aktuelle_Position;
	
	@Column(name = "Schatz")
	private Boolean schatz;
	
	@ManyToOne
	@JoinColumn(name = "Spiel")
	private Spiel spiel;
	
	Runde() {}
	
	Runde(Integer nummer, String spieler, Integer letzte_Position, Integer aktuelle_Position, Boolean schatz, Spiel spiel){
		init(nummer, spieler, letzte_Position, aktuelle_Position, schatz, spiel);
	}
	
	private void init(Integer nummer, String spieler, Integer letzte_Position, 
			Integer aktuelle_Position, Boolean schatz, Spiel spiel) {
		this.nummer = nummer;
		this.spieler = spieler;
		this.letzte_Position = letzte_Position;
		this.aktuelle_Position = aktuelle_Position;
		this.schatz = schatz;
		this.spiel = spiel;
	}
	
	public Integer GetNummer() {
		return this.nummer;
	}
	
	public String GetSpieler() {
		return this.spieler;
	}
	
	public void SetSpieler(String spieler) {
		this.spieler = spieler;
	}
	
	public Integer GetLetztePosition() {
		return this.letzte_Position;
	}
	
	public void SetLetztePosition(Integer letzte_Position) {
		this.letzte_Position = letzte_Position;
	}
	
	public Integer GetAktuellePosition() {
		return this.aktuelle_Position;
	}
	
	public void SetAktuellePosition(Integer aktuelle_Position) {
		this.aktuelle_Position = aktuelle_Position;
	}
	
	public Boolean GetSchatz() {
		return this.schatz;
	}
	
	public void SetSchatz(Boolean schatz) {
		this.schatz = schatz;
	}
	
	public Spiel GetSpiel() {
		return this.spiel;
	}
	
	public String GetAllInformation(Runde runde) {
		return "Runde Nummer: " + runde.GetNummer() + 
				"\nSpieler: " + runde.GetSpieler() +
				"\nLetzte Position: " + runde.GetLetztePosition() +
				"\nAktuelle Position: " + runde.GetAktuellePosition() +
				"\nSchatz: " + (runde.GetSchatz() ? "Ja" : "Nein") +
				"\nSpiel Nummer: " + runde.GetSpiel().GetNummer();
				
	}
	
	/*
	Create Table Runde (
			Nummer Integer,
			Spieler String,
			letzte_Position Integer,
			aktuelle_Position Integer,
			Schatz Boolean,
			Spiel Integer,
			Primary Key (Nummer),
			Foreign Key (Spiel) References Spiel(Nummer)
		);
*/

}
