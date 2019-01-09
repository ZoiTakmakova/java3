package ru.zt.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.Contacts;
import ru.zt.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class ContactDeletionTest extends TestBase {

@BeforeMethod
public void ensurePrecondition() {
  if(app.db().contacts().size()==0){
    if(app.db().groups().size()==0){
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
    app.goTo().gotoAddNewPage();
    app.contact().create(new ContactData().withLastname("Ivanov1").withFirstname("Ivan1").
            withHomePhone("111111").withMobilePhone("22222").withWorkPhone("33333").inGroup("test1"), true);
  }
}

@Test
public void ContactDeletionTest() {
  app.goTo().homePage();
 Contacts before = app.db().contacts();
  ContactData deletedContact = before.iterator().next();
  app.contact().delete(deletedContact);
  app.goTo().homePage();
  Contacts after = app.db().contacts();
  assertEquals(after.size(), before.size() - 1);//проверка: кол-во контактов после удаления должно быть равно кол-ву контактов до удаления  -1

  assertThat(after, equalTo(before.without(deletedContact)));
  verifyContactListInUI();/*настройка конфигурации запуска -DverifyUI=true*/
}
}
