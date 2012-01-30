import javax.swing.SwingUtilities;


public class Tortuga {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		@SuppressWarnings("unused")
		MainWindow f= new MainWindow();
            }
        });
	}

}
