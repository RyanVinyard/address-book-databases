import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("contacts/:id/entries/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Contact contact = Contact.find(Integer.parseInt(request.params(":id")));
      model.put("contact", contact);
      model.put("template", "templates/contact-entries-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/entries/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Entry entry = Entry.find(Integer.parseInt(request.params(":id")));
      model.put("entry", entry);
      model.put("template", "templates/entry.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/entries", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("entries", Entry.all());
      model.put("template", "templates/entries.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/contacts", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String dateOfBirth = request.queryParams("dateOfBirth");
      Contact newContact = new Contact(name, dateOfBirth);
      newContact.save();
      model.put("template", "templates/contact-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/contacts/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/contact-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/entries", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Contact contact = Contact.find(Integer.parseInt(request.queryParams("contactId")));
      String address = request.queryParams("address");
      String phoneNumber = request.queryParams("phoneNumber");
      String email = request.queryParams("email");
      Entry newEntry = new Entry(address, phoneNumber, email, contact.getId());
      newEntry.save();
      model.put("contact", contact);
      model.put("template", "templates/contact-entry-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/contacts", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("contacts", Contact.all());
      model.put("template", "templates/contacts.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/contacts/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Contact contact = Contact.find(Integer.parseInt(request.params(":id")));
      model.put("contact", contact);
      model.put("template", "templates/contact.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
