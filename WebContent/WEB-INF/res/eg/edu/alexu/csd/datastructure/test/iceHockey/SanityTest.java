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

public class SanityTest {
    
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
    public void testNoPlayers() {
        String[] image = {
                "11111", 
                "1AAA1", 
                "1A1A1", 
                "1AAA1", 
                "11111" };
        Point[] answer = new Point[]{
        };
        Integer team = 7;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testEmptyImage() {
        String[] image = {
                ""
        };
        Point[] answer = new Point[]{
        };
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testNullImage() {
        String[] image = null;
        //Point[] answer = null;
        Integer team = 1;
        Integer threashold = 3;
        try {
            assertNull("Result should be null", playersFinder.findPlayers(image, team, threashold));
        } catch (Exception e) {
            fail("Result should be null");
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void testLinearImage() {
        String[] image = {
                "1111"
        };
        Point[] answer = new Point[]{
                new Point(4 , 1)
        };
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testLinearLongImage() {
        String[] image = {
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X"
        };
        Point[] answer = new Point[10];
        for(int i=0; i<10; i++)
            answer[i] = new Point(4+i*10 , 1);
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testLinearLongFullImage() {
        String[] image = {
                "1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X"
        };
        Point[] answer = new Point[25];
        for(int i=0; i<25; i++)
            answer[i] = new Point(1+i*4 , 1);
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testDiagonalImage() {
        String[] image = {
                "1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X",
                "X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1X1"
        };
        Point[] answer = new Point[50];
        for(int i=0; i<25; i++)
            answer[i*2] = new Point(1+i*4 , 1);
        for(int i=0; i<25; i++)
            answer[i*2+1] = new Point(3+i*4 , 3);
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 1000)
    public void testColumnImage() {
        String[] image = {
                "1",
                "1",
                "1",
                "1",
        };
        Point[] answer = new Point[]{
                new Point(1 , 4)
        };
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }

    @org.junit.Test(timeout = 1000)
    public void testSingleElementImage() {
        String[] image = {
                "1",
        };
        Point[] answer = new Point[]{
                new Point(1 , 1)
        };
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }

    @org.junit.Test(timeout = 10000)
    public void testFullImageTwoTeams() {
        String[] image = {
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "1111X1111X1111X1111X1111X1111X1111X1111X1111X1111X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "2222X2222X2222X2222X2222X2222X2222X2222X2222X2222X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
        };
        Point[] answer1 = new Point[50];
        for(int i=0; i<10; i++)
            for(int j=0; j<5; j++)
                answer1[j+i*5] = new Point(4+i*10 , 4+j*20);
        Integer team1 = 1;
        Integer threashold = 3;
        assertEqualResult(answer1, playersFinder.findPlayers(image, team1, threashold));
        Point[] answer2 = new Point[50];
        for(int i=0; i<10; i++)
            for(int j=0; j<5; j++)
                answer2[j+i*5] = new Point(4+i*10 , 14+j*20);
        Integer team2 = 2;
        playersFinder = (IPlayersFinder)TestRunner.getImplementationInstance();
        assertEqualResult(answer2, playersFinder.findPlayers(image, team2, threashold));
    }

    @org.junit.Test(timeout = 10000)
    public void testFullImageNestedPlayers() {
        String[] image = {
                "11111111111111111111111111111111111111111111111111",
                "1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1",
                "1O1111111111111111111111111111111111111111111111O1",
                "1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1",
                "1O1O111111111111111111111111111111111111111111O1O1",
                "1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1",
                "1O1O1O11111111111111111111111111111111111111O1O1O1",
                "1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1",
                "1O1O1O1O1111111111111111111111111111111111O1O1O1O1",
                "1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1",
                "1O1O1O1O1O111111111111111111111111111111O1O1O1O1O1",
                "1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1",
                "1O1O1O1O1O1O11111111111111111111111111O1O1O1O1O1O1",
                "1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1111111111111111111111O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O111111111111111111O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O11111111111111O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1OOOOOOOOOOOO1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1111111111O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1OOOOOOOO1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O111111O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1OOOO1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1O11O1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1O11O1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1OOOO1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O111111O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1OOOOOOOO1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1111111111O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1OOOOOOOOOOOO1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O11111111111111O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O111111111111111111O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1111111111111111111111O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1",
                "1O1O1O1O1O1O11111111111111111111111111O1O1O1O1O1O1",
                "1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1",
                "1O1O1O1O1O111111111111111111111111111111O1O1O1O1O1",
                "1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1",
                "1O1O1O1O1111111111111111111111111111111111O1O1O1O1",
                "1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1",
                "1O1O1O11111111111111111111111111111111111111O1O1O1",
                "1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1",
                "1O1O111111111111111111111111111111111111111111O1O1",
                "1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1",
                "1O1111111111111111111111111111111111111111111111O1",
                "1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1",
                "11111111111111111111111111111111111111111111111111",                       
        };
        Point[] answer = new Point[13];
        for(int i=0; i<answer.length; i++)
            answer[i] = new Point(50,50);
        Integer team = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team, threashold));
    }
    
    @org.junit.Test(timeout = 10000)
    public void testFullImageNestedTeams() {
        String[] image = {
                "11111111111111111111111111111111111111111111111111",
                "12222222222222222222222222222222222222222222222221",
                "12111111111111111111111111111111111111111111111121",
                "12122222222222222222222222222222222222222222222121",
                "12121111111111111111111111111111111111111111112121",
                "12121222222222222222222222222222222222222222212121",
                "12121211111111111111111111111111111111111111212121",
                "12121212222222222222222222222222222222222221212121",
                "12121212111111111111111111111111111111111121212121",
                "12121212122222222222222222222222222222222121212121",
                "12121212121111111111111111111111111111112121212121",
                "12121212121222222222222222222222222222212121212121",
                "12121212121211111111111111111111111111212121212121",
                "12121212121212222222222222222222222221212121212121",
                "12121212121212111111111111111111111121212121212121",
                "12121212121212122222222222222222222121212121212121",
                "12121212121212121111111111111111112121212121212121",
                "12121212121212121222222222222222212121212121212121",
                "12121212121212121211111111111111212121212121212121",
                "12121212121212121212222222222221212121212121212121",
                "12121212121212121212111111111121212121212121212121",
                "12121212121212121212122222222121212121212121212121",
                "12121212121212121212121111112121212121212121212121",
                "12121212121212121212121222212121212121212121212121",
                "12121212121212121212121211212121212121212121212121",
                "12121212121212121212121211212121212121212121212121",
                "12121212121212121212121222212121212121212121212121",
                "12121212121212121212121111112121212121212121212121",
                "12121212121212121212122222222121212121212121212121",
                "12121212121212121212111111111121212121212121212121",
                "12121212121212121212222222222221212121212121212121",
                "12121212121212121211111111111111212121212121212121",
                "12121212121212121222222222222222212121212121212121",
                "12121212121212121111111111111111112121212121212121",
                "12121212121212122222222222222222222121212121212121",
                "12121212121212111111111111111111111121212121212121",
                "12121212121212222222222222222222222221212121212121",
                "12121212121211111111111111111111111111212121212121",
                "12121212121222222222222222222222222222212121212121",
                "12121212121111111111111111111111111111112121212121",
                "12121212122222222222222222222222222222222121212121",
                "12121212111111111111111111111111111111111121212121",
                "12121212222222222222222222222222222222222221212121",
                "12121211111111111111111111111111111111111111212121",
                "12121222222222222222222222222222222222222222212121",
                "12121111111111111111111111111111111111111111112121",
                "12122222222222222222222222222222222222222222222121",
                "12111111111111111111111111111111111111111111111121",
                "12222222222222222222222222222222222222222222222221",
                "11111111111111111111111111111111111111111111111111",                   
        };
        Point[] answer = new Point[13];
        for(int i=0; i<answer.length; i++)
            answer[i] = new Point(50,50);
        Integer team1 = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team1, threashold));
        Integer team2 = 1;
        playersFinder = (IPlayersFinder)TestRunner.getImplementationInstance();
        assertEqualResult(answer, playersFinder.findPlayers(image, team2, threashold));
    }
    
    @org.junit.Test(timeout = 10000)
    public void testFullImageCenters() {
        String[] image = {
                "11111111111111111111111111111111111111111111111111",
                "1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1",
                "1O1111111111111111111111111111111111111111111111O1",
                "1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1",
                "1O1O111111111111111111111111111111111111111111O1O1",
                "1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1",
                "1O1O1O11111111111111111111111111111111111111O1O1O1",
                "1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1",
                "1O1O1O1O1111111111111111111111111111111111O1O1O1O1",
                "1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1",
                "1O1O1O1O1O111111111111111111111111111111O1O1O1O1O1",
                "1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1",
                "1O1O1O1O1O1O11111111111111111111111111O1O1O1O1O1O1",
                "1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1111111111111111111111O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O111111111111111111O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O11111111111111O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1OOOOOOOOOOOO1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1111111111O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1OOOOOOOO1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O111111O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1OOOO1O1O1O1O1O1O1O1O1O1O1O1",
                "XXXXXXXXXXXXXXXXXXXXXXXXX1O1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1O11O1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O1OOOO1O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1O111111O1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1OOOOOOOO1O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1O1111111111O1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O1OOOOOOOOOOOO1O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1O11111111111111O1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1O111111111111111111O1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1O1111111111111111111111O1O1O1O1O1O1O1",
                "1O1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1O1",
                "1O1O1O1O1O1O11111111111111111111111111O1O1O1O1O1O1",
                "1O1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1O1",
                "1O1O1O1O1O111111111111111111111111111111O1O1O1O1O1",
                "1O1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1O1",
                "1O1O1O1O1111111111111111111111111111111111O1O1O1O1",
                "1O1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1O1",
                "1O1O1O11111111111111111111111111111111111111O1O1O1",
                "1O1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1O1",
                "1O1O111111111111111111111111111111111111111111O1O1",
                "1O1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1O1",
                "1O1111111111111111111111111111111111111111111111O1",
                "1OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1",
                "11111111111111111111111111111111111111111111111111",                       
        };
        Point[] answer = new Point[13];
        for(int i=0; i<answer.length; i++)
            answer[i] = new Point(50,50);
        Integer team1 = 1;
        Integer threashold = 3;
        assertEqualResult(answer, playersFinder.findPlayers(image, team1, threashold));
        Integer team2 = 1;
        playersFinder = (IPlayersFinder)TestRunner.getImplementationInstance();
        assertEqualResult(answer, playersFinder.findPlayers(image, team2, threashold));
    }

}
