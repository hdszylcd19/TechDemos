package com.oneday.demo.obj;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/4/8 0008 16:29
 */
public class TestObjReference {
    private static List<Obj> sList = new ArrayList<>();

    public static void main(String[] args) {
        Obj obj = new Obj();
        obj.i = 1;
        add(obj);

        obj = null;
        System.out.println("sList = [" + sList + "]");
    }

    private static void add(Obj obj) {
        sList.add(obj);
    }

    private static class Obj {
        int i;

        @Override
        public String toString() {
            return "Obj{" +
                    "i=" + i +
                    '}';
        }
    }
}
