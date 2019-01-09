package ru.zt.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.zt.addressbook.appmanager.ApplicationManager;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.Contacts;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

Logger logger = LoggerFactory.getLogger(TestBase.class);

protected static final ApplicationManager app
        = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

@BeforeSuite/*один запуск*/
public void setUp() throws Exception {
  app.init();
}

@AfterSuite(alwaysRun = true)/*один запуск*/
public void tearDown() {
  app.stop();
}

@BeforeMethod
public void logTestStart(Method m, Object[] p) {
  logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));

}

@AfterMethod(alwaysRun = true)
public void logTestStop(Method m) {
  logger.info("Stop test " + m.getName());
}

public void verifyGroupListInUI() {
  if (Boolean.getBoolean("verifyUI")) {
    //список групп из БД
    Groups dbGroups = app.db().groups();
    //список групп из польз.интерфейса
    Groups uiGroups = app.group().all();
    assertThat(uiGroups, equalTo(dbGroups.stream()
            .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
            .collect(Collectors.toSet())));
  }
}

public void verifyContactListInUI() {
  if (Boolean.getBoolean("verifyUI")) {
    //список контактов из БД
    Contacts dbContacts = app.db().contacts();
    //список групп из польз.интерфейса
    Contacts uiContacts = app.contact().all();
    /*assertThat(uiContacts, equalTo(dbContacts.stream()
            .map((c) -> new ContactData().withId(c.getId()).withFirstname(c.getFirstname()).withLastname(c.getLastname()))
            .collect(Collectors.toSet())));*/
    assertThat(uiContacts, equalTo(dbContacts));
  }
}
}
