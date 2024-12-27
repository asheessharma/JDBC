
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String name = "root";
    private static final String password = "MySQL@123";
    public static void main(String[] args) throws ClassNotFoundException,SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,name,password);
            Statement statement = con.createStatement();
            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> reservation(sc, statement);
                    case 2 -> viewReservation(statement);
                    case 3 -> getRoomNumber(sc, statement);
                    case 4 -> updateReservation(sc, statement);
                    case 5 -> deleteReservation( sc, statement);
                    case 0 -> {
                        exit();
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Choose again carefully..");
                }
            }

        }catch (SQLException | InterruptedException e){
            System.out.println(e.getMessage());
        }

    }
    private static void reservation( Scanner sc, Statement statement){
        try{
            System.out.print("Enter the name: ");
            String name = sc.next();
            sc.nextLine();
            System.out.print("Enter the room number: ");
            int rno = sc.nextInt();
            System.out.print("Enter the contact number: ");
            String cno = sc.next();

            String sql = "INSERT INTO reservation(name, room, contact_no) VALUES('" + name + "'," + rno + ", '" + cno + "')";

                int affectedRows = statement.executeUpdate(sql);

                if(affectedRows>0){
                    System.out.println("Reservation successful!");
                }else{
                    System.out.println("Reservation failed.");
                }



        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void viewReservation( Statement statement)throws SQLException,InterruptedException{
        String sql = "SELECT id, name, room, contact_no, reservation_date FROM reservation;";

        try(ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String gName = resultSet.getString("name");
                int room = resultSet.getInt("room");
                String cno = resultSet.getString("contact_no");
                String date = resultSet.getTimestamp("reservation_date").toString();

                System.out.println("===============================================\n");
                System.out.println("Reservation id: " + id);
                System.out.println("Name: "+ gName);
                System.out.println("Room no: "+ room);
                System.out.println("Contact Details: "+ cno);
                System.out.println("Reservation Date: "+ date);
                System.out.println("\n===============================================");

            }
            Thread.sleep(999);
        }
    }

    private static void getRoomNumber(Scanner scanner,Statement statement){
        try {
            System.out.print("Enter reservation id: ");
            int id = scanner.nextInt();
            System.out.print("Enter guest name: ");
            String name = scanner.next();

            String sql = "SELECT room FROM reservation WHERE id = "+ id +
                    " AND name = '" + name +"';";

            try(ResultSet resultSet = statement.executeQuery(sql)){

                if(resultSet.next()){
                    int room = resultSet.getInt("room");
                    System.out.println("Room number for Reservation ID: "+ id + " and Guest " + name + " is: " + room);
                }else {
                    System.out.println("No reservation found for the Id: "+id);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void updateReservation(Scanner scanner, Statement statement){
        try {
            System.out.print("Enter reservation ID to update: ");
            int reservationID = scanner.nextInt();
            scanner.nextLine();

            if (!reservationExist(reservationID,statement)) {
                System.out.println("Reservation not found for the given ID. ");
                return;
            }

            System.out.print("Enter the guest name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter the new room number: ");
            int newRoom = scanner.nextInt();
            System.out.print("Enter new Contact number: ");
            String newContact = scanner.next();

            String sql = "UPDATE reservation SET name = '" + newName + "', room = " + newRoom + ", contact_no = '" + newContact + "' WHERE id = " + reservationID;

            int affectedRows = statement.executeUpdate(sql);

            if(affectedRows > 0){
                System.out.println("Reservation updated successfully!");
            }else{
                System.out.println("Reservation update failed.");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void deleteReservation( Scanner scanner, Statement statement){
        try {
            System.out.print("Enter the reservation ID: ");
            int reservationId = scanner.nextInt();

            if(!reservationExist(reservationId,statement)){
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservation WHERE id = " + reservationId;

            int affectedRows = statement.executeUpdate(sql);
            if(affectedRows > 0){
                System.out.println("Reservation deleted successfully.");
            }else {
                System.out.println("Reservation deletion failed.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static boolean reservationExist( int reservationId , Statement statement){
        try {
            String sql = "SELECT id FROM reservation WHERE id = "+reservationId;

            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static void exit() throws InterruptedException{
        System.out.print("\nExiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank You For using Hotel Reservation System!!!");
    }
}


