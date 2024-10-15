package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;

public class EmployeeManagement {

    static final String driverName = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/shivam";
    static final String username = "root";
    static final String password = "Mysql17#";
    
    // Adding "IF NOT EXISTS" to avoid error if the table already exists
    static final String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                                "eno INT PRIMARY KEY AUTO_INCREMENT, " +
                                "ename VARCHAR(30), " +
                                "esal DOUBLE, " +
                                "deprt VARCHAR(20), " +
                                "age INT)";

    static Connection connection;
    
    public static void connect() throws SQLException, ClassNotFoundException {
 
    	connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection established");

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Table created successfully");
    }
    
    
    //To add employee record in Employee Table.
    public static void addEmployee() throws SQLException {
    	Scanner scanner=new Scanner(System.in);
    	
    	System.out.print("Enter employee name: ");
    	String empName=scanner.nextLine();
    	
    	System.out.print("Enter salary: ");
    	Double empSal=scanner.nextDouble();
    	scanner.nextLine();
    	
    	System.out.print("Enter employee department: ");
    	String empDeprt=scanner.nextLine();
    	
    	
    	System.out.print("Enter employee age: ");
    	Integer empAge=scanner.nextInt();
    	
    	String query = "INSERT INTO employee(ename, esal, deprt, age) VALUES (?, ?, ?, ?)";
    	
    	PreparedStatement preparedStatement = connection.prepareStatement(query);
    	
    	preparedStatement.setString(1, empName);
    	preparedStatement.setDouble(2, empSal);
    	preparedStatement.setString(3, empDeprt);
    	preparedStatement.setInt(4, empAge);
    	
    	Integer rowAffected = preparedStatement.executeUpdate();
    	System.out.println(rowAffected+"Row added sucessfully..");
    	
    }
    
    //To view employee record from Employee Table.
    public static void viewEmployee() throws SQLException {
    	String query = "Select * from employee";
    	
    	Statement statement = connection.createStatement();
    	ResultSet resultSet = statement.executeQuery(query);
    	
    	System.out.println("Employee List: ");
    	while(resultSet.next()) {
    		System.out.println("Employe No: "+resultSet.getInt("eno")+ 
    				",Name: "+resultSet.getString("ename")+
    				", Salary: "+resultSet.getDouble("esal")+
    				",Department: "+resultSet.getString("deprt")+
    				",Age: "+resultSet.getInt("age"));
    	}
    }
    
    
    //To Update employee record in Employee Table
    public static void updateEmployee() throws SQLException {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.print("Enter employee ID to update salary : ");
    	Integer empNo = scanner.nextInt();
    	
    	scanner.nextLine(); 
    	
    	System.out.print("Enter salary to update");
    	Double empSal = scanner.nextDouble();
    	
    	String query = "UPDATE employee SET esal = ? WHERE eno = ?";
    	PreparedStatement preparedStatement= connection.prepareStatement(query);
    	preparedStatement.setDouble(1, empSal);
    	preparedStatement.setInt(2, empNo);
    	
    	int rowUpdated = preparedStatement.executeUpdate();
    	System.out.println(rowUpdated+"row updated.");
    	
    }
    
    
    //To  delete record from Employee Table
    public static void deleteEmployee() throws SQLException {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.print("Enter employee no to delete: ");
    	Integer empNo = scanner.nextInt();
    	
    	String query = "Delete from employee Where eno=?";
    	
    	PreparedStatement preparedStatement = connection.prepareStatement(query);
    	
    	preparedStatement.setInt(1,empNo);
    	int rowsAffected = preparedStatement.executeUpdate();
        System.out.println(rowsAffected + " employee deleted.");
    }
    
    
    public static void closeConnection() throws SQLException {
    	connection.close();
    	System.out.println("Disconnected....");
    }

    public static void main(String[] args) {
        try {
            connect();
            Scanner scanner = new Scanner(System.in);
            Integer choice;
            do {
            	System.out.println("\n  ---Employee Managemet System.");
            	System.out.println("1. Add Employee.");
            	System.out.println("2. View Employee.");
            	System.out.println("3. Update Employee Salary.");
            	System.out.println("4. Delete Employee.");
            	System.out.println("5. Close Connection from Database.");
            	System.out.println("6. Existing...");
            	
            	
            	System.out.println(" Enter Choice. ");
            	choice = scanner.nextInt();
            	
            	switch(choice) {
            		case 1:
            			addEmployee();
            			break;
            		case 2:
            			viewEmployee();
            			break;
            		case 3:
            			updateEmployee();
            			break;
            		case 4: 
            			deleteEmployee();
            			break;
            		case 5:
            			closeConnection();
            			break;
            		case 6:
            			System.exit(0);
            			break;
            		default:
            			System.out.println("Invalid choice......");
            	}
            }while(choice !=6);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
