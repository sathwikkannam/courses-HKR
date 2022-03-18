package se.hkr;

import java.sql.*;
import java.util.Scanner;

public class DatabaseConnection {
    private final Connection connection;


    public DatabaseConnection(String address, String port, String username, String password) throws SQLException {
        String schema = "classicmodels";
        connection = DriverManager.getConnection("jdbc:mysql://"+address+":"+port+"/" + schema, username, password);
    }

    public ResultSet executeQuery(String columns, String from, int limit){
        String sql;
        ResultSet result = null;

        if(limit > 0){
            sql = "SELECT " + columns + " FROM " + from + " LIMIT " + limit;
        }else{
            sql = "SELECT " + columns + " FROM " + from;
        }

        try{
            PreparedStatement stm =  connection.prepareStatement(sql);
            result = stm.executeQuery();
            System.out.println(getRowCount(stm) + " row(s) affected.");
        }catch(SQLException e){
            printSQLException(e);
        }

        return result;
    }


    public int executeUpdate(String typeOfUpdate, Scanner input){
        String sql = null;
        PreparedStatement stm = null;

        switch (typeOfUpdate) {
            case "UPDATE", "update" -> sql = update(input);
            case "DELETE", "delete" -> sql = delete(input);
            case "INSERT", "insert" -> sql = insert(input);
        }

        try{
            stm = this.connection.prepareStatement(sql);
            stm.executeQuery();
        }catch(SQLException e){
            printSQLException(e);
        }

        return getRowCount(stm);

    }


    public String insert(Scanner input){
        System.out.print("Enter table: ");
        String table = input.nextLine();
        System.out.print("Enter attributes (seperated by ','): ");
        String attributes = input.nextLine();
        System.out.print("Values (seperated by ','): ");
        String values = input.nextLine();

        return "INSERT INTO " + table + "(" + attributes + ") VALUES (" + values + ")";
    }

    public String delete(Scanner input){
        System.out.print("Enter table: ");
        String table = input.nextLine();
        System.out.print("Enter attribute: ");
        String attribute = input.nextLine();
        System.out.print("Enter its value: ");
        String attributeValue = input.nextLine();

        return "DELETE FROM " + table + " WHERE " + attribute + " = " + attributeValue;
    }

    public String update(Scanner input){
        System.out.print("Enter table: ");
        String table = input.nextLine();
        System.out.print("Enter attributes (seperated by ','): ");
        String attributes = input.nextLine();
        System.out.print("Enter its value (seperated by ','): ");
        String attributesValue = input.nextLine();
        System.out.print("Condition attribute: ");
        String condition = input.nextLine();
        System.out.print("Condition's value? ");
        String conditionValue = input.nextLine();

        StringBuilder sql = new StringBuilder("UPDATE " + table + " SET ");

        if(attributes.contains(",") && attributesValue.contains(",")){
            String[] splitAttributes = attributes.split(",");
            String[] splitValues = attributesValue.split(",");

            for (int i = 0; i < splitAttributes.length; i++) {
                sql.append(splitAttributes[i]).append(" = ").append(splitValues[i]);
                if(i != splitAttributes.length - 1){
                    sql.append(", ");
                }else{
                    sql.append(" ");
                }
            }
        }else{
            sql.append(attributes).append(" = ").append(attributesValue);
        }

        sql.append(" WHERE ").append(condition).append(" = ").append(conditionValue);


        return sql.toString();

    }


    public int getRowCount(PreparedStatement stm){
        int rows = 0;
        try{
            rows = stm.getUpdateCount();
        }catch(SQLException e){
            printSQLException(e);
        }

        return rows;

    }

    public void printSQLException(SQLException exception){
        System.out.println("SQLException: " + exception.getMessage());
        System.out.println("SQLState: " + exception.getSQLState());
        System.out.println("Error Code: " + exception.getErrorCode());
    }

}
