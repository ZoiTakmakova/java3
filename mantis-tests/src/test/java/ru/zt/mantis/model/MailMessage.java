package ru.zt.mantis.model;

public class MailMessage {

  /*Кому пришло письмо*/
  public  String to;
  /*Текст письма*/
  public String text;

  public MailMessage(String to, String text){
    this.to=to;
    this.text=text;
  }


}
