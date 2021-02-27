package pl.edu.pw.mini.mg1.raycasting.objects;

import org.joml.Matrix4d;
import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.materials.Dummy;
import pl.edu.pw.mini.mg1.raycasting.materials.Material;
import pl.edu.pw.mini.mg1.raycasting.rays.Hittable;

public abstract class Model implements Hittable {
    private Material material = new Dummy();

    protected Matrix4d transformation = new Matrix4d();
    protected Matrix4d invTransformation = new Matrix4d();
    private Vector3d position = new Vector3d();
    private Vector3d rotation = new Vector3d();
    private Vector3d scale = new Vector3d(1);

    public Material getMaterial() {
        return material;
    }

    public Model setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Model setPosition(Vector3d position) {
        this.position = position;
        updateTransformation();
        return this;
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public Model setRotation(Vector3d rotation) {
        this.rotation = rotation;
        updateTransformation();
        return this;
    }

    public Vector3d getScale() {
        return scale;
    }

    public Model setScale(Vector3d scale) {
        this.scale = scale;
        updateTransformation();
        return this;
    }

    private void updateTransformation() {
        transformation = new Matrix4d()
                .translate(position)
                .rotateXYZ(rotation)
                .scale(scale);
        transformation.invert(invTransformation.identity());
    }
}
