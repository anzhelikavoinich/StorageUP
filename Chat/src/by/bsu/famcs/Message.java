package by.bsu.famcs;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Message {
    private String id;
    private String author;
    private String message;
    private long timestamp;
    public Message(String id, String author, String message, long timestamp){
        this.setID(id);
        this.setAutor(author);
        this.setMessage(message);
        this.setDate(timestamp);
    }
    public Message(String author, String message){
        this.author = author;
        this.id = UUID.randomUUID().toString();
        Date date  = new Date();
        this.timestamp = date.getTime();

        //System.out.println(DateFormat.getTimeInstance().format(timestamp));
        //System.out.println(DateFormat.getInstance().format(timestamp));
        this.message = message;
    }

    public String getAutor(){
        return this.author;
    }
    public String getID(){
        return this.id;
    }
    public String getMessage(){
        return this.message;
    }
    public long getDate(){
        return this.timestamp;
    }
    public void setAutor(String author){
        this.author = author;
    }
    public void setID(String id){
        this.id = id;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setDate(long timestamp){
        this.timestamp = timestamp;
    }
    public String userPrint(){
        return "Author : " + this.author + "\n" + "Message : " +
                         this.message + "\n" + new SimpleDateFormat().format(new Date(timestamp));
    }
    @Override
    public String toString(){
        return ("Id : " + this.id + "\n" + "Author : " + this.author + "\n" + "Time : "
                + timestamp + "\n" + "Message : " + this.message);
    }
    /*@Override
    public Object clone(){
        return new Message(this.id, this.author, this.message, this.timestamp);
    }
    @Override
    public int hashCode(){
        int prime = 3;
        int rez = 1;
        rez = rez * prime + this.id.hashCode();
        rez = rez * prime + this.author.hashCode();
        rez = rez * prime + this.message.hashCode();
        rez = rez * prime + (int)this.timestamp;
        return rez;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(this.getClass() == obj.getClass()){
            Message tmp = (Message)obj;
            if(this.id.equals(tmp.id) && this.author.equals(tmp.author)){
                if(this.timestamp == tmp.timestamp && this.message.equals(tmp.message)){
                    return true;
                }
                return false;
            }
            return false;
        }
        return  false;
    }*/
}
