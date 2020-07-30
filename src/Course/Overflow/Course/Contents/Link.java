/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course.Contents;

/**
 *
 * @author Md Mehedi Hasan
 */
public class Link extends Lecture{
    String url;
    String Description;

    public Link(String url, String Description) {
        this.url = url;
        this.Description = Description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isIsPreview() {
        return isPreview;
    }

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }
}
