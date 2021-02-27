package pl.edu.pw.mini.mg1.controllers;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import pl.edu.pw.mini.mg1.raycasting.cameras.Camera;
import pl.edu.pw.mini.mg1.raycasting.cameras.OrthographicCamera;
import pl.edu.pw.mini.mg1.raycasting.cameras.PerspectiveCamera;

import java.util.function.Supplier;

public class MouseScrollController implements EventHandler<ScrollEvent> {
    private final Supplier<Camera> camera;
    private final Runnable rerender;
    private double sensitivity = 0.002;

    public MouseScrollController(Supplier<Camera> camera, Runnable rerender) {
        this.camera = camera;
        this.rerender = rerender;
    }

    @Override
    public void handle(ScrollEvent event) {
        Camera cam = camera.get();
        if(cam instanceof OrthographicCamera) {
            double size = ((OrthographicCamera) cam).getSize();
            size = Math.max(0.01, size - event.getDeltaY() * sensitivity);
            ((OrthographicCamera) cam).setSize(size);
        } else if(camera.get() instanceof PerspectiveCamera) {
            double fov = ((PerspectiveCamera) cam).getFov();
            fov = Math.min(90, Math.max(15, fov - event.getDeltaY() * sensitivity * 10));
            ((PerspectiveCamera) cam).setFov(fov);
        }

        rerender.run();
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }
}
