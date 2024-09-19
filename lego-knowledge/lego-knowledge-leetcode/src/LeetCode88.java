import java.util.Arrays;

class LeetCode88 {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        System.out.println((Arrays.toString(nums1)));
        merge(nums1, 3, nums2, 3);
        System.out.println((nums1));
    }

    /**
     * 双指针解法
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] tmp = new int[nums1.length];
        int i = 0;
        int j = 0;
        int c = 0;
        while (i < m && j < n) {
            tmp[c++] = nums1[i] <= nums2[j] ? nums1[i++] : nums2[j++];
        }
        /**
         * 拷贝数组
         * @see LeetCode88#arraycopy()
         */
        System.arraycopy(nums1, i, tmp, c, m - i);
        System.arraycopy(nums2, j, tmp, c, n - j);
        System.arraycopy(tmp, 0, nums1, 0, tmp.length);
//        System.out.println("tmp = " + Arrays.toString(nums1));
    }

    // System.arraycopy: 拷贝数组
    public static void arraycopy() {
        int[] sourceArray = {1, 2, 3, 4, 5};
        int[] destinationArray = new int[10];

        // 从 sourceArray 的索引 1 处开始，复制 3 个元素到 destinationArray 的索引 2 处
        System.arraycopy(sourceArray, 1, destinationArray, 2, 3);

        // 打印目标数组的内容
        for (int i : destinationArray) {
            System.out.print(i + " ");
        }
        // 输出: 0 0 2 3 4 0 0 0 0 0
    }
}
