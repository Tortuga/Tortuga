import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TortueVerte extends Tortue {
	
	
	public TortueVerte(Point position) {
		super(position);
		try {
			img=ImageIO.read(new File("images/tortue.vert.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
