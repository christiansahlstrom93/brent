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
    boolean createdUser = false;
    public String emailCheck(String email){
        try{
             String args = "SELECT email FROM Users WHERE email ='" +email+"'";
            setStatement(getConn().createStatement());
            setResultSet(getStatement().executeQuery(args));

            if (getResultSet().next()) {
                result = "fail";
                } else {
                    result = "success";
                }
            
             
         }catch(Exception e){
            System.out.println("ERROR I BEAN " + e);
         }
        return result;
    }
     public boolean createUser(String lastname, String firstname, String email, String password, String phonenumber, String address, String city, String areacode,String imgURL,String orient) throws ClassNotFoundException {
         
         
        try {
            String args = "INSERT INTO Users (firstname,lastname,email,password,phonenumber,address,city,areacode,imageurl,imageorientation) VALUES ("+"'"+ firstname+"',"+"'"+ lastname +"',"+"'"
                    + email +"',"+"'"+password+"','"+phonenumber +"','"+ address+"','"+ city+"','"+areacode+"','"+imgURL+"','"+orient+"')";
            setStatement(getConn().createStatement());
                    int exe = getStatement().executeUpdate(args);
            if (exe >= 1) {
                createdUser = true;
            } else {
                createdUser = false;
            }
           

        } catch (Exception e) {

        }
        return createdUser;
    }
}
