package ru.zt.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTest extends TestBase {
@BeforeMethod
public void ensurePrecondition() {
  if (app.db().groups().size() == 0) {
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("test1"));
  }
}

//@Test (enabled = false) /*ТЕСТ ОТКЛЮЧЕН*/
@Test
public void testGroupDeletion() {
  app.goTo().groupPage();
  Groups before = app.db().groups();
  GroupData deletedGroup = before.iterator().next();
  app.group().delete(deletedGroup);
  assertEquals(app.group().count(), before.size() - 1);
  Groups after = app.db().groups();

  assertThat(after, equalTo(before.without(deletedGroup)));
  verifyGroupListInUI();/*настройка конфигурации запуска -DverifyUI=true*/
}
}
