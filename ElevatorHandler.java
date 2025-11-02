import java.util.*;

public class ElevatorHandler {
    private Elevator elevator;
    private Scanner scanner;
    private boolean running;

    public ElevatorHandler(int numFloors, int capacity, Scanner scanner) {
        this.elevator = new Elevator(numFloors, capacity);
        this.scanner = scanner;
        this.running = true;
    }

    public void initializeElevator(){
        System.out.println("Elevator Started Running");
        while (running) {
            displayMenu();
            handleUserInput();
        }
        
        scanner.close();
    }

    private void displayMenu() {

        System.out.println("1. Call elevator (UP/DOWN button)");
        System.out.println("2. Select destination floor");
        System.out.print("Choose option: ");
    }
    
    private void handleUserInput(){
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("1")) {
            callElevator();
        } else if (choice.equals("2")) {
            selectDestination();
        } else {
            System.out.println("Invalid, please choose 1 or 2.");
            return;
        }
        
        elevator.moveElevator();
        showStatus();
    }

    private void callElevator(){
        System.out.print("From which floor? (1-" + elevator.getFloors() + "): ");
        int floor = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Direction (UP/DOWN): ");
        String direction = scanner.nextLine().toUpperCase().trim();

        elevator.callElevator(direction, floor);
    }

    private void selectDestination() {
        System.out.print("Enter destination floor (1-" + elevator.getFloors() + "): ");
        
        int floor = Integer.parseInt(scanner.nextLine().trim());
        boolean success = elevator.floorDestination(floor);
        
        if (success) {
            System.out.println(" Destination floor " + floor + " added");
            System.out.println("  New occupancy: " + elevator.getRemainingCapacity() + "/" + elevator.getCapacity());
        } else {
            System.out.println("Elevator at capacity");
        }
    }

    private void showStatus(){
        System.out.println("Elevator currently at floor" + elevator.getCurrentFloor() + " going " + elevator.getDirection());
        System.out.println("Current capacity: " + elevator.getRemainingCapacity());
    }

}
