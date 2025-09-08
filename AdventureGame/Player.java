package AdventureGame;

public class Player {
    private String name;
    private int score;

    // Overloaded constructors
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public Player(String name, int startingScore) {
        this.name = name;
        this.score = startingScore;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        this.score += points;
    }
}
