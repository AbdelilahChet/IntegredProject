package bean;

import java.io.Serializable;

public class UserProjectRelationId implements Serializable {
    private User user;
    private Project project;

    /**
     * lets consider we will not have more than 9999 project created in our database
     *
     * @return combination of both id
     */
    public int hashCode() {
        return (int) (user.getId() * 10000 + project.getId());
    }

    public boolean equals(Object object) {
        if (object instanceof UserProjectRelationId) {
            UserProjectRelationId otherId = (UserProjectRelationId) object;
            return (otherId.user.getId() == this.user.getId())
                    && (otherId.project.getId() == this.project.getId());
        }
        return false;
    }
}
