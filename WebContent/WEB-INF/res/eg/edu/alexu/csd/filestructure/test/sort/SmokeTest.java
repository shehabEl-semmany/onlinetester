package eg.edu.alexu.csd.filestructure.test.sort;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;

import eg.edu.alexu.csd.TestRunner;

import eg.edu.alexu.csd.filestructure.sort.INode;
import eg.edu.alexu.csd.filestructure.sort.IHeap;

import eg.edu.alexu.csd.filestructure.test.Util;

public class SmokeTest {
    
    public static Class<?> getSpecifications(){
        return IHeap.class;
    }
    
    @org.junit.Test
    public void testBuildInput() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();
        Collection<Integer> input = Util.toCollection(6, 3, 5, 7, 1, 4, 2);
        heap.build(input);
        Assert.assertEquals(input.size(), heap.size());
    }
    
    @org.junit.Test
    public void testInsertHeapElement() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();
        Assert.assertEquals(0, heap.size());
        heap.insert(1000);
        heap.insert(36);
        heap.insert(19);
        Assert.assertEquals(3, heap.size());
    }
    
    @org.junit.Test
    public void testExtractHeapElement() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();
        heap.insert(1000);
        heap.insert(36);
        heap.insert(19);
        int sizeBefore = heap.size();
        Integer max = heap.extract();
        Assert.assertNotNull(max);
        int sizeAfter = heap.size();
        Assert.assertEquals(sizeBefore-1, sizeAfter);
    }
    
    @org.junit.Test
    public void testEmptyHeap() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();
        heap.insert(1000);
        heap.insert(36);
        heap.insert(19);
        int size = heap.size();
        for(int i=0; i<size; i++)
            Assert.assertNotNull(heap.extract());
        Assert.assertEquals(0, heap.size());
    }
    

    // tests buildMaxHeap small
    @org.junit.Test
    public void testBuildMaxHeap() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();
        int nElements = 1000;
        ArrayList<Integer> input = Util.buildRandomInput(nElements);
//      Collections.shuffle(input);

        heap.build(input);

        Assert.assertNotNull(heap.getRoot());

        ArrayList<Integer> list = new ArrayList<Integer>();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), list));
        Assert.assertEquals(input.size(), list.size());

        Collections.sort(list);
        Integer[] inputArray = new Integer[input.size()];
        input.toArray(inputArray);
        Arrays.sort(inputArray);
        boolean coverageFlag = true;
        for (int i = 0; i < inputArray.length && coverageFlag; i++) {
            coverageFlag &= inputArray[i].equals(list.get(i));
        }
        Assert.assertTrue(coverageFlag);
    }
    
    @org.junit.Test
    public void testGenericTypeBuildInput() {
        @SuppressWarnings("unchecked")
        IHeap<String> heap = (IHeap<String>)TestRunner.getImplementationInstance();
//      Collection<Integer> input = Util.toCollection(6, 3, 5, 7, 1, 4, 2);
        String[] inputArray = {"Fawzy", "Caroline", "Ebtehal", "Gamal", "Ahmed", "Dina", "Bahi"}; 
        ArrayList<String> input = new ArrayList<String>();
        for (String s : inputArray)
            input.add(s);
        heap.build(input);
        Assert.assertNotNull(heap.getRoot());

        ArrayList<String> list = new ArrayList<String>();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), list));
        Assert.assertEquals(input.size(), list.size());

        Collections.sort(list);
        Arrays.sort(inputArray);
        boolean coverageFlag = true;
        for (int i = 0; i < inputArray.length && coverageFlag; i++) {
            coverageFlag &= inputArray[i].equals(list.get(i));
        }
        Assert.assertTrue(coverageFlag);
    }

    @org.junit.Test
    public void testMaxHeapify() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();

        Collection<Integer> input = Util.toCollection(6, 3, 5, 7, 1, 4, 2);
        heap.build(input);
        // expected heap tree order according to order of input: 7,6,5,3,1,4,2

        // test (a): heapify a leaf node:
        INode<Integer> leaf = heap.getRoot().getLeftChild().getLeftChild();
        heap.heapify(leaf);
        ArrayList<Integer> holder = new ArrayList<Integer>();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), holder));
        // expected heap tree order after maxHeapify: 7,6,5,3,1,4,2

        // test (b): heapfiy an intermediate
        INode<Integer> intermediate1 = heap.getRoot().getRightChild();
        intermediate1.setValue(1);
        heap.heapify(intermediate1);
        holder.clear();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), holder));
        // expected heap tree order after maxHeapify: 7,6,4,3,1,1,2

        // test (c): heapify a root node, but not till leaves
        INode<Integer> root1 = heap.getRoot();
        root1.setValue(3);
        heap.heapify(root1);
        holder.clear();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), holder));
        // expected heap tree order after maxHeapify: 6,3,4,3,1,1,2
        
        // test (d): heapify a root node, down to a leaves
        INode<Integer> root2 = heap.getRoot();
        root2.setValue(0);
        heap.heapify(root2);
        holder.clear();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), holder));
        // expected heap tree order after maxHeapify: 4,3,2,3,1,1,0
        
    }
    
    @org.junit.Test
    public void testMaxHeapExtract() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();

        Collection<Integer> input = Util.toCollection(6, 3, 5, 7, 1, 4, 2);
        heap.build(input);
        // expected heap tree order according to order of input: 7,6,5,3,1,4,2
        ArrayList<Integer> holder = new ArrayList<Integer>();
        Integer[] sortedInput = new Integer[input.size()];
        input.toArray(sortedInput);
        Arrays.sort(sortedInput);
        
        for (int i = sortedInput.length - 1; i > 0; --i) {
            Integer max = heap.extract();
            Assert.assertEquals(sortedInput[i], max);
            Assert.assertTrue(Util.validateHeap(heap.getRoot(), holder));
            holder.clear();
        }
    }

    @org.junit.Test
    public void testMaxHeapInsert() {
        @SuppressWarnings("unchecked")
        IHeap<Integer> heap = (IHeap<Integer>)TestRunner.getImplementationInstance();
        heap.build(new ArrayList<Integer>()); // empty heap
        Collection<Integer> input = Util.toCollection(6, 3, 5, 7, 1, 4, 2);
        for (Integer element : input) {
            heap.insert(element);
        }
        ArrayList<Integer> holder = new ArrayList<Integer>();
        Assert.assertTrue(Util.validateHeap(heap.getRoot(), holder));
    }
    
}
