package by.bsu.famcs;

import org.apache.log4j.Logger;

import javax.json.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {
    private List<Message> listMes;
    private static Logger logger = Logger.getLogger(MessageManager.class);
    public MessageManager(){
        listMes = new LinkedList<>();
    }
    public void writeToFile(String fileName){
        PrintStream ps = null;
        try {
            ps = new PrintStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("Can not found file " + fileName + " .");

        }
        ArrayList<JsonObject> arr = new ArrayList<>();
        for (Message item : listMes) {
            JsonObject mesObject = Json.createObjectBuilder()
                    .add("Id", item.getID())
                    .add("author", item.getAutor())
                    .add("timestamp", item.getDate())
                    .add("message", item.getMessage()).build();
            arr.add(mesObject);
        }
        if (ps != null) {
            ps.println(arr);
            ps.close();
        }

    }
    public JsonArray getJsonArray(String fileName){
        BufferedReader br;
        String mesJSONData = null;
        try {
            br = new BufferedReader(new FileReader(new File(fileName)));
            mesJSONData = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("Can not found file " + fileName + " .");

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        JsonReader reader;
        JsonArray mesArray = null;
        if (mesJSONData != null) {
            reader = Json.createReader(new StringReader(mesJSONData));
            mesArray = reader.readArray();
            reader.close();
        }

        return mesArray;
    }
    public void readFromFile(String fileName) {
        JsonArray mesArray = getJsonArray(fileName);
        for (JsonValue jsonVal : mesArray) {
            String mesJSONData = jsonVal.toString();
            JsonReader reader = Json.createReader(new StringReader(mesJSONData));
            JsonObject mesObject = reader.readObject();
            reader.close();
            listMes.add(new Message(mesObject.getString("Id"), mesObject.getString("author"),
                    mesObject.getString("message"),Long.parseLong(mesObject.get("timestamp").toString()) ));
        }
    }
    public void addMessage(Message mes){
        listMes.add(mes);
    }
    public void deleteMesByID(String id){
        Message mes = searchMesByID(id);
        if(mes != null){
            listMes.remove(mes);
        } else {
            System.out.println("Not found");
        }
    }
    public Message searchMesByID(String id){
        for (Message item : listMes) {
            if (item.getID().equals(id)) {
                return item;
            }
        }
        return null;
    }
    public void viewMesHistory(String fileName) {
        readFromFile(fileName);
        Collections.sort(listMes,new ComparatorByTime());
        for (Message item : listMes){
            System.out.println(item.userPrint());
        }
    }
    public List<Message> searchByAuthor(String name){
        List<Message> foundMesList = new ArrayList<>();
        for (Message item : listMes) {
            if(item.getAutor().equals(name)){
                foundMesList.add(item);
            }
        }
        return foundMesList;
    }
    public List<Message> searchByLexeme(String word){
        List<Message> foundMesList = new ArrayList<>();
        for (Message item : listMes) {
            if(item.toString().toLowerCase().contains(word.toLowerCase())){
                foundMesList.add(item);
            }
        }
        return foundMesList;
    }
    public List<Message> searchByRegex(String regex){
        List<Message> foundMesList = new ArrayList<>();
        Pattern pattern  = Pattern.compile(regex);
        Matcher matcher;
        for (Message item : listMes) {
            matcher = pattern.matcher(item.userPrint());
            if(matcher.find()){
                foundMesList.add(item);
            }
        }
        return foundMesList;
    }
    public List<Message> viewHistoryPeriod(Date start, Date end){
        List<Message> foundMesList = new ArrayList<>();
        Long long1 = start.getTime();
        Long long2 = end.getTime();
        for (Message item: listMes) {
            Long curr = item.getDate();
            if((curr >= long1)&&(curr <= long2)){
                foundMesList.add(item);
            }
        }
        return foundMesList;
    }

}
