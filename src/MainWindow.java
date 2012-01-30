import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;


public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow() throws HeadlessException {
		super();
		Plateau plateau = new  Plateau();
		this.setTitle("Tortuga");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setUndecorated(false);
		//this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		this.add(plateau,BorderLayout.CENTER);
		this.setVisible(true);
	}

}
