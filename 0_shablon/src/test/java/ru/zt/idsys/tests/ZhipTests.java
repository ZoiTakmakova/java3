package ru.zt.idsys.tests;

import org.testng.annotations.Test;

public class ZhipTests extends TestBase{

    @Test
    public void testZhip() {
        app.getZhipHelper().goToSectionIP();//здесь нужно подождать время необходимое на подключение к стенду
        app.getZhipHelper().goToSubsectionIP();




    }

}