package Geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public interface IShape {
    public String toString();
    public void draw(GraphicsContext gc, ColorPicker cpLine);
}
