import java.util.*;
import java.util.function.Consumer;

public class SortTest2 {

    public static void insertionSort(int[] data) {
        int n = data.length;
        for (int i = 1; i < n; ++i) {
            int key = data[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }

    public static void bubbleSort(int[] data) {
        int n = data.length;
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for ( j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;
                } 
            }

            if (swapped == false)
                break; 
        }
    }

    public static int partition(int[] data, int low, int high) {

        int middle = low + (high - low) / 2;

        swap(data, middle, high);

        int pivot = data[high];
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            if (data[j] < pivot) {
                i++;
                swap(data, i, j);
            }
        }

        swap(data, i + 1, high);

        return i + 1;
    }
    
    public static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void quickSort(int[] data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);

            quickSort(data, low, pi - 1);
            quickSort(data, pi + 1, high);
        }
    }

    public static void merge(int data[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = data[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = data[m + 1 + j];

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                data[k] = L[i];
                i++;
            }
            else {
                data[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            data[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            data[k] = R[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int data[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(data, l, m);
            mergeSort(data, m + 1, r);

            merge(data, l, m, r);
        }
    }

    public static void shellSort(int data[]) {
        int n = data.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = data[i];
                int j = i;
                while (j >= gap && data[j - gap] > key) {
                    data[j] = data[j - gap];
                    j -= gap;
                }
                data[j] = key;
            }
        }
    }

    public static void printData(int[] data) {
        for (int x : data) {
            System.out.print(x + " ");
        }
        System.out.println("--------------------------");
        System.out.println();
    }



    public static int[] createAscendingData(int n) {
        int[] data = new int[n];

        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }

        return data;
    }

    public static int[] createDescendingData(int n) {
        int[] data = new int[n];

        for (int i = 0; i < data.length; i++) {
            data[i] = n - i;
        }

        return data;
    }

    public static void testSort(String name, int[] data, Consumer<int[]> sort) {
                            

        int[] copy = Arrays.copyOf(data, data.length);

        long start = System.nanoTime();

        sort.accept(copy);

        long stop = System.nanoTime();

        System.out.println(name + ": "
            + (stop - start) / 1000000000.0 + " seconds");
    }

    public static int[] createRandomData(int n) {
        int[] data = new int[n];

        Random gen = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = gen.nextInt(n);
        }

        return data;
    }
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int size = 100000;
        int[] data;

        System.out.println("Choose data:");
        System.out.println("1. Random data");
        System.out.println("2. Ascending data");
        System.out.println("3. Descending data");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();

        switch (choice) {

            case 1:
                data = createRandomData(size);
                break;

            case 2:
                data = createAscendingData(size);
                break;

            case 3:
                data = createDescendingData(size);
                break;

            default:
                System.out.println("Invalid choice!");
                scanner.close();
                return;
    }

        System.out.println("\n--- Sorting results ---");

        testSort("Insertion Sort", data, SortTest::insertionSort);

        testSort("Bubble Sort", data, SortTest::bubbleSort);

        testSort("Shell Sort", data, SortTest::shellSort);

        testSort("Array Sort", data, Arrays::sort);

        testSort("Quick Sort", data,
                arr -> quickSort(arr, 0, arr.length - 1));

        testSort("Merge Sort", data,
                arr -> mergeSort(arr, 0, arr.length - 1));

    scanner.close();
}
}
