import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args)throws ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // To load drivers
            System.out.println("Drivers loaded Successfully");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        String url = "jdbc:mysql://localhost:3306/Students";
        String username = "root";
        String password = "MySQL@123";
        String query = "UPDATE employees SET name = 'ashish' WHERE id = 3;" ;

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Successfully Connected to database");
            Statement stmt = con.createStatement();
            int rowsEffected = stmt.executeUpdate(query); //To insert/Delete/Update data. It returns no of rows affected
            if(rowsEffected > 0){
                System.out.println(rowsEffected + " Rows updated Successfully..");
            }else{
                System.out.println("update failed");
            }
//            ResultSet rs = stmt.executeQuery(query);// To retrieve data
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String job_title = rs.getString("job_title");
//                double salary = rs.getDouble("salary");
//                System.out.println('\n' + "******************************************");
//                System.out.println("Id: " + id);
//                System.out.println("Name: "+ name);
//                System.out.println("Job Title: " + job_title);
//                System.out.println("Salary: " + salary);
//            }
//            rs.close();
            stmt.close();
            con.close();
            System.out.println("\n>> Connections closed successfully <<");
        }catch (SQLException e){
            System.err.println("Connection failed: "+ e.getMessage());
        }

    }
}