package dao;

import bean.FileInProject;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FileInProjectDAO {

    @PersistenceContext
    private EntityManager em;

    public FileInProjectDAO() {
    }

    public void removeFile(int idProject, String nom){
        System.out.println(idProject + "  " + nom);
        em.createNativeQuery("DELETE FROM FILE WHERE ID_PROJECT = ? and NAME = ?")
                .setParameter(1, idProject)
                .setParameter(2, nom)
                .executeUpdate();
    }

}
