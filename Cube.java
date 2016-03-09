/**
 * Created by Jun Yuan on 2016-03-07.
 */
import java.awt.*;


public class Cube {

   private double cubeLength; // Length of each edge of the cube
   private PlanePoint[] vertexArray2D = new PlanePoint[8]; // Vertices of cube in plane of screen
   private Boolean[] visibleVertices = new Boolean[8]; // Which of the 8 vertices can be seen?
   private int[] xPoints = new int[4]; // Stores x and y coordinates of the
   private int[] yPoints = new int[4]; // polygon (representing a face) to be drawn

   /**
    * construct a cube
    * @param length edge length of cube
    */
   public Cube(int length) {
      this.cubeLength = length;
   }

   public double getCubeLength() {
      return cubeLength;
   }

   public void setCubeLength(int cubeLength) {
      this.cubeLength = cubeLength;
   }

   public PlanePoint getVertexArray2D(int i) {
      return vertexArray2D[i];
   }

   public void setVertexArray2D(PlanePoint p, int i) {
      this.vertexArray2D[i] = p;
   }

   public Boolean getVisibleVertices(int i) {
      return visibleVertices[i];
   }

   public void setVisibleVertices(Boolean[] visibleVertices) {
      this.visibleVertices = visibleVertices;
   }

   public int getxPoints(int i) {
      return xPoints[i];
   }

   public void setxPoints(int p, int i) {
      this.xPoints[i] = p;
   }

   public int getyPoints(int i) {
      return yPoints[i];
   }

   public void setyPoints(int p, int i) {
      this.yPoints[i] = p;
   }

   public void draw(Graphics g) {

      // Draws face 0
      if (visibleVertices[0] && visibleVertices[1] && visibleVertices[2] && visibleVertices[3]) {
         g.setColor(Color.black);
         for (int i = 0; i <= 3; i++) {
            xPoints[i] = (int) vertexArray2D[i].getX();
            yPoints[i] = (int) vertexArray2D[i].getY();
         }
         g.fillPolygon(xPoints, yPoints, 4);
         g.setColor(Color.magenta);
         g.drawPolygon(xPoints, yPoints, 4);
      }

      // Draws face 1
      if (visibleVertices[4] && visibleVertices[5] && visibleVertices[6] && visibleVertices[7]) {
         g.setColor(Color.black);
         for (int i = 4; i <= 7; i++) {
            xPoints[i - 4] = (int) vertexArray2D[i].getX();
            yPoints[i - 4] = (int) vertexArray2D[i].getY();
         }
         g.fillPolygon(xPoints, yPoints, 4);
         g.setColor(Color.magenta);
         g.drawPolygon(xPoints, yPoints, 4);
      }

      // Draws face 2
      if (visibleVertices[0] && visibleVertices[1] && visibleVertices[5] && visibleVertices[4]) {
         g.setColor(Color.black);
         xPoints[0] = (int) vertexArray2D[0].getX();
         yPoints[0] = (int) vertexArray2D[0].getY();
         xPoints[1] = (int) vertexArray2D[1].getX();
         yPoints[1] = (int) vertexArray2D[1].getY();
         xPoints[2] = (int) vertexArray2D[5].getX();
         yPoints[2] = (int) vertexArray2D[5].getY();
         xPoints[3] = (int) vertexArray2D[4].getX();
         yPoints[3] = (int) vertexArray2D[4].getY();
         g.fillPolygon(xPoints, yPoints, 4);
         g.setColor(Color.magenta);
         g.drawPolygon(xPoints, yPoints, 4);
      }

      // Draws face 3
      if (visibleVertices[2] && visibleVertices[3] && visibleVertices[7] && visibleVertices[6]) {
         g.setColor(Color.black);
         xPoints[0] = (int) vertexArray2D[2].getX();
         yPoints[0] = (int) vertexArray2D[2].getY();
         xPoints[1] = (int) vertexArray2D[6].getX();
         yPoints[1] = (int) vertexArray2D[6].getY();
         xPoints[2] = (int) vertexArray2D[7].getX();
         yPoints[2] = (int) vertexArray2D[7].getY();
         xPoints[3] = (int) vertexArray2D[3].getX();
         yPoints[3] = (int) vertexArray2D[3].getY();
         g.fillPolygon(xPoints, yPoints, 4);
         g.setColor(Color.magenta);
         g.drawPolygon(xPoints, yPoints, 4);
      }

      //Draws face 4
      if (visibleVertices[1] && visibleVertices[2] && visibleVertices[6] && visibleVertices[5]) {
         g.setColor(Color.black);
         xPoints[0] = (int) vertexArray2D[1].getX();
         yPoints[0] = (int) vertexArray2D[1].getY();
         xPoints[1] = (int) vertexArray2D[2].getX();
         yPoints[1] = (int) vertexArray2D[2].getY();
         xPoints[2] = (int) vertexArray2D[6].getX();
         yPoints[2] = (int) vertexArray2D[6].getY();
         xPoints[3] = (int) vertexArray2D[5].getX();
         yPoints[3] = (int) vertexArray2D[5].getY();
         g.fillPolygon(xPoints, yPoints, 4);
         g.setColor(Color.magenta);
         g.drawPolygon(xPoints, yPoints, 4);
      }

      //Draws face 5
      if (visibleVertices[0] && visibleVertices[3] && visibleVertices[7] && visibleVertices[4]) {
         g.setColor(Color.black);
         xPoints[0] = (int) vertexArray2D[0].getX();
         yPoints[0] = (int) vertexArray2D[0].getY();
         xPoints[1] = (int) vertexArray2D[3].getX();
         yPoints[1] = (int) vertexArray2D[3].getY();
         xPoints[2] = (int) vertexArray2D[7].getX();
         yPoints[2] = (int) vertexArray2D[7].getY();
         xPoints[3] = (int) vertexArray2D[4].getX();
         yPoints[3] = (int) vertexArray2D[4].getY();
         g.fillPolygon(xPoints, yPoints, 4);
         g.setColor(Color.magenta);
         g.drawPolygon(xPoints, yPoints, 4);
      }
   }
}
