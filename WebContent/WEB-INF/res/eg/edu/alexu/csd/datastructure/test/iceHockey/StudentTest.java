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

public class StudentTest {
    
    public static Class<?> getSpecifications(){
        return IPlayersFinder.class;
    }
    
    IPlayersFinder playersFinder;
    static int warningsCount = 0;

    @Before
    public void init(){
         playersFinder = (IPlayersFinder)TestRunner.getImplementationInstance();;
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
    public void cs44TestFullImageOneTeam() {
        String [] image = {
                "3333", 
                "3333", 
                "3333", 
                "3333"
            };
        Point [] answer = new Point [] { new Point (4, 4)};
        Integer team = 3;
        Integer threashold = 9;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs44TestFullImageTwoTeam() {
        String [] image ={
                "3113", 
                "3131", 
                "3223", 
                "1311"
                };
        Point [] answer = new Point [] { new Point (1, 3)};
        Integer team = 3;
        Integer threashold = 9;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }

    @org.junit.Test(timeout = 1000)
    public void cs44TestEdges() {
        String [] image = {
                "33UUU", 
                "U3UUU", 
                "UUU3U", 
                "UUU3U", 
                "3333U"
                };
        Point [] answer = new Point [] { new Point (2, 2) , new Point(4, 7)};
        Integer team = 3;
        Integer threashold = 9;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs44TestThreashold() {
        String [] image = {
                "4Q44A", 
                "S4T48", 
                "4MN4B", 
                "4444V", 
                "UUUU4"
                };
        Point [] answer = new Point [] { new Point(4, 4) };
        Integer team = 4;
        Integer threashold = 6;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs44TestSmallThreashold() {
        String [] image = {
                "3EEE3", 
                "TF125", 
                "12345", 
                "00000", 
                "30003"};
        Point [] answer = new Point [] { new Point(1, 1), new Point(1, 9), new Point(5, 5), new Point(9, 1), new Point(9, 9)};
        Integer team = 3;
        Integer threashold = 2;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs44TestSingleElementImage() {
        Point [] answer = new Point [] { new Point(1, 1)};
        String [] image = {"1"};
        Integer team = 1;
        Integer threashold = 1;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestThreashold() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "45TT",
                        "A4QW",
                        "BV49",
                        "3334"
                }, 4, 5);
        Point[] Points = {};
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestFullImage() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "55555",
                        "55555",
                        "55555",
                        "55555" , 
                        "55555"
                }, 5, 16);
        Point[] Points = {
              new Point(5, 5),
        };
        assertArrayEquals(Points,answer);
    }

    @org.junit.Test(timeout = 1000)
    public void cs55TestEdges1() {
        Point answer[] = playersFinder.findPlayers(new String[]{
                "33A3",
                "B333",
                "CDEF",
                "3333"}, 3, 16);
        Point[] Points = {
              new Point(4, 2),
              new Point(4, 7)
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestEdges2() {
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "00A0",
                        "B000",
                        "CDEF",
                        "3333"
                }, 0, 1);
        Point[] Points = {
              new Point(4, 2)
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestEdges3() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "33A3",
                        "B333",
                        "C34F",
                        "3333"
                }, 3, 16);
        Point[] Points = {
              new Point(4, 4),
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestEdges4() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "1113",
                        "B131",
                        "C14F",
                        "3131"
                }, 1, 5);
        Point[] Points = {
              new Point(3, 4),
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestNoPlayer() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "A9A8",
                        "B567",
                        "CDEF",
                        "4321"
                }, 0, 1);
        Point[] Points = {};
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestSixPlayers1() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]
                {
                    "44444H44S4",
                    "K444K4L444",
                    "4LJ44T44XH",
                    "444O4VIF44",
                    "44C4D4U444",
                    "4V4Y4KB4M4",
                    "G4W4HP4O4W",
                    "4444ZDQ4S4",
                    "4BR4Y4A444",
                    "4G4V4T4444"
                    }, 4, 16);

        Point[] Points = {
                new Point (3,8), 
                new Point (4,16),
                new Point (5,4), 
                new Point (16,3), 
                new Point (16,17), 
                new Point (17,9)
        };
        assertArrayEquals(Points,answer);
    }

    @org.junit.Test(timeout = 1000)
    public void cs55TestSixPlayers2() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "8D88888J8L8E888",
                        "88NKMG8N8E8JI88",
                        "88SQGB8I8J88W88",
                        "U88H8NI8CZB88B8",
                        "8PK8H8T8888TQR8"
                }, 8, 8);
        Point[] Points = {
              new Point(3,4), 
              new Point(9,3), 
              new Point(17,3), 
              new Point(18,8), 
              new Point(23,4), 
              new Point(27,5)
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestFourPlayers() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                    "44444H44S4",
                    "K444K4L444",
                    "4LJ44T44XH",
                    "444O4VIF44",
                    "44C4D4U444",
                    "4G4V4T4444"
                    }, 4, 16);
        Point[] Points = {
              new Point(3,8),
              new Point(5,4),
              new Point(16,3),  
              new Point(16,9)
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestTwoPlayer() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "44444H44S4",
                        "K444K4L444",
                        "4LJ44444XH",
                        "44444VIF44",
                        "44C4D4U444",
                        "4G4V4T4444"
                        }, 4, 16);
        Point[] Points = {
              new Point(10,6),
              new Point(16,9)
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestHorizontalImage() {
        
        Point answer[] = playersFinder.findPlayers(
                new String[]{
                        "8D88888J8L8E888"
                }, 8, 8);
        Point[] Points = {
              new Point(9,1), 
              new Point(27,1)
        };
        assertArrayEquals(Points,answer);
    }
    
    @org.junit.Test(timeout = 1000)
    public void  cs56TestThreashold() {
        String[] photo = {
                "111111111",
                "AAAAAAAA1",
                "1111111A1",
                "1AAAAACA1",
                "1ACCCCCA1",
                "1AC111CA1",
                "1ACCCCCA1",
                "1AAAAAAA1",
                "111111111"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 1, 16);
        Point[] xs=new Point[1];
        xs[0]=new Point();
        xs[0].setLocation(9, 9);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestEdges1() {
        String[] photo = {
                "111111111",
                "AAAAAAAA1",
                "1111111A1",
                "1AAAAACA1",
                "1ACCCCCA1",
                "1AC111CA1",
                "1ACCCCCA1",
                "1AAAAAAA1",
                "111111111"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 1, 8);
        Point[] xs=new Point[2];
        xs[0]=new Point();
        xs[0].setLocation(9, 9);
        xs[1]=new Point();
        xs[1].setLocation(9, 11);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestThreasholdSnake() {
        String[] photo = {
                "BBBBBBBBB",
                "66666666B",
                "BBBBBBB6B",
                "B66666C6B",
                "B6CCCCC6B",
                "B6CBBBC6B",
                "B6CCCCC6B",
                "B6666666B",
                "BBBBBBBBB"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 6, 16);
        Point[] xs=new Point[1];
        xs[0]=new Point();
        xs[0].setLocation(8, 9);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestEdges2() {
        String[] photo = {
                "BBBBBBBBB",
                "AAAAAAAAB",
                "BBBBBBBAB",
                "BAAAAA5AB",
                "BA55555AB",
                "BA5BBB5AB",
                "BA55555AB",
                "BAAAAAAAB",
                "BBBBBBBBB"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 5, 16);
        Point[] xs=new Point[1];
        xs[0]=new Point();
        xs[0].setLocation(9,10);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestThreePlayers() {
        String[] photo = {
                "88UU88DD",
                "88UU88DD",
                "UU88UUDD",
                "UU88UUDD"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 8, 16);
        Point[] xs=new Point[3];
        for(int i=0;i<3;i++){
            xs[i]=new Point();
        }
        xs[0].setLocation(2, 2);
        xs[1].setLocation(6, 6);
        xs[2].setLocation(10, 2);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestOnePlayer() {
        String[] photo = {
                "TTUUTT77",
                "TTUUTT77",
                "UUTTUU77",
                "UUTTUU77"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 7, 16);
        Point[] xs=new Point[1];
        xs[0]=new Point();
        xs[0].setLocation(14, 4);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestTwoPlayers() {
        String[] photo = {
                "AAAAA3",
                "A333A3",
                "A3AAA3",
                "A3A333",
                "A3AAA3",
                "A333A3",
                "AAAAA3"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 3, 16);
        Point[] xs=new Point[2];
        for(int i=0;i<2;i++){
            xs[i]=new Point();
        }
        xs[0].setLocation(5, 7);
        xs[1].setLocation(9, 7);
        Assert.assertArrayEquals(xs,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestThreasholdSmall() {
        String[] photo = {
                "AAAAA3",
                "A333A3",
                "AAAAA3"
        };
        Point[] answer = new Point[]{};
        Point[] point=playersFinder.findPlayers(photo, 3, 16);
        
        Assert.assertArrayEquals(answer,point);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestThreasholdSmallMatch() {
        String[] photo = {
                "AAAAA3",
                "A333A3",
                "A3AAA3"
        };
        Point[] point;
        
        point=playersFinder.findPlayers(photo, 3, 16);
        Point[] xs=new Point[1];
        xs[0]=new Point();
        xs[0].setLocation(5, 4);
        Assert.assertArrayEquals(xs,point);
    }

}
