/**
 * Created by Jun Yuan on 2016-03-07.
 */
import javax.swing.*;

/**
 * This class use JFrame to graphically display the JComponent
 */
public class FrameViewer extends JFrame {

   public static final int WIDTH = 1100;
   public static final int HEIGHT = 800;

   public static void main(String[] args) {
      JFrame fr = new FrameViewer();
      fr.setTitle("Parallax Cube");
      fr.add(new CubeComponent());
      fr.setSize(WIDTH, HEIGHT);
      fr.setVisible(true);
      fr.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
}
