package eg.edu.alexu.csd.oop.test.draw;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.oop.draw.DummyShape;

public class SanityTest {
    
    public static Class<?> getSpecifications(){
        return DrawingEngine.class;
    }
    
    @org.junit.Test
    public void testAddAndRemove() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        Shape shape = new DummyShape();
        instance.addShape(shape);
        
        assertEquals("Wrong number of returned shapes", 1, instance.getShapes().length);
        
        try {
            instance.removeShape(shape);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't remove an existing shape", e);
        }
        
        assertEquals("Wrong number of returned shapes after remove", 0, instance.getShapes().length);
    }
    
    @org.junit.Test
    public void testAddAndUpdate() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        Shape shape1 = new DummyShape();
        shape1.setColor(Color.RED);
        instance.addShape(shape1);
        
        assertEquals("Wrong number of returned shapes", 1, instance.getShapes().length);
        
        Shape current1 = null, current2 = null;
        try {
            current1 = instance.getShapes()[0];
        } catch (Throwable e) {
            TestRunner.fail("Engine can't return the first shape after update", e);
        }
        
        Shape shape2 = new DummyShape();
        shape2.setColor(Color.BLUE);
        try {
            instance.updateShape(shape1, shape2);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't update an existing shape", e);
        }
        
        assertEquals("Wrong number of returned shapes after update", 1, instance.getShapes().length);
        
        try {
            current2 = instance.getShapes()[0];
            assertNotEquals("You should clone the shape then apply the update", current1, current2);
        } catch (Exception e) {
            TestRunner.fail("Engine can't return the first shape after update", e);
        }
    }
    
    @org.junit.Test
    public void testAddAndRemoveWrong() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        Shape shape = new DummyShape();
        instance.addShape(shape);
        
        assertEquals("Wrong number of returned shapes", 1, instance.getShapes().length);
        
        try {
            instance.removeShape(shape);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't remove an existing shape", e);
        }
        
        assertEquals("Wrong number of returned shapes after remove", 0, instance.getShapes().length);
        
        try {
            instance.removeShape(shape);
            fail("Engine accepts removing non existing shape");
        } catch (Throwable e) {}
    }
    
    @org.junit.Test
    public void testAddAndUndo() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        instance.addShape(new DummyShape());
        instance.addShape(new DummyShape());
        instance.addShape(new DummyShape());
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action", e);
        }
        
        assertEquals("Wrong number of returned shapes after undo", 2, instance.getShapes().length);
    }
    
    @org.junit.Test
    public void testUndoAndRedoLimit() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        for (int i = 1; i <= 21; i++)
            instance.addShape(new DummyShape());
        for (int i = 1; i <= 20; i++)
            instance.undo();
        assertEquals("Wrong number of returned shapes after many undo", 1, instance.getShapes().length);
        for (int i = 1; i <= 20; i++)
            instance.redo();
        assertEquals("Wrong number of returned shapes after many redo", 21, instance.getShapes().length);
        
        for (int i = 1; i <= 20; i++)
            instance.undo();
        
        try {
            instance.undo();
        } catch (Throwable e) {}
        assertEquals("Wrong number of returned shapes after undo", 1, instance.getShapes().length);
        
        instance.addShape(new DummyShape());
        try {
            instance.undo();
            instance.undo();
        } catch (Throwable e) {}
        assertEquals("Wrong number of returned shapes after many undo", 1, instance.getShapes().length);
        
        try {
            instance.redo();
            instance.redo();
        } catch (Throwable e) {}
        assertEquals("Wrong number of returned shapes after many redo", 2, instance.getShapes().length);
    }
    
    @org.junit.Test
    public void testAddAndUpdateAndRemove() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        Shape shape1 = new DummyShape();
        shape1.setColor(Color.RED);
        instance.addShape(shape1);
        
        Shape shape2 = new DummyShape();
        shape2.setColor(Color.RED);
        instance.addShape(shape2);
        
        Shape shape3 = new DummyShape();
        shape3.setColor(Color.BLUE);
        try {
            instance.updateShape(shape1, shape3);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't update an existing shape", e);
        }
        
        try {
            instance.removeShape(shape2);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't remove an existing shape", e);
        }
        
        assertEquals("Wrong number of returned shapes after remove", 1, instance.getShapes().length);
    }

    @org.junit.Test
    public void testAddAndUpdateAndRemoveAndUndo() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        Shape shape1 = new DummyShape();
        shape1.setColor(Color.RED);
        instance.addShape(shape1);
        
        Shape shape2 = new DummyShape();
        shape2.setColor(Color.RED);
        instance.addShape(shape2);
        
        Shape shape3 = new DummyShape();
        shape3.setColor(Color.BLUE);
        try {
            instance.updateShape(shape1, shape3);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't update an existing shape", e);
        }
        
        try {
            instance.removeShape(shape2);
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't remove an existing shape", e);
        }
        
        assertEquals("Wrong number of returned shapes after remove", 1, instance.getShapes().length);
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 1", e);
        }
        
        assertEquals("Wrong number of returned shapes after undo", 2, instance.getShapes().length);
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 2", e);
        }
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 3", e);
        }
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 4", e);
        }
        
        assertEquals("Wrong number of returned shapes after undo All", 0, instance.getShapes().length);
    }
    
    @org.junit.Test
    public void testAddAndUpdateAndRemoveAndUndoAndRedo() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        
        Shape shape1 = new DummyShape();
        shape1.setColor(Color.RED);
        instance.addShape(shape1);      // action 1
        
        Shape shape2 = new DummyShape();
        shape2.setColor(Color.GREEN);
        instance.addShape(shape2);      // action 2
        
        Shape shape3 = new DummyShape();
        shape3.setColor(Color.BLUE);
        try {
            instance.updateShape(shape1, shape3);   // action 3
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't update an existing shape", e);
        }
        
        try {
            instance.removeShape(shape2);   // action 4
        } catch (Throwable e) {
            TestRunner.fail("Engine didn't remove an existing shape", e);
        }
        
        assertEquals("Wrong number of returned shapes after remove", 1, instance.getShapes().length);
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 4", e);
        }
        
        assertEquals("Wrong number of returned shapes after undo", 2, instance.getShapes().length);
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 3", e);
        }
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 2", e);
        }
        
        try {
            instance.undo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to undo action 1", e);
        }
        
        assertEquals("Wrong number of returned shapes after undo All", 0, instance.getShapes().length);
        
        try {
            instance.redo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to redo action 1", e);
        }
        
        try {
            instance.redo();
        } catch (Throwable e) {
            TestRunner.fail("Engine failed to redo action 2", e);
        }
        
        assertEquals("Wrong number of returned shapes after undo All", 2, instance.getShapes().length);
        
        try {
            boolean foundGreen = false;
            boolean foundRed = false;
            for(Shape current : instance.getShapes()){
                if(Color.RED.equals(current.getColor()))
                    foundRed = true;
                if(Color.GREEN.equals(current.getColor()))
                    foundGreen = true;
            }
            if (!foundGreen)
                fail("Shape 2 should is missing after redo");
            if (!foundRed)
                fail("Shape 1 should is missing after redo");
        } catch (Throwable e) {
            TestRunner.fail("Engine can't return the original shapes after redo", e);
        }
    }
    
    private void saveAndLoad(DrawingEngine instance1, DrawingEngine instance2, String type){
        try {
            List<Class<? extends Shape>> supportedShapes = instance1.getSupportedShapes();
            assertFalse("No supported shapes returned", supportedShapes.isEmpty());
            for(Class<? extends Shape> shapeClass : supportedShapes){
                try {
                    instance1.addShape((Shape)shapeClass.newInstance());
                } catch (Throwable e) {
                    TestRunner.fail("Failed to create shape of type: " + shapeClass, e);
                }
            }
        } catch (Exception e) {
            TestRunner.fail("No supported shapes returned", e);
        }
        try {
            Shape shape1 = new DummyShape();
            shape1.setColor(Color.RED);
            instance1.addShape(shape1);
        } catch (Throwable e) {
            TestRunner.fail("Failed to create dymmy shape", e);
        }
        int countBefore = 0;
        try {
            countBefore = instance1.getShapes().length;
            assertNotEquals("Engine returned no shapes, before save!", 0, countBefore);
        } catch (Exception e) {
            TestRunner.fail("Failed to return shapes, before save", e);
        }

        String fileName = Math.random() + "_testDraw." + type;
        try {
            instance1.save(fileName);
        } catch (Throwable e) {
            TestRunner.fail("Fail to save", e);
        }

        try {
            List<Class<? extends Shape>> supportedShapes = instance1.getSupportedShapes();
            assertFalse("No supported shapes returned", supportedShapes.isEmpty());
            for(Class<? extends Shape> shapeClass : supportedShapes){
                try {
                    instance1.addShape((Shape)shapeClass.newInstance());
                } catch (Throwable e) {
                    TestRunner.fail("Failed to create shape of type: " + shapeClass, e);
                }
            }
        } catch (Exception e) {
            TestRunner.fail("No supported shapes returned", e);
        }
        
        try {
            instance2.load(fileName);
        } catch (Throwable e) {
            TestRunner.fail("Fail to load", e);
        }

        int countAfter = 0;
        try {
            countAfter = instance2.getShapes().length;
            assertNotEquals("Engine returned no shapes, after load!", 0, countAfter);
            assertEquals("Shapes count different after load!", countBefore, countAfter);
        } catch (Exception e) {
            TestRunner.fail("Failed to return shapes, after load", e);
        }
    }
    
    @org.junit.Test
    public void testSaveAndLoadXML() {
        // Encoding ISO-8859-1
        DrawingEngine instance1 = (DrawingEngine)TestRunner.getImplementationInstance();
        DrawingEngine instance2 = (DrawingEngine)TestRunner.getImplementationInstance();
        saveAndLoad(instance1, instance2, "XmL");
    }

    @org.junit.Test
    public void testSaveAndLoadJSON() {
        DrawingEngine instance1 = (DrawingEngine)TestRunner.getImplementationInstance();
        DrawingEngine instance2 = (DrawingEngine)TestRunner.getImplementationInstance();
        saveAndLoad(instance1, instance2, "JsOn");
    }

    @org.junit.Test
    public void testSaveAndLoadAndUndo() {
        DrawingEngine instance1 = (DrawingEngine)TestRunner.getImplementationInstance();
        saveAndLoad(instance1, instance1, "Xml");

        int countBefore = 0;
        try {
            countBefore = instance1.getShapes().length;
            assertNotEquals("Engine returned no shapes!", 0, countBefore);
        } catch (Exception e) {
            TestRunner.fail("Failed to return shapes, before save", e);
        }

        try {
            instance1.undo();
        } catch (Throwable e) {
        }
        
        int countAfter = 0;
        try {
            countAfter = instance1.getShapes().length;
            assertNotEquals("Engine returned no shapes!", 0, countAfter);
            assertEquals("Undo shouldn't be active after reload!", countBefore, countAfter);
        } catch (Exception e) {
            TestRunner.fail("Failed to return shapes, after load", e);
        }
    }
    
    /*
    @org.junit.Test
    public void testGetPlugins(){
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        instance.installPluginShape(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/oop/test/draw/res/moon.jar");
        try {
            for(Class<? extends Shape> c: instance.getSupportedShapes()){
                System.out.println(c);
                if(c.getName().equals("eg.edu.alexu.csd.oop.draw.plugin.Moon")){
                    return;
                }
            }
        } catch (Throwable e) {
            TestRunner.fail("Failed to get supported shapes!", e);
        }
        fail("Failed to find our new plugin!");
    }
    //*/

    /*
    @org.junit.Test
    public void testUsePlugins(){
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstance();
        instance.installPluginShape(TestRunner.getResourceDir() + "/WEB-INF/res/eg/edu/alexu/csd/oop/test/draw/res/moon.jar");
        try {
            for(Class<? extends Shape> c: instance.getSupportedShapes()){
                System.out.println(c);
                if(c.getName().equals("eg.edu.alexu.csd.oop.draw.plugin.Moon")){
                    instance.addShape(c.newInstance());
                    instance.addShape(c.newInstance());
                    instance.addShape(c.newInstance());
                    instance.addShape(c.newInstance());
                    instance.addShape(new DummyShape());
                    assertEquals("Wrong number of returned shapes and plugin shapes", 5, instance.getShapes().length);
                    return;
                }
            }
        } catch (Exception e) {
            TestRunner.fail("Failed to get supported shapes!", e);
        }
        fail("Failed to use our new plugin!");
    }
    //*/
    
    /*
    @SuppressWarnings("unchecked")
    private Class<? extends Shape> installPluginShapes(String jarPath)
    {
        try 
        {
            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> e = jarFile.entries();
    
            URL[] urls = { new URL("jar:file:" + jarPath + "!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);
    
            while (e.hasMoreElements()) 
            {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }
                // -6 because of .class
                String classURL = je.getName().substring(0,je.getName().length()-6);
                String className = classURL.replace('/', '.');
                Class<? extends Shape> s = (Class<? extends Shape>) cl.loadClass(className);
                jarFile.close();
                return s;
            }
            jarFile.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    //*/

}