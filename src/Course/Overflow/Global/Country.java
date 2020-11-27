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

    public Country(Integer id) {
        this.id = id;
        if (DB.valueExist("COUNTRY", "ID", id.toString())) {
            ResultSet rs = DB.executeQuery("SELECT * FROM COUNTRY WHERE ID = #", id.toString());
            try {
                rs.next();
                name = rs.getString("NAME");
                adminId = rs.getString("ADMIN_ID");
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Country.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Country(Integer id, String name, String adminId) {
        this.id = id;
        this.name = name;
        this.adminId = adminId;
    }

    public Country(String name) {
        this.name = name;
        ResultSet rs = DB.executeQuery("SELECT * FROM COUNTRY WHERE NAME = '#'", name);
        try {
            if (!rs.next()) {
                rs.close();
                return;
            };
            id = rs.getInt("ID");
            adminId = rs.getString("ADMIN_ID");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Country.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Country> getList() {
        ArrayList<Country> list = new ArrayList<>();
        ResultSet rs = DB.executeQuery("SELECT * FROM COUNTRY");
        try {
            while (rs.next()) {
                list.add(new Country(rs.getInt("ID"), rs.getString("NAME"), rs.getString("ADMIN_ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Country.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdminId() {
        return adminId;
    }

    public static void createNewCountry(String text) {
        String sql = "INSERT INTO COUNTRY (ID,NAME,ADMIN_ID) VALUES(#,'#','#')";
        boolean x = DB.execute(sql, DB.generateId("COUNTRY").toString(), text, "shammya");
    }

    public static void changeCountryName(String oldName, String newName) {
        ResultSet rs = DB.executeQuery("SELECT ID FROM COUNTRY WHERE NAME = '" + oldName + "'");
        try {
            rs.next();
            if (oldName != newName) {
                String sql = "UPDATE COUNTRY SET NAME = '#' WHERE ID = #";
                boolean x = DB.execute(sql, newName, rs.getString("ID"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteCountry(String selected) {
        ResultSet rs = DB.executeQuery("SELECT ID FROM COUNTRY WHERE NAME = '#'", selected);
        try {
            rs.next();
            String id = rs.getString("ID");
            DB.execute("UPDATE PERSON  SET COUNTRY_ID = NULL WHERE COUNTRY_ID = # ", id);
            DB.execute("DELETE FROM COUNTRY WHERE NAME = '#' ", selected);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
