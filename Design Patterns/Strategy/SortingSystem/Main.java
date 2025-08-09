package SortingSystem;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Integer[] nums = new Integer[100000];
        for (int i = 0; i < 100000; i++) {
            nums[i] = new Random().nextInt();
        }
        
        Sorter<Integer> sorter = new Sorter<>(new HeapSort());

        /*
         * Time required by Heap Sort
         */
        long start = System.currentTimeMillis();
        sorter.sort(Arrays.copyOf(nums, nums.length));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        /*
         * Time required by Merge Sort
         */
        sorter.setSortingStrategy(new MergeSort());
        start = System.currentTimeMillis();
        sorter.sort(Arrays.copyOf(nums, nums.length));
        end = System.currentTimeMillis();
        System.out.println(end - start);

        /*
         * Time required by Quick Sort
         */
        sorter.setSortingStrategy(new Quicksort());
        start = System.currentTimeMillis();
        sorter.sort(Arrays.copyOf(nums, nums.length));
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
