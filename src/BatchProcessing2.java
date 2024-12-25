import java.sql.*;
import java.util.Scanner;
public class BatchProcessing2 {
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
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully...");
            connection.setAutoCommit(false);
            Scanner scanner = new Scanner(System.in);
            String query = "INSERT INTO employees(id,name,job_title,salary) VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            while (true){
                System.out.print("id: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("name: ");
                String name = scanner.nextLine();
                System.out.print("job_title: ");
                String job = scanner.nextLine();
                System.out.print("salary: ");
                double salary = scanner.nextDouble();


                preparedStatement.setInt(1,id);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,job);
                preparedStatement.setDouble(4,salary);
                preparedStatement.addBatch();
                System.out.print("Add more values Y/N ? : ");
                String ans = scanner.next();
                if(ans.equalsIgnoreCase("N")){
                    break;
                }
            }
            int [] batchResult = preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Batch executed successfully....");


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
