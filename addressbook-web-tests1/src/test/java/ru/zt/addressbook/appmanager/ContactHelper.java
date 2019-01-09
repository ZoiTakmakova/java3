package ru.zt.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

public ContactHelper(WebDriver wd) {
  super(wd);
}

public void returnToHomePage() {
  click(By.linkText("home page"));
}

public void fillContactData(ContactData contactData, boolean creation) {
  type(By.name("firstname"), contactData.getFirstname());
  type(By.name("middlename"), contactData.getMiddlename());
  type(By.name("lastname"), contactData.getLastname());
  type(By.name("address"), contactData.getAddress());
  type(By.name("home"), contactData.getHomePhone());
  type(By.name("mobile"), contactData.getMobilePhone());
  type(By.name("work"), contactData.getWorkPhone());
  type(By.name("email"), contactData.getEmail_1());
  type(By.name("email2"), contactData.getEmail_2());
  type(By.name("email3"), contactData.getEmail_3());
 // attach(By.name("photo"), contactData.getPhoto());

  //элемент из выпадающего списка
  if (creation) {
    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
  } else {
    Assert.assertFalse(isElementPresent(By.name("new_group")));
  }
}

public void submitAddNewCreation() {
  click(By.xpath("//div[@id='content']/form/input[21]"));
}

public void selectContact(int index) {
  wd.findElements(By.name("selected[]")).get(index).click();
}

public void selectContactById(int id) {
  wd.findElement(By.cssSelector("input[value ='" + id + "']")).click();
}

public void submitEditContact(int id) {
  wd.findElement(By.cssSelector("a[href ='edit.php?id=" + id + "']")).click();
  //wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a",id))).click();
  //wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a",id))).click();
  //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
}

public void submitDeletionContact() {
  click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
}

public void completionDeletion() {
  wd.switchTo().alert().accept();
}

public void submitUpDateContact() {
  click(By.xpath("//div[@id='content']/form[1]/input[22]"));
}

public void create(ContactData contact, boolean b) {
  fillContactData(contact, b);
  submitAddNewCreation();
  contactCash = null;
  returnToHomePage();
}

public void modify(ContactData contact) {
  selectContactById(contact.getId());
  submitEditContact(contact.getId());
  fillContactData(contact, false);
  contactCash = null;
  submitUpDateContact();
}

public void delete(ContactData contact) {
  selectContactById(contact.getId());
  submitDeletionContact();
  contactCash = null;
  completionDeletion();
}

private Contacts contactCash = null;

//формирование множества контактов
public Contacts all() {
  if (contactCash != null) {
    return new Contacts(contactCash);
  }
  contactCash = new Contacts();
  Contacts contacts = new Contacts();
  List<WebElement> elements = wd.findElements(By.name("entry"));
  for (WebElement element : elements) {
    int id = Integer.parseInt(element.findElement(By.tagName("td")).findElement(By.tagName("input")).getAttribute("id"));
    String lastname = element.findElement(By.xpath(".//td[2]")).getText();
    String firstname = element.findElement(By.xpath(".//td[3]")).getText();
    String address = element.findElement(By.xpath(".//td[4]")).getText();
    String allEmail = element.findElement(By.xpath(".//td[5]")).getText();
    String allPhones = element.findElement(By.xpath(".//td[6]")).getText();
    contactCash.add(new ContactData().withId(id).withLastname(lastname).withFirstname(firstname).withAddress(address)
            .withEmail(allEmail).withAllPhones(allPhones));
  }
  return new Contacts(contactCash);
}

public ContactData infoFromEditForm(ContactData contact) {
  initContactModificationById(contact.getId());//выбор контакта по идентификатору
  String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
  String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
  String address = wd.findElement(By.name("address")).getAttribute("value");
  String email_1 = wd.findElement(By.name("email")).getAttribute("value");
  String email_2 = wd.findElement(By.name("email2")).getAttribute("value");
  String email_3 = wd.findElement(By.name("email3")).getAttribute("value");
  String home = wd.findElement(By.name("home")).getAttribute("value");
  String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
  String work = wd.findElement(By.name("work")).getAttribute("value");
  wd.navigate().back();
  return  new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withAddress(address)
          .withEmail_1(email_1).withEmail_2(email_2).withEmail_3(email_3)
          .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
}

//выбор контакта по идентификатору
private void initContactModificationById(int id) {
  //находим чек-бокс
  WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']",id)));
  //далее переходим к родительскому эл-ту относительно чек-бокса [input(чек-бокс)-->td(ячейка)-->tr(строка)]
  WebElement row = checkbox.findElement(By.xpath("./../.."));
  //берем полный список ячеек, те ищем в строке все элементы, которые имеют тег "td"
  List<WebElement> cells = row.findElements(By.tagName("td"));
  //среди этих ячеек берем по номеру [get(7)] нужную, внутри ячейки находим ссылку с тегом "а"
  cells.get(7).findElement(By.tagName("a")).click();
}



}



