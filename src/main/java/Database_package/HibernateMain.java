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
		// TODO Auto-generated method stub
		configureSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Spiel spiel1 = new Spiel(3,"Heute","Testspiel","Testmap" );
			session.save(spiel1);
			Runde runde1 = new Runde(1, "spieler", 0, 10, false, spiel1);
			session.save(runde1);
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
