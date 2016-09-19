import org.sql2o.*;
import java.util.*;

public class Entry {
  private String address;
  private String phoneNumber;
  private String email;
  private Integer contactId;
  private Integer id;

  @Override
  public boolean equals(Object otherEntry) {
    if (!(otherEntry instanceof Entry)) {
      return false;
    } else {
      Entry newEntry = (Entry) otherEntry;
      return this.getAddress().equals(newEntry.getAddress()) &&
             this.getPhoneNumber().equals(newEntry.getPhoneNumber()) &&
             this.getEmail().equals(newEntry.getEmail()) &&
             this.getId() == newEntry.getId() &&
             this.getContactId() == newEntry.getContactId();
    }
  }

  public Entry(String _address, String _phoneNumber, String _email, int _contactId) {
    this.address = _address;
    this.phoneNumber = _phoneNumber;
    this.email = _email;
    this.contactId = _contactId;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public Integer getContactId() {
    return contactId;
  }

  public Integer getId() {
    return id;
  }

  public static List<Entry> all() {
    String sql = "SELECT id, address, phoneNumber, email, contactId FROM entries";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Entry.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO entries (address, phoneNumber, email, contactId)" +
                   " VALUES (:address, :phoneNumber, :email, :contactId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("address", this.address)
        .addParameter("phoneNumber", this.phoneNumber)
        .addParameter("email", this.email)
        .addParameter("contactId", this.contactId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Entry find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM entries where id=:id";
      Entry entry = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Entry.class);
      return entry;
  }
 }





}
