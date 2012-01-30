import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TortueRouge extends Tortue {

	public TortueRouge(Point position) {
		super(position);
		try {
			img=ImageIO.read(new File("images/tortue.rouge.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
