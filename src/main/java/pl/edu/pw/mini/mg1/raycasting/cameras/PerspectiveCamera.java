package pl.edu.pw.mini.mg1.raycasting.cameras;

import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.rays.Ray;

public class PerspectiveCamera extends Camera {

    private double fov = 60;

    public PerspectiveCamera(Vector3d position, Vector3d rotation) {
        super(position, rotation);
    }

    @Override
    public Ray getRay(double x, double y) {
        return new Ray(
                new Vector3d(getPosition()),
                new Vector3d(
                        lowerLeft.x + x * horizontal.x + y * vertical.x,
                        lowerLeft.y + x * horizontal.y + y * vertical.y,
                        lowerLeft.z + x * horizontal.z + y * vertical.z
                ).sub(getPosition()));
    }

    @Override
    protected void recalculateRayOrigins() {
        double height = 2.0 * Math.tan(Math.toRadians(fov) / 2.0);
        double width = height * getAspect();

        horizontal = new Vector3d(right).mul(width);
        vertical = new Vector3d(up).mul(height);
        lowerLeft = new Vector3d(getPosition()).sub(new Vector3d(horizontal).add(vertical).div(2)).add(front);
    }

    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }
}
