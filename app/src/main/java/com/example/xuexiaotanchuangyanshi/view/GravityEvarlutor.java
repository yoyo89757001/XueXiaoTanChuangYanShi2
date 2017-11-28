package com.example.xuexiaotanchuangyanshi.view;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2017/9/28.
 */

public class GravityEvarlutor implements TypeEvaluator<Integer> {

    /**
     * 因为fraction是从0~1，因此我们可以将总时长看成1
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

        //_h1为初始下落高度，_h2,_h3,_h4为各次的回弹高度
        int _h1 = endValue - startValue;
        int _h2 = _h1/7;
        int _h3 = _h1/35;
        int _h4 = _h1/105;

        //根据t = Math.sqrt(2 * h/a)以及t1 + 2*t2 + 2*t3 + 2*t4 = 1，可算出：
        double t1 = 1 / 2.28917;

        //从而得出重力加速度
        double a = (2 * _h1) / (t1 * t1);

        //再求出t2,t3,t4
        double t2 = Math.sqrt(2 * _h2 / a);
        double t3 = Math.sqrt(2 * _h3 / a);
        double t4 = Math.sqrt(2 * _h4 / a);

        //算出各个时间段的最大速度vt，因为每次在最大高度时满足v = 0，所以在接触地面时，达到最大速度vt = a * t
        double vt1 = a * t1;
        double vt2 = a * t2;
        double vt3 = a * t3;
        double vt4 = a * t4;

        //将fraction进行分段，便于每段独立分析
        double fraction2_1 = fraction - t1;
        double fraction2_2 = fraction - t1 - t2;
        double fraction3_1 = fraction - t1 - (2 * t2);
        double fraction3_2 = fraction - t1 - (2 * t2) - t3;
        double fraction4_1 = fraction - t1 - (2 * t2) - (2 * t3);
        double fraction4_2 = fraction - t1 - (2 * t2) - (2 * t3) - t4;

        //分段算出最终值h
        int h = 0;
        if(fraction <= t1) {
            h = (int)(_h1 - a * 0.5 * fraction * fraction);
        }else if(fraction > t1 && fraction <= (t1 + t2)){//2-1
            h = (int)(vt2 * fraction2_1 - a * 0.5 * fraction2_1 * fraction2_1);
        }else if(fraction > (t1 + t2) && fraction <= (t1 + (2 * t2))){//2-2
            h = (int)(_h2 - a * 0.5 * fraction2_2 * fraction2_2);
        }else if(fraction > (t1 + (2 * t2)) && fraction <= (t1 + (2 * t2) + t3)){//3-1
            h = (int)(vt3 * fraction3_1 - a * 0.5 * fraction3_1 * fraction3_1);
        }else if(fraction > (t1 + (2 * t2) + t3) && fraction <= (t1 + (2 * t2) + (2 * t3))){//3-2
            h = (int)(_h3 - a * 0.5 * fraction3_2 * fraction3_2);
        }else if(fraction > (t1 + (2 * t2) + (2 * t3))&& fraction <= (t1 + (2 * t2) + (2 * t3) + t4)){//4-1
            h = (int)(vt4 * fraction4_1 - a * 0.5 * fraction4_1 * fraction4_1);
        }else{//4-2
            h = (int)(_h4 - a * 0.5 * fraction4_2 * fraction4_2);
        }
        if(fraction == 1){
            h = 0;
        }
        return h;
    }
}