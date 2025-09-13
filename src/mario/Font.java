package mario;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Font {
	public int pos_x;
	public int pos_y;
	public String text;
	protected BufferedImage img;
	protected BufferedImage img2;
	protected BufferedImage[] rotate_img = new BufferedImage[32+6*16];
	protected BufferedImage mario_img;
	protected BufferedImage[] text_img = new BufferedImage[32+6*16];
	private int width = 16, height = 16;
	public static String file = "font.png";
	public static String mario_file = "Mario_Standing.gif";
	public static String rotate_file = "fontRotate.png";
	public Font(int pos_x, int pos_y, String text) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.text = text;
		try {
			 if(img == null) {
				 img = ImageIO.read(new File(file));
			 }
			 if(mario_img == null) {
				 mario_img = ImageIO.read(new File(mario_file));
			 }
			 if(img2 == null) {
				 img2 = ImageIO.read(new File(rotate_file));
			 }
		} catch (IOException e) {
		     System.out.println("Internal Error:" + e.getMessage());
		}
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 16; j++) {
				text_img[32+i*16+j] = img.getSubimage(
			            j * width,
			            i * height,
			            width,
			            height
			        );
			}
		}
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 6; j++) {
				rotate_img[32+i*6+j] = img2.getSubimage(
			            j * width,
			            i * height,
			            width,
			            height
			        );
			}
		}
	}
	public void draw(Graphics g) {
		for(int i = 0; i < text.length(); i++) {
			if((int)text.charAt(i)>=32 &&(int)text.charAt(i) < 128)
				g.drawImage(text_img[(int)text.charAt(i)], pos_x+width*i, pos_y, width, height, null);
		}
	}
	public void drawMario(Graphics g) {
		g.drawImage(mario_img, pos_x, pos_y, width, height, null);
	}
	public void drawRotate(Graphics g) {
		for(int i = 0; i < text.length(); i++) {
			if((int)text.charAt(i)>=32 &&(int)text.charAt(i) < 128)
				g.drawImage(rotate_img[(int)text.charAt(i)], pos_x+width*i, pos_y, width, height, null);
		}
	}
}
