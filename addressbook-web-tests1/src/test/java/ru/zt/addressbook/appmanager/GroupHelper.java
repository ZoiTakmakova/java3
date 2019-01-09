package ru.zt.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {


public GroupHelper(WebDriver wd) {
  super(wd);
}

public void returnToGroupPage() {
  click(By.linkText("group page"));
}

public void submitGroupCreation() {
  click(By.name("submit"));
}//обновить группу

public void fillGroupForm(GroupData groupData) {
  type(By.name("group_name"), groupData.getName());
  type(By.name("group_header"), groupData.getHeader());
  type(By.name("group_footer"), groupData.getFooter());
}//заполнить группу

public void initGroupCreation() {
  click(By.name("new"));
}

public void deleteSelectedGroups() {

  click(By.name("delete"));
}

public void selectGroupById(int id) {
  wd.findElement(By.cssSelector("input[value ='" + id + "']")).click();
}

public void initGroupModification() {
  click(By.name("edit"));
} // редактировать группу

public void submitGroupModification() {
  click(By.name("update"));

}

public void create(GroupData group) {
  initGroupCreation();
  fillGroupForm(group);
  submitGroupCreation();
  //зачистка кэша
  groupCash = null;
  returnToGroupPage();
}

public void modify(GroupData group) {
  selectGroupById(group.getId());//выбрать последнюю в списке группу
  initGroupModification();// редактировать группу
  fillGroupForm(group);//заполнить группу
  submitGroupModification();//обновить группу
  //зачистка кэша
  groupCash = null;
  returnToGroupPage();
}

public void delete(GroupData group) {
  selectGroupById(group.getId());
  deleteSelectedGroups();
  //зачистка кэша
  groupCash = null;
  returnToGroupPage();
}

public boolean isThereAGroup() {
  return isElementPresent(By.name("selected[]"));
}

public int count() {
  return wd.findElements(By.name("selected[]")).size();
}


private Groups groupCash = null;

public Groups all() {
  //если кэш не пустой
  if (groupCash != null) {
    //возвращаем его копию
    return new Groups(groupCash);
  }
  //иначе считываем список групп со страницы веб-приложения
  groupCash = new Groups();
  List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
  for (WebElement element : elements) {
    String name = element.getText();
    int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
    groupCash.add(new GroupData().withId(id).withName(name));
  }
  return new Groups(groupCash);
}


}
