import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: LeetCode01 两数之和
 * @link <a href="https://leetcode.cn/problems/two-sum/submissions/569778407/">两数之和</a>
 * @author: daiyifan
 * @create: 2024-10-03 14:33
 */
public class LeetCode1 {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 3};
        int target = 6;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashTable.containsKey(target - nums[i])) {
                return new int[]{hashTable.get(target - nums[i]), i};
            } else {
                hashTable.put(nums[i], i);
            }
        }
        return new int[0];
    }
}
