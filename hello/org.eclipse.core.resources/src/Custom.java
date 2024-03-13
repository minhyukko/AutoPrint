// Java Program to Creating our Own Custom Class
// Importing input output classes
import java.io.*;

// Class 1
// Helper class
class Employee {

    // Member variables of this class
    // first attribute
    int id;

    // second attribute
    int salary;

    // third attribute
    String name;

    // Member function of this class
    // Method 1
    public void printDetails() {
        // Print and display commands
        System.out.println("My id is " + id);
        System.out.println("This is my name " + name);
        System.out.println("Method printDetails successfully completed.");
    }

    // Method 2
    public int getSalary() {
        System.out.println("Method getSalary successfully completed.");
        // Simply returning the salary
        return salary;
    }
}

// Class 2
// Main class
class Custom {

    // Main driver method
    public static void main(String[] args) {
        // Display message only
        System.out.println("This is the custom class");
        // Creating object of custom class in the main()
        // method Instantiating a new Employee object
        Employee harry = new Employee();
        // Again creating object of custom class and
        // instantiating a new Employee object
        Employee robin = new Employee();
        // Initializing values for first object created
        // above
        harry.id = 23;
        System.out.println("harry.id: " + harry.id);
        harry.salary = 100000;
        System.out.println("harry.salary: " + harry.salary);
        harry.name = "Ritu bhatiya";
        System.out.println("harry.name: " + harry.name);
        // Initializing values for second object created
        // above
        robin.id = 25;
        System.out.println("robin.id: " + robin.id);
        robin.salary = 150000;
        System.out.println("robin.salary: " + robin.salary);
        robin.name = "Amit thripathi";
        System.out.println("robin.name: " + robin.name);
        // Printing object attributes by
        // calling the method as defined in our class
        harry.printDetails();
        robin.printDetails();
        // Calling the method again of our class and
        // storing it in a variable
        int salary = robin.getSalary();
        // Print and display the above salary
        System.out.println("Salary of robin : " + salary + "$");
        System.out.println("ID : " + harry.id);
        System.out.println("Method main successfully completed.");
    }
}
