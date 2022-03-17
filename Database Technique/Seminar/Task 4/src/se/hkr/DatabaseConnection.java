package se.hkr;

import java.sql.*;

public class DatabaseConnection {
    private final Connection connection;


    public DatabaseConnection(String address, String port, String username, String password) throws SQLException {
        String schema = "classicmodels";
        connection = DriverManager.getConnection("jdbc:mysql://"+address+":"+port+"/" + schema, username, password);
    }

    public void executeQuery(String columns, String from, int limit){
        String sql;

        if(limit > 0){
            sql = "SELECT " + columns + " FROM " + from + " LIMIT " + limit;
        }else{
            sql = "SELECT " + columns + " FROM " + from;
        }

        try{
            PreparedStatement stm =  connection.prepareStatement(sql);
            ResultSet result = stm.executeQuery();

            for (String e : columns.split(",")){
                System.out.printf("\t%-12s\t|", e);
            }
            System.out.println("\n");

            while(result.next()){
                for (String colum : columns.split(",")){
                    System.out.printf("\t%-12s\t|",result.getString(colum.replaceAll(" ", "")));
                }
                System.out.println("\n");
            }

            printRowCount(stm);

        }catch(SQLException e){
            printSQLException(e);
        }

    }

//    Write two different methods in DatabaseConnection class. One for retrieving data from
//    database and the other for updating (or deleting) data in the database (you can decide
//    about what the methods shall do).


    public void executeUpdate(){
        try{
            PreparedStatement stm = this.connection.prepareStatement("e");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void printRowCount(PreparedStatement stm) throws SQLException{
        System.out.println("Rows affected of last statement: " + stm.getUpdateCount() + " rows.");
    }

    public void printSQLException(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }


}
