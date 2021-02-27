package pl.edu.pw.mini.mg1.raycasting.rays;

import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.objects.Model;

public class HitInfo {
    public Model model;
    public double distance;
    public Vector3d hitpoint;
    public Vector3d normal;

    public void copy(HitInfo other) {
        model = other.model;
        distance = other.distance;
        hitpoint = other.hitpoint;
        normal = other.normal;
    }
}
