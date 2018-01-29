package bean;

import constants.CONSTANTS;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "PROJECT")
@DiscriminatorValue("project")
public class Project extends Container implements Serializable {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL)
    private List<UserProjectRelation> users = new ArrayList();
    @Column(name="PATH")
    private String path;

    public Project(){}
    public Project(String name, String path) {
        super(name);
        this.path = path;
    }

    private boolean checkPermission(UserProjectRelation userProjectRelation, User mainUser, int right) {
        long uprId = userProjectRelation.getProject().getId();
        for (UserProjectRelation UPR : mainUser.getProjects())
            if (UPR.getProject().getId() == uprId && !UPR.getRights(right))
                return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (UserProjectRelation UPR : users) {
            str.append(UPR.toString());
            str.append("\n");
        }
        str.append("#################");
        for (FileInProject fileInProject : files) {
            str.append(fileInProject.toString());
            str.append("\n");
        }
        return str.toString();
    }

//    /**
//     * look and return an exising relation in the users list
//     * @param idUser the id of the User in the relation
//     * @param idProject the id of the project in the relation
//     * @return return the relation
//     */
//    public UserProjectRelation getRelation(int idUser, int idProject) {
//        for (UserProjectRelation userProjectRelation : users) {
//            if (userProjectRelation.getUser().getId() == idUser && userProjectRelation.getProject().getId() == idProject)
//                return userProjectRelation;
//            LOGGER.info(userProjectRelation.getProject().getId() + " " + idProject + " " + userProjectRelation.getUser().getId() + " " + idUser);
//        }
//        return null;
//    }
//

    /**
     * find a relation using a user
     * @param user
     * @return
     */
    public UserProjectRelation findRelation(User user) {
        for (UserProjectRelation relation : users)
            if (relation.getUser().getId() == user.getId())
                return relation;
        return null;
    }

    @Override
    public boolean haveRights(User user, int right) {
        return findRelation(user).getRights(right);
    }

    public void addUser(UserProjectRelation userProjectRelation, User mainUser, boolean isCreator) {
        if (!isCreator) {
            if (!checkPermission(userProjectRelation, mainUser, Right.ADD_MEMBER.getValue()))
                return;
            User user = userProjectRelation.getUser();
            for (UserProjectRelation upr : users)
                if (upr.getUser().getEmail().equals(user.getPseudo())) {
                    LOGGER.warning("User " + user.getPseudo() + " already belongs to project " + this.getName());
                    return;
                }
            user.getProjects().add(userProjectRelation);
            LOGGER.info("User " + user.getPseudo() + " added to project " + this.getName());
        } else {
            mainUser.getProjects().add(userProjectRelation);
        }
        LOGGER.setLevel(Level.INFO);
        users.add(userProjectRelation);
    }

    public void removeUser(User targetUser, User mainUser) {
        boolean right = haveRights(mainUser, Right.WRITE.getValue());
        LOGGER.info("Attempting to remove " + targetUser.getName() + " from " + getName());
        if (right)
            for (UserProjectRelation relation : users)
                if (relation.getUser().getId() == targetUser.getId())
                    users.remove(relation);
                else
                    LOGGER.warning(mainUser.getName() + "doesn't have the permission to remove an user");
    }


    public String zipIt(){
        return CONSTANTS.getInstance().zipIt(getName());
    }

    public DirectoryInProject getDir(String dirName){
        for(Container dir: children){
            if(dir.getName().equals(dirName))
                return (DirectoryInProject) dir;
        }
        return null;
    }

    public FileInProject getFile(String filename){
        List<String> dirs = Arrays.asList(filename.split("/"));
        return findFile(dirs, dirs.remove(dirs.size() - 1));
    }

    public void deleteFile(String filename, User user){
         List<String> dirs = Arrays.asList(filename.split("/"));
         FileInProject f = findFile(dirs, dirs.remove(dirs.size() - 1));
         f.getFather().getFiles().remove(f);
    }

    @Override
    public String getPath(){
        return path + "/" + name;
    }

    /**
     * getters setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserProjectRelation> getUsers() {
        return users;
    }

    public void setUsers(List<UserProjectRelation> users) {
        this.users = users;
    }

    public void setPath(String path) {
        this.path = path;
    }
}