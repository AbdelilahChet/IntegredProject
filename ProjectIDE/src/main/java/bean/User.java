package bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "USER")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "email", length = 255, unique = true)
    private String email;
    @Column(name = "pseudo", length = 255)
    private String pseudo;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "surname", length = 255)
    private String surname;
    @Column(name = "password", length = 255)
    private String password;

    // http://stackoverflow.com/questions/13027214/jpa-manytoone-with-cascadetype-all
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProjectRelation> projects;

    public User() {
    }

    public User(String email, String pseudo, String name, String surname, String password) {
        this.email = email;
        this.pseudo = pseudo;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public List<UserProjectRelation> getProjects() {
        return projects;
    }

    public void setProjects(List<UserProjectRelation> userProjectRelations) {
        this.projects = userProjectRelations;
    }

    public void addProject(UserProjectRelation project) {
        projects.add(project);
    }

    /**
     * Renvoie le pseudo de l'utilisateur
     *
     * @return le pseudo
     */
    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Renvoie l'email de l'utilisateur
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String Surname) {
        this.surname = Surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
