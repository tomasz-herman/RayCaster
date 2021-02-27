package pl.edu.pw.mini.mg1.raycasting.rendering;

import javafx.scene.paint.Color;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;
import pl.edu.pw.mini.mg1.raycasting.rays.Hittable;
import pl.edu.pw.mini.mg1.raycasting.rays.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scene implements Hittable {
    private Color skyColor = Color.SKYBLUE;
    private List<Hittable> hittableList = new ArrayList<>();

    public Scene(Hittable... models) {
        hittableList.addAll(Arrays.asList(models));
    }

    public Color getSkyColor() {
        return skyColor;
    }

    public void setSkyColor(Color skyColor) {
        this.skyColor = skyColor;
    }

    @Override
    public boolean test(Ray ray, HitInfo hitInfo, double from, double to) {
        HitInfo temp = new HitInfo();
        boolean hitAnything = false;
        double closest = to;

        for (Hittable obj : hittableList) {
            if(obj.test(ray, temp, from, closest)) {
                hitAnything = true;
                closest = temp.distance;
                hitInfo.copy(temp);
            }
        }

        return hitAnything;
    }

    public void add(Hittable hittable) {
        hittableList.add(hittable);
    }

    public void clear() {
        hittableList.clear();
    }
}
