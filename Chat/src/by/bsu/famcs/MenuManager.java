package by.bsu.famcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MenuManager {
    private BufferedReader br;
    private  MessageManager manager;
    private static Logger logger = Logger.getLogger(MenuManager.class);
    public MenuManager(){
        InputStreamReader isr = new InputStreamReader(System.in);
        this.br = new BufferedReader(isr);
        this.manager = new MessageManager();
        PropertyConfigurator.configure("log4j.properties");

    }
    public int getCount() {
        System.out.println("Select an action, please:\n" + "1. load messages from file\n" + "2. load messages to file\n" + "3. add message\n" +
                "4. view the history of messages in chronological order\n" + "5. delete the message by ID\n"
                + "6. search by author\n" + "7. search by word\n" + "8. search by regex\n" + "9. view the message history by the time\n" + "0.exit");
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Invalid input : " + line);
        }
        return Integer.parseInt(line);
    }
    public void loadData() {
        System.out.println("Specify the file name: ");
        String fileName;
        try {
            fileName = br.readLine();
            manager.readFromFile(fileName);
            logger.info("Load data from file " + fileName + " .");

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Invalid input : " + e.toString());
        }

    }
    public void saveChanges()  {
        System.out.println("Specify the file name: ");
        String fileName;
        try {
            fileName = br.readLine();
            manager.writeToFile(fileName);
            logger.info("Load data to file " + fileName + " .");
        } catch (IOException e) {
            logger.error("Invalid input : " + e.getMessage()) ;
            e.printStackTrace();
        }

    }
    public void addMessage() {
        System.out.println("Specify the author name: ");
        String name;
        try {
            logger.info("Add message.");
            name = br.readLine();
            System.out.println("Specify the message text: ");
            String text = br.readLine();
            if(text.length() >= 140){
                logger.warn("Big size message " + text.length());
            }
            Message mes = new Message(name, text);
            manager.addMessage(mes);
        } catch (IOException e) {
            logger.error("Invalid input.");
            e.printStackTrace();
        }

    }
    public void viewHistory() {
        System.out.println("Specify the file name: ");
        String fileName;
        try {
            fileName = br.readLine();
            logger.info("View history of messages from file " + fileName + " .");
            manager.viewMesHistory(fileName);
        } catch (IOException e) {
            logger.error("Invalid input : " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void delMessage()  {
        System.out.println("Specify the id of message: ");
        try {
            String id = br.readLine();
            logger.info("Delete message by id : " + id);
            manager.deleteMesByID(id);
        } catch (IOException e) {
            logger.error("Invalid input.");
            e.printStackTrace();
        }
    }
    public void searchByAuthor() {
        System.out.println("Specify the author name: ");
        String name;
        try {
            name = br.readLine();
            logger.info("Search messages by author : " + name);
            List<Message> foundList = manager.searchByAuthor(name);
            for (Message item : foundList){
                System.out.println(item.userPrint());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Invalid input.");
        }

    }
    public void searchByLexeme() {
        System.out.println("Specify the word: ");
        String word = null;
        try {
            word = br.readLine();
            logger.info("Search messages by word : " + word);
            System.out.println("Search ... ");
            List<Message> foundList = manager.searchByLexeme(word);
            for (Message item : foundList){
                System.out.println(item.userPrint());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Invalid input : " + word);
        }

    }
    public void searchByRegex() {
        System.out.println("Specify the regex: ");
        String regex = null;
        try {
            regex = br.readLine();
            logger.info("Search messages by regex : " + regex);
            System.out.println("Search ... ");
            List<Message> foundList = manager.searchByRegex(regex);
            for (Message item : foundList){
                System.out.println(item.userPrint());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Invalid input : "  + regex);
        }

    }
    private boolean isDateCorrect(String date){
        Pattern pattern  = Pattern.compile("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]");
        Matcher matcher = pattern.matcher(date);
        if(matcher.find()){
            return true;
        } else{
            logger.warn("Incorrect date "+ date + " .");

        }
        return false;
    }
    public void viewHistoryPeriod()  {
        System.out.println("Enter the period of time: (MM.DD.YYYY)");
        System.out.println("EXAMPLE: 02/17/16 - 04/19/2016");
        System.out.println("Enter start: ");
        String start = null;
        String end = null;
        try {
            start = br.readLine();
            System.out.println("Enter end: ");
            end = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Invalid input : " + start + " - " + end + " .");
        }
        if(isDateCorrect(start) && isDateCorrect(end)){
            Date d1 = null;
            Date d2 = null;
            if (start != null && end != null) {
                d1 = new Date(start);
                d2 = new Date(end);
            }
            if (d1 != null && d2 != null) {
                logger.info("View history message by period " + d1.toString() + " - " + d2.toString() + " .");

                List<Message> foundList = manager.viewHistoryPeriod(d1, d2);
                for (Message item : foundList) {
                    System.out.println(item.userPrint());
                }
            }
        } else {
            System.out.println("Incorrect format");
        }

    }
}
