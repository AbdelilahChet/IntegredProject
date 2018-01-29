package dao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bean.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDAO{
    @PersistenceContext
    private EntityManager em;

    public ProjectDAO() {
    }

    public Pair<Boolean,Project> createOrUpdateProject(String nom, String chemin) {

        int id = -99999;
        Project project = null;
        List<Integer> l = em.createNativeQuery("SELECT ID FROM PROJECT NATURAL JOIN FILE_OR_CONTAINER WHERE PATH=? AND NAME=?")
                    .setParameter(1, chemin)
                    .setParameter(2, nom)
                    .getResultList();
        if (l.size() > 0) {
            id = l.get(0);
            project = em.find(Project.class, id);
            return new Pair<Boolean, Project>(false,project);
        }
        if (project == null) {
           project = new Project(nom, chemin);
           em.persist(project);
            return new Pair<Boolean, Project>(true,project);
        }
        return null;
    }




    public Project getProject(int idProject){
        return em.find(Project.class, idProject);
    }

    /**
     * this file is use to change the directory of the project or his name
     * if each case we give the path and the name
     * @param idProject
     * @param nom the new project name
     * @param chemin the new project path
     */
    public void moveOrRenameProject(int idProject, String nom, String chemin){
        Project project = em.find(Project.class, idProject);
//        /** moving the file **/
//        try {
//            ProjectFile dir = new ProjectFile(project.getPATH() + project.getName());
//            dir.renameTo(new ProjectFile(chemin + nom));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        /********************/
        project.setName(nom);
        project.setPath(chemin);
        em.persist(project);
    }


    public void makeFile(String filename, int idDir, User user){
        Project fDir = em.find(Project.class, idDir);
        FileInProject newFile = fDir.addFile(filename, user);
        em.persist(newFile);
    }

    public void makeDir(String dirname, int idDir, User user){
        Project fDir = em.find(Project.class, idDir);
        DirectoryInProject newDir = fDir.addDir(dirname, user);
        em.persist(newDir);
    }
}