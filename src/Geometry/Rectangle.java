package Geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class Rectangle<T> extends Shape implements IShape{
    private Point<T> _A;
    private Point<T> _B;
    private double _lineWidth = -1;
    public Rectangle(){
        _A = new Point<T>();
        _B = new Point<T>();
    }
    public Rectangle(Point<T> A, Point<T> B){
        _A = A;
        _B = B;
    }
    public Rectangle(T a, T b, T c, T d){
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

    // swap point

    @Override
    public void draw(GraphicsContext gc, ColorPicker cpLine){
        if(_lineWidth == -1) {
            _lineWidth = gc.getLineWidth();
        }
        else{
            gc.setLineWidth(_lineWidth);
        }

        double minX = Math.min((double) _A.getX(),(double) _B.getX());
        double minY = Math.min((double) _A.getY(),(double) _B.getY());
        double width = Math.abs((double) _A.getX() - (double) _B.getX());
        double heigh = Math.abs((double) _A.getY() - (double) _B.getY());
        //gc.fillRect(minX, minY, width, heigh);
        gc.strokeRect(minX, minY, width, heigh);
    }
    @Override
    public String toString() {
        return "Rectangle{" +
                "_A=" + _A +
                ", _B=" + _B +
                '}';
    }
    public  String getType(){
        return "Rectangle";
    }
}
