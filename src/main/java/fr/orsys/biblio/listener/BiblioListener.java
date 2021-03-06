package fr.orsys.biblio.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.orsys.biblio.dao.AdherentDao;
import fr.orsys.biblio.dao.EmpruntDao;
import fr.orsys.biblio.dao.LivreDao;
import fr.orsys.biblio.entity.Adherent;
import fr.orsys.biblio.entity.Livre;
import fr.orsys.biblio.service.Bibliotheque;
import fr.orsys.biblio.service.impl.BibliothequeImpl;

/**
 * Application Lifecycle Listener implementation class BiblioListener
 *
 */
//@WebListener
public class BiblioListener implements ServletContextListener {
	static BeanFactory spring = new ClassPathXmlApplicationContext("/spring.xml");
	

    /**
     * Default constructor. 
     */
    public BiblioListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
	    public void contextDestroyed(ServletContextEvent event)  { 
	    	Bibliotheque biblio = (Bibliotheque)event.getServletContext().getAttribute("biblio");
	    	LivreDao livreDao = biblio.getLivreDao();
	    	biblio.getAdherentDao();
    		livreDao.delete(livreDao.findAll());     
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	
   
    	Bibliotheque biblio = spring.getBean(Bibliotheque.class);
    	//Bibliotheque biblio = new BibliothequeImpl(3, 5);
        LivreDao livreDao = biblio.getLivreDao();
        AdherentDao adherentDao = biblio.getAdherentDao();
         
 		Adherent ad1 = new Adherent("Dupond", "Jean", "0234567812", "jean.dupont.@yahoo.fr");
 		Adherent ad2 = new Adherent("Durant", "Jacques", "0223674512", "jacques.durant@free.fr");
 		Adherent ad3 = new Adherent("Martin", "Bernadette", "0138792012", "m.bernadette@gmail.com");
 		
 		adherentDao.save(ad1);
 		adherentDao.save(ad2);
 		adherentDao.save(ad3);

 		livreDao.save (new Livre("Stupeur et tremblements",1999, "Am�lie Nothomb"));
 		livreDao.save(new Livre("L'�tranger",1942, "Albert Camus"));
 		livreDao.save(new Livre("R�glez-lui son compte !",1949, "Fr�d�ric Dard"));
 		livreDao.save(new Livre("Tintin au Tibet",1960, "Herg�"));
 		
 		event.getServletContext().setAttribute("biblio", biblio);							
     
    }
	
}
