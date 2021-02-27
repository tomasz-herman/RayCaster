package pl.edu.pw.mini.mg1.raycasting.materials;

import javafx.scene.paint.Color;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;

public interface Material {
    Color shade(HitInfo hit);
}
