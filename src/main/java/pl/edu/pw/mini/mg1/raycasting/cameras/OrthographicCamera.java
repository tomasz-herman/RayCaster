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
                lowerLeft.add(horizontal.mul(x, new Vector3d()), new Vector3d())
                        .add(vertical.mul(y, new Vector3d())),
                new Vector3d(front));
    }

    @Override
    protected void recalculateRayOrigins() {
        double width = size;
        double height = width / getAspect();

        horizontal = right.mul(width, new Vector3d());
        vertical = up.mul(height, new Vector3d());
        lowerLeft = getPosition()
                .sub(horizontal.add(vertical, new Vector3d()).div(2), new Vector3d());
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public Camera copy() {
        OrthographicCamera camera = (OrthographicCamera) new OrthographicCamera(new Vector3d(), new Vector3d()).copy(this);
        camera.size = this.size;
        return this;    }
}
