package pl.edu.pw.mini.mg1.controllers;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import pl.edu.pw.mini.mg1.raycasting.cameras.Camera;

import java.util.function.Supplier;

public class KeyboardController extends AnimationTimer {
    private final Supplier<Camera> camera;
    private final Runnable rerender;
    private double sensitivity = 0.000000002;
    private boolean w, a, s, d, q, e;
    private long last = -1;

    public KeyboardController(Supplier<Camera> camera, Runnable rerender) {
        this.camera = camera;
        this.rerender = rerender;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    @Override
    public void handle(long now) {
        if(last == -1) last = now;
        long diff = now - last;
        if (w || a || s || d || q || e) {
            double dx = 0, dy = 0, dz = 0;
            if(w) dz += diff * sensitivity;
            if(a) dx -= diff * sensitivity;
            if(s) dz -= diff * sensitivity;
            if(d) dx += diff * sensitivity;
            if(q) dy += diff * sensitivity;
            if(e) dy -= diff * sensitivity;
            camera.get().move(dx, dy, dz);
            rerender.run();
        }

        last = now;
    }

    public class OnKeyPressed implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case W -> w = true;
                case A -> a = true;
                case S -> s = true;
                case D -> d = true;
                case E -> e = true;
                case Q -> q = true;
            }
        }
    }

    public class OnKeyReleased implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case W -> w = false;
                case A -> a = false;
                case S -> s = false;
                case D -> d = false;
                case E -> e = false;
                case Q -> q = false;
            }
        }
    }
}
