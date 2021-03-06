/**
 * Created by Jun Yuan on 2016-03-07.
 */


/**
 * this class defines points in 3D space and 3D vectors from the origin
 */
public class Point3D {
   private double x;
   private double y;
   private double z;

   /**
    *  constructs a 3 dimensional point
    * @param X x coordinate
    * @param Y y coordinate
    * @param Z z coordinate
    */
   Point3D(double X, double Y, double Z) {
      this.x = X;
      this.y = Y;
      this.z = Z;
   }

   // setters and getters
   public void setX(double X) {
      this.x = X;
   }
   public void setY(double Y) {
      this.y = Y;
   }
   public void setZ(double Z) {
      this.z = Z;
   }
   public double getX() {
      return this.x;
   }
   public double getY() {
      return this.y;
   }
   public double getZ() {
      return this.z;
   }

   /**
    * calculates distance of point from origin
    * @return 3 dimensional point
    */
   double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   /**
    * returns scalar (dot product) of this vector and argument vector
    * @param p
    * @return 3 dimensional point
    */
   double dot(Point3D p) {
      return this.getX() * p.getX() + this.getY() * p.getY() + this.getZ() * p.getZ();
   }

   /**
    * returns cross product this vector and argument vector
    * @param p
    * @return 3 dimensional point
    */
   Point3D cross(Point3D p) {
      return new Point3D(this.getY() * p.getZ() - this.getZ() * p.getY(), this.getZ() * p.getX() - this.getX() * p.getZ(), this.getX() * p.getY() - this.getY() * p.getX());
   }


   /**
    * returns unit vector
    * @return 3 dimensional point
    */
   Point3D unitVector() {
      return new Point3D(this.getX()/this.length(), this.getY()/this.length(), this.getZ()/this.length());
   }


   /**
    * returns vector from argument
    * @param p 3 dimensional point
    * @return  3 dimensional point
    */
   Point3D vectorFrom(Point3D p) {
      return new Point3D(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
   }


   /**
    * convert 3 dimensional point to 2 dimensional
    * @return 2 dimensional point
    */
   PlanePoint convertTo2D() {
      return new PlanePoint(((this.x - (CubeComponent.eyePoint.getX())) * CubeComponent.FOCAL_LENGTH/this.z) + (CubeComponent.eyePoint.getX()), ((this.y - (CubeComponent.eyePoint.getY())) * CubeComponent.FOCAL_LENGTH/this.z) + (CubeComponent.eyePoint.getY()));
   }

   /**
    * returns distance of this point from argument
    * @param p 3 dimensional point
    * @return 3 dimensional point
    */
   double distance(Point3D p) {
      return Math.sqrt((this.getX() - p.getX()) * (this.getX() - p.getX()) + (this.getY() - p.getY()) * (this.getY() - p.getY()) + (this.getZ() - p.getZ()) * (this.getZ() - p.getZ()));
   }
}