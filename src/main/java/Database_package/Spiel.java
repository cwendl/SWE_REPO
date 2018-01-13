package Database_package;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(
		name = "get_game_by_id",
	 	query = "from Spiel where Nummer = :nummer"
	)
			
})
@Entity
@Table(name="Spiel")
public class Spiel {
	@Id
	@Column(name = "Nummer")
	private Integer nummer;
	
	@Column(name = "Datum")
	private String datum;
	
	@Column(name = "Ergebnis")
	private String ergebnis;
	
	@Column(name = "Karte")
	private String karte;
	
	Spiel() {}
	
	Spiel(Integer nummer, String datum, String ergebnis, String karte){
		init(nummer, datum, ergebnis, karte);
	}
	
	private void init(Integer nummer, String datum, String ergebnis, String karte) {
		this.nummer = nummer;
		this.datum = datum;
		this.ergebnis = ergebnis;
		this.karte = karte;
	}
	
	//Getter and Setter
	public Integer GetNummer() {
		return this.nummer;
	}
	
	public String GetDatum() {
		return this.datum;
	}
	
	public void SetDatum(String datum) {
		this.datum = datum;
	}
	
	public String GetErgebnis() {
		return this.ergebnis;
	}
	
	public void SetErgebnis(String ergebnis) {
		this.ergebnis = ergebnis;
	}
	
	public String GetKarte() {
		return this.karte;
	}
	
	public void SetKarte(String karte) {
		this.karte = karte;
	}
}
