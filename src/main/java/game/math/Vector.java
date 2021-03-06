package game.math;


import org.lwjgl.opengl.GL11;

public class Vector {


  double v[] = new double[4];
  
  public static final Vector U1   = new Vector(1, 0, 0, 1);
  public static final Vector U2   = new Vector(0, 1, 0, 1);
  public static final Vector U3   = new Vector(0, 0, 1, 1);
  public static final Vector ZERO = new Vector(0, 0, 0, 1);
  public static final Vector ONES = new Vector(1, 1, 1, 1);
  
  public Vector(double x1, double x2, double x3, double x4) {
    v[0] = x1;
    v[1] = x2;
    v[2] = x3;
    v[3] = x4;
  }
  
  public Vector() {
  }

  public Vector(Vector o) {
    for(int i=0;i<4;++i) {
      v[i] = o.v[i];
    }
  }

  public Vector times(double s) {
    return new Vector(v[0], v[1], v[2], v[3] / s);
  }
  
  public Vector project() {
    return new Vector(v[0]/v[3], v[1]/v[3], v[2]/v[3], 1.0);
  }
  
  public Vector normalize() {
    return new Vector(v[0], v[1], v[2], unscaledLength());
  }

  public double unscaledLength() {
    return Math.sqrt(unscaledLengthSquared());
  }

  public double unscaledLengthSquared() {
    return v[0]*v[0] + v[1]*v[1] + v[2]*v[2];
  }
  
  public double dot(Vector o) {
    return (v[0] * o.v[0] + v[1] * o.v[1] + v[2] * o.v[2]) / (v[3] * o.v[3]);
  }
  
  public double length() {
    return unscaledLength() / v[3];
  }
  
  public Vector cross(Vector o) {
    return Matrix.skew(this).times(o);
  }

  public String toString() {
    StringBuffer b = new StringBuffer();
    b.append("[ ");
    for(int i=0;i<4;++i) {
      b.append(String.format("%2.2f", v[i]));
      if ( i != 3 ) {
        b.append(", ");
      }
    }
    b.append(" ]");
    return b.toString();
  }

  public boolean withinDelta(Vector o, double delta) {
    for(int i=0;i<3;++i) {
      if ( Math.abs(v[i]/v[4] - o.v[i]/o.v[4]) > delta ) {
        return false;
      }
    }
    return true;
  }

  public Vector minus() {
    return times(-1);
  }
  
  public void glTranslate() {
    GL11.glTranslated(v[0]/v[3], v[1]/v[3], v[2]/v[3]);
  }

  public void glRotate(double theta) {
    GL11.glRotated(theta * 180 / Math.PI , v[0]/v[3], v[1]/v[3], v[2]/v[3]);
  }

  public Vector times(Matrix m) {
    Vector r = new Vector();
    for(int j=0;j<4;++j) {
      for(int k=0;k<4;++k) {
        r.v[j] += v[k] * m.v[k*4+j];
      }
    }
    return r;
  }

  public Vector scaleTo(double s) {
    Vector r = new Vector(this);
    r.v[3] = unscaledLength() / s;
    return r;
  }

  public Vector plus(Vector o) {
    Vector r = new Vector();
    for(int i=0;i<3;++i) {
      r.v[i] = v[i]/v[3] + o.v[i]/o.v[3];
    }
    r.v[3] = 1.0;
    return r;
  }

  public double x() {
    return v[0] / v[3];
  }
    
  public double y() {
    return v[1] / v[3];
  }

  public double z() {
    return v[2] / v[3];
  }

  public Vector minus(Vector o) {
    Vector r = new Vector();
    for(int i=0;i<3;++i) {
      r.v[i] = v[i]/v[3] - o.v[i]/o.v[3];
    }
    r.v[3] = 1.0;
    return r;
  }

  public double lengthSquared() {
    return unscaledLengthSquared() / (v[3] * v[3]);
  }

  public double get(int i) {
    return v[i];
  }

  public void set(int i, double value) {
    v[i] = value;
  }
}
