package pl.edu.pw.mini.mg1.raycasting.rays;

import org.joml.Vector3d;

public class Ray {
    private final Vector3d origin;
    private final Vector3d direction;

    public Ray(Vector3d origin, Vector3d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3d at(double t) {
        return origin.add(direction.mul(t));
    }

    public Vector3d getOrigin() {
        return origin;
    }

    public Vector3d getDirection() {
        return direction;
    }
}
