import java.sql.*;

public class BatchProcessing {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students";
        String username = "root";
        String password = "MySQL@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded Successfully...");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection build..");
            con.setAutoCommit(false);
            Statement statement = con.createStatement();
            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(5,'Vaibhav', 'HR Manager',65000.0)");
            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(6,'Prashant', 'Tester',25000.0)");
            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(7,'Vishal', 'Floor Mentor',45000.0)");
            statement.addBatch("INSERT INTO employees(id,name, job_title, salary) VALUES(8,'Lakshay', 'Team Lead',55000.0)");
            int [] batchResult = statement.executeBatch();
            con.commit();
            System.out.println("Batch executed successfully....");

            statement.close();
            con.close();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
