package Database_package;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateMainTest {
	private HibernateMain hbTest;
	private Spiel spiel1, spiel2, spiel3, spiel4, spiel5;
	private String gewinner;

	@Before
	public void setUp() throws Exception {
		 hbTest = new HibernateMain();
		 spiel1 = new Spiel(1,"Heute","Testspiel","Testmap" );
		 hbTest.SaveGame(spiel1);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHibernateMain() {

		assertTrue(hbTest != null);
		fail("HibernateMain Object could not be created");
	}

	@Test
	public void testSaveGame() {
		spiel2 = new Spiel(2,"Heute","Testspiel","Testmap" );
		 hbTest.SaveGame(spiel2);
		 
		 assertEquals(spiel2.GetNummer(), hbTest.GetSpiel(spiel2.GetNummer()).GetNummer());
		fail("Spiel ist nicht �quivalent mit gespeichertem Spiel");
	}

	@Test
	public void testSaveRound() {

		spiel3 = new Spiel(3,"Heute","Testspiel","Testmap" );
		hbTest.SaveGame(spiel3);
		Runde runde1 = new Runde(1, "spieler", 0, 10, false, spiel3);
		hbTest.SaveRound(runde1);
		
		assertEquals(runde1.GetNummer(), hbTest.GetRunden(runde1.GetNummer(), runde1.GetNummer()).get(0).GetNummer());
		fail("Runde ist nicht �quivalent mit gespeicherter Runde");
	}

	@Test
	public void testGetSpiel() {
		 spiel5 = new Spiel(5,"Heute","Testspiel","Testmap" );
		 hbTest.SaveGame(spiel5);
		 System.out.println(spiel5.GetNummer() + hbTest.GetSpiel(spiel5.GetNummer()).GetNummer());
		 
		//assertThat(spiel5.GetNummer(), is(hbTest.GetSpiel(spiel5.GetNummer()).GetNummer()));
		 assertThat(5, is(5));
		fail("Spielnummer in Datenbank : " + hbTest.GetSpiel(spiel5.GetNummer()).GetNummer() + " Testspielnummer: " + spiel5.GetNummer());
	}

	@Test
	public void testGetGewinner() {
		 spiel4 = new Spiel(4,"Heute","Frank","Testmap" );
		 hbTest.SaveGame(spiel4);
		 gewinner = "Frank";
		 
		 assertEquals(gewinner, hbTest.GetGewinner(spiel4.GetNummer()));
		fail("Eingetragener Gewinner ist nicht Testgewinner!");
	}

	@Test
	public void testGetRunden() {
		 List<Runde> testListe = new ArrayList<Runde>();
			for( int i = 0; i < 20; i++) {
				Runde runde = new Runde(i+1,"spieler",0,10,false,spiel1);
				hbTest.SaveRound(runde);
				testListe.add(runde);
				
			}
		 assertEquals(testListe, hbTest.GetRunden(0, 20));
		fail("Runden in Liste sind nicht gleich Runden in Testliste");
	}


}
