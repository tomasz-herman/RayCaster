package pl.edu.pw.mini.mg1.raycasting.cameras;

import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.rays.Ray;
import pl.edu.pw.mini.mg1.raycasting.utils.Copyable;

public abstract class Camera implements Copyable<Camera> {
    public static final Vector3d UP_VEC = new Vector3d(0, 1, 0);

    private Vector3d position;
    private Vector3d rotation; // pitch, yaw, roll

    protected Vector3d front;
    protected Vector3d up;
    protected Vector3d right;

    protected Vector3d horizontal;
    protected Vector3d vertical;
    protected Vector3d lowerLeft;

    private double aspect;

    public Camera(Vector3d position, Vector3d rotation) {
        this.position = position;
        this.rotation = rotation;
        updateOrientation();
        recalculateRayOrigins();
    }

    public abstract Ray getRay(double x, double y);

    public double getAspect() {
        return aspect;
    }

    public void setAspect(double aspect) {
        this.aspect = aspect;
        recalculateRayOrigins();
    }

    public Vector3d getPosition() {
        return position;
    }

    public void move(double dx, double dy, double dz) {
        position
            .add(front.mul(dz, new Vector3d()))
            .add(up.mul(dy, new Vector3d()))
            .add(right.mul(dx, new Vector3d()));
        recalculateRayOrigins();
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public void rotate(double dPitch, double dYaw, double dRoll) {
        rotation.add(dPitch, dYaw, dRoll);
        updateOrientation();
        recalculateRayOrigins();
    }

    private void updateOrientation() {
        front = new Vector3d(
                Math.cos(rotation.x) * Math.cos(rotation.y),
                Math.sin(rotation.x),
                Math.cos(rotation.x) * Math.sin(rotation.y)
        ).normalize();
        right = front.cross(UP_VEC, new Vector3d()).normalize();
        up = right.cross(front, new Vector3d()).normalize();
    }

    protected abstract void recalculateRayOrigins();

    public Camera copy(Camera other) {
        this.position = new Vector3d(other.position);
        this.rotation = new Vector3d(other.rotation);
        this.aspect = other.aspect;
        updateOrientation();
        recalculateRayOrigins();
        return this;
    }
}
