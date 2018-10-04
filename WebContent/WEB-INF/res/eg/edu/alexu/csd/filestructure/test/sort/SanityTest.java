package eg.edu.alexu.csd.filestructure.test.sort;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;

import eg.edu.alexu.csd.TestRunner;

import eg.edu.alexu.csd.filestructure.sort.INode;
import eg.edu.alexu.csd.filestructure.sort.ISort;
import eg.edu.alexu.csd.filestructure.sort.IHeap;

import eg.edu.alexu.csd.filestructure.test.Util;

public class SanityTest {
    
    public static Class<?> getSpecifications(){
        return ISort.class;
    }

    @org.junit.Test
    public void testHeapSort() {
        @SuppressWarnings("unchecked")
        ISort<Integer> instance = (ISort<Integer>) TestRunner.getImplementationInstance();
        Collection<Integer> inputCollection = Util.toCollection(6, 3, 5, 7, 1, 4, 2);
        ArrayList<Integer> input = new ArrayList<Integer>(inputCollection);
        Integer[] sortedInput = new Integer[input.size()];
        input.toArray(sortedInput);
        Arrays.sort(sortedInput);
        IHeap<Integer> sorted = instance.heapSort(input);
        ArrayList<Integer> bfs = new ArrayList<Integer>();
        Queue<INode<Integer>> q = new LinkedList<INode<Integer>>();
        q.add(sorted.getRoot());
        while(!q.isEmpty()) {
            INode<Integer> current = q.poll();
            bfs.add(current.getValue());
            if (current.getLeftChild() != null)
                q.add(current.getLeftChild());
            if (current.getRightChild() != null)
                q.add(current.getRightChild());
        }
        boolean sortedFlag = true;
        for (int i = 0; i < sortedInput.length && sortedFlag; ++i) {
            sortedFlag &= sortedInput[i] == bfs.get(i);
        }
        Assert.assertTrue(sortedFlag);
    }

    @org.junit.Test(timeout = 2000)
    public void testSortSlow() {
        @SuppressWarnings("unchecked")
        ISort<Integer> instance = (ISort<Integer>) TestRunner.getImplementationInstance();
        int nElements = 1000;
        ArrayList<Integer> input = Util.buildRandomInput(nElements);
        instance.sortSlow(input);
        for (int i = 1; i < input.size(); ++i)
            Assert.assertTrue(input.get(i) >= input.get(i-1));
    }

    @org.junit.Test(timeout = 2000)
    public void testSortFast() {
        @SuppressWarnings("unchecked")
        ISort<Integer> instance = (ISort<Integer>) TestRunner.getImplementationInstance();
        int nElements = 100000;
        ArrayList<Integer> input = Util.buildRandomInput(nElements);
        instance.sortFast(input);
        for (int i = 1; i < input.size(); ++i)
            Assert.assertTrue(input.get(i) >= input.get(i-1));
    }

}
