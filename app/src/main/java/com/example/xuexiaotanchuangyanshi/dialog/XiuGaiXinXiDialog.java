package com.example.xuexiaotanchuangyanshi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xuexiaotanchuangyanshi.R;
import com.example.xuexiaotanchuangyanshi.view.MyEditText;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class XiuGaiXinXiDialog extends Dialog implements View.OnFocusChangeListener {
    private TextView title2;
    private Button l1,l2;
    private EditText shanchu;
    public XiuGaiXinXiDialog(Context context) {
        super(context, R.style.dialog_style2);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xiugaidialog, null);

        shanchu= (EditText) mView.findViewById(R.id.xiangce);
        title2= (TextView) mView.findViewById(R.id.title2);
        l1= (Button)mView. findViewById(R.id.queren);
        l2= (Button) mView.findViewById(R.id.quxiao);
        l1.setOnFocusChangeListener(this);
        l2.setOnFocusChangeListener(this);
        super.setContentView(mView);
    }

    public void setContents(String ss, String s3){
        title2.setText(ss);
        if (s3!=null)
        shanchu.setText(s3);
    }

    public String getContents(){

        return shanchu.getText().toString().trim();

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
    public void setOnQueRenListener(View.OnClickListener listener){
        l1.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setQuXiaoListener(View.OnClickListener listener){
        l2.setOnClickListener(listener);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.queren:
                l1.setBackgroundResource(R.drawable.jiaohu_tc);
                l1.setTextColor(Color.WHITE);
                l2.setBackgroundColor(Color.TRANSPARENT);
                l2.setTextColor(Color.parseColor("#FF1c97fe"));

                break;
            case R.id.quxiao:
                l2.setBackgroundResource(R.drawable.jiaohu_tc);
                l2.setTextColor(Color.WHITE);
                l1.setBackgroundColor(Color.TRANSPARENT);
                l1.setTextColor(Color.parseColor("#FF1c97fe"));
                break;
        }
    }
}
