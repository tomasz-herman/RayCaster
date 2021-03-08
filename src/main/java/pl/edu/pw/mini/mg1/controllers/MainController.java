package pl.edu.pw.mini.mg1.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.joml.Vector3d;
import pl.edu.pw.mini.mg1.raycasting.cameras.Camera;
import pl.edu.pw.mini.mg1.raycasting.cameras.OrthographicCamera;
import pl.edu.pw.mini.mg1.raycasting.cameras.PerspectiveCamera;
import pl.edu.pw.mini.mg1.raycasting.materials.Dummy;
import pl.edu.pw.mini.mg1.raycasting.materials.Phong;
import pl.edu.pw.mini.mg1.raycasting.objects.Model;
import pl.edu.pw.mini.mg1.raycasting.objects.Sphere;
import pl.edu.pw.mini.mg1.raycasting.rendering.Renderer;
import pl.edu.pw.mini.mg1.raycasting.rendering.Scene;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController {
    @FXML private Pane canvasPane;
    @FXML private VBox sidePane;
    @FXML private Canvas canvas;

    private Image image;
    private final AtomicBoolean rerender = new AtomicBoolean(true);
    private final Semaphore semaphore = new Semaphore(0);

    private final Phong phong = new Phong(this::getCamera);
    private final Model sphere = new Sphere()
            .setMaterial(phong);
    private final Scene scene = new Scene(sphere);
    private Camera camera = new PerspectiveCamera(
            new Vector3d(-5, 0, 0),
            new Vector3d());
    private volatile int adaptive = 5;

    @FXML private RadioButton orthographicCamera;
    @FXML private RadioButton perspectiveCamera;

    @FXML private ColorPicker lightColor;
    @FXML private ColorPicker objectColor;

    @FXML private Spinner<Double> scaleX;
    @FXML private Spinner<Double> scaleY;
    @FXML private Spinner<Double> scaleZ;

    @FXML private Slider rotationX;
    @FXML private Slider rotationY;
    @FXML private Slider rotationZ;

    @FXML private Slider positionX;
    @FXML private Slider positionY;
    @FXML private Slider positionZ;

    @FXML private Slider adaptiveLevels;
    @FXML private Slider lightExponent;

    @FXML void initialize() {
        setupCanvas();
        startRendering();
        startDrawing();
        installListeners();
        bindRadioButtons();
        bindColorPickers();
        bindSliders();
        bindSpinners();
    }

    private void bindRadioButtons() {
        ToggleGroup toggle = new ToggleGroup();
        perspectiveCamera.setToggleGroup(toggle);
        orthographicCamera.setToggleGroup(toggle);
        perspectiveCamera.setUserData(camera);
        orthographicCamera.setUserData(new OrthographicCamera(new Vector3d(-5, 0, 0), new Vector3d()));
        toggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            camera = ((Camera)newValue.getUserData()).copy((Camera)oldValue.getUserData());
            requestRender();
        });
    }

    private void bindColorPickers() {
        lightColor.valueProperty().addListener((observable, oldValue, newValue) -> {
            phong.setLightColor(newValue);
            requestRender();
        });
        objectColor.valueProperty().addListener((observable, oldValue, newValue) -> {
            phong.setObjectColor(newValue);
            requestRender();
        });
    }

    private void bindSpinners() {
        scaleX.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setScale(sphere.getScale().setComponent(0, newValue));
            requestRender();
        });
        scaleY.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setScale(sphere.getScale().setComponent(1, newValue));
            requestRender();
        });
        scaleZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setScale(sphere.getScale().setComponent(2, newValue));
            requestRender();
        });
    }

    private void bindSliders() {
        adaptiveLevels.valueProperty().addListener((observable, oldValue, newValue) -> adaptive = (int)(double) newValue);

        rotationX.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setRotation(sphere.getRotation().setComponent(0, Math.toRadians((double)newValue)));
            requestRender();
        });
        rotationY.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setRotation(sphere.getRotation().setComponent(1, Math.toRadians((double)newValue)));
            requestRender();
        });
        rotationZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setRotation(sphere.getRotation().setComponent(2, Math.toRadians((double)newValue)));
            requestRender();
        });

        positionX.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setPosition(sphere.getPosition().setComponent(0, (double)newValue));
            requestRender();
        });
        positionY.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setPosition(sphere.getPosition().setComponent(1, -(double)newValue));
            requestRender();
        });
        positionZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            sphere.setPosition(sphere.getPosition().setComponent(2, (double)newValue));
            requestRender();
        });

        lightExponent.valueProperty().addListener((observable, oldValue, newValue) -> {
            phong.setExponent(Math.sqrt((double)newValue));
            requestRender();
        });
    }

    private void setupCanvas() {
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());
        canvas.getGraphicsContext2D().setImageSmoothing(false);
    }

    private void requestRender() {
        rerender.set(true);
        semaphore.release();
    }

    private void installListeners() {
        canvas.setOnMouseClicked(event -> requestRender());
        canvas.heightProperty().addListener(observable -> requestRender());
        canvas.widthProperty().addListener(observable -> requestRender());
        MouseController mouseController = new MouseController(this::getCamera, this::requestRender);
        KeyboardController keyController = new KeyboardController(this::getCamera, this::requestRender);
        canvas.setOnMouseMoved(mouseController);
        canvas.setOnMouseDragged(mouseController);
        canvas.setOnKeyPressed(keyController.new OnKeyPressed());
        canvas.setOnKeyReleased(keyController.new OnKeyReleased());
        keyController.start();
        canvas.setOnScroll(new MouseScrollController(this::getCamera, this::requestRender));
        canvas.setFocusTraversable(true);
        canvas.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                sidePane.setVisible(!sidePane.isVisible());
                sidePane.setManaged(!sidePane.isManaged());
                requestRender();
            }
        });
    }

    private void startDrawing() {
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                canvas.requestFocus();
                canvas.getGraphicsContext2D().drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
            }
        }.start();
    }

    private void startRendering() {
        Thread renderThread = new Thread(() -> {
            Renderer renderer = new Renderer();

            while(true) {
                int width = (int) canvas.getWidth();
                int height = (int) canvas.getHeight();
                if(width == 0 || height == 0) continue;
                if(rerender.compareAndSet(true, false)) {
                    for (int i = adaptive; i >= 0; i--) {
                        int partialWidth = width >> i;
                        int partialHeight = height >> i;
                        if(partialWidth == 0 || partialHeight == 0) continue;
                        if(!rerender.get()) {
                            image = renderer.render(scene, camera, partialWidth, partialHeight);
                        } else break;
                    }
                }
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        renderThread.setDaemon(true);
        renderThread.start();
    }

    public Camera getCamera() {
        return camera;
    }
}
