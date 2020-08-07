package com.oneday.demo.test_arraylist;

import java.util.ArrayList;

/**
 * Desc: 测试ArrayList并发修改异常相关问题；
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/8/7 0007 14:53
 */
class TestArrayList {
    public static void main(String[] args) {
        /*基于jdk1.8.0_51版本测试*/
        // 通过实际测试发现：下面这段测试代码并不会报出"ConcurrentModificationException"
        /*
         * 我们知道增强for遍历的底层实现逻辑是迭代器。可以通过反编译查看class文件验证：
         *    ArrayList<String> arrayList1 = new ArrayList();
         *    arrayList1.add("1");
         *    arrayList1.add("2");
         *    for (Iterator localIterator = arrayList1.iterator(); localIterator.hasNext();)
         *    {
         *      s = (String)localIterator.next();
         *      if ("1".equals(s)) {
         *        arrayList1.remove(s);
         *      }
         *    }
         *
         * 所以问题的关键就在于ArrayList迭代器的实现：
         *    public boolean hasNext() {
         *        return cursor != size;
         *    }

         *    @SuppressWarnings("unchecked")
         *    public E next() {
         *        checkForComodification();
         *        int i = cursor;
         *        if (i >= size)
         *            throw new NoSuchElementException();
         *        Object[] elementData = ArrayList.this.elementData;
         *        if (i >= elementData.length)
         *            throw new ConcurrentModificationException();
         *        cursor = i + 1;
         *        return (E) elementData[lastRet = i];
         *    }
         *
         *   cursor指向当前遍历的元素，第一次遍历后cursor变成了1；又因为执行了remove操作，size此时也变成了1；
         *   所以遍历第二个元素的时候，判断hasNext时，cursor==size了，所以就没执行next方法，所以就没报错；
         *   等于第一个集合就遍历了一个元素。
         * */
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("1");
        arrayList1.add("2");
        for (String s : arrayList1) {
            if ("1".equals(s)) {
                arrayList1.remove(s);
            }
        }

        // 下面这段测试代码会报出"ConcurrentModificationException"
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("2");
        arrayList2.add("1");
        for (String s : arrayList2) {
            if ("1".equals(s)) {
                arrayList2.remove(s);
            }
        }
    }
}
