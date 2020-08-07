package com.oneday.demo.test_number;

/**
 * Desc:
 * Company: XueHai
 * Copyright: Copyright (c) 2016
 *
 * @author JiLin
 * @version 1.0
 * @since 2020/7/30 0030 16:57
 */
class TestFloat {
    public static void main(String[] args) {
        /*
         * Actual Exponent (unbiased)	    Exp (biased)	    Minimum	        Maximum	            Gap
         *          −1	                        126	            0.5	        ≈ 0.999999940395	≈ 5.96046e-8
         *          0	                        127	            1	        ≈ 1.999999880791	≈ 1.19209e-7
         *          1	                        128	            2	        ≈ 3.999999761581	≈ 2.38419e-7
         *          2	                        129	            4	        ≈ 7.999999523163	≈ 4.76837e-7
         *          10	                        137	            1024	    ≈ 2047.999877930	≈ 1.22070e-4
         *          11	                        138	            2048	    ≈ 4095.999755859	≈ 2.44141e-4
         *          23	                        150	            8388608	        16777215	        1
         *          24	                        151	            16777216	    33554430	        2
         *          127	                        254	            ≈ 1.70141e38	≈ 3.40282e38	    ≈ 2.02824e31
         * */
        /*超乎你的想象！！！以下代码f2所表示的数打印出来还是8388608.0*/
        float f1 = 8388608f;
        System.out.println("f1 = " + f1); //8388608.0
        float f2 = 8388608.5f;
        System.out.println("f2 = " + f2); //8388608.0
        float f3 = 8388609f;
        System.out.println("f3 = " + f3); //8388609.0
    }
}
