import java.util.*;
public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of floors: ");
        int floors = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.print("Enter elevator capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.println();
        
        // Create and start the elevator handler, passing the scanner
        ElevatorHandler handler = new ElevatorHandler(floors, capacity, scanner);
        handler.initializeElevator();
    }
    
}
