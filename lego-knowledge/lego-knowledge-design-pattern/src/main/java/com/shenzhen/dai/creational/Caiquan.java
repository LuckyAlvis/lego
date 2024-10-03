package com.shenzhen.dai.creational;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

/**
 * @description: 猜拳概率
 * @author: daiyifan
 * @create: 2024-05-19 11:47
 */
@Slf4j
public class Caiquan {
    @AllArgsConstructor
    private enum QuanTou {
        ST("石", "ST"),
        JD("剪", "JD"),
        BU("布", "BU"),
        ;
        private final String name;
        private final String code;

        public static QuanTou random(String name) {
            QuanTou quantou = QuanTou.values()[RandomUtils.nextInt(0, QuanTou.values().length)];
            System.out.print(name + "出" + quantou.name + "\t");
            return quantou;
        }

        private Boolean checkWin(QuanTou quanTou) {
            if (this.equals(QuanTou.ST)) {
                return quanTou.equals(QuanTou.JD);
            }

            if (this.equals(QuanTou.JD)) {
                return quanTou.equals(QuanTou.BU);
            }

            if (this.equals(QuanTou.BU)) {
                return quanTou.equals(QuanTou.ST);
            }
            System.out.println("数据有误------------------");
            return false;
        }
    }

    private static int aWinCount = 0;
    private static int bWinCount = 0;

    public static void main(String[] args) {
        for (int j = 0; j < 1; j++) {
            // 模拟A出拳
            long start = System.currentTimeMillis();
            for (int i = 1; i <= 10; i++) {
                System.out.println("第" + i + "局：");
                QuanTou resA = QuanTou.random("A");
                QuanTou resB = QuanTou.random("B");
                if (resA.equals(resB)) {
                    System.out.println("平局，重来\n");
                    continue;
                }
                Boolean win = checkWin(resA, resB);
                if (win) {
                    aWinCount++;
                    System.out.println("游戏结束\n");
                } else {
                    bT++;
                    System.out.println("，进入三局两胜");
                    Boolean winA = twoWinOverThree();
                    if (winA) {
                        aWinCount++;
                    } else {
                        bWinCount++;
                    }
                }
                currentCount();
            }
            System.out.println("\nA赢了" + aWinCount + "B赢了" + bWinCount +
                    "\nA胜率：" + (float) aWinCount / (aWinCount + bWinCount) * 100);
            long end = System.currentTimeMillis();
//            System.out.println(end - start);
        }

    }

    private static void currentCount() {
        System.out.println("\n------------------------当前比分" + aWinCount + ":" + bWinCount + "------------------------");
    }

    private static int aT = 0;
    private static int bT = 0;

    private static Boolean twoWinOverThree() {
//        System.out.println("\tA:B=" + aT + ":" + bT);
        System.out.print("\t");
        QuanTou resA = QuanTou.random("A");
        System.out.print("\t");
        QuanTou resB = QuanTou.random("B");
        if (resA.equals(resB)) {
            System.out.println("出拳结果一样，重来");
            twoWinOverThree();
        }
        Boolean win = checkWin(resA, resB);
        if (win) {
            aT++;
        } else {
            bT++;
        }
        System.out.println("\tA:B=" + aT + ":" + bT);
        if (aT == 2) {
            aT = 0;
            bT = 0;
            System.out.println("\t三局两胜A赢了\n");
            return true;
        } else if (bT == 2) {
            aT = 0;
            bT = 0;
            System.out.println("\t三局两胜B赢了\n");

            return false;
        } else {
            twoWinOverThree();
        }

        Boolean checkWin = resA.checkWin(resB);
        System.out.print("结果: " + (checkWin ? "A赢" : "B赢"));
        return checkWin;

    }

    private static Boolean checkWin(QuanTou resA, QuanTou resB) {
        Boolean checkWin = resA.checkWin(resB);
        System.out.print("结果: " + (checkWin ? "A赢" : "B赢"));
        return checkWin;

    }
}
