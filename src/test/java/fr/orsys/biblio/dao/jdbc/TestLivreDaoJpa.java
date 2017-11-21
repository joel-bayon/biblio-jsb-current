package fr.orsys.biblio.dao.jdbc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.orsys.biblio.dao.LivreDao;
import fr.orsys.biblio.dao.jdbc.LivreDaoJdbc;
import fr.orsys.biblio.dao.jpa.LivreDaoJpa;
import fr.orsys.biblio.entity.Livre;

public class TestLivreDaoJpa {
	
	
	static BeanFactory spring  = new ClassPathXmlApplicationContext("/spring.xml");
	//LivreDao livreDao = new LivreDaoJdbc(); mis en commentaire par DI le 20112017
	LivreDao livreDao = spring.getBean(LivreDao.class);
	
	@Test
	public void updateTest() {
		Livre livre   = new Livre("Stupeur et tremblements",1999, "Am���lie Nothomb");
		livre = livreDao.save(livre);
		livre = livreDao.findOne(livre.getId());
		livre.setAuteur("Am�lie Nothomb");
		livreDao.update(livre);
		Assert.assertEquals("Am�lie Nothomb", livreDao.findOne(livre.getId()).getAuteur());
		
		livreDao.delete(livreDao.findAll());
		Assert.assertEquals(0, livreDao.findAll().size());
	}
	
	@Test
	public  void daoTest() {
		Livre livre   = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		livre = livreDao.save(livre);
		System.out.println(livre.getId());
		livreDao.save(new Livre("L'étranger",1942, "Albert Camus"));
		livreDao.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
		livreDao.save(new Livre("Tintin au Tibet",1960, "Hergé"));
		
		for(Livre l : livreDao.findAll())
			System.out.println(l);
		
		Assert.assertEquals("Amélie Nothomb", livreDao.findOne(livre.getId()).getAuteur());
		livreDao.delete(livre.getId());
		List<Livre> livres = livreDao.findAll();
		Assert.assertEquals(3, livres.size());
		
		livre = new Livre("Tintin au Tibet",1960, "Hergé");
		livre = livreDao.save(livre);
		Assert.assertEquals(2, livreDao.getCount(livreDao.findOne(livre.getId())));
		
		
		livreDao.delete(livreDao.findAll());
		Assert.assertEquals(0, livreDao.findAll().size());

	}
	
	@Test 
	public  void removeTest() {
		Livre livre = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
		livre = livreDao.save(livre);
		Livre l  = livreDao.findOne(livre.getId());
		Assert.assertEquals("Amélie Nothomb", l.getAuteur());
		livreDao.delete(l);
		l = livreDao.findOne(livre.getId());
		Assert.assertNull(l);
	}

}
