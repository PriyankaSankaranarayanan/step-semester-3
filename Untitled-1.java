import java.util.*;

public class DataStructurePractice {

    // ============================================================
    // Problem 1: Library Catalog Lookup
    // ============================================================
    // Time Complexity:  O(log n) — binary search on sorted catalog
    // Space Complexity: O(1)     — iterative, no extra structures
    // ============================================================

    /**
     * Represents a book record with ISBN and title.
     */
    static class Book {
        String isbn;
        String title;

        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    /**
     * catalog: array of Book objects, sorted by ISBN ascending
     * targetIsbn: ISBN string to search for
     * Returns the title if found, else "Not Found"
     */
    public static String findBook(Book[] catalog, String targetIsbn) {
        int low = 0;
        int high = catalog.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = catalog[mid].isbn.compareTo(targetIsbn);

            if (cmp == 0) {
                return catalog[mid].title;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return "Not Found";
    }


    // ============================================================
    // Problem 2: Warehouse Grid Summary
    // ============================================================
    // Time Complexity:  O(m * n) — must visit every cell once
    // Space Complexity: O(1)     — only a few tracking variables
    // ============================================================

    /**
     * grid: 2D array of non-negative ints
     * Returns an int[] of size 3: {totalItems, maxRow, maxCol}
     * (Using int[] instead of tuple since Java lacks native tuples.)
     * Ties broken by first occurrence in row-major order.
     */
    public static int[] warehouseSummary(int[][] grid) {
        int totalItems = 0;
        int maxValue = -1;
        int maxRow = 0;
        int maxCol = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int value = grid[r][c];
                totalItems += value;

                if (value > maxValue) {
                    maxValue = value;
                    maxRow = r;
                    maxCol = c;
                }
            }
        }

        return new int[]{totalItems, maxRow, maxCol};
    }


    // ============================================================
    // Problem 3 & 4: Pair With Target Sum (Unsorted)
    // (Both problems are identical in requirements.)
    // ============================================================

    // ---------- Approach A: Brute Force ----------
    // Time Complexity:  O(n^2) — check every pair
    // Space Complexity: O(1)   — no extra storage
    // ============================================================

    public static boolean hasPairWithSumBruteforce(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    // ---------- Approach B: Hash Set (Optimal) ----------
    // Time Complexity:  O(n) — single pass, O(1) average set lookups
    // Space Complexity: O(n) — set may store up to n elements
    // ============================================================

    /**
     * Returns true if any two distinct elements sum to target.
     * Uses a HashSet to check complements in O(1) average time.
     */
    public static boolean hasPairWithSum(int[] nums, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            int complement = target - num;
            if (seen.contains(complement)) {
                return true;
            }
            seen.add(num);
        }
        return false;
    }


    // ============================================================
    // Problem 5: Maximize Area Between Two Boundaries
    // ============================================================

    // ---------- Approach A: Brute Force ----------
    // Time Complexity:  O(n^2) — check every pair of walls
    // Space Complexity: O(1)
    // ============================================================

    public static int maxContainerAreaBruteforce(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int area = Math.min(heights[i], heights[j]) * (j - i);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    // ---------- Approach B: Two Pointers (Optimal) ----------
    // Time Complexity:  O(n) — each pointer moves at most n steps
    // Space Complexity: O(1)
    // ============================================================

    /**
     * Returns the maximum water container area.
     * Area = min(heights[i], heights[j]) * (j - i)
     */
    public static int maxContainerArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        int maxArea = 0;

        while (left < right) {
            int width = right - left;
            int height = Math.min(heights[left], heights[right]);
            int area = height * width;
            maxArea = Math.max(maxArea, area);

            // Move the pointer at the shorter wall inward
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }


    // ============================================================
    // Main method: Quick self-test / demo
    // ============================================================
    public static void main(String[] args) {
        // Problem 1
        Book[] catalog = {
            new Book("0001112223", "Introduction to Algebra"),
            new Book("0002223334", "Beginning Python"),
            new Book("0003334445", "Classic Mythology"),
            new Book("0004445556", "Data and Society"),
            new Book("0005556667", "European History")
        };
        System.out.println(findBook(catalog, "0003334445"));  // Classic Mythology
        System.out.println(findBook(catalog, "0009998887"));  // Not Found

        // Problem 2
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };
        int[] summary = warehouseSummary(grid);
        System.out.println("(" + summary[0] + ", (" + summary[1] + ", " + summary[2] + "))");
        // Expected: (49, (2, 1))

        // Problems 3 & 4
        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9));  // true
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));      // false
        System.out.println(hasPairWithSumBruteforce(new int[]{2, 7, 11, 15}, 9));  // true

        // Problem 5
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxContainerArea(heights));             // 49
        System.out.println(maxContainerAreaBruteforce(heights));   // 49
    }
}