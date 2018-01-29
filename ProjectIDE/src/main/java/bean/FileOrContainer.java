package bean;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="FILEORCONT")
@Table(name="FILE_OR_CONTAINER")
public abstract class FileOrContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;
    @Column(name="NAME")
    protected String name;


    /**
     * On recupère le chemin recursivement 
     * @return
     */
    public String getPath(){
        return "";
    }

    public FileOrContainer(){}
    public FileOrContainer(String name) {
        this.name = name;
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
}
