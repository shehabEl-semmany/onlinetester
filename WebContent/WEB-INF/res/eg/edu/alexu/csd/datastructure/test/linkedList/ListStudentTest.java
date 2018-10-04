package eg.edu.alexu.csd.datastructure.test.linkedList;

import static org.junit.Assert.*;

import eg.edu.alexu.csd.TestRunner;
import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

public class ListStudentTest {
    
    /*
    @org.junit.Rule
    public Timeout globalTimeout = Timeout.seconds(60);
    //*/
    
    public static Class<?> getSpecifications(){
        return ILinkedList.class;
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestAdd() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add(1);
        instance.add(2);
        assertEquals(1, instance.get(0));
        assertEquals(2, instance.get(1));
    }

    @org.junit.Test(timeout = 1000)
    public void cs65TestAdd2() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add(1);
        instance.add(0,2);
        assertEquals(1, instance.get(1));
        assertEquals(2, instance.get(0));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestSet() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add(1);
        instance.add(3);
        instance.add(5);
        instance.set(1, 'F');
        assertEquals('F', instance.get(1));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestSetError() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        try {
            instance.add(1);
            instance.add(2);
            instance.set(4, 'F');
            fail("You should throw an exception when trying to set in a wrong index");
        } catch ( RuntimeException f) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestSubList() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add('a');
        instance.add('b');
        instance.add('c');
        instance.add('d');
        ILinkedList temp = instance.sublist(1, 2);
        assertEquals(temp.get(0), instance.get(1));
        assertEquals(temp.get(1), instance.get(2));
    
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestRemove() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add('a');
        instance.add('b');
        instance.add('c');
        instance.add('d');
        int pastSize = instance.size();
        instance.remove(1);
        assertEquals('c', instance.get(1));
        assertEquals(pastSize-1, instance.size());
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestRemoveError() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        try {
            instance.add(1);
            instance.add(2);
            instance.remove(5);
            fail("You should throw an exception when trying to remove at wrong index");
        } catch ( RuntimeException f) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestAddError() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        try {
            instance.add(1);
            instance.add(2);
            instance.add(-1,5);
            fail("Add with wrong index passed!");
        } catch ( RuntimeException e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestContains() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add(1);
        instance.add(2);
        instance.add(3);
        instance.add(4);
        assertFalse(!instance.contains(1));
        assertFalse(instance.contains(7));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs65TestClear() {
        ILinkedList instance = (ILinkedList)TestRunner.getImplementationInstance();
        instance.add(1);
        instance.add(2);
        instance.add(3);
        instance.add(4);
        instance.clear();
        assertEquals(0, instance.size());
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestAddMany() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<5;i++){
            c.add(i);
        }
        for(int i=0;i<5;i++){
            assertEquals(c.get(i),i);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestAddRemoveTwoLists() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<5;i++){
            c.add(i);
        }
        ILinkedList d = (ILinkedList)TestRunner.getImplementationInstance();
        d.add(1);
        d.add(2);
        d.add(3);
        c.remove(0);
        c.remove(3);
        for(int i=0;i<3;i++){
            assertEquals(c.get(i),d.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestAddRemoveTwoLists2() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<3;i++){
            c.add(i);
        }
        c.add(1,3);
        c.add(2,4);
        ILinkedList d = (ILinkedList)TestRunner.getImplementationInstance();
        d.add(0);
        d.add(3);
        d.add(4);
        d.add(1);
        d.add(2);
        for(int i=0;i<5;i++){
            assertEquals(c.get(i),d.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestAddRemoveTwoLists3() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<3;i++){
            c.add(i);
        }
        c.add(0,3);
        c.add(4,4);
        ILinkedList d = (ILinkedList)TestRunner.getImplementationInstance();
        d.add(3);
        d.add(0);
        d.add(1);
        d.add(2);
        d.add(4);
        for(int i=0;i<5;i++){
            assertEquals(c.get(i),d.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestAddRemoveTwoLists4() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<3;i++){
            c.add(i);
        }
        c.add(0,3);
        c.add(4,4);
        c.remove(1);
        c.remove(3);
        ILinkedList d = (ILinkedList)TestRunner.getImplementationInstance();
        d.add(3);
        d.add(1);
        d.add(2);
        assertEquals(c.size(), 3);
        for(int i=0;i<3;i++){
            assertEquals(c.get(i),d.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestAddRemoveTwoLists5() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<3;i++){
            c.add(i);
        }
        c.add(0,3);
        c.add(4,4);
        c.set(1, 7);
        c.set(4,9);
        ILinkedList d = (ILinkedList)TestRunner.getImplementationInstance();
        d.add(3);
        d.add(7);
        d.add(1);
        d.add(2);
        d.add(9);
        for(int i=0;i<5;i++){
            assertEquals(c.get(i),d.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestSublist() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<5;i++){
            c.add(i);
        }
        for(int i=0;i<5;i++){
            assertEquals(c.get(i),i);
        }
        ILinkedList d=(ILinkedList)c.sublist(1, 3);
        assertEquals(d.size(), 3);
        int k=0;
        for(int i=1;i<4;i++){
            assertEquals(d.get(k++),i);
        }
        assertEquals(c.size(), 5);
        for(int i=0;i<5;i++){
            assertEquals(c.get(i),i);
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestContains() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i=0;i<3;i++){
            c.add(i);
        }
        c.add(0,3);
        c.add(4,4);
        
        assertTrue(c.contains(4));
        assertTrue(c.contains(0));
        assertTrue(c.contains(1));
        assertTrue(c.contains(2));
        assertTrue(c.contains(3));
        assertFalse(c.contains(9));
        assertFalse(c.contains(7));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs56TestIsEmptyAndClear() {
        ILinkedList c = (ILinkedList)TestRunner.getImplementationInstance();
        assertTrue(c.isEmpty());
        for(int i=0;i<3;i++){
            c.add(i);
        }
        c.add(0,3);
        c.add(4,4);
        
        assertFalse(c.isEmpty());
        c.clear();
        assertTrue(c.isEmpty());
    }

    @org.junit.Test(timeout = 1000)
    public void cs52TestAdd() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(1);
        object.add(2);
        object.add(3);
        assertEquals(object.get(0), 1);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestAddAtIndex() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(1);
        object.add(2);
        object.add(3);
        object.add(2, 5);
        assertEquals(object.get(2), 5);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSet() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(1);
        object.add(2);
        object.add(3);
        object.set(1, 4);
        assertEquals(object.get(1), 4);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestIsEmpty() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(1);
        object.add(2);
        object.add(3);
        object.add(4);
        object.add(5);
        assertEquals(object.isEmpty(), false);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestAddAndSet() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        object.set(1, 10);
        object.set(4, 40);
        assertEquals(object.get(4), 40);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSetAndSize() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        object.set(1, 10);
        object.set(4, 40);
        assertEquals(object.size(), 6);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSetAndRemove(){
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        object.set(1, 10);
        object.set(4, 40);
        object.remove(1);
        assertEquals(object.get(1), 2);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSetAndRemove2() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        object.set(1, 10);
        object.set(4, 40);
        object.remove(1);
        object.remove(2);
        object.remove(3);
        assertEquals(object.get(1), 2);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestClearAndIsEmpty() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        object.clear();
        assertEquals(object.isEmpty(), true);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestContains() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        assertEquals(object.contains(5), true);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSize() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        assertEquals(object.size(), 6);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSublist() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        ILinkedList lista=(ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        lista=object.sublist(1, 3);
        
        for(int i=1;i<4;i++) {
        	assertEquals(lista.get(i-1),i );
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestContains2() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        assertEquals(object.contains(48), false);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestAddAtIndex2() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        object.add(6);
        object.add(7);
        object.add(8);
        object.add(9);
        object.add(10);
        object.add(11);
        object.add(12);
        assertEquals(object.get(11), 11);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestAddAndClearAndSet() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        
        object.clear();
        
        object.add(0,0);
        object.add(1,1);
        object.add(2,2);
        object.add(3,3);
        object.add(4,4);
        object.add(5,5);
        
        object.set(0,10);
        object.set(2,20);
        object.set(4,40);
        
        assertEquals(object.get(4), 40);
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestSetError() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,1);
        
        try{
            object.set(2,0);
            fail("Set in an invalid index passed!");
        }
        catch(Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs52TestGetInvalid() {
        ILinkedList object = (ILinkedList)TestRunner.getImplementationInstance();
        object.add(0,1);
        try{
            object.get(2);
            fail("Get from an invalid index passed!");
        }
        catch(Exception e) {
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs18TestAddDiffTypes() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add("EGYPT");
        list.add("australia");
        list.add(15.555);
        list.add("");
        assertEquals(10, list.get(0));
        assertEquals("EGYPT", list.get(1));
        assertEquals("australia", list.get(2));
        assertEquals(15.555, list.get(3));
        assertEquals("", list.get(4));
        try {
            list.get(-1);
            fail("Get from invalid index passed!");
        } catch (Exception e) {
        }

    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestAddDiffTypes2() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(666666);
        list.add("EGYPT");
        list.add(true);
        list.add("australia");
        list.add(2, 15.555);
        assertEquals(666666, list.get(0));
        assertEquals("EGYPT", list.get(1));
        assertEquals(15.555, list.get(2));
        assertEquals(true, list.get(3));
        assertEquals("australia", list.get(4));
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestAddDiffTypes3() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add("EGYPT");
        list.add(true);
        list.add("australia");
        list.set(2, 15.555);
        assertEquals(15.555, list.get(2));
        assertEquals("EGYPT", list.get(1));
        assertEquals("australia", list.get(3));
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestSublist() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        ILinkedList sublist = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10000);
        list.add(20000);
        list.add(30000);
        list.add(40000);
        list.add(50000);
        list.add(60000);
        list.add(70000);
        list.add(80000);
        sublist = list.sublist(4, 6);
        assertEquals(50000, sublist.get(0));
        assertEquals(60000, sublist.get(1));
        assertEquals(70000, sublist.get(2));
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestRemove() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add("EGYPT");
        list.add(true);
        list.add("australia");
        list.add(15.555);
        assertEquals("australia", list.get(3));
        assertEquals(5, list.size());
        list.remove(3);
        assertEquals(15.555, list.get(3));
        assertEquals(4, list.size());
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestContains() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(100);
        list.add("EGYPT");
        list.add(true);
        list.add("australia");
        list.add(15.555);
        assertEquals(true, list.contains(100));
        assertEquals(false, list.contains("sweden"));
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestSize() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add("EGYPT");
        list.add(true);
        list.add("australia");
        list.add(15.555);
        assertEquals(5, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestRemoveError() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add("EGYPT");
        list.add("australia");
        list.add(15.555);
        list.add(true);
        try {
            list.remove(-1);
            fail("Remove from invalid index passed!");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestSublist2() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        ILinkedList sublist = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        list.add(60);
        sublist = list.sublist(2, 4);
        sublist.add(true);
        assertEquals(4, sublist.size());
        assertEquals(50, sublist.get(2));
        assertEquals(true, sublist.get(3));
    }

    @org.junit.Test(timeout = 1000)
    public void cs18TestSublistError() {
        ILinkedList list = (ILinkedList)TestRunner.getImplementationInstance();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        list.add(60);
        try {
            list.sublist(4, 6);
            fail("You should throw an exception when trying to get a sublist with invalid 'from' or 'to'");
        } catch (Exception e) {
        }
    }

    @org.junit.Test(timeout = 1000)
    public void cs55TestSizeContains() {
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i = 5 ; i < 15 ; ++i){
            test.add(Integer.valueOf(i));
        }
        test.add(Integer.valueOf(1000000));
        assertEquals(11 , test.size());
        assertEquals(true , test.contains(Integer.valueOf(1000000)));
        assertEquals(false , test.contains(Integer.valueOf(4)));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestClear() {
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i = 5 ; i < 15 ; ++i){
            test.add(Integer.valueOf(i));
        }
        assertEquals(10 , test.size());
        assertEquals(false , test.isEmpty());
        test.clear();
        assertEquals(0 , test.size());
        assertEquals(true , test.isEmpty());
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestAddToIndex() {
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i = 5 ; i < 15 ; ++i){
            test.add(Integer.valueOf(i));
        }
        assertEquals(10 , test.size());
        assertEquals(false , test.contains(Integer.valueOf(15)));
        test.add(2, Integer.valueOf(15));
        assertEquals(11 , test.size());
        assertEquals(true , test.contains(Integer.valueOf(15)));
        assertEquals(Integer.valueOf(15) , test.get(2));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestRemoveNode() {
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for(int i = 5 ; i < 15 ; ++i){
            test.add(Integer.valueOf(i));
        }
        assertEquals(10 , test.size());
        assertEquals(true , test.contains(Integer.valueOf(7)));
        test.remove(2);
        assertEquals(9 , test.size());
        assertEquals(false , test.contains(Integer.valueOf(7)));
        assertEquals(Integer.valueOf(8) , test.get(2));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestRemoveAndContains() {
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for (int i = 0 ; i < 10 ; i ++){
            test.add(Integer.valueOf(i));
        }
        int inSize = 10;
        for (int i = 0 ; i < 5 ; i ++){
            test.remove(0);
            assertEquals(--inSize , test.size());
            assertEquals(false , test.contains(Integer.valueOf(i)));
        }
        for(int i = 0;i < 5; ++i){
            assertEquals(Integer.valueOf(5+i) , test.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestSublist(){
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for (int i = 0 ; i < 10 ; i++){
            test.add(Integer.valueOf(i));
        }
        ILinkedList sub = (ILinkedList)test.sublist(5, 7);
        assertEquals(3 , sub.size());
        for(int i = 0 ; i < 3 ; ++i){
            assertEquals(Integer.valueOf(5+i) , sub.get(i));
        }
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestAddObject(){
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        int a[] = {1 , 2, 3, 4, 5};
        for (int i = 0 ; i < 10 ; i++){
            test.add(a);
        }
        assertEquals(10 , test.size());
        assertEquals(a , test.get(0));
    }
    
    @org.junit.Test(timeout = 1000)
    public void cs55TestAddChar(){
        ILinkedList test = (ILinkedList)TestRunner.getImplementationInstance();
        for (int i = 0 ; i < 26 ; i++){
            test.add(i , (char)('a'+i));
        }
        String x = "";
        for(int i = 0 ; i < 26; ++i){
            x+=test.get(i);
        }
        assertEquals("abcdefghijklmnopqrstuvwxyz", x);
    }
    
}
