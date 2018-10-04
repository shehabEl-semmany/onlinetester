package eg.edu.alexu.csd.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DynamicClassLoader extends ClassLoader {
    private Map<String, Class<?>> classes = new HashMap<String, Class<?>>(); // cache
    private final List<String> jarFiles;
    private final String root;

    public DynamicClassLoader(String root, List<String> jars) {
        this.root = root;
        this.jarFiles = jars;
    }

    @Override
    public Class<?> loadClass(String s, boolean a)
            throws ClassNotFoundException {
        try {
            byte[] bytes = loadClassData(s);
            if (bytes == null)
                super.loadClass(s, a);
            return defineClass(s, bytes, 0, bytes.length);
        } catch (Throwable e) {
            try {
                return super.loadClass(s, a);
            } catch (Throwable e2) {
                return lookupJars(s);
            }
        }
    }

    @Override
    public Class<?> loadClass(String s) throws ClassNotFoundException {
        try {
            byte[] bytes = loadClassData(s);
            if (bytes == null)
                super.loadClass(s);
            return defineClass(s, bytes, 0, bytes.length);
        } catch (Throwable e) {
            try {
                return super.loadClass(s);
            } catch (Throwable e2) {
                return lookupJars(s);
            }
        }
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte[] bytes = loadClassData(className);
            if (bytes == null)
                super.findClass(className);
            return defineClass(className, bytes, 0, bytes.length);
        } catch (Throwable e) {
            return lookupJars(className);
        }
    }

    private Class<?> lookupJars(String className) throws ClassFormatError {
        byte classByte[];
        Class<?> result = null;
        result = (Class<?>) classes.get(className); // checks in cached classes
        if (result != null) {
            return result;
        }

        try {
            return findSystemClass(className);
        } catch (Throwable e1) {
        }
        for(String jarFile : jarFiles){
            JarFile jar = null;
            try {
                if (jarFile.isEmpty())
                    continue;
                jar = new JarFile(jarFile);
//              System.err.println(">>>>>> Name: " + jar.getName());
//              System.err.println(">>>>>> Class Searching for: " + className.replaceAll("\\.", "/") + ".class");
                JarEntry entry = jar.getJarEntry(className.replaceAll("\\.", "/") + ".class");
                if(entry==null)
                {
//                  System.err.println(">>>>>> Entry is null");
                    continue;
                }
//              System.err.println(">>>>>> Entry is okay");
                    
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                int nextValue = is.read();
                while (-1 != nextValue) {
                    byteStream.write(nextValue);
                    nextValue = is.read();
                }
                
                classByte = byteStream.toByteArray();
                result = defineClass(className, classByte, 0, classByte.length, null);
//              System.err.println(">>>>>> Class is done: " + result.getName());
                classes.put(className, result);
                return result;
            } catch (Throwable e2) {
                e2.printStackTrace();
                continue;
            } finally{
                if(jar!=null)
                    try {
                        jar.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
            }
        }
        return null;
    }

    private byte[] loadClassData(String className) throws IOException {
        File f = new File(root + "/" + className.replaceAll("\\.", "/")
                + ".class");
//      System.err.println(">>>>>> Root is: " + root);
        if (!f.exists()){
//          System.err.println("Fail to load " + className + " From " + f);
            return null;
        }
//      System.err.println("Reload " + className + " From " + f);
        int size = (int) f.length();
        byte buff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(buff);
        dis.close();
        return buff;
    }
}
