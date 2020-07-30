/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import javafx.scene.image.Image;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Category {
    int id;
    String name;
    String description;
    Image image;
    Category parent;

    public Category(int id, String name, String description, Image image, Category parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public Category getParent() {
        return parent;
    }
    
}
