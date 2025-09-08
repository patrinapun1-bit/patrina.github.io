package AdventureGame;

import java.util.Scanner;

public class UnderwaterCave {
    private Scanner input = new Scanner(System.in);

    public void enter(Player player) {
        System.out.println("\n--- You have entered the Underwater Cave ---");
        System.out.println("Choose a number between 1-4 to select your tool: ");
        int choice = input.nextInt();
        input.nextLine(); // consume newline
        switch (choice) {
            case 1 -> System.out.println("You picked up: Oxygen Tank");
            case 2 -> System.out.println("You picked up: Diving Suit");
            case 3 -> System.out.println("You picked up: Harpoon");
            case 4 -> System.out.println("You picked up: Waterproof Map");
            default -> System.out.println("You dive in with nothing but your breath.");
        }

        // Trivia Question
        String question = "What gas do fish extract from water?";
        String answer = "Oxygen";
        int attempts = 0;
        boolean correct = false;

        while (attempts < 2 && !correct) {
            System.out.println("Trivia challenge: " + question);
            String response = input.nextLine();
            if (response.equalsIgnoreCase(answer)) {
                System.out.println("Correct! +50 points");
                player.addPoints(50);
                correct = true;
            } else {
                attempts++;
                if (attempts < 2) {
                    System.out.println("Incorrect. Try again! (Second and last chance)");
                } else {
                    System.out.println("Incorrect again. No points awarded.");
                }
            }
        }
        System.out.println("Current points: " + player.getScore());
    }
}
