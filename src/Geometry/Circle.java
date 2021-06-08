package Geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class Circle<T> extends Shape implements IShape{
    private Point<T> _A;
    private Point<T> _B;
    private double _lineWidth = -1;
    public Circle(){
        _A = new Point<T>();
        _B = new Point<T>();
    }
    public Circle(Point<T> A, Point<T> B){
        _A = A;
        _B = B;
    }
    public Circle(T a, T b, T c, T d){
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
    public Double getRadius(){
        Double x1 = (Double) _A.getX();
        Double y1 = (Double) _A.getY();
        Double x2 = (Double) _B.getX();
        Double y2 = (Double) _B.getY();
        Double d = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        return d/2;
    }
    public Point<Double> getCenter(){
        Double x1 = (Double) _A.getX();
        Double y1 = (Double) _A.getY();
        Double x2 = (Double) _B.getX();
        Double y2 = (Double) _B.getY();
        return new Point<Double>((x1+x2)/2, (y1+y2)/2);
    }
    @Override
    public void draw(GraphicsContext gc, ColorPicker cpLine){
        if(_lineWidth == -1) {
            _lineWidth = gc.getLineWidth();
        }
        else{
            gc.setLineWidth(_lineWidth);
        }
        Double r = getRadius();
        gc.strokeOval(getCenter().getX()-r,(Double) getCenter().getY()-r, 2*r, 2*r);
    }
    @Override
    public String toString() {
        return "Circle{" +
                "_A=" + _A +
                ", _B=" + _B +
                '}';
    }
    public String getType(){
        return "Circle";
    }
}
