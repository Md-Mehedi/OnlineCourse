/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Course;

import java.util.Date;

/**
 *
 * @author Md Mehedi Hasan
 */
public class PromoCode {
    Date start;
    Date end;
    String name;
    double off;

    public PromoCode(Date start, Date end, String name, double off) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.off = off;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOff() {
        return off;
    }

    public void setOff(double off) {
        this.off = off;
    }
}
