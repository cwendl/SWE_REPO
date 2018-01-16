package Database_package;
import static org.hamcrest.CoreMatchers.anything;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateMain {

	private static SessionFactory sessionFactory = null;
	private Session session = null;
	private Transaction tx = null;

	public HibernateMain(){
		configureSessionFactory();
		this.session = null;
		this.tx = null;
	}
	private static SessionFactory configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		try {
		configuration.configure();
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		try {
			Class.forName("org.sqlite.JDBC");
		}catch ( ClassNotFoundException e) {
			e.printStackTrace();
		}
		sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}
	
	public static void main(String[] args) {
			HibernateMain hb = new HibernateMain();
			/*Spiel spiel1 = new Spiel(1,"Heute","Testspiel","Testmap" );
			Runde runde1 = new Runde(1, "spieler", 0, 10, false, spiel1);
			
			hb.SaveGame(spiel1);
			hb.SaveRound(runde1);
			
			Spiel spiel2 = new Spiel(2,"Heute","Frank hat das Spiel gewonnen","Testmap" );
			hb.SaveGame(spiel2);
			

			Spiel spiel1 = hb.GetSpiel(2);
			for( int i = 0; i < 20; i++) {
				Runde runde = new Runde(i+1,"spieler",0,10,false,spiel1);
				hb.SaveRound(runde);
			}
			*/
			Spiel spiel3 = null;
			spiel3 = hb.GetSpiel(2);
			if(spiel3 != null)
				System.out.println("Das Spiel mit der Nummer: " + spiel3.GetNummer() + " wurde gefunden!");
			
			System.out.println((hb.GetGewinner(spiel3.GetNummer())));
			List<Runde> rundenListe = hb.GetRunden(spiel3, 10, 20);
			if (rundenListe != null) {
				for(int i = 0; i< rundenListe.size(); i++)
					System.out.println(rundenListe.get(i).GetAllInformation(rundenListe.get(i)));
			}
				
			
			

	}
	
	public void SaveGame(Spiel spiel) {
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			try {
			session.save(spiel);
			}catch(Exception e) {
				e.printStackTrace();
			}

			session.flush();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void SaveRound(Runde runde) {
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			try {
			session.save(runde);
			}catch(Exception e) {
				e.printStackTrace();
			}
			

			session.flush();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public Spiel GetSpiel(Integer nummer) {
		try {
		session = sessionFactory.openSession();
		
		Spiel spiel = session.createNamedQuery("get_game_by_id", Spiel.class)
				.setParameter("nummer", nummer)
				.getSingleResult();
		
		tx = session.beginTransaction();
		session.flush();
		tx.commit();
		return spiel;
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session!= null) {
				session.close();
			}
		}
	    System.out.println("Game not found! Returning null");
		return null;
	}
	
	public String GetGewinner(Integer spielnummer) {
		try {
		session = sessionFactory.openSession();
		
		Spiel spiel = session.createNamedQuery("get_game_by_id", Spiel.class)
				.setParameter("nummer", spielnummer)
				.getSingleResult();
		
		tx = session.beginTransaction();
		session.flush();
		tx.commit();
		if(spiel != null)
			return spiel.GetErgebnis();
		else
			return "Kein Spiel gefunden";
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session!= null) {
				session.close();
			}
		}
	    System.out.println("Game not found! Returning null");
		return null;
	}
	
	public List<Runde> GetRunden(Spiel spiel, Integer start, Integer stop){
		try {
		session = sessionFactory.openSession();
		
		List<Runde> rundenListe = session.createNamedQuery("get_rounds_from_to", Runde.class)
				.setParameter("nummer1", start)
				.setParameter("nummer2", stop)
				.setParameter("spiel", spiel)
				.getResultList();
		
		tx = session.beginTransaction();
		session.flush();
		tx.commit();
		if(rundenListe != null)
			return rundenListe;
		else
			return null;
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session!= null) {
				session.close();
			}
		}
		return null;
	}

}
