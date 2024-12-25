import java.sql.*;
import java.util.Scanner;
public class PrepState {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students";
        String userName = "root";
        String password = "MySQL@123";
//        String query = "SELECT * FROM employees WHERE name = ?";
        String query1 = "INSERT INTO employees(id,name, job_title, salary) VALUES(?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully...");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            Connection con = DriverManager.getConnection(url,userName,password);
            System.out.println("Connection established Successfully..");

            PreparedStatement preparedStatement = con.prepareStatement(query1);


            Scanner scanner = new Scanner(System.in);
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("NAME: ");
            String name = scanner.nextLine();
            System.out.print("JOB: ");
            String job = scanner.nextLine();
            System.out.print("SALARY: ");
            double salary  = scanner.nextDouble();

            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,job);
            preparedStatement.setDouble(4,salary);

            int rowsUpdated = preparedStatement.executeUpdate();

            if(rowsUpdated>0){
                System.out.println("Row inserted in database...");
            }else{
                System.out.println("Insertion failed...");
            }
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String job = rs.getString("job_title");
//                double salary = rs.getDouble("salary");
//
//                System.out.println("-->> "+ id + "  " + name + "  " + job + "  " + salary);
//            }
//            rs.close();
            con.close();
            preparedStatement.close();
            System.out.println();
            System.out.println("Connection Closed Successfully...");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
