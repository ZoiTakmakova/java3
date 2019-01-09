package ru.zt.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTest extends TestBase {

@BeforeMethod
public void ensurePrecondition() {
  app.goTo().homePage();
  if (app.contact().all().size() == 0) {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
    }
    app.goTo().gotoAddNewPage();
    app.contact().create(new ContactData().withLastname("Ivanov1").withFirstname("Ivan1").withGroup("test1").
            withHomePhone("111111").withMobilePhone("22222").withWorkPhone("33333"), true);

    app.contact().create(new ContactData().withLastname("Ivanov1").withFirstname("Ivan1").
            withHomePhone("111111").withMobilePhone("22222").withWorkPhone("33333"), true);
  }
}

@Test
public  void  testContactPhone(){
  app.goTo().homePage();
  ContactData contact = app.contact().all().iterator().next(); //загрузка множество контактов[all()], и выбираем
  // какой-то контактслучайным[.iterator().next()]
  //infoFromEditForm(contact) - метод загрузки информации из формы редактирования контактов
  ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
//сравниваем телефоны, полученные с главной страницы с очищенной строчкой
  assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

}

private <T> String mergePhones(ContactData contact) {

  return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone())
          //отфильтровываем строки
          .stream().filter((s)->!s.equals(""))
          //применяем функцию очистки
          .map(ContactPhoneTest::cleaned)
          //собираем 1 строчку из очищенных телефонов
          .collect(Collectors.joining("\n"));
}

//функция, удаляющая ненужные символы
 public static String cleaned(String phone){
  return phone.replaceAll("\\s","").replaceAll("[-()]","");
 }

}
