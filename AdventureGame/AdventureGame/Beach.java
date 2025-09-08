package AdventureGame;

import java.util.Scanner;

public class Beach {
    private Scanner input = new Scanner(System.in);

    public void enter(Player player) {
        System.out.println("\n--- You are at the Beach ---");
        System.out.println("Choose a number between 1-4 to select your weapon: ");
        int choice = input.nextInt();
        input.nextLine(); // consume newline
        switch (choice) {
            case 1 -> System.out.println("You picked up: Shield");
            case 2 -> System.out.println("You picked up: Bow");
            case 3 -> System.out.println("You picked up: Sword");
            case 4 -> System.out.println("You picked up: Dagger");
            default -> System.out.println("You wander around empty-handed.");
        }

        // Trivia Question
        String question = "What is the capital of France?";
        String answer = "Paris";
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
