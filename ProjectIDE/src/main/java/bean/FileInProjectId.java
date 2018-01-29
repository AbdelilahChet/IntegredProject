package bean;

import java.io.*;

public class FileInProjectId implements Serializable{
    private Project project;
    private int id;

    public int hashCode(){
        return (int)(project.getId() *10000 + id);
    }

    public FileInProjectId(){}

    public FileInProjectId(Project project, int id){
        this.id = id;
        this.project = project;
    }

    public boolean equals(Object object){
        if(object instanceof FileInProjectId){
            FileInProjectId otherId = (FileInProjectId) object;
            return (otherId.id == this.id) && otherId.project.getId() == this.project.getId();
        }
        return false;
    }


    public Project getProject() {
        return project;
    }

    public int getId() {
        return id;
    }

    public void setIdProject(Project project) {
        this.project = project;
    }

    public void setId(int id) {
        this.id = id;
    }
}
