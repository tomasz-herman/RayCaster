package pl.edu.pw.mini.mg1.controllers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pl.edu.pw.mini.mg1.raycasting.cameras.Camera;

import java.util.function.Supplier;

public class MouseController implements EventHandler<MouseEvent> {
    private final Supplier<Camera> camera;
    private final Runnable rerender;
    private double sensitivity = 0.002;

    private double lastX;
    private double lastY;

    public MouseController(Supplier<Camera> camera, Runnable rerender) {
        this.camera = camera;
        this.rerender = rerender;
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.isPrimaryButtonDown()) {
            double deltaX = event.getX() - lastX;
            double deltaY = event.getY() - lastY;
            camera.get().rotate(deltaY * sensitivity, deltaX * sensitivity, 0);
            rerender.run();
        }

        lastX = event.getX();
        lastY = event.getY();
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }
}
