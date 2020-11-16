/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import Course.Overflow.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Category {
    Integer id;
    String name;
    Image image;
    Category parent;
    String adminId;

    public Category(Integer id, String name, Image image, Category parent) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.parent = parent;
    }
    
    public Category(Integer id){
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM CATEGORY WHERE ID = #", id.toString());
        try {
            if(rs.next()){
                this.name = rs.getString("NAME");
                if(rs.getInt("PARENT_ID") != -1) this.parent = new Category(rs.getInt("PARENT_ID"));
                else this.parent = null;
                this.adminId = rs.getString("ADMIN_ID");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Category(String name){
        this.name = name;
        ResultSet rs = DB.executeQuery("SELECT * FROM CATEGORY WHERE NAME = '#'", name);
        try {
            if(rs.next()){
                this.id = rs.getInt("ID");
                if(rs.getInt("PARENT_ID") != 0) this.parent = new Category(rs.getInt("PARENT_ID"));
                else parent = null;
                this.adminId = rs.getString("ADMIN_ID");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Category getParent() {
        return parent;
    }
    
    public static ArrayList<Category> getMainCategories(){
        ArrayList<Category> list = new ArrayList<Category>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID IS NULL");
        try {
            while(rs.next()){
                list.add(new Category(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static ArrayList<Category> getSubCategories(Category parent){
        ArrayList<Category> list = new ArrayList<Category>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID = #", parent.getId().toString());
        try {
            while(rs.next()){
                list.add(new Category(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
