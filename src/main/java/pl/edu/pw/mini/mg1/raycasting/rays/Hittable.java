package pl.edu.pw.mini.mg1.raycasting.rays;

import pl.edu.pw.mini.mg1.raycasting.utils.Copyable;

public interface Hittable extends Copyable<Hittable> {
    boolean test(Ray ray, HitInfo hit, double from, double to);
}
