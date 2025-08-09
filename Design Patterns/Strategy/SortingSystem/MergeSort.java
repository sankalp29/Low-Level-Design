package SortingSystem;

public class MergeSort implements SortingStrategy<Integer> {

    @Override
    public Integer[] sort(Integer[] data) {
        if (data == null || data.length <= 1) {
            return data; // Already sorted
        }
        mergeSort(data, 0, data.length - 1);
        return data;
    }
    
    private void mergeSort(Integer[] data, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(data, left, mid);      // Sort left half
            mergeSort(data, mid + 1, right); // Sort right half
            merge(data, left, mid, right);   // Merge sorted halves
        }
    }
    
    private void merge(Integer[] data, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
    
        Integer[] leftArray = new Integer[n1];
        Integer[] rightArray = new Integer[n2];
    
        // Copy data into temp arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = data[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = data[mid + 1 + j];
        }
    
        // Merge temp arrays back into original
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                data[k++] = leftArray[i++];
            } else {
                data[k++] = rightArray[j++];
            }
        }
    
        // Copy remaining elements
        while (i < n1) {
            data[k++] = leftArray[i++];
        }
        while (j < n2) {
            data[k++] = rightArray[j++];
        }
    }    
}
