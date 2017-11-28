package com.example.xuexiaotanchuangyanshi.dialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.example.xuexiaotanchuangyanshi.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class QueRenDialog extends Dialog {

    private Button positiveButton,quxiao;
    private TextView shuoming;
    private View quxiao_v;
    public QueRenDialog(Context context, String ss) {
        super(context, R.style.dialog_style);
        setCustomDialog(ss);
    }

    private void setCustomDialog(String ss) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll, null);
        shuoming= (TextView) mView.findViewById(R.id.a3);
        positiveButton= (Button) mView.findViewById(R.id.a5);
        quxiao= (Button) mView.findViewById(R.id.a6);
        super.setContentView(mView);

        shuoming.setText(ss);
    }


    public void setTestColo(){
        shuoming.setTextColor(Color.parseColor("#ffe70707"));

    }

    public void setViewShow(){
        quxiao.setVisibility(View.VISIBLE);

    }
    public  void setButtonText(String ss){
        positiveButton.setText(ss);
    }

    public  void setCountText(String ss){
        shuoming.setText(ss);
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        positiveButton.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnQuXiaoListener(View.OnClickListener listener){
        quxiao.setOnClickListener(listener);
    }
}
