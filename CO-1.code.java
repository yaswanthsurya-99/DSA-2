public class AttendanceSearch {

    static int linearComparisons = 0;
    static int binaryComparisons = 0;

    // Linear Search
    public static int linearSearch(int[] arr, int key) {
        linearComparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            linearComparisons++;

            if (arr[i] == key) {
                return i + 1; // Position starts from 1
            }
        }

        return -1;
    }

    // Binary Search
    public static int binarySearch(int[] arr, int key) {
        binaryComparisons = 0;

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            binaryComparisons++;

            int mid = (low + high) / 2;

            if (arr[mid] == key) {
                return mid + 1; // Position starts from 1
            } else if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        // Sorted Student IDs
        int[] studentIDs = {101, 102, 103, 104, 105};

        System.out.println("Attendance Search System");

        // First Search
        int searchID = 103;

        System.out.println("\nSearching Student ID: " + searchID);

        int linearResult = linearSearch(studentIDs, searchID);

        System.out.println("\nLinear Search Result:");
        if (linearResult != -1) {
            System.out.println("Student Found at Position " + linearResult);
        } else {
            System.out.println("Student Not Found");
        }

        int binaryResult = binarySearch(studentIDs, searchID);

        System.out.println("\nBinary Search Result:");
        if (binaryResult != -1) {
            System.out.println("Student Found at Position " + binaryResult);
        } else {
            System.out.println("Student Not Found");
        }

        // Second Search
        searchID = 108;

        System.out.println("\nSearching Student ID: " + searchID);

        linearResult = linearSearch(studentIDs, searchID);

        System.out.println("\nLinear Search Result:");
        if (linearResult != -1) {
            System.out.println("Student Found at Position " + linearResult);
        } else {
            System.out.println("Student Not Found");
        }

        binaryResult = binarySearch(studentIDs, searchID);

        System.out.println("\nBinary Search Result:");
        if (binaryResult != -1) {
            System.out.println("Student Found at Position " + binaryResult);
        } else {
            System.out.println("Student Not Found");
        }

        // Performance Summary
        System.out.println("\nPerformance Summary:");
        System.out.println("Linear Search Comparisons : " + linearComparisons);
        System.out.println("Binary Search Comparisons : " + binaryComparisons);
    }
}