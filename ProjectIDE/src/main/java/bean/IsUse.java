
package bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(IsUseId.class)
@Table(name = "IS_USE")
public class IsUse {
    @Id
    @Column(name = "ID_USER")
    private int idUser;
    @Id
    @Column(name = "ID_FILE")
    private int idFile;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ID_USER", referencedColumnName = "ID")
    private User user;
    @Column(name = "LAST_ACTIVITY")
    private Date lastActivity;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ID_FILE", referencedColumnName = "ID")
    private FileInProject file;

    public IsUse(){}
    public IsUse(User user, FileInProject file) {
        this.file = file;
        this.idFile = file.getId();
        this.user = user;
        this.idUser = user.getId();
        this.lastActivity = new Date();
    }

    /**
     *
     * @param candidate regarde si un user peut ecrire sur un fichier
     *
     * @return  vrai si il n'y a pas de user dessus ou si le user qui possède le locket est le meme que celui qui demande l'acces
     */
    public boolean canAccess(User candidate) {
        return user == null || user.getId() == candidate.getId();
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFile() {
        return idFile;
    }

    public void setIdFile(int idFile) {
        this.idFile = idFile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public FileInProject getFile() {
        return file;
    }

    public void setFile(FileInProject file) {
        this.file = file;
    }
}
