package ru.zt.addressbook.tests;

import org.hibernate.SessionFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.Contacts;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTest extends TestBase {

private SessionFactory sessionFactory;
private ContactData contact;

//проверка предусловий
@BeforeMethod
public void ensurePrecondition1() {
  if (app.db().contacts().size() == 0) {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
    app.goTo().gotoAddNewPage();
    app.contact().create(new ContactData().withLastname("Ivanov1").withFirstname("Ivan1").
            withHomePhone("111111").withMobilePhone("22222").withWorkPhone("33333"), true);
  }
}

//проверка предусловия:если контакт не добавлен ни в одну группу, добавить в любую
@BeforeMethod
public void ensurePrecondition2() {
  app.goTo().homePage();
  //получить список контактов
  Contacts beforeAddedContact = app.db().contacts();
  //получить список всех групп
  Groups allGroups = app.db().groups();
  //цикл перебора всех существующих  контактов
  for (ContactData c : beforeAddedContact) {
    int contactId = c.getId();
    //получить список групп в которые включен контакт
    Groups beforeGroupsOfContact = c.getGroups();
    //если контакт не добавлен ни в одну группу, добавить в любую
    if (beforeGroupsOfContact.size() == 0) {
      app.contact().addToGroup(c, allGroups.iterator().next());
      //проверить что контакт добавлен в группу
      Contacts afterContact = app.db().contactInGroup(contactId);
      ContactData a = afterContact.iterator().next();
      Groups afterGroupsOfContact = a.getGroups();
      assertThat(afterGroupsOfContact.size(), equalTo(beforeGroupsOfContact.size() + 1));
    }else break;

  }
}

//проверка предусловий:если все контакты добавлены во все группы, создать новую группу
@BeforeMethod
public void ensurePrecondition3() {
  //получить список контактов
  Contacts beforeAddedContact = app.db().contacts();
  //получить список всех групп
  Groups allGroups = app.db().groups();
  //число проверенных контактов
  int n = 0;
  //цикл перебора всех существующих  контактов
  for (ContactData c : beforeAddedContact) {
    //число проверенных контактов увеличивается на 1
    ++n;
    //получить список групп в которые включен контакт
    Groups groupsOfContact = c.getGroups();
    //если количество групп,в которых состоит контакт и количество вообще существующих групп равны и  если контактов для проверки больше нет
    if (groupsOfContact.size() == allGroups.size() && n == beforeAddedContact.size()) {
       //создаем новую группу
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test2").withHeader("test2").withFooter("test3"));
    }
  }
}

//тест добавления контакта в группу
    @Test
    public void addContactToGroup () {

      app.goTo().homePage();
      //получить список контактов
      Contacts beforeContact = app.db().contacts();
      //получить список всех групп
      Groups allGroups = app.db().groups();
      //цикл перебора всех существующих  контактов
      for (ContactData c : beforeContact) {
//получить список групп в которые включен контакт
        Groups beforeGroupsOfContact = c.getGroups();
        int contactId = c.getId();
        //количество добавленных контактов
        int k = 0;
        //цикл перебора всех существующих групп
        for (GroupData g1 : allGroups) {
          if (k == 1) {
            break;
          } else {
            int g1Id = g1.getId();
            //количество попыток
            int n = 0;
            //цикл перебора всех групп в которые включен контакт
            for (GroupData g2 : beforeGroupsOfContact) {
              if (k == 1) {
                break;
              } else {
                int g2Id = g2.getId();
                //количество попыток увеличивается на 1
                ++n;
                //если ид групп не равны
                if (g1Id != g2Id) {
                  //проверить есть ли еще попытки
                  if (n == beforeGroupsOfContact.size()) {
                    //если попыток нет, добавить с в g1
                    app.contact().addToGroup(c, g1);
                    //количество добавленных контактов увеличивается на 1
                    ++k;
                    //проверить что контакт добавлен в группу
                    Contacts afterContact = app.db().contactInGroup(contactId);
                    ContactData a = afterContact.iterator().next();
                    Groups afterGroupsOfContact = a.getGroups();
                    assertThat(afterGroupsOfContact.size(), equalTo(beforeGroupsOfContact.size() + 1));
                    break;
                  }
                } else break;
              }
            }
          }
        }
      }
    }
  }
