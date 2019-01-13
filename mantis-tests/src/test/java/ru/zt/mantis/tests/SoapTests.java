package ru.zt.mantis.tests;

import org.testng.annotations.Test;
import ru.zt.mantis.model.Issue;
import ru.zt.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class SoapTests extends TestBase{

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        //получить список проектов
        Set<Project> projects = app.soap().getProjects();
        //вывести на консоль кол-во проектов
        System.out.println(projects.size());
        //вывести на консоль названия проектов
        for(Project project: projects){
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException{
        //получить список проектов
        Set<Project> projects = app.soap().getProjects();
        //
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue decription").withProject(projects.iterator().next());
        //создать проект
        Issue created = app.soap().addIssue(issue);
        //сравнить
        assertEquals(issue.getSummary(),created.getSummary());
    }
}
