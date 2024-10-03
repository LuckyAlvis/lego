import java.util.Arrays;

/**
 * @description: LeetCode27 移除元素
 * @link <a href="https://leetcode.cn/problems/remove-element/description/">移除元素</a>
 * @author: daiyifan
 * @create: 2024-10-03 14:33
 */
class LeetCode27 {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        int val = 2;
        int k = removeElement2(nums, val);
        System.out.println("nums = " + (Arrays.toString(nums)));
        System.out.println("k = " + k);
    }

    /**
     * 双指针方式，从左右两侧分别遍历数据，右侧=val的则不管，把左侧=val的数据和右侧!=val的数据交换位置
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        int p1 = 0;
        int p2 = nums.length - 1;
        int k = 0;
        int tmp;
        while (p1 != p2) {
            if (nums[p2] == val) {
                p2--;
//                k++;
                continue;
            }
            if (nums[p1] == val) {
                tmp = nums[p1];
                nums[p1] = nums[p2];
                nums[p2] = tmp;
            }
            p1++;
//            System.out.println("nums = " + Arrays.toString(nums));
        }
        return p1 - 1;
    }

    /**
     * 原地覆盖方式，类似双指针，一个用来记录需要覆盖的位置，一个用来遍历
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement2(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (val != nums[i]) {
                k++;
                nums[k - 1] = nums[i];
            }
        }
        return k;
    }

}
