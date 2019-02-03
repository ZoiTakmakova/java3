package ru.zt.idsys.model;

public class CentRegData {
    //Ид центра регитрации
    private int idCentReg;
    //Наименование центра регистрации
    private String nameCentReg;
    //Филиал
    //Описание
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CentRegData that = (CentRegData) o;

        if (idCentReg != that.idCentReg) return false;
        return nameCentReg != null ? nameCentReg.equals(that.nameCentReg) : that.nameCentReg == null;
    }

    @Override
    public int hashCode() {
        int result = idCentReg;
        result = 31 * result + (nameCentReg != null ? nameCentReg.hashCode() : 0);
        return result;
    }

    public int getIdCentReg() {
        return idCentReg;
    }
    public String getNameCentReg() {
        return nameCentReg;
    }
    public String getDescription() {
        return description;
    }


    public CentRegData withIdCentReg(int idCentReg) {
        this.idCentReg = idCentReg;
        return this;
    }

    public CentRegData withNameCentReg(String nameCentReg) {
        this.nameCentReg = nameCentReg;
        return this;
    }
    public CentRegData withDescription(String descrCentReg) {
        this.description = description;
        return this;
    }



}
