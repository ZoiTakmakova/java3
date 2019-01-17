package ru.zt.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {
    @Test
    //получить из Github информацию о комитах
    public void testCommits() throws IOException {
        //установка соединения с Github через удаленный программныйц интерфейс
        Github github = new RtGithub("3c1a4604f7e3e4b51b30f4c5b3ac1e9ce4f9358f");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("ZoiTakmakova", "java3")).commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
