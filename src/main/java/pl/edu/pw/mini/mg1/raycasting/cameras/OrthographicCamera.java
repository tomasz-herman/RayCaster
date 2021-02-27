package pl.edu.pw.mini.mg1.raycasting.cameras;

import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.rays.Ray;

public class OrthographicCamera extends Camera {

    private double size = 10;

    public OrthographicCamera(Vector3d position, Vector3d rotation) {
        super(position, rotation);
        recalculateRayOrigins();
    }

    @Override
    public Ray getRay(double x, double y) {
        return new Ray(
                new Vector3d(
                        lowerLeft.x + x * horizontal.x + y * vertical.x,
                        lowerLeft.y + x * horizontal.y + y * vertical.y,
                        lowerLeft.z + x * horizontal.z + y * vertical.z
                ),
                new Vector3d(front));
    }

    @Override
    protected void recalculateRayOrigins() {
        double width = size;
        double height = width / getAspect();

        horizontal = new Vector3d(right).mul(width);
        vertical = new Vector3d(up).mul(height);
        lowerLeft = new Vector3d(getPosition()).sub(new Vector3d(horizontal).add(vertical).div(2));
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
