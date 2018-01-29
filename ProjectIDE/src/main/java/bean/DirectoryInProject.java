package bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Entity
@Table(name = "DIRECTORY")
@DiscriminatorValue("directory")
public class DirectoryInProject extends Container implements Serializable{
    @JoinColumn(name="ID_FATHER")
    private int idfather;
    private static final Logger LOGGER = Logger.getLogger(Project.class.getName());
    @ManyToOne
    @JoinColumn(name="ID_FATHER")
    protected Container father;


    public DirectoryInProject(){}
    public DirectoryInProject(String name, Container father){
        super(name);
        this.father = father;
    }

    public boolean haveRights(User user, int right){
        if(father == null)
            return haveRights(user, right);
        else
            return father.haveRights(user, right);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdfather() {
        return idfather;
    }

    public void setIdfather(int idfather) {
        this.idfather = idfather;
    }

    public Container getFather() {
        return father;
    }

    public void setFather(DirectoryInProject father) {
        this.father = father;
    }
//
//    public List<Container> getChildren() {
//        return children;
//    }
//
//    public void setChildren(List<Container> children) {
//        this.children = children;
//    }

    @Override
    public String getPath(){
        return father.getPath() + name;
    }
}
