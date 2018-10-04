package eg.edu.alexu.csd.oop.test.draw;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import javax.swing.DebugGraphics;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.DummyShape;

public class IntegrationTest {
    
    public static Class<?> getSpecifications(){
        return DrawingEngine.class;
    }
    
    @org.junit.Test
    public void testCreation() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        assertNotNull("Failed to create Engine using '" + getSpecifications().getName() + "' !", instance);
    }

    @org.junit.Test
    public void testGraphics() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        try {
            DummyShape shape = new DummyShape();
            shape.setColor(Color.RED);
            shape.setFillColor(Color.RED);
            shape.setPosition(new Point(0,0));
            shape.setProperties(new HashMap<String, Double>());
            instance.addShape(shape);
            instance.refresh(new DebugGraphics());
            assertTrue("Failed to use dummy Graphics object to draw dummy Shape", shape.isDraw());
        } catch (Throwable e) {
            TestRunner.fail("Failed to use dummy Graphics object", e);
        }
    }
    
    @org.junit.Test
    public void testCreateShapes(){
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        List<Class<? extends Shape>> supportedShapes = instance.getSupportedShapes();
        assertNotNull("No supported shapes returned, check getSupportedShapes function!", supportedShapes);
        assertFalse("No supported shapes returned, check getSupportedShapes function!", supportedShapes.isEmpty());
        for(Class<? extends Shape> shapeClass : supportedShapes){
            try {
                Shape shape = shapeClass.newInstance();
                assertNotNull("Failed to create shape", shape);
            } catch (Exception e) {
                TestRunner.fail("Failed to create shape", e);
            }
        }
    }

}
