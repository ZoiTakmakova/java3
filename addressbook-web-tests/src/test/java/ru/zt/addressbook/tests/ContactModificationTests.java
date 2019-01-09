package ru.zt.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.Contacts;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

//проверка предусловий
@BeforeMethod
public void ensurePrecondition() {
  if (app.db().contacts().size() == 0) {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
    app.goTo().gotoAddNewPage();
    //получаем из БД все группы
    Groups groups = app.db().groups();
    app.contact().create(new ContactData().withLastname("Ivanov1").withFirstname("Ivan1").
            withHomePhone("111111").withMobilePhone("22222").withWorkPhone("33333").inGroup(groups.iterator().next()), true);
  }
}

@Test
public void testContactModification() {

  app.goTo().homePage();
  Contacts before = app.db().contacts();
  ContactData modifiedContact = before.iterator().next();
  ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastname("Ivanov2").withFirstname("Ivan2").
          withHomePhone("111111").withMobilePhone("22222").withWorkPhone("33333");
  app.goTo().homePage();
  app.contact().modify(contact);
  Contacts after = app.db().contacts();
  assertEquals(after.size(), before.size());

  assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  verifyContactListInUI();/*настройка конфигурации запуска -DverifyUI=true*/
}
}
