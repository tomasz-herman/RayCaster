package pl.edu.pw.mini.mg1.raycasting.materials;

import javafx.scene.paint.Color;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;

public class Dummy implements Material {
    @Override
    public Color shade(HitInfo hit) {
        return Color.color(
                Math.min(Math.max(hit.normal.x * 0.5 + 0.5, 0), 1),
                Math.min(Math.max(hit.normal.y * 0.5 + 0.5, 0), 1),
                Math.min(Math.max(hit.normal.z * 0.5 + 0.5, 0), 1)
        );
    }

    @Override
    public Material copy() {
        return this;
    }
}
