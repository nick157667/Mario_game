package mario;

public class GreenKoopaTroop extends Enemy {
    
    private static String[] img_files_lt = {"Green_Koopa_Troopa.gif", "Green_Koopa_Troopa.gif",
            "Green_Koopa_Troopa.gif", "Green_Koopa_Troopa2.gif", "Green_Koopa_Troopa2.gif",
            "Green_Koopa_Troopa2.gif"};
    private static String dead_file = "Green_Koopa_Troopa_dead.png";
    
    public static int INIT_WIDTH = 32;
    public static int INIT_HEIGHT = 48;
    
    public GreenKoopaTroop(int courtWidth, int courtHeight, int startDistance, String label) {
        super(courtWidth, //int courtWidth
        		courtHeight, //int courtHeight
        		INIT_WIDTH, //int initWidth
        		INIT_HEIGHT, //int initHeight
        		startDistance, //int startDistance
        		img_files_lt, //String[] img_files
        		dead_file, 
        		label); 
    }

    
    @Override
    public int incrementScore(int score) {
        return score += 200;
    }
}
