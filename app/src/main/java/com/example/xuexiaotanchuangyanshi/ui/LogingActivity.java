package com.example.xuexiaotanchuangyanshi.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.example.xuexiaotanchuangyanshi.MyApplication;
import com.example.xuexiaotanchuangyanshi.R;
import com.example.xuexiaotanchuangyanshi.beans.BaoCunBean;
import com.example.xuexiaotanchuangyanshi.beans.BaoCunBeanDao;
import com.example.xuexiaotanchuangyanshi.utils.Utils;
import com.example.xuexiaotanchuangyanshi.view.GravityEvarlutor;

public class LogingActivity extends Activity {
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private ImageView logo;
    private int dw,dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        baoCunBeanDao= MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);
        logo= (ImageView) findViewById(R.id.logo);

        dw = Utils.getDisplaySize(LogingActivity.this).x;
        dh = Utils.getDisplaySize(LogingActivity.this).y;

        play(logo);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
               // ObjectAnimator.ofFloat(logo,"translationX",0,dw/3,dw/2),
             //   ObjectAnimator.ofFloat(logo,"translationY",dh,dh/3,dh/2,-dh/4,dh/6,dh/2),
                ObjectAnimator.ofFloat(logo,"scaleX",0,0.3f),
                ObjectAnimator.ofFloat(logo,"scaleY",0,0.3f)

        );
        animatorSet.setDuration(800);
        animatorSet.addListener(new AnimatorListenerAdapter(){
            @Override public void onAnimationEnd(Animator animation) {

                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        ObjectAnimator.ofFloat(logo,"scaleX",0.3f,1f),
                        ObjectAnimator.ofFloat(logo,"scaleY",0.3f,1f)
                );
                animatorSet2.setInterpolator(new AccelerateInterpolator());
                animatorSet2.setDuration(1400);
                animatorSet2.start();

            }
        });
        animatorSet.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(2400);
                    if (baoCunBean!=null){
                        switch (baoCunBean.getMoban()){
                            case 0:
                                startActivity(new Intent(LogingActivity.this, VlcVideoActivity.class));
                                break;
                            case 1:
                              //  startActivity(new Intent(LogingActivity.this, VlcVideoActivity2.class));
                                break;
                            case 2:
                             //   startActivity(new Intent(LogingActivity.this, VlcVideoActivity3.class));
                                break;
                            case 3:

                                break;
                        }
                    }else {
                        startActivity(new Intent(LogingActivity.this, VlcVideoActivity.class));
                    }
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void play(View v){
        ValueAnimator va = ValueAnimator.ofObject(new GravityEvarlutor(), 0, dh/10);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                logo.setY(dh/3 - (Integer) animation.getAnimatedValue());
            }
        });
        va.setDuration(2200);
        va.setInterpolator(new LinearInterpolator());
        va.start();
    }

}
