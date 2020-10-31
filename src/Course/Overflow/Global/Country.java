/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global;

import Course.Overflow.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Country {
    Integer id;
    String name;
    String adminId;
    
    public Country(Integer id){
        this.id = id;
        if(DB.valueExist("COUNTRY", "ID", id.toString())){
            ResultSet rs = DB.executeQuery("SELECT * FROM COUNTRY WHERE ID = #", id.toString());
            try {
                rs.next();
                name = rs.getString("NAME");
                adminId = rs.getString("ADMIN_ID");
            } catch (SQLException ex) {
                Logger.getLogger(Country.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Country(Integer id, String name, String adminId){
        this.id = id;
        this.name  = name;
        this.adminId = adminId;
    }
    
    public Country(String name){
        this.name = name;
        ResultSet rs = DB.executeQuery("SELECT * FROM COUNTRY WHERE NAME = '#'", name);
        try {
            rs.next();
            id = rs.getInt("ID");
            adminId = rs.getString("ADMIN_ID");
        } catch (SQLException ex) {
            Logger.getLogger(Country.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Country> getList(){
        ArrayList<Country> list = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT * FROM COUNTRY");
        try {
            while(rs.next()){
                list.add(new Country(rs.getInt("ID"), rs.getString("NAME"), rs.getString("ADMIN_ID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Country.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return name;
    }

    public String getAdminId() {
        return adminId;
    }
}
