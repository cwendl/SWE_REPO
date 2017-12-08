package Database_package;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateMain {

	private static SessionFactory sessionFactory = null;
	
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
			Spiel spiel1 = new Spiel(3,"Heute","Testspiel","Testmap" );
			Runde runde1 = new Runde(1, "spieler", 0, 10, false, spiel1);
			
			hb.SaveGame(spiel1);
			hb.SaveRound(runde1);

	}
	
	public void SaveGame(Spiel spiel) {
		configureSessionFactory();
		Session session = null;
		Transaction tx = null;
		
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
		configureSessionFactory();
		Session session = null;
		Transaction tx = null;
		
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

}
