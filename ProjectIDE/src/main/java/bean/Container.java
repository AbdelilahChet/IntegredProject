package bean;

import constants.CONSTANTS;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * https://en.wikibooks.org/wiki/Java_Persistence/Inheritance
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="CONT_TYPE")
@DiscriminatorValue("container")
@Table(name="CONTAINER")
public abstract class Container extends FileOrContainer{
    protected static final Logger LOGGER = Logger.getLogger(Project.class.getName());
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "father", cascade = CascadeType.ALL)
    protected List<FileInProject> files = new ArrayList();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "father", cascade = CascadeType.ALL)
    protected List<DirectoryInProject> children = new ArrayList<DirectoryInProject>();


    public Container(){}
    public Container(String name){
        super(name);
    }

    @Override
    public String toString() {
        String str = "";
        for (DirectoryInProject dir : children)
            str += dir.getName() + "\n";
        str += "#####################\n";
        for (FileInProject file : files)
            str += file.getName();
        return str;
    }

    /**
     * recherche un directory dans children
     * @param name le nom du directory
     * @return le directory
     */
    public DirectoryInProject getDir(String name){
        for(Container dir : children)
            if(dir.getName().equals(name))
                return (DirectoryInProject) dir;
        return null;
    }

    /**
     * recherche un directory dans children
     * @param id du directory
     * @return le directory
     */
    public DirectoryInProject getDir(int id){
        for(Container dir : children)
            if(dir.getId() == id)
                return (DirectoryInProject) dir;
        return null;
    }

    /**
     * revoit par recursion un fichier
     * @param path le chemin jusqu au fichier
     * @param name son nom
     * @return le fichier
     */
    public FileInProject findFile(List<String> path, String name){
        if(path.isEmpty()) {
            for (FileInProject file : files)
                if (file.getName().equals(name))
                    return file;
            return null;
        }
        else {
            path.remove(0);
            return getDir(path.get(0)).findFile(path, name);
        }
    }

    public boolean haveRights(User user, int rights){
        return true;
    }

    public FileInProject addFile(String filename, User user) {
        FileInProject newFile = null;
        boolean right = haveRights(user, Right.WRITE.getValue());
        LOGGER.info("Attempting to add a file in the project " + getName());
        if (right) {
            for (FileInProject file : files)
                if (file.toString().equals(filename)) {
                    return null;
                }
            LOGGER.info(user.getName() + " add the file " + filename+ " int the project " + getName());
            try {
                newFile = new FileInProject(filename, this);
                files.add(newFile);
            } catch (IOException e) {
                LOGGER.warning(e.toString());
            }
        } else
            LOGGER.warning(user.getName() + " doesn't have the permission to add a file in project " + getName());
        return newFile;
    }

    public DirectoryInProject addDir(String dirName, User user){
        DirectoryInProject newDir = null;
        if (haveRights(user, Right.WRITE.getValue())) {
            CONSTANTS.getInstance().createFolderOnDisk(getPath() + "/" + dirName);
            newDir = new DirectoryInProject(dirName, this);
            children.add(newDir);
        }
        return newDir;
    }

       // GETTERS AND SETTERS

    public List<DirectoryInProject> getChildren() {
        return children;
    }

    public void setChildren(List<DirectoryInProject> children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FileInProject> getFiles() {
        return files;
    }

    public void setFiles(List<FileInProject> files) {
        this.files = files;
    }
}
