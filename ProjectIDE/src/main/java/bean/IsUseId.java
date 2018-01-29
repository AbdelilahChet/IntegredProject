package bean;

import java.io.Serializable;

public class IsUseId implements Serializable {
    private int idUser;
    private int idFile;

    public IsUseId() {
    }

    public IsUseId(int idFile, int idUser) {
        this.idFile = idFile;
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        return idUser * 10000 + idFile;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof IsUseId) {
            IsUse otherId = (IsUse) object;
            return (otherId.getIdUser() == this.idUser) && otherId.getIdFile() == this.idUser;
        }
        return false;
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
}
