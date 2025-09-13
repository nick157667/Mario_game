package mario;
public class HighScore implements Comparable<HighScore> {
    private int score;
    private String username;
    
    public HighScore(String username, int score) {
        this.username = username;
        this.score = score;
    }
    
    @Override
    public int compareTo(HighScore o) {
        if (score < o.getScore()) return -1;
        if (score == o.getScore()) return 0;
        else return 1;
    }
    
    public int getScore() {
        return score;
    }
    
    public String getUsername() {
        return username;
    }
    
}
