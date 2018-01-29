package bean;

import javax.persistence.*;

@Entity
@Table(name="USER_PROJECT_RELATION")
@IdClass(UserProjectRelationId.class)
public class UserProjectRelation {
    @Id @Column(name="ID_USER")
    private long userId;
    @Id @Column(name="ID_PROJECT")
    private long projectId;
    @Column(name="MANAGER")
    private boolean isManager;
    private @Column(name="READ_RIGHT")          Boolean read;
    private @Column(name="WRITE_RIGHT")         Boolean write;
    private @Column(name="ADD_USER_RIGHT")      Boolean addUser;
    private @Column(name="CHANGE_RIGHTS_RIGHT") Boolean changeRights;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="ID_USER", referencedColumnName="ID")
    private User user;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="ID_PROJECT", referencedColumnName="ID")

    private Project project;

    public UserProjectRelation(){
        read = true;
        write = true;
        addUser = true;
        changeRights = true;
    }


    public UserProjectRelation(Project project, User user) {
        System.out.println("######################### RELATION CONSTRUCTOR #####################");
        this.project = project;
        this.user = user;

        this.projectId = getProject().getId();
        this.userId = getUser().getId();

        read = true;
        write = true;
        addUser = true;
        changeRights = true;
    }

    // GETTERS AND SETTERS

    public boolean getRights(int i) {
        switch (i){
            case 0:
                return read;
            case 1:
                return write;
            case 2:
                return addUser;
            case 3:
                return changeRights;
        }
        return false;
    }

    public void setRights(boolean right, int i) {
        switch (i){
            case 0:
                this.read = right;
            case 1:
                this.write = right;
            case 2:
                this.addUser = right;
            case 3:
                this.changeRights = right;
        }
    }


    public Project getProject(){return project; }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
