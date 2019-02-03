package ru.zt.idsys.model;

import java.util.Date;

public class CLientData {

    private String clientFullName;
    private Date debtorBirthDate;
    private String address;


    public String getClientFullName() {
        return clientFullName;
    }
    public String getAddress() {
        return address;
    }
    public Date getDebtorBirthDate() {
        return debtorBirthDate;
    }



    public CLientData withClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
        return this;
    }

    public CLientData withDebtorBirthDate(Date debtorBirthDate) {
        this.debtorBirthDate = debtorBirthDate;
        return this;
    }

    public CLientData withAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CLientData that = (CLientData) o;

        if (clientFullName != null ? !clientFullName.equals(that.clientFullName) : that.clientFullName != null)
            return false;
        if (debtorBirthDate != null ? !debtorBirthDate.equals(that.debtorBirthDate) : that.debtorBirthDate != null)
            return false;
        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result = clientFullName != null ? clientFullName.hashCode() : 0;
        result = 31 * result + (debtorBirthDate != null ? debtorBirthDate.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
