package mario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class HighScores {
    
    private static String file_name = "highscores.txt";
    private static List<HighScore> highScores;
    
    private FileReader fr;
    private FileWriter fw;
    private BufferedReader in;
    private BufferedWriter out;
    
    
    public HighScores() {
        highScores = new LinkedList<HighScore>();
        try {
            fr = new FileReader(file_name);
            in = new BufferedReader(fr);
            String newScore = in.readLine();
            while (newScore != null) {
                int space = newScore.indexOf(" ");
                String user = newScore.substring(0, space);
                int score = Integer.parseInt(newScore.substring(space + 1));
                highScores.add(new HighScore(user, score));
                newScore = in.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } finally {
            try {
                if (fr != null) fr.close();
                if (in != null) in.close();
            } catch (IOException e2) {
                System.out.println("Internal Error:" + e2.getMessage());
            }
        }
    }
    
    public List<HighScore> getHighScores() {
        List<HighScore> scores = new LinkedList<HighScore>();
        for (HighScore h : highScores) {
            scores.add(h);
        }
        Collections.sort(scores);
        Collections.reverse(scores);
        return scores;
    }
    
    public void addHighScore(HighScore hs) {
        try {
            fw = new FileWriter(file_name, true);
            out = new BufferedWriter(fw);
            if (hs != null) {
                highScores.add(hs);
                out.write(hs.getUsername() + " " + hs.getScore() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        } finally {
            try {
                if (out != null) out.close();
                if (fw != null) fw.close();
            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }
        }
    }
    
    public List<HighScore> getTopTenHighScores() {
        List<HighScore> scores = new LinkedList<HighScore>();
        for (HighScore h : highScores) {
            scores.add(h);
        }
        Collections.sort(scores);
        Collections.reverse(scores);
        if (scores.size() > 10) return scores.subList(0, 10);
        else return scores;
    }
}
