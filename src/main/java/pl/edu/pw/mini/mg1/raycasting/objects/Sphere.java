package pl.edu.pw.mini.mg1.raycasting.objects;

import org.joml.Matrix4d;
import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;
import pl.edu.pw.mini.mg1.raycasting.rays.Hittable;
import pl.edu.pw.mini.mg1.raycasting.rays.Ray;

public class Sphere extends Model {
    @Override
    public boolean test(Ray ray, HitInfo hit, double from, double to) {
        Ray transformedRay = new Ray(
                new Vector3d(ray.getOrigin()).mulPosition(invTransformation),
                new Vector3d(ray.getDirection()).mulDirection(invTransformation).normalize()
        );

        Vector3d distance = new Vector3d(transformedRay.getOrigin());

        double a = transformedRay.getDirection().lengthSquared();
        double bHalf = distance.dot(transformedRay.getDirection());
        double c = distance.lengthSquared() - 1;
        double disc = bHalf * bHalf - a * c;

        if (disc < 0)
        {
            return false;
        }

        double discSq = Math.sqrt(disc);

        double root = (-bHalf - discSq) / a;
        if (root < from || to < root)
        {
            root = (-bHalf + discSq) / a;
            if (root < from || to < root)
                return false;
        }

        hit.distance = root;
        hit.hitpoint = transformedRay.at(hit.distance);
        hit.normal = new Vector3d(hit.hitpoint);
        hit.hitpoint.mulPosition(transformation);
        hit.normal.mulTransposeDirection(invTransformation).normalize();
        hit.model = this;
        return true;
    }


    @Override
    public Hittable copy() {
        Sphere sphere = new Sphere();
        sphere.setScale(new Vector3d(getScale()));
        sphere.setPosition(new Vector3d(getPosition()));
        sphere.setRotation(new Vector3d(getRotation()));
        sphere.transformation = new Matrix4d(transformation);
        sphere.invTransformation = new Matrix4d(invTransformation);
        sphere.setMaterial(getMaterial().copy());
        return sphere;
    }
}
