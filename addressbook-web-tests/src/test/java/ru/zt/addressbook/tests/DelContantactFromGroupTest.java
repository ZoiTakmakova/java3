package ru.zt.addressbook.tests;

import org.testng.annotations.Test;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.Contacts;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.zt.addressbook.tests.TestBase.app;

public class DelContantactFromGroupTest  extends TestBase {

@Test
public void delContactFromGroup() {
  app.goTo().homePage();
  //получить список контактов
  Contacts allContact = app.db().contacts();
  //получить список всех групп
  Groups allGroups = app.db().groups();

  //количество удаленных контактов
  int n = 0;

  //цикл перебора всех существующих групп
  for (GroupData g1 : allGroups) {
    if (n == 1) {
      break;
    } else {
      int g1Id = g1.getId();

      //цикл перебора всех существующих  контактов
      for (ContactData c : allContact) {
        if (n == 1) {
          break;
        } else {
          int contactId = c.getId();
          //получить список групп в которые включен контакт
          Groups beforeGroupsOfContact = c.getGroups();

          //цикл перебора всех групп в которые включен контакт
          for (GroupData g2 : beforeGroupsOfContact) {
            int g2Id = g2.getId();
            //если ид групп  равны
            if (g1Id == g2Id) {
              //удаляем контакт из группы и покидаем все циклы
              app.contact().delFromGroup(c, g1);
              //количество количество удаленных контактов увеличивается на 1
              ++n;
              //проверить, что контакт удалился из группы
              Contacts afterContact = app.db().contactInGroup(contactId);
              ContactData a = afterContact.iterator().next();
              Groups afterGroupsOfContact = a.getGroups();
              assertThat(afterGroupsOfContact.size(), equalTo(beforeGroupsOfContact.size() - 1));
              break;
            }
          }
        }
      }
    }
  }
}
}
