package dao;

import bean.UserProjectRelation;
import bean.Project;
import bean.User;
import bean.UserProjectRelationId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserProjectRelationDAO {
//
    @PersistenceContext
    private EntityManager em;

    public void addUser(String status, int idGroupe, User user){
        UserProjectRelation userProjectRelation = em.find(UserProjectRelation.class, idGroupe);
        em.persist(userProjectRelation);
    }

    public UserProjectRelation createRelation(User mainUser, User user, Project project) {
        UserProjectRelation userProjectRelation = em.find(UserProjectRelation.class, new UserProjectRelationId(user.getId(),project.getId()));
        if(userProjectRelation == null) {
            System.out.println("Test doublon");
            userProjectRelation = new UserProjectRelation(project, user);
            project.addUser(userProjectRelation, mainUser, false);
            user.addProject(userProjectRelation);
            em.persist(userProjectRelation);
        }
        return userProjectRelation;
    }

    public List<Integer> getProjectIdByUser(int id_user){
        String request = "SELECT ID_PROJECT FROM USER_PROJECT_RELATION WHERE ID_USER = :userid";// Dois sélectionner tous les projets par utilisateur
        List<Integer> result = em.createNativeQuery(request).setParameter("userid",id_user).getResultList();
        if(!result.isEmpty()) return result;
        return null;
    }

    public void removeRelation(int idUser, int idProject){
        em.createNativeQuery("DELETE FROM USER_PROJECT_RELATION WHERE ID_USER = ? and ID_PROJECT = ?")
                .setParameter(1, idUser)
                .setParameter(2, idProject)
                .executeUpdate();
    }
}


