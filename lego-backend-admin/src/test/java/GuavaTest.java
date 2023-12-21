import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GuavaTest {

    /**
     * Joiner：把集合(或数组或可变参数)通过指定的分隔符连接成字符串
     */
    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(null);// 直接添加会报空指针 可通过skipNulls()跳过null 或通过useForNull替换null值
        Joiner joiner = Joiner.on(".")
//                .skipNulls(); //a.b.c
                .useForNull("空值"); //a.b.c.空值
        System.out.println(joiner.join(list));//a.b.c
    }

    /**
     * Splitter：通过指定的分隔符把字符串转为集合
     */
    @Test
    public void test2() {
        String str = "a,b,c";
        Splitter splitter = Splitter.on(",");
        Iterable<String> iterable = splitter.split(str);
        System.out.print("正常情况\t");
        System.out.println(iterable); //[a, b, c]

        System.out.println("\nomitEmptyStrings()");
        String str1 = "a,b,\"\", ,,,,,,,,,,,,c";
        Splitter splitter1 = Splitter.on(",");
        Iterable<String> iterable1 = splitter1.split(str1);
        System.out.print("空串情况不使用omitEmptyStrings()\t");
        System.out.println(iterable1); //[a, b, "", , , , , , , , , , , , , c]

        //过滤掉空白的字符串（不包含""和空格(空格不会被认为是空串)）
        String str2 = "a,b,\"\", ,,,,,,,,,,,,c";
        Splitter splitter2 = Splitter.on(",").omitEmptyStrings();
        Iterable<String> iterable2 = splitter2.split(str2);
        System.out.print("空串情况使用omitEmptyStrings()\t");
        System.out.println(iterable2); //[a, b, c]

        System.out.println("\ntrimResults()");
        String str3 = " a, b  ,   c  ";
        Splitter splitter3 = Splitter.on(",");
        Iterable<String> iterable3 = splitter3.split(str3);
        System.out.print("不使用trimResults()\t");
        System.out.println(iterable3); //[ a,  b  ,    c  ]

        //过滤掉空白的字符串（不包含""）
        String str4 = " a, b  ,   c  ";
        Splitter splitter4 = Splitter.on(",").trimResults();
        Iterable<String> iterable4 = splitter4.split(str4);
        System.out.print("使用trimResults()\t");
        System.out.println(iterable4); //[a, b, c]

        //字符串转list
        List<String> splitToList = splitter.splitToList(str);
        System.out.println(splitToList);//[a, b, c]
    }

    /**
     * 下划线和驼峰互转semantics
     */
    @Test
    public void test3() {
        String str = "student_name";
        //下划线转驼峰
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str));//studentName
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str));//StudentName
        String str1 = "studentName";
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str1));//student_name
    }

    /**
     * Lists用法，另有Sets、Maps
     */
    @Test
    public void test4() {
        //原来的方式
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        //使用Lists的方式
        ArrayList<String> list2 = Lists.newArrayList("a", "b", "c");
        //把list2分成小的集合，按照每2个一组，不用再写subList了
        List<List<String>> partition = Lists.partition(list2, 2);
        System.out.println(partition);//[[a, b], [c]]
    }

    /**
     * Ints用法 另有Longs、Doubles
     */
    @Test
    public void test5() {
        List<Integer> integers = Ints.asList(1, 2, 3);
        System.out.println(integers);
    }

    /**
     * Multiset
     * List：元素可重复的有序集合
     * Set：元素不可重复的无序集合
     * JDK没有一种类型是可重复的无序集合
     */
    @Test
    public void test6() {
        Multiset<String> strings = HashMultiset.create();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("a");
        System.out.println(strings);//[a x 2, b, c]
        System.out.println(strings.entrySet());//[a x 2, b, c]
        System.out.println(strings.elementSet());//[a, b, c]
        for (Multiset.Entry<String> stringEntry : strings.entrySet()) {
            System.out.println(stringEntry.getElement() + "\t个数:" + stringEntry.getCount());
        }
        //a	个数:2
        //b	个数:1
        //c	个数:1
    }

    /**
     * HashMultiMap
     */
    @Test
    public void test7() {
        Multimap<String, String> multimap = HashMultimap.create();
        multimap.put("a", "1");
        multimap.put("a", "2");
        multimap.put("a", "3");
        Collection<String> aValues = multimap.get("a");
        System.out.println(aValues);//[1, 2, 3]

        System.out.println(multimap.containsEntry("a", "1"));//true
        System.out.println(multimap.containsEntry("a", "2"));//true
        System.out.println(multimap.containsEntry("a", "3"));//true

        Map<String, Collection<String>> jdkMap = multimap.asMap();
        System.out.println(jdkMap);//{a=[1, 2, 3]}
    }

    /**
     * 不可变集合
     */
    @Test
    public void test8() {
        List<String> list = new ArrayList<>();
        list.add("aa");
        ImmutableList<Object> build = ImmutableList.builder().addAll(list).build();//[aa]

        //例如将某些数据放入缓存，不希望别人修改
        ImmutableList<Object> immutableList = ImmutableList.builder().add("aa").build();
        System.out.println(build);
//        immutableList.add("a");//java.lang.UnsupportedOperationException
        //JDK方式
        List<String> jdkUnmodifiableList = Collections.unmodifiableList(list);
        jdkUnmodifiableList.add("b");//java.lang.UnsupportedOperationException
    }

    /**
     * 不可变集合
     */
    @Test
    public void test9() {
        String param = null;
//        Preconditions.checkNotNull(param, "参数不能为空");
        //第一个expression：布尔值表达式，true：校验通过，不抛异常；false：校验不通过，抛出异常；
        Preconditions.checkArgument(param != null, "参数不能为空");
    }
}
