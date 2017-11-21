package fr.orsys.biblio.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import fr.orsys.biblio.dao.LivreDao;
import fr.orsys.biblio.entity.Livre;

@Repository
@Primary
@Transactional
public class LivreDaoJpa implements LivreDao {
	
	@PersistenceContext
	EntityManager em;
	

	@Override
	public Livre save(Livre entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public void update(Livre entity) {
		em.merge(entity);
	}

	@Override
	public Livre findOne(Integer primaryKey) {
		return em.find(Livre.class, primaryKey);
	}

	@Override
	public List<Livre> findAll() {
		String requeteJPQL = "select l from Livre l";
		return em.createQuery(requeteJPQL, Livre.class).getResultList();
	}

	@Override
	public void delete(Livre entity) {
		delete(entity.getId());

	}

	@Override
	public void delete(Integer id) {
		em.remove(em.find(Livre.class, id));
	}

	@Override
	public void delete(Iterable<Livre> entities) {
		for(Livre l : entities)
			delete(l);

	}

	@Override
	public long getCount(Livre livre) {
		String requete = "SELECT count(l) FROM Livre l where titre=:titre and auteur =:auteur";
		return em.createQuery(requete, Long.class)
				.setParameter("titre", livre.getTitre())
				.setParameter("auteur", livre.getAuteur())
				.getSingleResult();
	}

}
