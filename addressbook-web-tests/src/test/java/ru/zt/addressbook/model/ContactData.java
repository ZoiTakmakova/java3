package ru.zt.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {

@XStreamOmitField
@Id
@Column(name="id")
private int id =  Integer.MAX_VALUE;

@Expose
@Column(name="firstname")
private String firstname;

@Expose
@Transient/*поле пропущено*/
private String middlename;

@Expose
@Column(name="lastname")
private String lastname;

@Expose
@Type(type="text")
@Column(name="address")
private String address;

@Expose
@Column(name="home")
@Type(type="text")
private String homePhone;

@Expose
@Type(type="text")
@Column(name="mobile")
private String mobilePhone;

@Expose
@Type(type="text")
@Column(name="work")
private String workPhone;

@Expose
@Transient/*поле пропущено*/
private String allPhones;

@Expose
@Type(type="text")
@Column(name="email")
private String email_1;

@Expose
@Type(type="text")
@Column(name="email2")
private String email_2;

@Expose
@Type(type="text")
@Column(name="email3")
private String email_3;

@Expose
@Transient/*поле пропущено*/
private String allEmail;

@ManyToMany(fetch =  FetchType.EAGER)
@JoinTable(name="address_in_groups"
        ,joinColumns = @JoinColumn(name="id"),inverseJoinColumns = @JoinColumn(name="group_id"))
private Set<GroupData> groups= new HashSet<GroupData>();
/*
@Expose
@Column(name="photo")
@Type(type="text")
private String photo;

public ContactData withPhoto( File photo) {
  this.photo = photo.getPath();
  return this;
}

public File getPhoto() {  return new File(photo);}*/

public String getEmail_1() {  return email_1;}

public String getEmail_2() {  return email_2;}

public String getEmail_3() {  return email_3;}

public String getLastname() {
  return lastname;
}

public String getAddress() {
  return address;
}

public String getHomePhone() {
  return homePhone;
}

public String getMobilePhone() {
  return mobilePhone;
}

public String getWorkPhone() {
  return workPhone;
}

public String getAllPhones() {  return allPhones;}

public String getAllEmail() {  return allEmail;}

public int getId() {return id;}

public String getFirstname() {
  return firstname;
}

public String getMiddlename() {
  return middlename;
}


public ContactData withEmail_1(String email_1) {
  this.email_1 = email_1;
  return this;
}

public ContactData withEmail_2(String email_2) {
  this.email_2 = email_2;
  return this;
}

public ContactData withEmail_3(String email_3) {
  this.email_3 = email_3;
  return this;
}

public ContactData withAllPhones(String allPhones) {
  this.allPhones = allPhones;
  return this;
}

public ContactData withId(int id) {
  this.id = id;
  return this;
}

public ContactData withFirstname(String firstname) {
  this.firstname = firstname;
  return this;
}

public ContactData withMiddlename(String middlename) {
  this.middlename = middlename;
  return this;
}

public ContactData withLastname(String lastname) {
  this.lastname = lastname;
  return this;
}

public ContactData withAddress(String address) {
  this.address = address;
  return this;
}

public ContactData withHomePhone(String homePhone) {
  this.homePhone = homePhone;
  return this;
}

public ContactData withMobilePhone(String mobilePhone) {
  this.mobilePhone = mobilePhone;
  return this;
}

public ContactData withWorkPhone(String workPhone) {
  this.workPhone = workPhone;
  return this;
}

public ContactData withEmail(String allEmail) {
  this.allEmail = allEmail;
  return this;
}

public Groups getGroups() {
  return new Groups(groups);
}

@Override
public String toString() {
  return "ContactData{" +
          "id='" + id + '\'' +
          ", firstname='" + firstname + '\'' +
          ", lastname='" + lastname + '\'' +
          '}';
}
@Override
public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;

  ContactData that = (ContactData) o;

  if (id != that.id) return false;
  if (middlename != null ? !middlename.equals(that.middlename) : that.middlename != null) return false;
  return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
}

@Override
public int hashCode() {
  int result = id;
  result = 31 * result + (middlename != null ? middlename.hashCode() : 0);
  result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
  return result;
}

public ContactData inGroup(GroupData group) {
  groups.add(group);
  return this;
}
}
