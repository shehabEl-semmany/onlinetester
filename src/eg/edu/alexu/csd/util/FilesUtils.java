package eg.edu.alexu.csd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class FilesUtils {

    public static List<File> getFiles(File root, final String extention) {
        File files[] = root.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory()
                        || (extention != null? name.endsWith(extention): true);
            }
        });
        List<File> filtered = new LinkedList<File>();
        if (files == null)
            return filtered;
        for (File file : files)
            if (file.isDirectory())
                filtered.addAll(getFiles(file, extention));
            else
                filtered.add(file);
        return filtered;
    }

    public static void delete(File f){
        if(!f.exists())
            return;
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        f.delete();
    }

    public static File find(File f, String name) throws IOException {
        if(!f.exists())
            return null;
        if(f.getName().equals(name))
            return f;
        if (f.isDirectory()) {
            for (File c : f.listFiles()){
                File result = find(c, name);
                if(result != null)
                    return result;
            }
        }
        return null;
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
//          System.err.println(">>>>>> Copy list: ");
//          System.err.println(f);
            copy(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {        
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)
        ) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }
    
    public static String read(String path) {
        StringBuilder s = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null){
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            if (fr != null)
            {
                try
                {
                    fr.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return "***";
    }

}
