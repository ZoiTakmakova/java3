package ru.zt.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.zt.addressbook.model.ContactData;
import ru.zt.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

@Parameter(names = "-c", description = "Contact count")
public int count;

@Parameter(names = "-f", description = "Target file")
public String file;

@Parameter(names = "-d", description = "Data format")
public String format;

//запускаемый файл с функцией, который в качестве параметров принимает массив строк
//в качестве параметров передается колличество контактов и путь к файлу
public static void main(String[] args) throws IOException {
  //создаеем обект текущего класса
  ContactDataGenerator generator = new ContactDataGenerator();
  JCommander jCommander = new JCommander(generator);
  try {
    jCommander.parse(args);
    generator.run();
  } catch (ParameterException ex) {
    jCommander.usage();
    return;
  }
}

private void run() throws IOException {
  //1 часть: генерация данных
  List<ContactData> contacts = generateContacts(count);
  //проверка
  if (format.equals("csv")) {
    //2 часть: запись данных в файл csv
    saveAsCsv(contacts, new File(file));
  } else if (format.equals("xml")) {
    //2 часть: запись данных в файл xml
    saveAsXml(contacts, new File(file));
  } else if (format.equals("json")) {
    //2 часть: запись данных в файл xml
    saveAsJson(contacts, new File(file));
  } else {
    System.out.println("Unrecognized format" + format);
  }
}

private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
  Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
  String json = gson.toJson(contacts);
  try (Writer writer = new FileWriter(file)) {
    writer.write(json);
  }
}

//1й парамектр: список контактов, который нужно сохранять, 2й параметр файл в который нужно сохранять
private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
  //создаем объект типа XStream
  XStream xstream = new XStream();
  //подсказка: использовать анатацию класса
  xstream.processAnnotations(ContactData.class);
  String xml = xstream.toXML(contacts);
  try (Writer writer = new FileWriter(file)) {
    writer.write(xml);
  }
}

private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
  //открытие файла
  try (Writer writer = new FileWriter(file)) {
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s\n", contact.getLastname(), contact.getFirstname()/*, contact.getGroup()*/));
    }
  }
}

private List<ContactData> generateContacts(int count) {
  List<ContactData> contacts = new ArrayList<ContactData>();
  for (int i = 0; i < count; i++) {
    contacts.add(new ContactData().withLastname(String.format("Ivanov %s", i)).withFirstname(String.format("Ivan %s", i))
            .withAddress(String.format("address %s", i)).withEmail_1(String.format("%s@mail.ru", i)).withEmail_2(String.format("%s@mail.ru", i)).withEmail_3(String.format("%s@mail.ru", i)).withHomePhone(String.format("11111%s", i))
            .withMobilePhone(String.format("22222%s", i)).withWorkPhone(String.format("33333%s", i))/*.withGroup((String.format("test %s", i)))*/);
  }
  return contacts;
}


}
