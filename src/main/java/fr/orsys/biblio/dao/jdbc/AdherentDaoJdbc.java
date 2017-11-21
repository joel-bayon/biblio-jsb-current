package fr.orsys.biblio.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.mysql.jdbc.Statement;
import fr.orsys.biblio.dao.AdherentDao;
import fr.orsys.biblio.entity.Adherent;
import fr.orsys.biblio.util.ConnectionDBPool;

@Component
public class AdherentDaoJdbc implements AdherentDao {

	@Override
	public Adherent save(Adherent entity) {
		String requete = "INSERT INTO adherent (nom, prenom, tel, email) VALUES (?, ?, ?, ?)";
		Connection cxn = ConnectionDBPool.getConnection();
		try {
			PreparedStatement stmt = cxn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, entity.getNom());
			stmt.setString(2, entity.getPrenom());
			stmt.setString(3, entity.getTel());
			stmt.setString(4, entity.getEmail());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();         // Retrieve the automatically generated id    2 
			if (rs.next()) 
				entity.setId(rs.getInt(1));     
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionDBPool.close(cxn);
		}
		return entity;
	}

	@Override
	public void update(Adherent entity) {
		String requete = "UPDATE adherent  SET nom=?, prenom=?, tel=?,  email=? WHERE id=?";
		Connection cxn = ConnectionDBPool.getConnection();
		try {
			PreparedStatement stmt = cxn.prepareStatement(requete);
			stmt.setString(1, entity.getNom());
			stmt.setString(2, entity.getPrenom());
			stmt.setString(3, entity.getTel());
			stmt.setString(4, entity.getEmail());
			stmt.setInt(5, entity.getId());
			stmt.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionDBPool.close(cxn);
		}	

	}

	@Override
	public Adherent findOne(Integer primaryKey) {
		String requete = "select * from adherent where id = ?";
		Connection cxn = ConnectionDBPool.getConnection();
		Adherent a = null;
		
		try {
			PreparedStatement stmt = cxn.prepareStatement(requete);
			stmt.setInt(1, primaryKey);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				a = new Adherent(rs.getString("nom"), rs.getString("prenom"), rs.getString("tel"), rs.getString("email") );
				a.setId(rs.getInt("id"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionDBPool.close(cxn);
		}
		return a;
	}

	@Override
	public List<Adherent> findAll() {
		String requete = "select * from adherent";
		Connection cxn = ConnectionDBPool.getConnection();
		ArrayList<Adherent> adherents = new ArrayList<Adherent>();
		try {
			PreparedStatement stmt = cxn.prepareStatement(requete);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Adherent a = new Adherent(rs.getString("nom"), rs.getString("prenom"), rs.getString("tel"), rs.getString("email") );
				a.setId(rs.getInt("id"));
				adherents.add(a);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionDBPool.close(cxn);
		}
		return adherents;
	}

	@Override
	public void delete(Adherent entity) {
		delete(entity.getId());

	}

	@Override
	public void delete(Integer id) {
		String requete = "DELETE FROM adherent WHERE id=?";
		Connection cxn = ConnectionDBPool.getConnection();
		try {
			PreparedStatement stmt = cxn.prepareStatement(requete);
			stmt.setInt(1, id);
			stmt.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionDBPool.close(cxn);
		}	

	}

	@Override
	public void delete(Iterable<Adherent> entities) {
		for(Adherent l : entities)
			delete(l);

	}

	@Override
	public boolean isPresent(Adherent adherent) {
		String requete = "select * from adherent where nom = ? and prenom = ? and email = ?";
		Connection cxn = ConnectionDBPool.getConnection();
		boolean result = false;
		
		try {
			PreparedStatement stmt = cxn.prepareStatement(requete);
			stmt.setString(1, adherent.getNom());
			stmt.setString(2, adherent.getPrenom());
			stmt.setString(3, adherent.getEmail());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				result = true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectionDBPool.close(cxn);
		}
		return result;
	}

}
