package ru.zt.rest;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;


public class RestTests extends TestBase{

    @Test
    //создание нового баг-репорта в баг-трекере Bugify
    public void testCreateIssue() throws IOException {
        skipIfNotFixed(665);
    //получить старый список баг-репортов
        Set<Issue> oldIssues=getIssues();
    //создать новый баг-репорт
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
        int issueId = createIssue(newIssue);//метод создания нового баг-репорта, возвращает ид нового б-р.та
    //получить старый список баг-репортов
        Set<Issue> newIssues=getIssues();
        oldIssues.add(newIssue.withId(issueId));
    //сравнить новый со старым списком
        assertEquals(oldIssues,newIssues);

    }

        public Set<Issue> getIssues() throws IOException {
        //авторизоваться и получить список всех б-р.тов в формате
            String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues.json?page=1&limit=50"))
                    .returnContent().asString();
            //анализируем строчку
            JsonElement parsed = new JsonParser().parse(json);
            JsonElement issues = parsed.getAsJsonObject().get("issues");
            return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }
    public Executor getExecutor(){
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490","");//имя пользователя,пароль пусто
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("http://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        //анализируем строчку
        JsonElement parsed = new JsonParser().parse(json);
        //возвращаем ид созданного б-р.та
        return parsed.getAsJsonObject().get("issue_id").getAsInt();

    }

}
