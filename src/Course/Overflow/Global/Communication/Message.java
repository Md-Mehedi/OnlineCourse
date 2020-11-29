/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global.Communication;

import Course.Overflow.DB;
import Course.Overflow.Files.Files;
import Course.Overflow.Global.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Message {
    Integer id;
    String senderId;
    String receiverId;
    Files message;
    
    public Message(Integer id){
        this.id = id;
        try {
            ResultSet rs = DB.executeQuery("SELECT * FROM MESSAGE WHERE ID = #", id.toString());
            if(rs.next()){
                this.senderId = rs.getString("SENDER_ID");
                this.receiverId = rs.getString("RECEIVER_ID");
                this.message = new Files(rs.getInt("FILE_ID"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Message(Person sender, Person receiver, Files message){
        this.id = DB.generateId("MESSAGE");
        this.senderId = sender.getUsername();
        this.receiverId = receiver.getUsername();
        this.message = message;
        DB.execute("INSERT INTO MESSAGE(ID, SENDER_ID, RECEIVER_ID, FILE_ID) VALUES(#, '#', '#', #)", id.toString(), senderId, receiverId, message.getId().toString());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Files getMessage() {
        return message;
    }

    public void setMessage(Files message) {
        this.message = message;
    }    
    
    public static ArrayList<Message> getMessageList(Person sender, Person receiver){
        ArrayList<Message> list = new ArrayList();
        try {
            ResultSet rs = DB.executeQuery("SELECT ID FROM MESSAGE WHERE (SENDER_ID = '#' AND RECEIVER_ID = '#') OR (SENDER_ID = '#' AND RECEIVER_ID = '#') ORDER BY (SELECT UPLOAD_TIME FROM FILES WHERE ID = FILE_ID) ASC", sender.getUsername(), receiver.getUsername(), receiver.getUsername(), sender.getUsername());
            while(rs.next()){
                list.add(new Message(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static ArrayList<Person> getMessageReceipentList(Person person){
        ArrayList<Person> list = new ArrayList();
        try {
            ResultSet rs = DB.executeQuery(
                    "SELECT SENDER_ID, RECEIVER_ID\n" +
                    "FROM MESSAGE M, FILES F\n" +
                    "WHERE M.FILE_ID = F.ID\n" +
                    "AND (RECEIVER_ID = '#' OR SENDER_ID = '#')\n" +
                    "GROUP BY SENDER_ID, RECEIVER_ID\n" +
                    "ORDER BY MAX(UPLOAD_TIME) DESC", 
                  person.getUsername(), person.getUsername());
            ArrayList<String> persons = new ArrayList();
            while(rs.next()){
                if(rs.getString("SENDER_ID").equals(person.getUsername()) && !persons.contains(rs.getString("RECEIVER_ID"))){
                    list.add(new Person(rs.getString("RECEIVER_ID")));
                    persons.add(rs.getString("RECEIVER_ID"));
                }
                else if(rs.getString("RECEIVER_ID").equals(person.getUsername()) && !persons.contains(rs.getString("SENDER_ID"))){
                    list.add(new Person(rs.getString("SENDER_ID")));
                    persons.add(rs.getString("SENDER_ID"));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static Message getLastMessage(Person person1, Person person2){
        Message m = null;
        try {
            ResultSet rs = DB.executeQuery("SELECT ID FROM MESSAGE WHERE (SENDER_ID = '#' AND RECEIVER_ID = '#') OR (SENDER_ID = '#' AND RECEIVER_ID = '#') ORDER BY (SELECT UPLOAD_TIME FROM FILES WHERE ID = FILE_ID) DESC", person1.getUsername(), person2.getUsername(), person2.getUsername(), person1.getUsername());
            if(rs.next()){
                m = new Message(rs.getInt("ID"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
}
