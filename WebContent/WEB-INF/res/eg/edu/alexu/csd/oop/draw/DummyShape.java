package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class DummyShape implements Shape, Cloneable {
    
    private Point position;
    private Map<String, Double> properties;
    private Color color;
    private Color fColor;
    
    private boolean tryDraw;
    
    @Override
    public Object clone() throws CloneNotSupportedException { return super.clone(); }
    @Override
    public void setPosition(Point position) { this.position = position; }
    @Override
    public Point getPosition() { return position; }
    @Override
    public void setProperties(Map<String, Double> properties) { this.properties = properties; }
    @Override
    public Map<String, Double> getProperties() { return properties; }
    @Override
    public void setColor(Color color) { this.color = color; }
    @Override
    public Color getColor() { return color; }
    @Override
    public void setFillColor(Color fcolor) { this.fColor = fcolor; }
    @Override
    public Color getFillColor() { return fColor; }
    @Override
    public void draw(Graphics canvas) { tryDraw = true; }

    public boolean isDraw(){
        return tryDraw;
    }
    
}