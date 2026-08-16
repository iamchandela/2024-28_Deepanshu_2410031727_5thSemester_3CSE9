import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PowerOfTwoMaxHeap {

    private final List<Integer> heap;
    private final int childrenPerNode;

    public PowerOfTwoMaxHeap(int exponentForChildren) {
        if (exponentForChildren < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }
        this.heap = new ArrayList<>();
        // Requirement 2: Every parent node in the heap must have 2^x children.
        // I use Math.pow to calculate 2^x and store it.
        this.childrenPerNode = (int) Math.pow(2, exponentForChildren);
    }

    // Requirement 4: The heap must implement an insert method.
    public void insert(int item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    // Requirement 5: The heap must implement a pop max method.
    public int popMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        int max = heap.get(0);
        int lastItem = heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heap.set(0, lastItem);
            siftDown(0);
        }
        return max;
    }

    private boolean isEmpty() {
        return heap.isEmpty();
    }

    private int getParentIndex(int childIndex) {
        if (childIndex == 0) {
            return -1;
        }
        return (childIndex - 1) / childrenPerNode;
    }

    private int getChildIndex(int parentIndex, int childPosition) {
        // Position should be 1, 2, ..., childrenPerNode
        if (childPosition < 1 || childPosition > childrenPerNode) {
            throw new IllegalArgumentException("Child position must be between 1 and " + childrenPerNode);
        }
        return childrenPerNode * parentIndex + childPosition;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = getParentIndex(index);
            // Requirement 1: Max Heap property check (parent >= child)
            if (heap.get(index) > heap.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int parentIndex) {
        int maxIndex = parentIndex;
        while (true) {
            // Find the child with the maximum value
            int currentChildIdx = 1;
            int currentHeapSize = heap.size();
            while (currentChildIdx <= childrenPerNode) {
                int childIdx = getChildIndex(parentIndex, currentChildIdx);
                if (childIdx < currentHeapSize && heap.get(childIdx) > heap.get(maxIndex)) {
                    maxIndex = childIdx;
                }
                currentChildIdx++;
            }

            // If the max value isn't the parent, swap and continue sifting down
            if (maxIndex != parentIndex) {
                swap(parentIndex, maxIndex);
                parentIndex = maxIndex;
                // Important: Reset maxIndex for next level's comparisons
                // No need to reset, next siftDown will initialize maxIndex
            } else {
                break;
            }
        }
    }

    private void swap(int index1, int index2) {
        int temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

   
    public static void main(String[] args) {
        // Exponent = 1 (2^1 = 2 children per node, like a regular binary max heap)
        PowerOfTwoMaxHeap myBinaryHeap = new PowerOfTwoMaxHeap(1); 
        System.out.println("Testing with Exponent=1 (2 children per node):");
        myBinaryHeap.insert(5);
        myBinaryHeap.insert(10);
        myBinaryHeap.insert(3);
        myBinaryHeap.insert(20);
        System.out.println("Max element: " + myBinaryHeap.popMax() + " (Expected: 20)");
        System.out.println("Next max element: " + myBinaryHeap.popMax() + " (Expected: 10)");

        // Exponent = 2 (2^2 = 4 children per node)
        System.out.println("\nTesting with Exponent=2 (4 children per node):");
        PowerOfTwoMaxHeap myQuaternaryHeap = new PowerOfTwoMaxHeap(2);
        myQuaternaryHeap.insert(100);
        myQuaternaryHeap.insert(50);
        myQuaternaryHeap.insert(120);
        myQuaternaryHeap.insert(30);
        myQuaternaryHeap.insert(200);
        System.out.println("Max element: " + myQuaternaryHeap.popMax() + " (Expected: 200)");
        System.out.println("Next max element: " + myQuaternaryHeap.popMax() + " (Expected: 120)");
    }
}