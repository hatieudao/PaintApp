package Geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

import java.util.ArrayList;

public class DrawnShape<T> extends Shape implements IShape{
    private ArrayList<Point<T>> _A = new ArrayList<Point<T>>();
    private double _lineWidth = -1;

    public void addPoint(Point<T> newPoint){
        _A.add(newPoint);
    }
    public Point<T> getPoint(int index){
        return _A.get(index);
    }
    public int getSize(){
        return _A.size();
    }
    @Override
    public void draw(GraphicsContext gc, ColorPicker cpLine){
        if(_lineWidth == -1) {
            _lineWidth = gc.getLineWidth();
        }
        else{
            gc.setLineWidth(_lineWidth);
        }
        for(int i = 0; i<_A.size(); ++i){
            if(i == 0){
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
                _A.get(i).draw(gc, cpLine);
                continue;
            }
            _A.get(i).draw(gc, cpLine);
            gc.stroke();
            if(i+1 == _A.size()){
                gc.closePath();
            }
        }
    }
    @Override
    public String toString() {
        return "DrawnShape{" +
                "Start=" + _A.get(0) +
                ", End=" + _A.get(_A.size()-1) +
                '}';
    }

    @Override
    public String getType() {
        return "DrawnShape";
    }
}
