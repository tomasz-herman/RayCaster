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
                lowerLeft.add(horizontal.mul(x, new Vector3d()), new Vector3d())
                        .add(vertical.mul(y, new Vector3d()))
                        .sub(getPosition()));
    }

    @Override
    protected void recalculateRayOrigins() {
        double height = 2.0 * Math.tan(Math.toRadians(fov) / 2.0);
        double width = height * getAspect();

        horizontal = right.mul(width, new Vector3d());
        vertical = up.mul(height, new Vector3d());
        lowerLeft = getPosition()
                .sub(horizontal.add(vertical, new Vector3d())
                        .div(2), new Vector3d()).add(front);
    }

    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }

    @Override
    public Camera copy() {
        PerspectiveCamera camera = (PerspectiveCamera) new PerspectiveCamera(new Vector3d(), new Vector3d()).copy(this);
        camera.fov = this.fov;
        return this;
    }
}
