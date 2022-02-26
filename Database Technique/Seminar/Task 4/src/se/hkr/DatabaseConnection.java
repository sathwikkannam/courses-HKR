package se.hkr;

import java.sql.*;

public class DatabaseConnection {
    private final Statement statement;


    public DatabaseConnection(String address, String port, String schema, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://"+address+":"+port+"/" + schema, username, password);
        statement =  connection.createStatement();
    }

    public void executeQuery(String select, String column){
        try{

            ResultSet resultSet = statement.executeQuery(select);

            while(resultSet.next()){
                System.out.println(resultSet.getString(column));
            }

        }catch(SQLException e){
            System.out.println("Invalid command");
        }

    }


    public void executeUpdate(){
        try{
            ResultSet resultSet = statement.executeQuery("Ss");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
