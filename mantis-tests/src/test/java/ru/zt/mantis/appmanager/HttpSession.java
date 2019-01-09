package ru.zt.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
  private CloseableHttpClient httpclient;
  private ApplicationManager app;

public HttpSession(ApplicationManager app) {
  this.app = app;
  //новая сессия-объект который будет отправлять запросы на сервер
  httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
}

//логин
public boolean login(String username, String password) throws IOException {
  //создание тела(пустого) будующего запроса
  HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
  //формирование набора параметров
  List<NameValuePair> params = new ArrayList<>();
  params.add(new BasicNameValuePair("username", username));
  params.add(new BasicNameValuePair("password", password));
  params.add(new BasicNameValuePair("secure_session", "on"));
  params.add(new BasicNameValuePair("return", "index.php"));
  //упаковка параметров в зарание созданный запрос post
  post.setEntity(new UrlEncodedFormEntity(params));
  //отправка запроса. результатом является ответ от сервера(response)
  CloseableHttpResponse response = httpclient.execute(post);
  //получение текса ответа сервера
  String body = getTextFrom(response);
  //провера: успешно ли вошел пользователь по имени пользователя
 return body.contains(String.format("<span class=\"italic\">%s</span>", username));
}

//вспомогательная функция для получения текста ответа сервера
private String getTextFrom(CloseableHttpResponse response) throws IOException {
  try {
    return EntityUtils.toString(response.getEntity());
  } finally {
    response.close();

  }
}
//метод определяет какой пользователь сейчас залогинен
public boolean isLoggedInAs(String username) throws IOException {
  HttpGet get = new HttpGet(app.getProperty("web.baseUrl")+"/index.php");
  CloseableHttpResponse response = httpclient.execute(get);
  String body = getTextFrom(response);
  return body.contains(String.format("<span class=\"italic\">%s</span>",username));

}
}