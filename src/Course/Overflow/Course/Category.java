package Course.Overflow.Course;

import Course.Overflow.DB;
import Course.Overflow.Global.GLOBAL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.util.Pair;

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

    public Category(Integer id) {
        this.id = id;
        ResultSet rs = DB.executeQuery("SELECT * FROM CATEGORY WHERE ID = #", id.toString());
        try {
            if (rs.next()) {
                this.name = rs.getString("NAME");
                if (rs.getInt("PARENT_ID") != -1) {
                    this.parent = new Category(rs.getInt("PARENT_ID"));
                } else {
                    this.parent = null;
                }
                this.adminId = rs.getString("ADMIN_ID");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Category(String name) {
        this.name = name;
        ResultSet rs = DB.executeQuery("SELECT * FROM CATEGORY WHERE NAME = '#'", name);
        try {
            if (rs.next()) {
                this.id = rs.getInt("ID");
                if (rs.getInt("PARENT_ID") != 0) {
                    this.parent = new Category(rs.getInt("PARENT_ID"));
                } else {
                    parent = null;
                }
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

    public static ArrayList<Pair<Category, ArrayList<Category>>> getAllCategories() {
        ArrayList<Pair<Category, ArrayList<Category>>> lists = new ArrayList<>();
        ResultSet rsMainCat = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID IS NULL");
        try {
            while (rsMainCat.next()) {
                Category mainCat = new Category(rsMainCat.getInt("ID"));
                ArrayList<Category> subCatList = new ArrayList();
                ResultSet rsSubCat = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID = #", mainCat.getId().toString());
                while(rsSubCat.next()){
                    subCatList.add(new Category(rsSubCat.getInt("ID")));
                }
                rsSubCat.close();
                lists.add(new Pair(mainCat, subCatList));
            }
            rsMainCat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

    public static ArrayList<Category> getMainCategories() {
        ArrayList<Category> list = new ArrayList<>();
        ResultSet rsMainCat = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID IS NULL");
        try {
            while (rsMainCat.next()) {
                list.add(new Category(rsMainCat.getInt("ID")));
            }
            rsMainCat.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static ArrayList<Category> getSubCategories(Category parent) {
        ArrayList<Category> list = new ArrayList<Category>();
        ResultSet rs = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID = #", parent.getId().toString());
        try {
            while (rs.next()) {
                list.add(new Category(rs.getInt("ID")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void createNewCategory(String name) {
        String sql = "INSERT INTO CATEGORY VALUES (#,'#',NULL,NULL,'#')";
        boolean x = DB.execute(sql, DB.generateId("CATEGORY").toString(), name, GLOBAL.ADMIN.getUsername());
    }

    public static void createNewSubCategory(String name, String parentname) {
        Category ctg = new Category(parentname);
        String sql = "INSERT INTO CATEGORY VALUES (#,'#',#,NULL,'#')";
        boolean x = DB.execute(sql, DB.generateId("CATEGORY").toString(), name, ctg.getId().toString(), GLOBAL.ADMIN.getUsername());
    }

    public static void DeleteCategory(String name) {
        String sql = "DELETE FROM CATEGORY WHERE PARENT_ID  = # ";
        Category ctg = new Category(name);
        DB.execute(sql, ctg.getId().toString());
        sql = "DELETE FROM CATEGORY WHERE NAME = '#'";
        DB.execute(sql, name);

    }

    public static ArrayList<Pair<Category, ArrayList<Category>>> getTreeList() {
        ArrayList<Pair<Category, ArrayList<Category>>> lists = new ArrayList();
        try {
            ResultSet rsMain = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID IS NULL ORDER BY NAME ASC");
            while(rsMain.next()){
                Category main = new Category(rsMain.getInt("ID"));
                ResultSet rsSub = DB.executeQuery("SELECT ID FROM CATEGORY WHERE PARENT_ID = # ORDER BY NAME ASC", main.getId().toString());
                ArrayList<Category> list = new ArrayList();
                while(rsSub.next()){
                    list.add(new Category(rsSub.getInt("ID")));
                }
                lists.add(new Pair<Category, ArrayList<Category>>(main, list));
                rsSub.close();
            }
            rsMain.close();
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lists;
    }

}
