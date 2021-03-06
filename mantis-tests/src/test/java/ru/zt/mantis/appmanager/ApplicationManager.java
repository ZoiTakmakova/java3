package ru.zt.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {


private final Properties properties;
private WebDriver wd;
private String browser;
private RegistrationHelper registrationHelper;
private FtpHelper ftp;
private MailHelper mailHelper;
private NewPasswordHelper newPasswordHelper;
private DbHelper dbHelper;
  private SoapHelper soapHelper;


  public ApplicationManager(String browser) {
  this.browser = browser;
  properties = new Properties();
}

public void init() throws IOException {
  String target = System.getProperty("target", "local");
  properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  dbHelper = new DbHelper();
}

public void stop() {
  if (wd != null) {
    wd.quit();
  }
}
/*метод инициализации HTTP клиента при каждом обращении*/
public HttpSession newSession() {

  return new HttpSession(this);
}


public String getProperty(String key) {
  return properties.getProperty(key);
}

public NewPasswordHelper changePassword() {
  if (newPasswordHelper == null) {
    newPasswordHelper = new NewPasswordHelper(this);
  }
  return newPasswordHelper;

}

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

public FtpHelper ftp() {
  if (ftp == null) {
    ftp = new FtpHelper(this);
  }
  return ftp;
}

public WebDriver getDriver() {
  if (wd == null) {
    if (browser.equals(BrowserType.FIREFOX)) {
      wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true).setBinary("C://Program Files/Mozilla Firefox/firefox.exe"));
    } else if (browser.equals(BrowserType.CHROME)) {
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE)) {
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
  }
  return wd;
}

public MailHelper mail(){
  if (mailHelper == null){
    mailHelper=new MailHelper(this);
  }
  return mailHelper;
}
  public DbHelper db() {
    return dbHelper;
  }

  public SoapHelper soap(){
  if (soapHelper==null){
    soapHelper = new SoapHelper(this);
    wd.get(properties.getProperty("web.baseUrl"));
  }
  return soapHelper;
  }
}
