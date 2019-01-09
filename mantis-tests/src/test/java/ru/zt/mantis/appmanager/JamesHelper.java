package ru.zt.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;

import javax.mail.Session;
import javax.mail.Store;
import java.io.PrintStream;

public class JamesHelper {

    private ApplicationManager app;

    private TelnetClient telnet;
    private PrintStream out;

    private Session mailSession;
    private Store store;
    private String mailserver;

    public JamesHelper(ApplicationManager app) {
        this.app=app;
        telnet=new TelnetClient();
        mailSession=Session.getDefaultInstance(System.getProperties());
    }

    public boolean doesUserExist(String name){
        initTelnetSession();
        write("verify "+name);
        String result = readUntil("exist");
        closeTelnetSession();
        return  result.trim().equals("User "+name+"exist");
    }

    public void createUser(String name, String password) {
        initTelnetSession();
        write("adduser " + name);
        String result = readUntile("User " + name + "added");
        closeTelnetSession();
    }

    public void deleteUser(String name, String password) {
        initTelnetSession();
        write("deluser " + name);
        String result = readUntile("User " + name + "deleted");
        closeTelnetSession();
    }

    private  void  initTelnetSession(){
        mailserver = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminlogin");
        String password = app.getProperty("mailserver.adminpassword");

        try {
            telnet.connect(mailserver, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
        }catch (Exception e){
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write("");

        readUntil("Login id:");
        write("login");
        readUntil("Password:");
        write("password");

        readUntil("Welcome+login+. HELP for a list for commands");
    }
    


    }
