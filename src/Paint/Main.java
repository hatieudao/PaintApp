package Paint;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import Geometry.*;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    private ToggleButton drawBtn = new ToggleButton("Draw");
    private ToggleButton lineBtn = new ToggleButton("Line");
    private ToggleButton rectBtn = new ToggleButton("Rectange");
    private ToggleButton circleBtn = new ToggleButton("Circle");
    private Label line_color = new Label("Line Color");
    private Label fill_color = new Label("Fill Color");
    private Label line_width = new Label("3.0");

    private Button undo = new Button("Undo");
    private Button redo = new Button("Redo");
    private Button save = new Button("Save");
    private Button open = new Button("Open");

    private ColorPicker cpLine = new ColorPicker(Color.BLACK);
    private ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);

    private Slider slider = new Slider(1, 30, 2);
    private VBox buttons = new VBox(10);
    private ArrayList<Shape> undoHistory = new ArrayList<Shape>();
    private ArrayList<Shape> redoHistory = new ArrayList<Shape>();

    private int width = 1280;
    private int height = 800;
    @Override
    public void start(Stage primaryStage) throws Exception{
        /* ----------User Interface---------- */
        Canvas canvas = new Canvas(width-100, height);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        BorderPane pane = new BorderPane();
        pane.setLeft(buttons);
        pane.setCenter(canvas);
        Scene scene = new Scene(pane, width, height);
        primaryStage.setTitle("Paint App Java - 19120452 - Trần Trọng Hoàng Anh");
        primaryStage.setScene(scene);
        primaryStage.show();
        /*-----------Mouse Event-----------*/
        setupCanvasMouseEvent(gc, canvas);
        setupSlider(gc);
        setupColorPicker(gc);
        setupButtons(gc);
        setupVBox();
        setupSaveAndOpenFile(primaryStage, canvas, gc);
    }

    private void setupCanvasMouseEvent(GraphicsContext gc, Canvas canvas){
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        AtomicReference<Shape> shape = new AtomicReference<Shape>();
        canvas.setOnMousePressed(e->{
            if(drawBtn.isSelected()) {
                DrawnShape<Double> drawnShape = (DrawnShape<Double>) shapeFactory.create("DrawnShape");
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
                drawnShape.addPoint(new Point<Double>(e.getX(), e.getY()));
                drawnShape.getPoint(0).draw(gc, cpLine);
                shape.set(drawnShape);
            }
            else if(lineBtn.isSelected()){
                gc.setStroke(cpLine.getValue());
                Point<Double> pointA = new Point<Double>(e.getX(), e.getY());
                Line<Double> line = (Line<Double>) shapeFactory.create("Line");
                line.setA(pointA);
                line.setB(pointA);
                shape.set(line);
            }
            else if(rectBtn.isSelected()){
                gc.setStroke(cpLine.getValue());
                Point<Double> pointA = new Point<Double>(e.getX(), e.getY());
                Rectangle<Double> rectangle = (Rectangle<Double>) shapeFactory.create("Rectangle");
                rectangle.setA(pointA);
                rectangle.setB(pointA);
                shape.set(rectangle);
            }
            else if(circleBtn.isSelected()){
                gc.setStroke(cpLine.getValue());
                Point<Double> pointA = new Point<Double>(e.getX(), e.getY());
                Circle<Double> circle = (Circle<Double>) shapeFactory.create("Circle");
                circle.setA(pointA);
                circle.setB(pointA);
                shape.set(circle);
            }
        });

        canvas.setOnMouseDragged(e->{
            if(drawBtn.isSelected()) {
                DrawnShape<Double> drawnShape = (DrawnShape<Double>) shape.get();
                drawnShape.addPoint(new Point<Double>(e.getX(), e.getY()));
                drawnShape.getPoint(drawnShape.getSize()-1).draw(gc, cpLine);
                gc.stroke();
                shape.set(drawnShape);
            }
            else if(lineBtn.isSelected()){
                Line<Double> line = (Line<Double>) shape.get();
                gc.clearRect(0, 0,width, height);
                drawStack(gc);
                Point<Double> pointB = new Point<Double>(e.getX(), e.getY());
                line.setB(pointB);
                line.draw(gc, cpLine);
            }
            else if(rectBtn.isSelected()){
                Rectangle<Double> rectangle = (Rectangle<Double>) shape.get();
                gc.clearRect(0, 0,width, height);
                drawStack(gc);
                Point<Double> pointB = new Point<Double>(e.getX(), e.getY());
                rectangle.setB(pointB);
                rectangle.draw(gc, cpLine);
            }
            else if(circleBtn.isSelected()){
                Circle<Double> circle = (Circle<Double>) shape.get();
                gc.clearRect(0, 0,width, height);
                drawStack(gc);
                Point<Double> pointB = new Point<Double>(e.getX(), e.getY());
                circle.setB(pointB);
                circle.draw(gc, cpLine);
            }
        });

        canvas.setOnMouseReleased(e->{
            if(drawBtn.isSelected()) {
                DrawnShape<Double> drawnShape = (DrawnShape<Double>) shape.get();
                drawnShape.addPoint(new Point<Double>(e.getX(), e.getY()));
                drawnShape.getPoint(drawnShape.getSize()-1).draw(gc, cpLine);
                gc.stroke();
                shape.set(drawnShape);
                gc.closePath();
            }
            else if(lineBtn.isSelected()){
                Line<Double> line = (Line<Double>) shape.get();
                Point<Double> pointB = new Point<Double>(e.getX(), e.getY());
                line.setB(pointB);
                line.draw(gc, cpLine);
            }
            else if(rectBtn.isSelected()){
                Rectangle<Double> rectangle = (Rectangle<Double>) shape.get();
                Point<Double> pointB = new Point<Double>(e.getX(), e.getY());
                rectangle.setB(pointB);
                rectangle.draw(gc, cpLine);
            }
            else if(circleBtn.isSelected()){
                Circle<Double> circle = (Circle<Double>) shape.get();
                Point<Double> pointB = new Point<Double>(e.getX(), e.getY());
                circle.setB(pointB);
                circle.draw(gc, cpLine);
            }
            undoHistory.add(shape.get());
            redoHistory.clear();

        });
    }
    private void setupButtons(GraphicsContext gc){
        ToggleButton[] toolsArr = {drawBtn, lineBtn, rectBtn, circleBtn};

        ToggleGroup tools = new ToggleGroup();

        drawBtn.setSelected(true);

        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }

        Button[] basicArr = {undo, redo, save, open};

        for(Button btn : basicArr) {
            btn.setMinWidth(90);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(Color.WHITE);
            btn.setStyle("-fx-background-color: #666;");
        }
        save.setStyle("-fx-background-color: #80334d;");
        open.setStyle("-fx-background-color: #80334d;");

        undo.setOnAction(e->{
            if(undoHistory.isEmpty()){
                System.out.println("There is no action to undo");
                return;
            }
            Shape beRemovedShape = undoHistory.get(undoHistory.size()-1) ;
            redoHistory.add(beRemovedShape);
            undoHistory.remove(undoHistory.size()-1);
            drawStack(gc);
        });
        redo.setOnAction(e->{
            if(redoHistory.isEmpty()){
                System.out.println("There is no action to redo");
                return;
            }
            Shape top = redoHistory.get(redoHistory.size()-1);
            top.draw(gc, cpLine);
            undoHistory.add(top);
        });
    }
    private void setupSaveAndOpenFile(Stage primaryStage, Canvas canvas, GraphicsContext gc){
        save.setOnAction(e->{
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");

            File file = savefile.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(width-100, height);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });
        open.setOnAction((e)->{
            FileChooser openFile = new FileChooser();
            openFile.setTitle("Open File");
            File file = openFile.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    gc.drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });
    }
    private void setupColorPicker(GraphicsContext gc){
        cpLine.setOnAction(e->{
            gc.setStroke(cpLine.getValue());
        });
        cpFill.setOnAction(e->{
            gc.setFill(cpFill.getValue());
        });
    }
    private void setupSlider(GraphicsContext gc){
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.valueProperty().addListener(e->{
            gc.setLineWidth(slider.getValue());
        });
    }
    private void setupVBox(){
        buttons.getChildren().addAll(drawBtn, lineBtn, rectBtn, circleBtn, line_color, cpLine, fill_color, cpFill, line_width, slider, undo, redo, open, save);
        buttons.setPadding(new Insets(5));
        buttons.setStyle("-fx-background-color: #999");
        buttons.setPrefWidth(100);
    }

    private void drawStack(GraphicsContext gc){
        gc.clearRect(0,0,width, height);
        for(int i = 0; i<undoHistory.size(); ++i){
            Shape shape = undoHistory.get(i);
            shape.draw(gc, cpLine);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
