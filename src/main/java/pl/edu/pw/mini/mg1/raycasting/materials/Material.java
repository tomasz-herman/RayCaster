package pl.edu.pw.mini.mg1.raycasting.materials;

import javafx.scene.paint.Color;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;
import pl.edu.pw.mini.mg1.raycasting.utils.Copyable;

public interface Material extends Copyable<Material> {
    Color shade(HitInfo hit);
}
