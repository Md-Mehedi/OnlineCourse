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
public class Video extends Lecture{
    String url;
    String Description;
    String filename;

    public Video(String url, String Description, String filename) {
        this.url = url;
        this.Description = Description;
        this.filename = filename;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
