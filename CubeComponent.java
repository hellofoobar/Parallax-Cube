/**
 * Created by Jun Yuan on 2016-03-07.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import javax.swing.event.*;

/**
 * This class
 */
public class CubeComponent extends JComponent implements MouseInputListener, MouseWheelListener, ComponentListener {

   Cube cube = new Cube(250);

   public static boolean isBounceMode = true;
   public Ellipse2D modeSelection = new Ellipse2D.Double(60, 60, 35, 35);

   public static final double FOCAL_LENGTH = 2200; // Distance from viewer to screen in pixels
   public static int windowWidth = FrameViewer.WIDTH;
   public static int windowHeight = FrameViewer.HEIGHT;
   public static Point3D eyePoint = new Point3D(windowWidth/2, windowHeight/2, 0); // Coordinates in 3D space of user's eye


   public double sphereRad = Math.sqrt(cube.getCubeLength() * cube.getCubeLength() * 3 / 4); // Radius of a sphere centred in the middle of the cube passing through each vertex
   public double startX = windowWidth/2 - cube.getCubeLength() /2; // The initial 3D coordinates
   public double startY = windowHeight/2 - cube.getCubeLength() /2; // of the vertex beginning
   public int startZ = 2500; // top-left of picture.
   public int clickedVertex = -1; // Index from -1 to 7 indicating which vertex has just been clicked (-1 indicates no vertex)
   public int oldX; // Stores the prior coordinates of a
   public int oldY; // vertex before a mouse movement
   public boolean isOutOfBounds; // Has the mouse pointer been dragged out of the rotatable area?


   public Point3D[] vertexArray3D = new Point3D[8]; // Vertices of cube in 3D space
//   public static PlanePoint[] vertexArray2D = new PlanePoint[8]; // Vertices of cube in plane of screen
//   public Boolean[] visibleVertices = new Boolean[8]; // Which of the 8 vertices can be seen?
//   public int[] xPoints = new int[4]; // Stores x and y coordinates of the
//   public int[] yPoints = new int[4]; // polygon (representing a face) to be drawn


   /**
    * constructor for event listeners, and cube's vertex coordinates
    */
   CubeComponent() {
      super();
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addMouseWheelListener(this);
      this.addComponentListener(this);
      vertexArray3D[0] = new Point3D(startX, startY, startZ);
      vertexArray3D[1] = new Point3D(startX, startY + cube.getCubeLength(), startZ);
      vertexArray3D[2] = new Point3D(startX + cube.getCubeLength(), startY + cube.getCubeLength(), startZ);
      vertexArray3D[3] = new Point3D(startX + cube.getCubeLength(), startY, startZ);
      vertexArray3D[4] = new Point3D(startX, startY, startZ + cube.getCubeLength());
      vertexArray3D[5] = new Point3D(startX, startY + cube.getCubeLength(), startZ + cube.getCubeLength());
      vertexArray3D[6] = new Point3D(startX + cube.getCubeLength(), startY + cube.getCubeLength(), startZ + cube.getCubeLength());
      vertexArray3D[7] = new Point3D(startX + cube.getCubeLength(), startY, startZ + cube.getCubeLength());
   }

   double velocityX = 4;
   double velocityY = 4;

   @Override
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;

      // Converts the vertex coordinates to 2D points
      for (int i = 0; i <= 7; i++) {
         //cube.vertexArray2D[i] = vertexArray3D[i].convertTo2D();
         cube.setVertexArray2D(vertexArray3D[i].convertTo2D(), i);
      }
      // Calculates which vertices are visible
      //cube.visibleVertices = isVisible(vertexArray3D);
      cube.setVisibleVertices(isVisible(vertexArray3D));

      if (isBounceMode) {

         cube.draw(g);
         bounce();
         cube.draw(g);

         g.setColor(Color.MAGENTA);
         g2.fill(modeSelection);
         g.setColor(Color.black);
//         g.setFont(new Font("HonMincho", 0, 18));
//         g.drawString("Click dot to enter Translate Mode", 15, 60);

      } else {

         cube.draw(g);

         g.setColor(Color.MAGENTA);
         g2.fill(modeSelection);
         g.setColor(Color.black);
//         g.setFont(new Font("HonMincho", 0, 18));
//         g.drawString("Click dot to enter Bounce Mode", 15, 60);
//         g.setFont(new Font("HonMincho", 0, 18));
//         g.drawString("Click anywhere and start dragging the cube", getWidth()/2 - 200, 60);
      }
   }

   /**
    * Calculates visibilities of cube vertices
    * @param arr the array of cube vertices
    * @return array of visible vertices as boolean
    */
   Boolean[] isVisible(Point3D[] arr) {
      Boolean[] array = new Boolean[8];

      for (int i = 0; i <= 7; i++) {
         Point3D eyeVector = new Point3D(eyePoint.getX() - arr[i].getX(), eyePoint.getY() - arr[i].getY(), eyePoint.getZ() - arr[i].getZ());
         double length = eyeVector.length();
         Point3D alongVector = new Point3D(arr[i].getX() + eyeVector.getX() / length * cube.getCubeLength(), arr[i].getY() + eyeVector.getY() / length * cube.getCubeLength(), arr[i].getZ() + eyeVector.getZ() / length * cube.getCubeLength());
         Point3D vector1 = new Point3D(alongVector.getX() - vertexArray3D[1].getX(), alongVector.getY() - vertexArray3D[1].getY(), alongVector.getZ() - vertexArray3D[1].getZ());
         Point3D vector2 = new Point3D(alongVector.getX() - vertexArray3D[7].getX(), alongVector.getY() - vertexArray3D[7].getY(), alongVector.getZ() - vertexArray3D[7].getZ());
         if (vector1.getX() * (vertexArray3D[5].getX() - vertexArray3D[1].getX()) + vector1.getY() * (vertexArray3D[5].getY() - vertexArray3D[1].getY()) + vector1.getZ() * (vertexArray3D[5].getZ() - vertexArray3D[1].getZ()) > 0
                 &&  vector1.getX() * (vertexArray3D[0].getX() - vertexArray3D[1].getX()) + vector1.getY() * (vertexArray3D[0].getY() - vertexArray3D[1].getY()) + vector1.getZ() * (vertexArray3D[0].getZ() - vertexArray3D[1].getZ()) > 0
                 &&  vector1.getX() * (vertexArray3D[2].getX() - vertexArray3D[1].getX()) + vector1.getY() * (vertexArray3D[2].getY() - vertexArray3D[1].getY()) + vector1.getZ() * (vertexArray3D[2].getZ() - vertexArray3D[1].getZ()) > 0
                 &&  vector2.getX() * (vertexArray3D[3].getX() - vertexArray3D[7].getX()) + vector2.getY() * (vertexArray3D[3].getY() - vertexArray3D[7].getY()) + vector2.getZ() * (vertexArray3D[3].getZ() - vertexArray3D[7].getZ()) > 0
                 &&  vector2.getX() * (vertexArray3D[4].getX() - vertexArray3D[7].getX()) + vector2.getY() * (vertexArray3D[4].getY() - vertexArray3D[7].getY()) + vector2.getZ() * (vertexArray3D[4].getZ() - vertexArray3D[7].getZ()) > 0
                 &&  vector2.getX() * (vertexArray3D[6].getX() - vertexArray3D[7].getX()) + vector2.getY() * (vertexArray3D[6].getY() - vertexArray3D[7].getY()) + vector2.getZ() * (vertexArray3D[6].getZ() - vertexArray3D[7].getZ()) > 0
                 ) {
            array[i] = false;
         } else {
            array[i] = true;
         }
      }
      return array;
   }

   /**
    * gets centre point of cube
    * @return an object of Point3D
    */
   Point3D centrePoint() {
      Point3D vector = vertexArray3D[0].vectorFrom(vertexArray3D[6]);
      return new Point3D(vertexArray3D[6].getX() + vector.getX()/2, vertexArray3D[6].getY() + vector.getY()/2, vertexArray3D[6].getZ() + vector.getZ()/2);
   }

   // Given points a and b, vector r and an angle, returns
// the new position of a if the line joining a and b
// is rotated through the angle about b in a plane
// perpendicular to r
   Point3D rotate(Point3D a, Point3D b, Point3D r, double angle) {
      Point3D bToa = a.vectorFrom(b);
      Point3D v = r.cross(bToa);
      Point3D p = new Point3D(Math.cos(angle) * bToa.getX() + Math.sin(angle) * v.getX(), Math.cos(angle) * bToa.getY() + Math.sin(angle) * v.getY(), Math.cos(angle) * bToa.getZ() + Math.sin(angle) * v.getZ());
      return new Point3D(b.getX() + p.getX(), b.getY() + p.getY(), b.getZ() + p.getZ());
   }

   // Calculates where the user's eye should be according
// to the current window size (called after a resize)
   void setEyePoint() {
      eyePoint = new Point3D(this.getWidth()/2, this.getHeight()/2, 0);
   }

   // Required empty methods for MouseInputListener,
// MouseWheelListener and ComponentListener
   public void mouseClicked(MouseEvent e) {
      if (modeSelection.contains(e.getX(), e.getY())) {
         isBounceMode = !isBounceMode;
         repaint();
      }
   }
   public void mouseEntered(MouseEvent e) {
   }
   public void mouseExited(MouseEvent e) {
   }
   public void mouseReleased(MouseEvent event) {
   }
   public void mouseMoved(MouseEvent event) {
   }
   public void componentShown(ComponentEvent e) {
   }
   public void componentHidden(ComponentEvent e) {
   }
   public void componentMoved(ComponentEvent e) {
   }

   // When mouse is pressed before a drag, this method
// stores the coordinates of the mouse pointer and
// calculates which vertex the press is near to,
// if any
   public void mousePressed(MouseEvent e) {
      oldX = e.getX();
      oldY = e.getY();
      isOutOfBounds = false;
      clickedVertex = -1;
      for (int i = 0; i <= 7; i++) {
         if (cube.getVisibleVertices(i) && new PlanePoint(e.getX(), e.getY()).distance(cube.getVertexArray2D(i)) < 20) {
            clickedVertex = i;
         }
      }
   }

   /**
    *
    */
   public void bounce() {
      ActionListener listener = new TimerListener();
      final int DELAY = 10;
      Timer t = new Timer(DELAY, listener);
      t.start();

      for (int i = 0; i <= 7; i++) {
         // Calculate new position
         double x = vertexArray3D[i].getX();
         double y = vertexArray3D[i].getY();
         vertexArray3D[i].setX(x + velocityX);
         vertexArray3D[i].setY(y + velocityY);
      }

      // Check if moves over the bounds. If so, adjust the position and speed.
      if (vertexArray3D[0].getX() < this.getX() || vertexArray3D[1].getX() < this.getX() || vertexArray3D[4].getX() < this.getX() || vertexArray3D[5].getX() < this.getX()) {
         velocityX = -velocityX; // Reflect along normal
         //vertexArray3D[i].setX(this.getX());     // Re-position the ball at the edge
         vertexArray3D[0].setX(this.getX());     // Re-position the ball at the edge
         vertexArray3D[1].setX(this.getX());     // Re-position the ball at the edge
         vertexArray3D[4].setX(this.getX());     // Re-position the ball at the edge
         vertexArray3D[5].setX(this.getX());     // Re-position the ball at the edge

         vertexArray3D[2].setX(this.getX() + cube.getCubeLength());     // Re-position the ball at the edge
         vertexArray3D[3].setX(this.getX() + cube.getCubeLength());     // Re-position the ball at the edge
         vertexArray3D[6].setX(this.getX() + cube.getCubeLength());     // Re-position the ball at the edge
         vertexArray3D[7].setX(this.getX() + cube.getCubeLength());     // Re-position the ball at the edge

      } else if (vertexArray3D[2].getX() > this.getWidth() || vertexArray3D[3].getX() > this.getWidth() || vertexArray3D[6].getX() > this.getWidth() || vertexArray3D[7].getX() > this.getWidth()) {
         velocityX = -velocityX;
         //vertexArray3D[i].setX(this.getWidth());
         vertexArray3D[2].setX(this.getWidth());     // Re-position the ball at the edge
         vertexArray3D[3].setX(this.getWidth());     // Re-position the ball at the edge
         vertexArray3D[6].setX(this.getWidth());     // Re-position the ball at the edge
         vertexArray3D[7].setX(this.getWidth());     // Re-position the ball at the edge

         vertexArray3D[0].setX(this.getWidth() - cube.getCubeLength());     // Re-position the ball at the edge
         vertexArray3D[1].setX(this.getWidth() - cube.getCubeLength());     // Re-position the ball at the edge
         vertexArray3D[4].setX(this.getWidth() - cube.getCubeLength());     // Re-position the ball at the edge
         vertexArray3D[5].setX(this.getWidth() - cube.getCubeLength());     // Re-position the ball at the edge
      }
      // May cross both x and y bounds
      if (vertexArray3D[0].getY() < this.getY() || vertexArray3D[3].getY() < this.getY() || vertexArray3D[4].getY() < this.getY() || vertexArray3D[7].getY() < this.getY()) {
         velocityY = -velocityY;
         //vertexArray3D[i].setY(this.getY());
         vertexArray3D[0].setY(this.getY());
         vertexArray3D[3].setY(this.getY());
         vertexArray3D[4].setY(this.getY());
         vertexArray3D[7].setY(this.getY());

         vertexArray3D[1].setY(this.getY() + cube.getCubeLength());
         vertexArray3D[2].setY(this.getY() + cube.getCubeLength());
         vertexArray3D[5].setY(this.getY() + cube.getCubeLength());
         vertexArray3D[6].setY(this.getY() + cube.getCubeLength());

      } else if (vertexArray3D[1].getY() > this.getHeight() || vertexArray3D[2].getY() > this.getHeight() || vertexArray3D[5].getY() > this.getHeight() || vertexArray3D[6].getY() > this.getHeight()) {
         velocityY = -velocityY;
         //vertexArray3D[i].setY(this.getHeight());
         vertexArray3D[1].setY(this.getHeight());
         vertexArray3D[2].setY(this.getHeight());
         vertexArray3D[5].setY(this.getHeight());
         vertexArray3D[6].setY(this.getHeight());

         vertexArray3D[0].setY(this.getHeight() - cube.getCubeLength());
         vertexArray3D[3].setY(this.getHeight() - cube.getCubeLength());
         vertexArray3D[4].setY(this.getHeight() - cube.getCubeLength());
         vertexArray3D[7].setY(this.getHeight() - cube.getCubeLength());
      }
   }

   // Deals with a mouse drag
   public void mouseDragged(MouseEvent e) {
      boolean front = true;
// if drag is not from a vertex, cube is translated...
      if (clickedVertex == -1) {
         for (int i = 0; i <= 7; i++) {
            vertexArray3D[i].setX(vertexArray3D[i].getX() + (e.getX() - oldX));
            vertexArray3D[i].setY(vertexArray3D[i].getY() + (e.getY() - oldY));
         }
         this.repaint();
      }
// ...otherwise it's rotated
      else {
// Tests if the vertex is at the front or back of the cube
         if (clickedVertex >= 0) {
            Point3D test = inSphere(cube.getVertexArray2D(clickedVertex).getX(), cube.getVertexArray2D(clickedVertex).getY(), true);
            if (test != null) {
               if (test.distance(vertexArray3D[clickedVertex]) < 1) {
                  front = true;
               } else {
                  front = false;
               }
            }
         }
         Point3D moveTo = inSphere(e.getX(), e.getY(), front);
         if (clickedVertex >= 0 && moveTo != null && !isOutOfBounds) {
            Point3D c = centrePoint();
            Point3D current = vertexArray3D[clickedVertex];
            Point3D r = current.vectorFrom(centrePoint()).cross(moveTo.vectorFrom(centrePoint()));
            double angle = Math.asin(r.length() / (current.vectorFrom(centrePoint()).length() * moveTo.vectorFrom(centrePoint()).length()));
            r = r.unitVector();
            vertexArray3D[clickedVertex] = moveTo;
            for (int i = 0; i <= 7; i++) {
               if (i != clickedVertex) {
                  double s = r.dot(vertexArray3D[i]);
                  double a = s - (r.getX() * c.getX() + r.getY() * c.getY() + r.getZ() * c.getZ());
                  Point3D b = new Point3D(c.getX() + a * r.getX(), c.getY() + a * r.getY(), c.getZ() + a * r.getZ());
                  vertexArray3D[i] = rotate(vertexArray3D[i], b, r, angle);
               }
            }
            this.repaint();
         }
      }
      oldX = e.getX(); // Stores the current pointer
      oldY = e.getY(); // coordinates ready for the next drag
   }

   // Translates the cube towards or away from user
// according to rolling the mouse wheel
   public void mouseWheelMoved(MouseWheelEvent e) {
      int n = e.getWheelRotation();
// if statement checks that the cube is within a certain distance range
      if ((vertexArray3D[0].getZ() > sphereRad * 2 + 500 || n < 0) && (cube.getCubeLength() / vertexArray3D[0].getZ() > 0.01 || n > 0)) {
         for (int i = 0; i <= 7; i++) {
            vertexArray3D[i].setZ(vertexArray3D[i].getZ() - 250 * n);
         }
         this.repaint();
      }
   }

   // If a given plane point with coordinates (x,y) corresponds
// to a 3D point in the rotatable region of the cube (a sphere), this
// method returns that point.  Otherwise it returns null.  It finds
// the point by using vector equations and solving a quadratic
   Point3D inSphere(double x, double y, boolean front) {
      double sol1, sol2, sol;
      Point3D p3D = new Point3D(x, y, FOCAL_LENGTH);
      Point3D v = p3D.vectorFrom(eyePoint);
      Point3D c = centrePoint();
      double A = v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ();
      double B = 2 * (v.getX() * (eyePoint.getX() - c.getX()) + v.getY() * (eyePoint.getY() - c.getY()) + v.getZ() * (eyePoint.getZ() - c.getZ()));
      double C = eyePoint.getX() * eyePoint.getX() + c.getX() * c.getX() - 2 * eyePoint.getX() * c.getX() + eyePoint.getY() * eyePoint.getY() + c.getY() * c.getY() - 2 * eyePoint.getY() * c.getY() + eyePoint.getZ() * eyePoint.getZ() + c.getZ() * c.getZ() - 2 * eyePoint.getZ() * c.getZ() - sphereRad * sphereRad;
      if (B * B - 4 * A * C >= 0) {
         sol1 = (-B + Math.sqrt(B * B - 4 * A * C)) / (2 * A);
         sol2 = (-B - Math.sqrt(B * B - 4 * A * C)) / (2 * A);
      } else {
         isOutOfBounds = true;
         return null;
      }
      if (front) {
         sol = Math.min(sol1, sol2);
      } else {
         sol = Math.max(sol1, sol2);
      }
      return new Point3D(eyePoint.getX() + sol * v.getX(), eyePoint.getY() + sol * v.getY(), eyePoint.getZ() + sol * v.getZ());
   }

   // After a resize, repositions the cube accordingly
// and set's the user's eyePoint to the middle of the window
   public void componentResized(ComponentEvent e) {
      setEyePoint();
      Point3D c = centrePoint();
      double centreRatioX = c.getX() / windowWidth;
      double centreRatioY = c.getY() / windowHeight;
      Point3D newCentre = new Point3D(this.getWidth() * centreRatioX, this.getHeight() * centreRatioY, c.getZ());
      for (int i = 0; i <= 7; i++) {
         vertexArray3D[i] = new Point3D(newCentre.getX() + (vertexArray3D[i].getX() - c.getX()), newCentre.getY() + (vertexArray3D[i].getY() - c.getY()), vertexArray3D[i].getZ());
      }
      windowWidth = this.getWidth();
      windowHeight = this.getHeight();
      this.repaint();
   }

   /**
    * returns original size of the JComponent
    */
   public Dimension getPreferredSize() {
      return new Dimension(windowWidth, windowHeight);
   }

   /**
    * inner class to listen for event changes
    */
   class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         repaint();
      }
   }
}