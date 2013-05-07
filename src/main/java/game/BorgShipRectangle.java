package game;

public class BorgShipRectangle extends PhysicalObject {

  public BorgShipRectangle(Context context) {
    super(context, new Vector(0, 0, 0, 1), new Vector(100, -50, -100, 1), new Vector(200, 200, 200, 1));
  }

}
