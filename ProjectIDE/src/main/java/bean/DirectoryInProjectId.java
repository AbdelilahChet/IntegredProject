package bean;

import java.io.Serializable;

public class DirectoryInProjectId implements Serializable{
    private int id;
    private Project project;

    /**
     * lets consider we will not have more than 9999 project created in our database
     *
     * @return combination of both id
     */
    public int hashCode() {
        return (int) (id * 10000 + project.getId());
    }

    public boolean equals(Object object) {
        if (object instanceof DirectoryInProjectId) {
            DirectoryInProjectId otherId = (DirectoryInProjectId) object;
            return (otherId.getId() == id)
                    && (otherId.project.getId() == this.project.getId());
        }
        return false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
