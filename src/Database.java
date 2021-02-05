import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Database {
    static String fileName = "db.txt";

    public static User register(User user) throws IOException {

        JsonArray jsonTree = parseFromFile().getAsJsonArray();
        System.out.println(jsonTree.toString());
        JsonObject j = new JsonObject();
        j.addProperty("firstName", user.getFirstName());
        j.addProperty("lastName", user.getLastName());
        j.addProperty("userName", user.getUserName());
        j.addProperty("password", user.getPassword());

        jsonTree.add(j);

        saveToFile(jsonTree);
return user;

    }

    public static User login(String username, String password) throws IOException {
        JsonElement jsonTree = parseFromFile();
        if(jsonTree.isJsonArray()){
            for(JsonElement obj: jsonTree.getAsJsonArray()){
                var _user = obj.getAsJsonObject();
               User user = new User(_user.get("firstName").getAsString(),_user.get("lastName").getAsString(),_user.get("userName").getAsString(),_user.get("password").getAsString());
               if(user.getPassword().equals(password) && user.getUserName().equals(username)){

                   return user;
               }

            }

        }

        return null;
    }

    private static JsonElement parseFromFile() throws IOException {
        JsonParser parser = new JsonParser();
        try{


            String data =   new String(Files.readAllBytes(Paths.get(fileName)));


            JsonElement jsonTree = parser.parse(data);

            System.out.println(jsonTree.toString());
            return jsonTree;

        }catch(Exception e){
            System.out.println(e);

            JsonElement jsonTree = parser.parse("[]");
            saveToFile(jsonTree);


            return jsonTree;

        }



    } private static void saveToFile (JsonElement jsonElement) throws IOException {


            String jsonString = jsonElement.toString();


            FileWriter myWriter = new FileWriter(fileName);

            myWriter.write(jsonString);
            myWriter.close();



    }

    public static void main(String[] args ) throws IOException {


    //   register(new User("ayo","kunle","ayo","pass"));
    //  System.out.println(login("ayo","pass"));
    }
}
