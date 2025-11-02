import java.util.*;

public class Elevator {
    private int floors;
    private int currentFloor;
    private int numCurr;
    private int capacity;
    private String state;
    private String direction;
    private Set<Integer> ups;
    private Set<Integer> downs;
    private Set<Integer> pickupFloors;
    private boolean moving;
    private Map<Integer, Integer> destinationCounts;

    public Elevator(int numFloors, int capacityPpl){
        this.floors = numFloors;
        this.capacity = capacityPpl;
        this.currentFloor = 0;
        this.numCurr = 0;
        this.ups = new TreeSet<>();
        this.downs = new TreeSet<>(Collections.reverseOrder());
        this.pickupFloors = new TreeSet<>();
        this.direction = null;
        this.state = "CLOSED";
        this.moving = false;
        this.destinationCounts = new HashMap<>();
    }

    public void callElevator(String direction, int newFloor){
        if (newFloor <= 0 || newFloor > this.floors){
            return;
        } else {
            if(direction.equals("UP")){
                this.ups.add(newFloor);
                if(this.direction == null){
                    this.direction = direction;
                }
            } else if (direction.equals("DOWN")){
                this.downs.add(newFloor);
                if(this.direction == null){
                    this.direction = direction;
                }
            }
        }
    }

    public boolean floorDestination(int floor){
        if (floor > 0 && floor <= this.floors && this.currentFloor != floor){
            if(this.numCurr < this.capacity){
                pickupFloors.add(floor);
                destinationCounts.put(floor, destinationCounts.getOrDefault(floor, 0) + 1);
                this.numCurr++;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void moveElevator(){
       if (this.state.equals("OPEN")){
            closeDoors();
        } else if (checkFloorCalls() == true){
            openDoors();
        } else {
            move();
        }
    }

    public void closeDoors(){
        this.state = "MOVING";
        this.moving = true;
    }

    public void openDoors(){
        this.state = "OPEN";
        this.moving = false;
        
        if(this.pickupFloors.contains(this.currentFloor)){
            int exiting = destinationCounts.getOrDefault(this.currentFloor, 0);
            this.numCurr -= exiting;
            destinationCounts.remove(this.currentFloor);
        }
        
        boolean shouldBoard = (this.ups.contains(this.currentFloor) && this.direction.equals("UP")) ||
                             (this.downs.contains(this.currentFloor) && this.direction.equals("DOWN"));
        
        if(shouldBoard){
            int tryingToBoard = 1;
            int actuallyBoarded = Math.min(tryingToBoard, this.capacity - this.numCurr);
            
            if(actuallyBoarded > 0){
                this.numCurr += actuallyBoarded;
            }
        }
    }

    public boolean checkFloorCalls(){
        if (this.pickupFloors.contains(this.currentFloor) && this.moving){
            this.moving = false;
            this.pickupFloors.remove(this.currentFloor);
            this.state = "STILL";
            return true;
        } 
        if (this.ups.contains(this.currentFloor) && this.moving && this.direction.equals("UP")){
            this.moving = false;
            this.ups.remove(this.currentFloor);
            this.state = "STILL";
            return true;
        } 
        if(this.downs.contains(this.currentFloor) && this.moving && this.direction.equals("DOWN")){
            this.moving = false;
            this.downs.remove(this.currentFloor);
            this.state = "STILL";
            return true;
        } 
        if (!this.moving){
           return false;
        }
        return false;
    }

    public void move(){
        if(this.direction.equals("UP")){
            this.currentFloor += 1;
        } else if(this.direction.equals("DOWN")){
            this.currentFloor -=1;
        }
        if(this.direction.equals("UP") && !hasCallsUp()){
            if(!hasCallsDown()){
                this.direction = "STILL";
                this.moving = false;
            } else {
                this.direction = "DOWN";
                this.moving = true;
            }
        }

        if(this.direction.equals("DOWN") && !hasCallsDown()){
            if(!hasCallsUp()){
                this.direction = "STILL";
                this.moving = false;
                this.state = "STILL";
            } else {
                this.direction = "UP";
                this.moving = true;
            }
        }

        if(this.direction.equals("STILL") && this.moving == false){
            if(hasCallsUp()){
                this.direction = "UP";
                this.moving = true;
                this.state = "MOVING";
            } else if (hasCallsDown()){
                this.direction = "DOWN";
                this.moving = true;
                this.state = "MOVING"; 
            }
        }
    }

    public boolean hasCallsUp(){
        for(int i = this.currentFloor  + 1; i <= this.floors; i++){
            if (this.ups.contains(i)){
                return true;
            }
        }

        for (int i = this.currentFloor + 1; i <= this.floors; i++){
            if(this.pickupFloors.contains(i)){
                return true;
            }
        }
        return false;
    }

    public boolean hasCallsDown(){
        for (int i = this.currentFloor - 1; i > 0; i--){
            if(this.downs.contains(i)){
                return true;
            }
        }
        for (int i = this.currentFloor - 1; i > 0; i--){
            if(this.pickupFloors.contains(i)){
                return true;
            }
        }
        return false;
    }

    public int getFloors(){
        return this.floors;
    }

    public int getRemainingCapacity(){
        return this.numCurr;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public String getDirection(){
        return this.direction;
    }
}