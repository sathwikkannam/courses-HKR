package Task_1;

import java.util.Arrays;

public class BinaryHeap{
    private final int[] heap;
    private int index;


    public BinaryHeap(int size){
       this.heap = new int[size + 1];
       this.index = 0;

    }


    // Add to the end of the heap.
    // Then check from key -> root to heapify up.
    public void insert(int key){
        heap[index++] = key;
        minHeapify(index - 1);
    }


    /*
        Check from bottom node to the root.
        If the child has a parent and the parent's key is > last node: swap them,
        Then jump to its parent node and repeat until a node has no parent.
    */
    private void minHeapify(int lastChild){
        if(!hasParent(lastChild)){
            return;
        }

        if(getParentOf(lastChild) > getKey(lastChild)){
            swap(getParentIndexOf(lastChild), lastChild);
            minHeapify(getParentIndexOf(lastChild));
        }

    }

    private int getLeftChildOf(int parentIndex){
        return heap[(2 * parentIndex) + 1];
    }

    private int getRightChildOf(int parentIndex){
        return heap[(2 * parentIndex) + 2];
    }

    private int getParentIndexOf(int childIndex){
        return (childIndex  - 1) / 2;
    }


    private boolean hasParent(int childIndex) {
        return getParentIndexOf(childIndex) >= 0;
    }

    private int getParentOf(int childIndex){
        return heap[getParentIndexOf(childIndex)];
    }

    private int getKey(int index){
        return this.heap[index];
    }

    private void swap(int a , int b){
        int temp = heap[a];

        heap[a] = heap[b];
        heap[b] = temp;

    }



    @Override
    public String toString(){
        return Arrays.toString(heap);

    }


}
