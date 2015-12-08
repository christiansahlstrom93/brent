/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans;

import javax.ejb.Local;
import org.json.JSONArray;

/**
 *
 * @author Christian
 */
@Local
public interface AdBeanLocal {

    JSONArray getAdListData(String location, String product);

    JSONArray getUserCredentials(String usrName);

}
