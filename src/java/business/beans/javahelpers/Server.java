/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.beans.javahelpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Christian
 */
public abstract class Server {

    private Connection conn;
    private ResultSet resultSet;
    private Statement statement;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public boolean connectToServer() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }

        try {
            setConn(DriverManager
                    .getConnection("jdbc:mysql://109.74.1.55:3306/Brent"
                            + "?user=" + "root" + "&password=" + "r6vzpvjn"));
            return true;
        } catch (Exception ex) {
            System.out.println("eX " + ex);
            return false;
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }    
}
