package bean;


import constants.CONSTANTS;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("file")
@Table(name = "FILE")
public class FileInProject extends FileOrContainer{
    @JoinColumn(name = "ID_FATHER")
    private int idFather;
    @ManyToOne
    @JoinColumn(name = "ID_FATHER", referencedColumnName = "ID")
    private Container father;


    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    private List<IsUse> isUse = new ArrayList<IsUse>();


    public FileInProject() {}

    public FileInProject(String nom, Container dir) throws IOException {
        super(nom);
        this.father = dir;
        this.idFather = dir.getId();
        CONSTANTS.getInstance().createFileOnDisk(getPath() + "/" + nom);
    }

    @Override
    public String getPath(){
        return father.getPath() + "/" + name;
    }

    /**
     * Gestion de collision sur un fichier, system de locket
     * @return  vrai si on peut prendre le locket sur le fichier
     */
    public boolean isWritable(){
        return isUse.isEmpty();
    }

    /**
     *  @return Si l'user a les droit d'acces à un fichier il les prend et renvoit vrai
     *  sinon renvoit false
     *
     */
    public boolean takeLocket(User user){
        if(isUse.isEmpty() && father.haveRights(user, Right.WRITE.getValue())) {
            isUse.add(new IsUse(user, this));
            return true;
        }
        return false;
    }

    /**
     * @param user le user qui veut quitter le locket, on verifie s'il s"agit de celui qui le possède actuellement
     * @return si le user en question est bien le bon on il peut se retirer du locket
     * sinon rien
     */
    public boolean leaveLocket(User user){
        if(!isUse.isEmpty() && isUse.get(0).getIdUser() == user.getId())
            return true;
        return false;
    }


    /**
     * determine si l'user est inactif
     * @return vrai si le temps d'inactivité est supérieur à 500 secondes
     */
    public boolean leaveLocketOnTime(){
        Long currentTime = new Date().getTime();
        if(CONSTANTS.getInstance().getTimeLocket() > (currentTime - isUse.get(0).getLastActivity().getTime()) )
            return true;
        return false;
    }


    /**
     * permet d'ecrire sur un fichier sur le disque
     * si l'user a ecrit dans le fichier alors le last activity de isUse est mis à l'heure
     * actuelle
     * @param content le contenu du ficher
     */
    public void writeOnFile(String content) {
        CONSTANTS.getInstance().writeOnFileOnDisk(content, getPath());
        if(!isUse.isEmpty())
            isUse.get(0).setLastActivity(new Date());
    }

    public void deleteFile() {
        CONSTANTS.getInstance().deleteFileOrDirectoryOndisk(getPath());
    }



    //getter and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Container getFather() {
        return father;
    }

    public void setFather(DirectoryInProject father) {
        this.father = father;
    }

    public List<IsUse> getIsUse() {
        return isUse;
    }

    public void setIsUse(List<IsUse> isUse) {
        this.isUse = isUse;
    }

    public int getIdFather() {
        return idFather;
    }

    public void setIdFather(int idFather) {
        this.idFather = idFather;
    }

    public void setFather(Container father) {
        this.father = father;
    }
}

