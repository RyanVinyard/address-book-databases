import org.sql2o.*;
import java.util.*;

public class Contact {
  private String name;
  private String dateOfBirth;
  private int id;

  @Override
  public boolean equals(Object otherContact) {
    if (!(otherContact instanceof Contact)) {
      return false;
    } else {
      Contact newContact = (Contact) otherContact;
      return this.getName().equals(newContact.getName()) &&
             this.getDateOfBirth().equals(newContact.getDateOfBirth()) &&
             this.getId() == newContact.getId();
    }
  }

  public Contact(String _name, String _dateOfBirth) {
    this.name = _name;
    this.dateOfBirth = _dateOfBirth;
  }

  public String getName() {
    return name;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public Integer getId() {
    return id;
  }

  public static List<Contact> all() {
    String sql = "SELECT id, name, dateOfBirth FROM contacts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Contact.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO contacts (name, dateOfBirth)" +
                   " VALUES (:name, :dateOfBirth)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("dateOfBirth", this.dateOfBirth)
        .executeUpdate()
        .getKey();
    }
  }

  public List<Entry> getEntries() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM entries where contactId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Entry.class);
    }
  }

  public static Contact find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM contacts where id=:id";
      Contact contact = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Contact.class);
      return contact;
    }
  }


}
