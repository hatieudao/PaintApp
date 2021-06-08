package Geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class Line<T> extends Shape implements IShape{
    private Point<T> _A;
    private Point<T> _B;
    private double _lineWidth = -1;
    public Line(){
        _A = new Point<T>();
        _B = new Point<T>();
    }
    public Line(Point<T> A, Point<T> B){
        _A = A;
        _B = B;
    }
    public Line(T a, T b, T c, T d){
        _A = new Point<T>(a, b);
        _B = new Point<T>(c, d);
    }

    public Point<T> getA() {
        return _A;
    }

    public Point<T> getB() {
        return _B;
    }
    public void setA(Point<T> _A) {
        this._A = _A;
    }

    public void setB(Point<T> _B) {
        this._B = _B;
    }

    @Override
    public void draw(GraphicsContext gc, ColorPicker cpLine){
        if(_lineWidth == -1) {
            _lineWidth = gc.getLineWidth();
        }
        else{
            gc.setLineWidth(_lineWidth);
        }
        gc.setStroke(cpLine.getValue());
        gc.strokeLine((Double) _A.getX(),(Double) _A.getY(),(Double)_B.getX(),(Double) _B.getY());
    }

    @Override
    public String toString() {
        return "Line{" +
                "_A=" + _A +
                ", _B=" + _B +
                '}';
    }
    public  String getType(){
        return "Line";
    }
}
