package ru.zt.addressbook.tests;

import org.testng.annotations.Test;
import ru.zt.addressbook.model.GroupData;
import ru.zt.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

@Test
public void testDbConnextion() {
  Connection conn = null;
  try {
    //вход в БД
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
    //извлечение информации из БД
    Statement st = conn.createStatement();
    //Поместить результат запроса в переменную rs
    ResultSet rs = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
    //создание экземпляра коллекции
    Groups groups = new Groups();
    //пока еще есть записи
    while (rs.next()) {
      //
      groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name"))
              .withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer")));
    }
    rs.close();;
    st.close();
    conn.close();

    System.out.println(groups);
    // Do something with the Connection


  } catch (SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());
    System.out.println("VendorError: " + ex.getErrorCode());
  }
}
}
