package dao;

import bean.User;
import constants.Encryption;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDAO {

    private final static Logger LOGGER = Logger.getLogger(User.class.getName());
    @PersistenceContext
    private EntityManager em;

    public UserDAO() {
    }

    /**
     * Renvoie l'utilisateur correspondant à cet email
     *
     * @param pseudo le pseudo ou email de l'utilisateur
     * @return l'utilisateur ou null s'il n'existe pas
     * @throws java.lang.Exception
     */
    public User connexion(String pseudo, String password) {
        try {
            // handle encryption
            String encryptedPassword = new Encryption().encrypt(password);
            List result = em.createQuery("SELECT u FROM User u WHERE (u.pseudo = :pseudo OR u.email = :pseudo) AND u.password = :password")
                    .setParameter("pseudo", pseudo)
                    .setParameter("password", encryptedPassword)
                    .getResultList();
            if (!result.isEmpty()) return (User) result.get(0);
            else return null;
        } catch (Exception e) {
            LOGGER.setLevel(Level.WARNING);
            LOGGER.warning("User " + pseudo + " failed to connect");
            throw new PersistenceException(e);
        }
    }

    public User getUser(int id) {
        List result = em.createNativeQuery("SELECT ID FROM USER WHERE ID = ?")
                .setParameter(1, id)
                .getResultList();
        if (!result.isEmpty()) return em.find(User.class, result.get(0));
        else return null;
    }

    /**
     * Créée un nouvel utilisateur ou met à jour son pseudo
     *
     * @return l'utilisateur créé ou lis à jour
     * @throws java.lang.Exception
     */
    public void createOrUpdate(User u) throws Exception {
        try {
            em.persist(u);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
