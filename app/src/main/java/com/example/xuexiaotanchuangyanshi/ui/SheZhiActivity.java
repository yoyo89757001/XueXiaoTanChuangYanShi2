package com.example.xuexiaotanchuangyanshi.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.xuexiaotanchuangyanshi.MyApplication;
import com.example.xuexiaotanchuangyanshi.R;
import com.example.xuexiaotanchuangyanshi.beans.BaoCunBean;
import com.example.xuexiaotanchuangyanshi.beans.BaoCunBeanDao;
import com.example.xuexiaotanchuangyanshi.dialog.XiuGaiXinXiDialog;
import com.sdsmdg.tastytoast.TastyToast;
import java.util.ArrayList;
import java.util.List;



public class SheZhiActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9;
    private List<Button> sheZhiBeanList;
    private RecyclerView mTvRecyclerView;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private int moban=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baoCunBeanDao= MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        setContentView(R.layout.activity_she_zhi);
        if (baoCunBean==null ){
           BaoCunBean baoCunBean=new BaoCunBean();
            baoCunBean.setId(123456L);
            baoCunBeanDao.insert(baoCunBean);
            Log.d("SheZhiActivity", "插入");
        }else {
            moban=baoCunBean.getMoban();
        }

        if (baoCunBean!=null && baoCunBean.getIsHengOrShu()){
            /**
             * 设置为横屏
             */
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

        }else {
            /**
             * 设置为竖屏
             */
            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

        }


        bt1= (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt1.setOnFocusChangeListener(this);
        bt2= (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt2.setOnFocusChangeListener(this);
        bt3= (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        bt3.setOnFocusChangeListener(this);
        bt4= (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(this);
        bt4.setOnFocusChangeListener(this);
        bt5= (Button) findViewById(R.id.bt5);
        bt5.setOnClickListener(this);
        bt5.setOnFocusChangeListener(this);
        bt6= (Button) findViewById(R.id.bt6);
        bt6.setOnClickListener(this);
        bt6.setOnFocusChangeListener(this);
        bt7= (Button) findViewById(R.id.bt7);
        bt7.setOnClickListener(this);
        bt7.setOnFocusChangeListener(this);

        bt1.requestFocus();
        sheZhiBeanList = new ArrayList<>();
        sheZhiBeanList.add(bt1);
        sheZhiBeanList.add(bt2);
        sheZhiBeanList.add(bt3);
        sheZhiBeanList.add(bt4);
        sheZhiBeanList.add(bt5);
        sheZhiBeanList.add(bt6);
        sheZhiBeanList.add(bt7);

        if (baoCunBean!=null && baoCunBean.getIsHengOrShu()){
            bt6.setText("已设置为横屏");
        }else {
            bt6.setText("已设置为竖屏");
        }

}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.d("SheZhiActivity", "opopopop");

        return super.onKeyDown(keyCode, event);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                ChongsZHI();
                bt1.requestFocus();
                bt1.setTextColor(Color.WHITE);
                bt1.setBackgroundResource(R.drawable.zidonghuoqu1);
                  AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt1,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt1,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet.setDuration(300);
                    animatorSet.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {
                            final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                            if (baoCunBean.getShipingIP()==null){
                                dialog.setContents("设置网络摄像头IP","192.168.2.56");
                            }else {
                                dialog.setContents("设置网络摄像头IP",baoCunBean.getShipingIP());
                            }

                            dialog.setOnQueRenListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    baoCunBean.setShipingIP(dialog.getContents());
                                    baoCunBeanDao.update(baoCunBean);
                                    baoCunBean=baoCunBeanDao.load(123456L);
                                    dialog.dismiss();
                                }
                            });
                            dialog.setQuXiaoListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    animatorSet.start();
                break;
            case R.id.bt2:
                ChongsZHI();
                bt2.requestFocus();
                bt2.setTextColor(Color.WHITE);
                bt2.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt2,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt2,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet2.setDuration(300);
                animatorSet2.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                        if (baoCunBean.getZhujiDiZhi()==null){
                            dialog.setContents("设置主机地址","ws://192.166.2.65:9000/video");
                        }else {
                            dialog.setContents("设置主机地址",baoCunBean.getZhujiDiZhi());
                        }
                        dialog.setOnQueRenListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baoCunBean.setZhujiDiZhi(dialog.getContents());
                                baoCunBeanDao.update(baoCunBean);
                                baoCunBean=baoCunBeanDao.load(123456L);
                                dialog.dismiss();
                            }
                        });
                        dialog.setQuXiaoListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                });
                animatorSet2.start();
                break;
            case R.id.bt3:
                ChongsZHI();
                bt3.requestFocus();
                bt3.setTextColor(Color.WHITE);
                bt3.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt3,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt3,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet3.setDuration(300);
                animatorSet3.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                        if (baoCunBean.getTuisongDiZhi()==null){
                            dialog.setContents("设置推送地址","http://192.168.2.50");
                        }else {
                            dialog.setContents("设置推送地址",baoCunBean.getTuisongDiZhi());
                        }
                        dialog.setOnQueRenListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baoCunBean.setTuisongDiZhi(dialog.getContents());
                                baoCunBeanDao.update(baoCunBean);
                                baoCunBean=baoCunBeanDao.load(123456L);
                                dialog.dismiss();
                            }
                        });
                        dialog.setQuXiaoListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
                animatorSet3.start();

                break;
            case R.id.bt4:
                ChongsZHI();
                bt4.requestFocus();
                bt4.setTextColor(Color.WHITE);
                bt4.setBackgroundResource(R.drawable.zidonghuoqu1);
                break;
            case R.id.bt5:
                ChongsZHI();
                bt5.requestFocus();
                bt5.setTextColor(Color.WHITE);
                bt5.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet5 = new AnimatorSet();
                animatorSet5.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt5,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt5,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet5.setDuration(300);
                animatorSet5.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        if (baoCunBean.getIsShowMoshengren()){ //false为 竖屏
                            baoCunBean.setIsShowMoshengren(false);
                            baoCunBeanDao.update(baoCunBean);
                            baoCunBean=baoCunBeanDao.load(123456L);
                            bt5.setText("已设置为不弹");
                            TastyToast.makeText(SheZhiActivity.this,"已设置为不弹",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();

                        }else {
                            baoCunBean.setIsShowMoshengren(true);
                            baoCunBeanDao.update(baoCunBean);
                            baoCunBean=baoCunBeanDao.load(123456L);
                            bt5.setText("已设置为弹出");
                            TastyToast.makeText(SheZhiActivity.this,"已设置为弹出",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                        }


                    }
                });
                animatorSet5.start();
                break;
            case R.id.bt6:
                ChongsZHI();
                bt6.requestFocus();
                bt6.setTextColor(Color.WHITE);
                bt6.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet6 = new AnimatorSet();
                animatorSet6.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt6,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt6,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet6.setDuration(300);
                animatorSet6.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        if (baoCunBean.getIsHengOrShu()){ //false为 竖屏
                            baoCunBean.setIsHengOrShu(false);
                            baoCunBeanDao.update(baoCunBean);
                            baoCunBean=baoCunBeanDao.load(123456L);
                            bt6.setText("已设置为竖屏");
                            TastyToast.makeText(SheZhiActivity.this,"已设置为竖屏",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();

                        }else {
                            baoCunBean.setIsHengOrShu(true);
                            baoCunBeanDao.update(baoCunBean);
                            baoCunBean=baoCunBeanDao.load(123456L);
                            bt6.setText("已设置为横屏");
                            TastyToast.makeText(SheZhiActivity.this,"已设置为横屏",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                        }


                    }
                });
                animatorSet6.start();

                break;

            case R.id.bt7:
                ChongsZHI();
                bt7.requestFocus();
                bt7.setTextColor(Color.WHITE);
                bt7.setBackgroundResource(R.drawable.zidonghuoqu1);
                AnimatorSet animatorSet7 = new AnimatorSet();
                animatorSet7.playTogether(
                        //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                        ObjectAnimator.ofFloat(bt7,"scaleX",1.0f,1.2f,1.0f),
                        ObjectAnimator.ofFloat(bt7,"scaleY",1.0f,1.2f,1.0f)
                );
                //animatorSet.setInterpolator(new DescelerateInterpolator());
                animatorSet7.setDuration(300);
                animatorSet7.addListener(new AnimatorListenerAdapter(){
                    @Override public void onAnimationEnd(Animator animation) {
                        //弹窗
                        //MoBanDialog dialog=new MoBanDialog(SheZhiActivity.this,baoCunBeanDao);
                       // dialog.show();
                        bt7.setEnabled(true);
                    }
                });
                animatorSet7.start();
                bt7.setEnabled(false);

                break;
        }

    }

    private void  ChongsZHI(){
        if (sheZhiBeanList!=null){
        for (int i=0;i<sheZhiBeanList.size();i++){
            sheZhiBeanList.get(i).setBackgroundResource(R.drawable.zidonghuoqu2);
            sheZhiBeanList.get(i).setTextColor(Color.parseColor("#1b37d6"));
          }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.bt1:
                Log.d("SheZhiActivity", "hasFocus:1" + hasFocus);
                if (hasFocus){
                    ChongsZHI();
                    bt1.setTextColor(Color.WHITE);
                    bt1.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt1,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt1,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                break;
            case R.id.bt2:
                Log.d("SheZhiActivity", "hasFocus:2" + hasFocus);
                if (hasFocus){
                    ChongsZHI();
                    bt2.setTextColor(Color.WHITE);
                    bt2.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt2,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt2,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                break;
            case R.id.bt3:
                if (hasFocus){
                    ChongsZHI();
                    bt3.setTextColor(Color.WHITE);
                    bt3.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt3,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt3,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                Log.d("SheZhiActivity", "hasFocus:3" + hasFocus);
                break;
            case R.id.bt4:
                if (hasFocus){
                    ChongsZHI();
                    bt4.setTextColor(Color.WHITE);
                    bt4.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt4,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt4,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                Log.d("SheZhiActivity", "hasFocus:4" + hasFocus);
                break;
            case R.id.bt5:
                if (hasFocus){
                    ChongsZHI();
                    bt5.setTextColor(Color.WHITE);
                    bt5.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt5,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt5,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
                Log.d("SheZhiActivity", "hasFocus:5" + hasFocus);
                break;
            case R.id.bt6:
                if (hasFocus){
                    ChongsZHI();
                    bt6.setTextColor(Color.WHITE);
                    bt6.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt6,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt6,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }

                break;
            case R.id.bt7:
                if (hasFocus){
                    ChongsZHI();
                    bt7.setTextColor(Color.WHITE);
                    bt7.setBackgroundResource(R.drawable.zidonghuoqu1);
                    AnimatorSet animatorSet6 = new AnimatorSet();
                    animatorSet6.playTogether(
                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
                            ObjectAnimator.ofFloat(bt7,"scaleX",1.0f,1.2f,1.0f),
                            ObjectAnimator.ofFloat(bt7,"scaleY",1.0f,1.2f,1.0f)
                    );
                    //animatorSet.setInterpolator(new DescelerateInterpolator());
                    animatorSet6.setDuration(300);
                    animatorSet6.addListener(new AnimatorListenerAdapter(){
                        @Override public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animatorSet6.start();
                }
              //  Log.d("SheZhiActivity", "hasFocus7:" + hasFocus);
                break;
        }
    }


}
