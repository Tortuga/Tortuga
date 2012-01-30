import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Tortue {
	
	protected Image img;
	Point pos;
	
	public Tortue(Point position){
		this.pos=position;
		try {
			img=ImageIO.read(new File("images/tortue.retourner.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point position) {
		this.pos = position;
	}

	public Image getImg() {
		return img;
	}
	
}
