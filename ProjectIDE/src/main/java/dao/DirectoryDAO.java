package dao;

import bean.DirectoryInProject;
import bean.FileInProject;
import bean.Pair;
import bean.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by abdelilah chet on 25/01/18.
 */
@Repository
public class DirectoryDAO {

    @PersistenceContext
    private EntityManager em;

    public DirectoryDAO(){}

    /**
     * Permet de creer de savoir si un dossier existe en base de donnee
     * @param name
     * @return vrai si l'objet est cree, faux si l'objet existe deja 
     */
    public Boolean existDirectory(String name, int idFather){

        List<Integer> l = em.createNativeQuery("SELECT ID FROM DIRECTORY NATURAL JOIN FILE_OR_CONTAINER WHERE NAME=? AND ID_FATHER=?")
                .setParameter(1, name)
                .setParameter(2, idFather)
                .getResultList();
        if (l.size() > 0) {
            return true;
        }
        else {
            return true;
        }
    }

    public DirectoryInProject getDirectoryById(int id){
        System.out.println("hey");
        DirectoryInProject dir =  em.find(DirectoryInProject.class, id);
        System.out.println(dir.getName());
        return dir;
    }

    public void makeDir(String dirname, int idDir, User user){
        DirectoryInProject fDir = em.find(DirectoryInProject.class, idDir);
        DirectoryInProject newDir = fDir.addDir(dirname, user);
        em.persist(newDir);
    }

    public void makeFile(String filename, int idDir, User user){
        DirectoryInProject fDir = em.find(DirectoryInProject.class, idDir);
        FileInProject newFile = fDir.addFile(filename, user);
        em.persist(newFile);
    }
}
