import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(String[] args) {

    RuntimeTypeAdapterFactory<Command> commandAdapterFactory = RuntimeTypeAdapterFactory
      .of(Command.class)
      .registerSubtype(Status.class)
      .registerSubtype(Cancel.class)
      .registerSubtype(Wizard.class)
      .registerSubtype(GetIP.class);

    Gson gson = new GsonBuilder()
      .registerTypeAdapterFactory(commandAdapterFactory)
      .create();

    // 1
    {
      Command command = new Status("STATUS");
      String json = gson.toJson(command);
      System.out.println(json);

      Command invCommand = gson.fromJson(json, Command.class);
      if (invCommand instanceof Status) {
        System.out.println(((Status) invCommand).statusStr);
      }
    }
    // 1

    // 2
    {
      Command command = new Wizard("WIZARD", 123);
      String json = gson.toJson(command);
      System.out.println(json);

      Command invCommand = gson.fromJson(json, Command.class);
      if (invCommand instanceof Wizard) {
        System.out.println(((Wizard) invCommand).wizardStr + " " + (((Wizard) invCommand).wizardInt));
      }
    }
    // 2

    // 3
    {
      Command command = new GetIP();
      String json = gson.toJson(command);
      System.out.println(json);

      Command invCommand = gson.fromJson("{'type':'GetIP', 'xxx': 'yyy'}", Command.class);
      if (invCommand instanceof GetIP) {
        System.out.println(((GetIP) invCommand).toString());
      }
    }
    // 3
  }
}
