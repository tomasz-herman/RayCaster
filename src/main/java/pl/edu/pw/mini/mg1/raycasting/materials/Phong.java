package pl.edu.pw.mini.mg1.raycasting.materials;

import javafx.scene.paint.Color;
import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.cameras.Camera;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;

import java.util.function.Supplier;

public class Phong implements Material {
    private final Supplier<Camera> camera;
    private Color objectColor;
    private Color lightColor;
    private double exponent = 1;

    public Phong(Supplier<Camera> camera) {
        this.camera = camera;
        objectColor = Color.YELLOW;
        lightColor = Color.WHITE;
    }

    @Override
    public Color shade(HitInfo hit) {
        Vector3d cameraPos = camera.get().getPosition();
        Vector3d position = hit.hitpoint;
        Vector3d normal = hit.normal;
        Vector3d toCamera = cameraPos.sub(position, new Vector3d()).normalize();
        double specular = normal.dot(toCamera);
        if(specular < 0) return Color.BLACK;
        specular = Math.pow(specular, exponent);
        return Color.color(
                objectColor.getRed() * lightColor.getRed() * specular,
                objectColor.getGreen() * lightColor.getGreen() * specular,
                objectColor.getBlue() * lightColor.getBlue() * specular
        );
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
