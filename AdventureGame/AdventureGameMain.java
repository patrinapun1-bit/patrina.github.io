package AdventureGame;

import java.util.Scanner;

public class AdventureGameMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("----------------------- Welcome to the Adventure Game -----------------------");
        System.out.print("\nHello Traveller! What is your name? \n> ");
        String name = input.nextLine();

        Player player = new Player(name);
        System.out.println("\nHello " + player.getName() + "! You are a young adventurer, both ambitious and courageous!");
        System.out.println("\nYour mission: navigate through the Beach, Forest, and Underwater Cave.");
        System.out.println("Answer trivia, collect items, and accumulate points to win!");
        System.out.println("---------------------------------------------------------------------------");

        // Enter each area
        new Beach().enter(player);
        new Forest().enter(player);
        new UnderwaterCave().enter(player);

        // Final result
        System.out.println("\n----------------- Adventure Completed! -----------------");
        System.out.println("Your total points: " + player.getScore());

        if (player.getScore() >= 100) {
            System.out.println("Congratulations " + player.getName() + "! You WIN!");
        } else {
            System.out.println("Sorry " + player.getName() + ". You LOSE!");
        }

        input.close();
    }
}
