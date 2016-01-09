/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Javahelpers;

/**
 *
 * @author Christian
 */
public class SearchFilter {

    public static final String EMPTY_FIELD = "nothing";

    public String getAdQuery(String location, String prod) {
        String args;

        if (EMPTY_FIELD.equals(location) && EMPTY_FIELD.equals(prod)) {
            args = "SELECT * from ads order by adid desc";
        } else if (!EMPTY_FIELD.equals(location) && EMPTY_FIELD.equals(prod)) {
            args = "SELECT * from ads where city = '"+location+"' order by adid desc";
        } else if (!EMPTY_FIELD.equals(prod) && EMPTY_FIELD.equals(location)) {
            args = "SELECT * from ads where title like '%"+prod+"%' order by adid desc";
        } else {
            args = "SELECT * from ads where city = '"+location+"' and title like '%"+prod+"%' order by adid desc";
        }
        
        System.out.println(args);

        return args;
    }
}
