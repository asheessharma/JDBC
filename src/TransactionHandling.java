import java.sql.*;
public class TransactionHandling {

    public static void main(String[] args) {
         String url = "jdbc:mysql://localhost:3306/students";
         String userName = "root";
         String password = "MySQL@123";
         String queryDebit = "UPDATE accounts SET balance = balance - ? WHERE account_num = ?";
         String queryCredit = "UPDATE accounts SET balance = balance + ? WHERE account_num = ? ";



         try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             System.out.println("Drivers loaded.");
         }catch (ClassNotFoundException e){
             e.printStackTrace();
         }

         try {
             Connection con = DriverManager.getConnection(url,userName,password);
             System.out.println("\nConnection established.");
             con.setAutoCommit(false);
             PreparedStatement withdraw = con.prepareStatement(queryDebit);
             PreparedStatement deposit = con.prepareStatement(queryCredit);
             withdraw.setDouble(1,500.90);
             withdraw.setString(2,"account456");
             deposit.setDouble(1,500.90);
             deposit.setString(2,"account123");
             int rowsAffectedWithdraw = withdraw.executeUpdate();
             int rowAffectedDeposit = deposit.executeUpdate();

             if(rowAffectedDeposit > 0 && rowsAffectedWithdraw >0){
                 con.commit();
                 System.out.println("Transaction committed..");
             }else{
                 con.rollback();
                 System.out.println("Transaction failed... ");
             }


         }catch (SQLException e){
             System.out.println(e.getMessage());
         }

    }






}
