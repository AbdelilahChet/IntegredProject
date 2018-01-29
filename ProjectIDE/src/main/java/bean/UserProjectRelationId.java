package bean;

import java.io.Serializable;

public class UserProjectRelationId implements Serializable {
    private long userId;
    private long projectId;

    public UserProjectRelationId(){}

    public UserProjectRelationId(long userId, long projectId){
        this.userId = userId;
        this.projectId = projectId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * lets consider we will not have more than 9999 project created in our database
     * @return combination of both id
     */
    public int hashCode() {
        return (int)(userId*10000 + projectId);
    }

    public boolean equals(Object object) {
        if (object instanceof UserProjectRelationId) {
            UserProjectRelationId otherId = (UserProjectRelationId) object;
            return (otherId.userId == this.userId) && (otherId.projectId == this.projectId);
        }
        return false;
    }
}
