package pl.edu.pw.mini.mg1.raycasting.rendering;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import pl.edu.pw.mini.mg1.raycasting.cameras.Camera;
import pl.edu.pw.mini.mg1.raycasting.rays.HitInfo;
import pl.edu.pw.mini.mg1.raycasting.rays.Ray;
import pl.edu.pw.mini.mg1.raycasting.utils.Parallel;

import java.util.concurrent.CompletableFuture;

public class Renderer {
   public Image render(Scene scene, Camera camera, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        camera.setAspect((double) width / height);
        Parallel.parallelFor(width, (from, to) -> {
            for (int i = from; i < to; i++) {
                for (int j = 0; j < height; j++) {
                    double u = (double) i / (width - 1);
                    double v = (double) j / (height - 1);
                    Ray ray = camera.getRay(u, v);
                    Color color = shade(ray, scene);
                    image.getPixelWriter().setColor(i, j, color);
                }
            }
        });

        return image;
    }

    public CompletableFuture<Image> renderAsync(Scene scene, Camera camera, int width, int height) {
        return CompletableFuture.supplyAsync(() -> render(scene, camera, width, height));
    }

    public Color shade(Ray ray, Scene scene) {
        HitInfo hitInfo = new HitInfo();

        if (scene.test(ray, hitInfo, 0.0001, Double.POSITIVE_INFINITY)) {
            return hitInfo.model.getMaterial().shade(hitInfo);
        }

        return scene.getSkyColor();
    }
}
