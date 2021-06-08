package Geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class Point<T> implements IShape{
    private T _X;
    private T _Y;
    private double _lineWidth = -1;
    public Point(){
    }
    public  Point(T x, T y){
        _X = x;
        _Y = y;
    }
    public T getX() {
        return _X;
    }
    public T getY() {
        return _Y;
    }

    public void setX(T _X) {
        this._X = _X;
    }
    public void setY(T _Y) {
        this._Y = _Y;
    }
    @Override
    public void draw(GraphicsContext gc, ColorPicker cpLine){
        if(_lineWidth == -1) {
            _lineWidth = gc.getLineWidth();
        }
        else{
            gc.setLineWidth(_lineWidth);
        }
        gc.lineTo((Double)_X, (Double)_Y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "_X=" + _X +
                ", _Y=" + _Y +
                '}';
    }
}
