package SortingSystem;

public class HeapSort implements SortingStrategy<Integer>{

    @Override
    public Integer[] sort(Integer[] data) {
        int n = data.length;
    
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(data, n, i);
        }
    
        // Extract elements one by one from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = data[0];
            data[0] = data[i];
            data[i] = temp;
    
            // Call max heapify on the reduced heap
            heapify(data, i, 0);
        }
    
        return data;
    }
    
    // To heapify a subtree rooted with node i, n is size of heap
    private void heapify(Integer[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left child
        int right = 2 * i + 2; // right child
    
        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
    
        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
    
        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
    
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}
