/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans.javahelpers;

/**
 *
 * @author Ant
 */
public class CreateUser extends Server {
    String result;
     public String createUser(String lastname, String firstname, String email, String password, String phonenumber, String address, String city, String areacode,String imgURL,String orient) throws ClassNotFoundException {

        try {
            String args = "INSERT INTO Users (firstname,lastname,email,password,phonenumber,address,city,areacode,imageurl,imageorientation) VALUES ("+"'"+ firstname+"',"+"'"+ lastname +"',"+"'"
                    + email +"',"+"'"+password+"','"+phonenumber +"','"+ address+"','"+ city+"','"+areacode+"','"+imgURL+"','"+orient+"')";
            setStatement(getConn().createStatement());
                    int exe = getStatement().executeUpdate(args);
            if (exe >= 1) {
                result ="success";
                } else {
                    result = "failed";
                }
           

        } catch (Exception e) {

        }
        return result;
    }
}
