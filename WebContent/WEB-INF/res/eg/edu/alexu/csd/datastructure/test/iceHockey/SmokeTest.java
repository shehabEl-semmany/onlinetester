package eg.edu.alexu.csd.datastructure.test.iceHockey;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.iceHockey.IPlayersFinder;

public class SmokeTest {
    
    public static Class<?> getSpecifications(){
        return IPlayersFinder.class;
    }
    
    IPlayersFinder playersFinder;
    static int warningsCount = 0;

    @Before
    public void init(){
         playersFinder = (IPlayersFinder)TestRunner.getImplementationInstance();
    }
    
    private void assertEqualResult(Point[] expected, Point[] result){
        Assert.assertNotNull(expected);
        Assert.assertNotNull(result);
        List<String> warnings = new LinkedList<String>();
        if(result.length > expected.length){
            warnings.add("Wrong Result Size = " + result.length + " (expected size = " + expected.length + ")");
            Point[] temp = expected;
            expected = new Point[result.length];
            int i=0;
            for(; i<temp.length; i++)
                expected[i] = temp[i];
            int empty = 0;
            for(;i<result.length;i++){
                if(result[i]!=null){
                    expected[i] = new Point();
                    empty++;
                }
            }
            if(empty>0)
                warnings.add("Result contains " + empty + " unused empty objects!");
        }
        Point[] ordered = Arrays.copyOfRange(result , 0 , result.length);
        Arrays.sort(ordered, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if(p1==null || p1.x==0 && p1.y==0) return 1;
                if(p2==null || p2.x==0 && p2.y==0) return -1;
                int r = p1.x - p2.x;
                return r==0 ? p1.y - p2.y : r;
            }
        });
        if(!Arrays.equals(ordered, result)){
            result = ordered;
            warnings.add("Unordered Result!");
        }
        Assert.assertArrayEquals(expected, result);
        if(!warnings.isEmpty()){
            StringBuffer buffer = new StringBuffer();
            for(String w : warnings)
                buffer.append("Warning [" + warningsCount++ + "] : " + w).append("\n");
            Assert.fail(buffer.toString());
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSheetSample1() {
        String[] image = {
                "33JUBU33", 
                "3U3O4433", 
                "O33P44NB", 
                "PO3NSDP3", 
                "VNDSD333", 
                "OINFD33X"};
        Point[] answer = 
            new Point[]{
                new Point(4, 5),
                new Point(13, 9),
                new Point(14, 2),
            };
        Integer team = 3;
        Integer threashold = 16;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSheetSample2() {
        String[] image = {
                "44444H44S4", 
                "K444L4K444", 
                "4LJ44T44XH", 
                "444O4VIF44", 
                "44C4U4D444", 
                "4V4Y4KB4M4", 
                "G4W4HP4O4W", 
                "4444ZDQ4S4", 
                "4BR4Y4A444", 
                "4G4V4T4444"};
        Point[] exp = new Point[50];
        for(int index = 0; index < 50; index++){
            exp[index] = new Point();
        }
        Point[] answer = new Point[]{
                new Point(3, 8),
                new Point(4, 16), 
                new Point(5, 4),
                new Point(16, 3),
                new Point(16, 17),
                new Point(17, 9),
        };
        Integer team = 4;
        Integer threashold = 16;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSheetSample3() {
        String[] image = {
                "8D88888J8L8E888", 
                "88NKMG8N8E8JI88", 
                "888NS8EU88HN8EO", 
                "LUQ888A8TH8OIH8", 
                "888QJ88R8SG88TY", 
                "88ZQV88B88OUZ8O", 
                "FQ88WF8Q8GG88B8", 
                "8S888HGSB8FT8S8", 
                "8MX88D88888T8K8", 
                "8S8A88MGVDG8XK8", 
                "M88S8B8I8M88J8N", 
                "8W88X88ZT8KA8I8", 
                "88SQGB8I8J88W88", 
                "U88H8NI8CZB88B8", 
                "8PK8H8T8888TQR8"};
        Point[] answer = new Point[] {
                new Point(1, 17),
                new Point(3, 3), 
                new Point(3, 10),
                new Point(3, 25),
                new Point(5, 21),
                new Point(8, 17),
                new Point(9, 2),
                new Point(10, 9),
                new Point(12, 23),
                new Point(17, 16),
                new Point(18, 3),
                new Point(18, 11),
                new Point(18, 28),
                new Point(22, 20),
                new Point(23, 26),
                new Point(24, 15),
                new Point(27, 2),
                new Point(28, 26),
                new Point(29, 16),
        };
        Integer team = 8;
        Integer threashold = 9;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testSheetSample4() {
        String[] image = {
                "11111", 
                "1AAA1", 
                "1A1A1", 
                "1AAA1", 
                "11111" };
        Point[] answer = new Point[]{
                new Point(5, 5),
                new Point(5, 5),
        };
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
}
