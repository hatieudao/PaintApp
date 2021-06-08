package Geometry;

public class ShapeFactory {
    private static final ShapeFactory _instance = new ShapeFactory();

    private ShapeFactory(){}

    public static ShapeFactory getInstance(){
        return _instance;
    }

    public IShape create(String type, Double a, Double b, Double c, Double d){
        switch (type){
            case "Line":
                return  new Line<Double>(a, b, c, d);
            case "Circle":
                return  new Circle<Double>(a, b, c, d);
            case "Rectangle":
                return new Rectangle<Double>(a, b, c, d);
            case "DrawnShape":
                return  new DrawnShape<Double>();
            default:
                throw new IllegalArgumentException("This shape type is unsupported");
        }
    }
    public IShape create(String type){
        switch (type){
            case "Line":
                return new Line<Double>();
            case "Circle":
                return new Circle<Double>();
            case "Rectangle":
                return new Rectangle<Double>();
            case "DrawnShape":
                return new DrawnShape<Double>();
            default:
                throw new IllegalArgumentException("This shape type is unsupported");
        }
    }

}
