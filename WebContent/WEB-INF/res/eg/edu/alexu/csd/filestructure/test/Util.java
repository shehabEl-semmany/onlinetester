package eg.edu.alexu.csd.filestructure.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import eg.edu.alexu.csd.filestructure.sort.INode;

public class Util {
//
//  public static class DummyHeap implements ITree<Integer>{
//      INode<Integer> root;
//      public INode<Integer> getRoot() {   return root;    }
//      public void setRoot(INode<Integer> root) {  this.root = root;   }
//      public int size() { return 0;   }
//  }
//  
//  public static class DummyNode implements INode<Integer>{
//      INode<Integer> left;
//      INode<Integer> right;
//      INode<Integer> parent;
//      Integer v;
//      public DummyNode(Integer v) {
//          this.v = v;
//      }
//      public INode<Integer> getLeftChild() {  return left;    }
//      public void setLeftChild(INode<Integer> child) { left = child;  }
//      public INode<Integer> getRightChild() { return right;   }
//      public void setRightChild(INode<Integer> child) { right = child;    }
//      public INode<Integer> getParent() { return parent;  }
//      public void setParent(INode<Integer> parent) { this.parent = parent;        }
//      public Integer getValue() { return v;   }
//      public void setValue(Integer value) { v = value; }
//      public int compareTo(INode<Integer> node) { return 0; }
//  }
//  
//  public static void createChild(INode<Integer> node, List<Integer> elements){
//      if(elements.isEmpty()) return;
//      double operation = Math.random();
//      if(operation>0.6 && elements.size() > 2){ // two children
//          DummyNode right = new DummyNode(elements.remove(0));
//          DummyNode left = new DummyNode(elements.remove(0));
//          List<Integer> halfList = new LinkedList<Integer>();
//          int halfSize = elements.size()/2;
//          for(int i=0; i<halfSize; i++) halfList.add(elements.remove(0));
//          node.setRightChild(right);
//          createChild(right, halfList);
//          node.setLeftChild(left);
//          createChild(left, elements);
//      }else{ // single child
//          DummyNode child = new DummyNode(elements.remove(0));
//          if(operation > 0.3)
//              node.setLeftChild(child);
//          else
//              node.setRightChild(child);
//          createChild(child, elements);
//      }
//  }
//  
//  public static ITree<Integer> buildTree(List<Integer> elements){
//      DummyHeap dummyHeap = new DummyHeap();
//      DummyNode root = new DummyNode(elements.remove(0));
//      createChild(root, elements);
//      dummyHeap.setRoot(root);
//      return dummyHeap;
//  }
//  
//  public static ITree<Integer> buildHeap(){
//      DummyHeap dummyHeap = new DummyHeap();
//      DummyNode root = new DummyNode(100);
//      root.setLeftChild(new DummyNode(19));
//      root.setRightChild(new DummyNode(36));
//      dummyHeap.setRoot(root);
//      return dummyHeap;
//  }
    
    public static Collection<Integer> toCollection(Integer... elements){
        Collection<Integer> input = new LinkedList<Integer>();
        for(Integer i : elements)
            input.add(i);
        return input;
    }
    
    public static <T extends Comparable<T>> boolean validateHeap(INode<T> node, ArrayList<T> inOrderList) {
        T v = node.getValue();
        inOrderList.add(node.getValue());
        INode<T> left = node.getLeftChild();
        if (left != null) {
            if (v.compareTo(left.getValue()) < 0 || !validateHeap(left, inOrderList))
                return false;
        }
        INode<T> right = node.getRightChild();
        if (right != null) {
            if (v.compareTo(right.getValue()) < 0 || !validateHeap(right, inOrderList))
                return false;
        }
        return true;
    }

    public static ArrayList<Integer> buildRandomInput(int nElements) {
        int maxVal = (int) (0.8 * nElements);
        Random randGen = new Random(0);
        ArrayList<Integer> input = new ArrayList<Integer>(nElements);
        for (int i = 0; i < nElements; i++) {
            int value = randGen.nextInt() % maxVal;
            input.add(value);
        }
        return input;
    }
}
