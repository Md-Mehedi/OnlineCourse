/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Files;

import Course.Overflow.DB;
import Course.Overflow.Global.ToolKit;
import java.io.File;
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
            if(!rs.next()) return;
            type = new FileType(rs.getInt("TYPE"));
            title = rs.getString("TITLE");
            content = rs.getString("CONTENT");
            uploadTime = rs.getDate("UPLOAD_TIME");
            lastUpdateTime = rs.getDate("LAST_UPDATE");
        } catch (SQLException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Files(File file, FileType type){
        this(file, type, "No Title");
    }
    
    public Files(File file, FileType type, String title){
        this(type, title, ToolKit.copyFile(file, type));
    }
    
    public Files(FileType type, String title, String content){
        System.out.println(type.getId());
        this.id = DB.generateId("FILES");
        this.type = type;
        this.title = title;
        this.content = content;
        this.uploadTime = new Date();
        this.lastUpdateTime = uploadTime;System.out.println("check");
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
    
}
