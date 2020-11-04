/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Course.Overflow.Global;

import Course.Overflow.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Mehedi Hasan
 */
public class CreditCard {
    Integer id;
    String cardNo;
    String nameOnCard;
    Date expireDate;
    
    public CreditCard(Integer id){
        this.id = id;
        if(DB.valueExist("CREDIT_CARD", "ID", id.toString())){
            ResultSet rs = DB.executeQuery("SELECT * FROM CREDIT_CARD WHERE ID = #", id.toString());
            try {
                if(!rs.next()) return;

                cardNo = rs.getString("CARD_NO");
                nameOnCard = rs.getString("NAME_ON_CARD");
                expireDate = ToolKit.makeDateForJAVA(rs.getDate("EXPIRE_DATE"));
            } catch (SQLException ex) {
                Logger.getLogger(CreditCard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public CreditCard(Integer id, String cardNo, String nameOnCard, Date expireDate){
        this.id = id;
        this.cardNo = cardNo;
        this.nameOnCard = nameOnCard;
        this.expireDate = expireDate;
    }
    
    public static CreditCard insertCreditCard(String cardNo, String nameOnCard, Date expireDate){
        Integer id = DB.generateId("CREDIT_CARD");
        DB.execute("INSERT INTO CREDIT_CARD VALUES(#, '#', '#', #)", id.toString(), cardNo, nameOnCard, ToolKit.JDateToDDate(expireDate));
        return new CreditCard(id, cardNo, nameOnCard, expireDate);
    }   

    public Integer getId() {
        return id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
        DB.execute("UPDATE CREDIT_CARD SET CARD_NO = '#' WHERE ID = '#'", cardNo, id.toString());
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
        DB.execute("UPDATE CREDIT_CARD SET NAME_ON_CARD = '#' WHERE ID = '#'", nameOnCard, id.toString());
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
        DB.execute("UPDATE CREDIT_CARD SET EXPIRE_DATE = # WHERE ID = '#'", ToolKit.JDateToDDate(expireDate), id.toString());
    }
    
    public void deleteCard(){
        DB.execute("DELETE FROM CREDIT_CARD WHERE ID = #", id.toString());
    }
}
