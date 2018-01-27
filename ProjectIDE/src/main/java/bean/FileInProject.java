package bean;


import constants.CONSTANTS;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "FILE")
public class FileInProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME", length = 255)
    private String nom;
    @Column(name = "PATH", length = 255)
    private String path;
    @ManyToOne
    @JoinColumn(name = "ID_PROJECT", referencedColumnName = "ID")
    private Project project;

    public FileInProject() {
    }

    public FileInProject(String nom, String path, Project project) throws IOException {
        CONSTANTS.getInstance().createFileOnDisk(project.getPath() + "/" + path + "/" + nom);
        this.nom = nom;
        this.path = path;
        this.project = project;
    }

    public String toString() {
        return path + nom;
    }

    public void writeOnFile(String content) {
        CONSTANTS.getInstance().writeOnFileOnDisk(content, project.getPath() + "/" + path);
    }

    public void deleteFile() {
        CONSTANTS.getInstance().deleteFileOrDirectoryOndisk(project.getPath() + "/" + path);
    }
//
//    private DataFormat timelocked = null;
//    private User locker = null;
//
//    public User getLocker() {
//        return locker;
//    }
//
//    public void setLocker(User locker) {
//        this.locker = locker;
//    }
//


    //getter and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

//    public DataFormat getTimelocked() {
//        return timelocked;
//    }
//
//    public void setTimelocked(DataFormat timelocked) {
//        this.timelocked = timelocked;
//    }
}

