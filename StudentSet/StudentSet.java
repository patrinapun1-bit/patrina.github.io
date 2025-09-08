import java.util.Scanner;// import any necessary scanners 
import java.util.Random;// import random scanners 

public class StudentSet { // class name 
    private String novels;// data attribute 
    private String stationary; //data attribute 
    private String additionalItem; //data attribute 

    public StudentSet(String novels, String stationary) {// argument constructor that brings in two data attributes 
        this.novels = novels;//initializes and declares novels to what is eventually stored in novels 
        this.stationary = stationary;//initializes and declares stationary to what is eventually stored in stationary 
    }

    public String getNovels() {//getter function for novel data attribute that allows for retrieval 
        return novels;//returns what is stored in 'novel'
    }

    public String getStationary() {//getter function for stationary data attribute that allows for retrieval 
        return stationary;//returns what is stored in 'stationary' 
    }

    public String getAdditionalItem() {//getter function for additional item data attribute that allows for retrieval
        return additionalItem;//returns what is stored in 'additional item'
    }

    public void setAdditionalItem() {//setter function that allows for storage and modification
        String[] gift = {"Lunch Box", "Water Bottle", "Pencil Case"};//an array of strings that can be utilized stored into 'gift'
        Random choices = new Random();//created to generate a series of random value 
        int index = (int)(Math.random() * (gift.length));//generates a random index utilizing the array  
        additionalItem = gift[index];//assigns the data attribute to a randomly chosen index of the gift array 
    }

    public void Additive(Scanner input) { //a void method that holds a bonus spin when called, while bringing in scanner input as an argument
        System.out.println();// line of space 
        System.out.println("We are currently offering a spin for a free item for the holiday season!");// print statement
        System.out.println("Would you like to spin? (yes/no)");//print statement
        String spin = input.nextLine(); // Get user input for spin
          if (spin.equalsIgnoreCase("yes")) { // if else statement regarding if user input equals "yes" 
            setAdditionalItem();  // Set a random gift if spin is "yes"
            System.out.println("You opted for a free spin!" + " You get a free: " + getAdditionalItem());// a print statement and retrieving/calling 'AdditionalItem' 
            System.out.println("Thank you for your participation today! If you can provide your email/parent's email, we will contact you in approximately 2-3 business days for your items: ");
            String email = input.nextLine();  // Collect email
            System.out.println("Please provide your first name as well.");
            String name = input.nextLine();  // Collect name
            System.out.println("Thank you " + name + ". We will contact you soon, thank you for taking your time to join us today.");
        } else {  
            System.out.println("You opted for no free spin!");
            System.out.println("Thank you for your participation today! If you can provide your email/parent's email, we will contact you in approximately 2-3 business days for your items: ");
            String emails = input.nextLine();  // Collect email
            System.out.println("Please provide your first name as well.");
            String names = input.nextLine();  // Collect name
            System.out.println("Thank you " + names + ". We will contact you soon, thank you for taking your time to join us today!");
        }
    }

    public static void main(String[] args) { // main method needed for program to function
        Scanner input = new Scanner(System.in);  // Keep the scanner open for the entire program
        StudentSet[] student = new StudentSet[4];//object instance and array 
        student[0] = new StudentSet("Math Textbook", "a set of pencils and erasers");
        student[1] = new StudentSet("English Textbook", "a set of pencils and erasers");
        student[2] = new StudentSet("Science Textbook", "a set of pencils and erasers");
        student[3] = new StudentSet("Literature Textbook", "a set of pencils and erasers");
        
        System.out.println("Welcome! Where do you currently reside?");
        System.out.println("Option 1: Baltimore County");
        System.out.println("Option 2: Franklin Florida");
        System.out.println("Option 3: Tintic Utah");
        System.out.println("Please write down the current city/county:");
        String option = input.nextLine();//user input

        System.out.println("What grade are you in? We currently only offer resources (Textbook and stationary) from grades 6-12.");
        System.out.println("Please enter your grade number:");
        int options = input.nextInt();//user input
        input.nextLine();  // Consume leftover newline

        System.out.println("You are currently in grade: " + options + ". Which subject textbook do you currently need for " + options + "th grade?");
        System.out.println("Option 1: Math");
        System.out.println("Option 2: English");
        System.out.println("Option 3: Science");
        System.out.println("Option 4: Literature");
        System.out.println("Please write down the subject: ");
        String subjects = input.nextLine();//user input

        int attempts = 0;//intialize/declare variable attempts to 0
         while (attempts < 2) {//a while loop that 'while attempts is less than 2
            if (subjects.equalsIgnoreCase("Math")) {// if else statement embedded if user input equals "Math"
                System.out.println("You will receive: ");
                System.out.println("Textbook: " + student[0].getNovels());//retrieving the data attribute novels and the specific array assigned to student[0]
                System.out.println("Stationary: " + student[0].getStationary());//retrieving the data attribute stationary and the specific array assigned to student[0]
                student[0].Additive(input);  //calls for the Additive method while bringing the scanner input, while assigned to the specific array
                 break;  // Exit the loop after handling the valid subject
            } else if (subjects.equalsIgnoreCase("English")) { 
                System.out.println("You will receive: ");
                System.out.println("Textbook: " + student[1].getNovels());//retrieving the data attribute novels and the specific array assigned to student[1]
                System.out.println("Stationary: " + student[1].getStationary());
                student[1].Additive(input);//calls for the Additive method while bringing in the same scanner input, while assigned to student[1] array
                break;
            } else if (subjects.equalsIgnoreCase("Science")) {
                System.out.println("You will receive: ");
                System.out.println("Textbook: " + student[2].getNovels());//retrieving the data attribute novels and the specific array assigned to student[2]
                System.out.println("Stationary: " + student[2].getStationary());
                student[2].Additive(input);//calls for the Additive method while bringing in the same scanner input, while assigned to student[2]
                break;
            } else if (subjects.equalsIgnoreCase("Literature")) {
               System.out.println("You will receive: ");
                System.out.println("Textbook: " + student[3].getNovels());
                System.out.println("Stationary: " + student[3].getStationary());
                student[3].Additive(input);
                break;
            } else {
                attempts++;// if the user fails to enter one of the four options, the variable increases by 1 
                  if (attempts < 2) {// if the user does not enter one of the four options  
                    System.out.println("That is not an option provided. Please try again.");//print statement 
                    subjects = input.nextLine();  // Get new input from the user
                } else {//if the user attempts exceed two 
                    System.out.println("You have run out of attempts. The program will now shut down, please try again later.");//print statement 
                    input.close();//program closes 
                  }
            }
        }

        input.close();  // Close the scanner at the end of the program
    }
}
