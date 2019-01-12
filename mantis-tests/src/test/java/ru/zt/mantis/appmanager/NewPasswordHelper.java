package ru.zt.mantis.appmanager;

import org.openqa.selenium.By;

public class NewPasswordHelper extends HelperBase  {

    public NewPasswordHelper(ApplicationManager applicationManager) {
        super(applicationManager);
    }

    //переходит в раздел Manage Users
    public void pageManageUsers() { click(By.cssSelector("a[href ='/mantisbt-1.2.19/manage_user_page.php")); }
    //выбрать пользователя
    public  void selectUser(int id) {
        wd.findElement(By.cssSelector("a[href ='manage_user_edit_page.php?user_id="+id+"']")).click();    }
    //нажать кнопку Reset Password
    public  void submitResetPassword() {
        wd.findElement(By.cssSelector("input[value ='Reset Password']")).click();    }

    /*проходим по ссылке*/
    public  void resetPassword(String cofirmationLink, String newPassword) {
        wd.get(cofirmationLink);
        //ввести новый пароль
        //<input type="password" size="32" maxlength="1024" name="password">
        type(By.name("password"), newPassword);
        //подтвердить новый пароль
        //<input type="password" size="32" maxlength="1024" name="password_confirm">
        type(By.name("password_confirm"), newPassword);
        //нажать Update User
        //<input type="submit" class="button" value="Update User">
        click(By.cssSelector("input[value='Update User']"));


    }






}
