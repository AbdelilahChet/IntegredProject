package bean;

import constants.CONSTANTS;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {


    private static final Logger LOGGER = Logger.getLogger(Project.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME", length = 255)
    private String name;
    @Column(name = "PATH", length = 255)
    private String path;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<UserProjectRelation> users = new ArrayList();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<FileInProject> files = new ArrayList();

    public Project(){}
    public Project(String nom, String chemin) {
        this.name = nom;
        this.path = chemin;
        CONSTANTS.getInstance().createProjectOnDisk(chemin + "/" + nom);
    }

    public static Logger getLOGGER() {
        return LOGGER;
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

    public UserProjectRelation getRelation(int idUser, int idProject) {
        for (UserProjectRelation userProjectRelation : users) {
            if (userProjectRelation.getUser().getId() == idUser && userProjectRelation.getProject().getId() == idProject)
                return userProjectRelation;
            LOGGER.info(userProjectRelation.getProject().getId() + " " + idProject + " " + userProjectRelation.getUser().getId() + " " + idUser);
        }
        return null;
    }

    public UserProjectRelation findRelation(User user) {
        for (UserProjectRelation relation : users)
            if (relation.getUser().getId() == user.getId())
                return relation;
        return null;
    }

    public Boolean haveRights(User user, int right) {
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
                    LOGGER.warning(mainUser.getName() + "doesn't have the permission to remove a user");
    }

    public void addFile(FileInProject fileInProject, User user) {
        boolean right = haveRights(user, Right.WRITE.getValue());
        LOGGER.info("Attempting to add a file in the project " + getName());
        if (right) {
            for (FileInProject file : files)
                if (file.toString().equals(fileInProject.toString())) {
                    return;
                }
            LOGGER.info(user.getName() + " add the file " + fileInProject.getNom() + " int the project " + getName());
            files.add(fileInProject);
        } else
            LOGGER.warning(user.getName() + " doesn't have the permission to add a file in project " + getName());
    }

    public void deleteFile(String name, User user) {
        LOGGER.setLevel(Level.INFO);
        boolean right = haveRights(user, Right.WRITE.getValue());
        if (right) {
            for (FileInProject file : files)
                if (file.getNom().equals(name)) {
                    file.deleteFile();
                    file.setProject(null);
                    files.remove(file);
                    LOGGER.info("file " + file.getNom() + " deleted");
                    return;
                }
        }
        LOGGER.setLevel(Level.WARNING);
        LOGGER.warning("file not found");
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<UserProjectRelation> getUsers() {
        return users;
    }

    public void setUsers(List<UserProjectRelation> users) {
        this.users = users;
    }

    public List<FileInProject> getFiles() {
        return files;
    }

    public void setFiles(List<FileInProject> files) {
        this.files = files;
    }

    /**
     * getters setters
     */


    public FileInProject getFile(String name) {
        for (FileInProject file : files)
            if (file.getNom().equals(name))
                return file;
        return null;
    }
}