/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Files;

import Course.Overflow.DB;
import Course.Overflow.Global.ToolKit;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Files {    
    
    Integer id;
    FileType type;
    String title;
    String content;
    Date uploadTime;
    Date lastUpdateTime;
    
    public Files(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM FILES WHERE ID = #", id.toString());
        try {
            if(!rs.next()) {rs.close(); return;}
            type = FileType.valueOf(rs.getInt("TYPE"));
            title = rs.getString("TITLE");
            content = rs.getString("CONTENT");
            uploadTime = rs.getDate("UPLOAD_TIME");
            lastUpdateTime = rs.getDate("LAST_UPDATE");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Files(File file, FileType type){
        this(file, type, "No Title");
    }
    
    public Files(File file, FileType type, String title){
        this(type, title, ToolKit.copyFile(file, type, DB.generateId("FILES")));
    }
    
    public Files(FileType type, String title, String content){
        this.id = DB.generateId("FILES");
        this.type = type;
        this.title = title;
        this.content = content;
        this.uploadTime = new Date();
        this.lastUpdateTime = uploadTime;
        DB.execute(
              "INSERT INTO FILES VALUES(#, #, '#', '#', #, #)", 
              id.toString(),
              type.getId().toString(),
              title,
              content,
              ToolKit.JDateToDDate(uploadTime),
              ToolKit.JDateToDDate(lastUpdateTime)
        );
    }

    public Integer getId() {
        return id;
    }

    public FileType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setType(FileType type) {
        if(this.type.equals(type)) return;
        this.type = type;
        DB.execute("UPDATE FILES SET TYPE = # WHERE ID = #", type.getId().toString(), id.toString());
    }

    public void setTitle(String title) {
        if(this.title.equals(title)) return;
        this.title = title;
        DB.execute("UPDATE FILES SET TITLE = '#' WHERE ID = #", title, id.toString());
        updateTime();
    }

    public void setContent(String content) {
        if(this.content.equals(content)) return;
        this.content = content;
        DB.execute("UPDATE FILES SET CONTENT = '#' WHERE ID = #", content, id.toString());
        updateTime();
    }
    
    public void setFile(File file){
        if(file.getAbsolutePath().equals(new File(ToolKit.makeAbsoluteLocation(content)).getAbsolutePath())) return;
        deleteFile();
        setContent(ToolKit.copyFile(file, type, id));
    }
    
    private void updateTime(){
        DB.execute("UPDATE FILES SET LAST_UPDATE = # WHERE ID = #", ToolKit.getCurTimeDB(), id.toString());
    }
    
    public void delete(){
        DB.execute("DELETE FILES WHERE ID = #", id.toString());
        deleteFile();
    }
    
    public void deleteFile(){
        if(content.equals("")) return;
        switch(type){
            case PICTURE  :
            case PDF      :
            case VIDEO    :
                File file = new File(ToolKit.makeAbsoluteLocation(content));
                try {
                    java.nio.file.Files.delete(new File(ToolKit.makeAbsoluteLocation(content)).toPath());
                } catch (IOException ex) {
                    Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
                }
            //System.out.println("File is deleted... " + file.delete());
        }
    }
}
