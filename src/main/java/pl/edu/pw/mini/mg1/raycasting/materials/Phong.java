package pl.edu.pw.mini.mg1.raycasting.materials;

import javafx.scene.paint.Color;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;

public class Phong implements Material {
    private Color objectColor;
    private Color lightColor;
    private double exponent;

    public Phong() {
        objectColor = Color.YELLOW;
        lightColor = Color.WHITE;
    }

    @Override
    public Color shade(HitInfo hit) {
//        return objectColor;
        return Color.color(Math.max(hit.normal.y, 0), 0, 0);
    }


    public Color getObjectColor() {
        return objectColor;
    }

    public void setObjectColor(Color objectColor) {
        this.objectColor = objectColor;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public double getExponent() {
        return exponent;
    }

    public void setExponent(double exponent) {
        this.exponent = exponent;
    }
}
