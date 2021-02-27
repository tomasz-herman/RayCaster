package pl.edu.pw.mini.mg1.raycasting.rays;

public interface Hittable {
    boolean test(Ray ray, HitInfo hit, double from, double to);
}
