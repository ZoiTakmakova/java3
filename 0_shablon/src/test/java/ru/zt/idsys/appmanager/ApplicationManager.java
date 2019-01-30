package ru.zt.idsys.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

WebDriver wd;


private SessionHelper sessionHelper;
private NavigationHelper navigationHelper;

private String browser;

public ApplicationManager(String browser){

  this.browser = browser;
}

public void init() {
 /* String browser = BrowserType.FIREFOX;*/
   if (browser.equals(BrowserType.CHROME)){
    wd = new ChromeDriver();
  }else  if (browser.equals(BrowserType.IE)) {
    wd = new InternetExplorerDriver();
  }

 wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
 wd.get("http://192.168.1.212:8080/bank_client/#REESTR_IP");
  navigationHelper = new NavigationHelper(wd);
  sessionHelper = new SessionHelper(wd);
  sessionHelper.login("sysdba", "masterkey");
}

public void stop() {
  wd.quit();
}


public NavigationHelper getNavigationHelper() {
  return navigationHelper;
}


}
