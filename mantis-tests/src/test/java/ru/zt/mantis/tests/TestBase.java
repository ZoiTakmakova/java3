package ru.zt.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.zt.mantis.appmanager.ApplicationManager;
import ru.zt.mantis.model.UserData;
import ru.zt.mantis.model.Users;


import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

//Logger logger = LoggerFactory.getLogger(TestBase.class);

protected static final ApplicationManager app
        = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

@BeforeSuite/*один запуск*/
public void setUp() throws Exception {
  app.init();
  app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.back");
}

@AfterSuite(alwaysRun = true)/*один запуск*/
public void tearDown() throws IOException {
  app.ftp().restore("config_inc.php.back", "config_inc.php");
  app.stop();
}

}
