//package com.example.xuexiaotanchuangyanshi.dialog;
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.app.Dialog;
//import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.example.xuexiaotanchuangyanshi.R;
//import com.example.xuexiaotanchuangyanshi.beans.BaoCunBean;
//import com.example.xuexiaotanchuangyanshi.beans.BaoCunBeanDao;
//import com.example.xuexiaotanchuangyanshi.utils.ImageUtil;
//import com.example.xuexiaotanchuangyanshi.utils.Utils;
//import com.sdsmdg.tastytoast.TastyToast;
//
//
///**
// * @Function: 自定义对话框
// * @Date: 2013-10-28
// * @Time: 下午12:37:43
// * @author Tom.Cai
// */
//public class MoBanDialog extends Dialog  {
//    private RecyclerView recyclerView;
//    private int dh,dw;
//    private LinearLayoutManager linearLayoutManager;
//    private int[] datas=new int[]{R.drawable.moban2,R.drawable.moban1,R.drawable.moban3};
//    private BaoCunBeanDao baoCunBeanDao;
//    private BaoCunBean baoCunBean;
//
//
//
//    public MoBanDialog(Context context,BaoCunBeanDao dao) {
//        super(context, R.style.dialog_style22);
//        dw = Utils.getDisplaySize(context).x;
//        dh = Utils.getDisplaySize(context).y;
//        this.baoCunBeanDao=dao;
//        setCustomDialog(context);
//    }
//
//    private void setCustomDialog(Context c) {
//        View mView = LayoutInflater.from(getContext()).inflate(R.layout.yuyueee, null);
//        LinearLayout top_ll= (LinearLayout) mView.findViewById(R.id.ll_top);
//       recyclerView= (RecyclerView) mView.findViewById(R.id.recyclerView_moban);
//        linearLayoutManager=new LinearLayoutManager(c);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        MyAdapter adapter=new MyAdapter(c,datas);
//        recyclerView.setAdapter(adapter);
//
//        super.setContentView(mView);
//
//        ViewGroup.LayoutParams lp=top_ll.getLayoutParams();
//        lp.width=dw;
//        lp.height=dh/3;
//        top_ll.setLayoutParams(lp);
//        top_ll.invalidate();
//    }
//
//
//
//
//    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//        private Context context;
//
//        private int[] datas = null;
//        public MyAdapter(Context contexts,int[] datas) {
//            this.datas = datas;
//            this.context=contexts;
//
//        }
//        //创建新View，被LayoutManager所调用
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.moban_item,viewGroup,false);
//            ViewHolder vh = new ViewHolder(view);
//
//            return vh;
//        }
//        //将数据与界面进行绑定的操作
//        @Override
//        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
//            viewHolder.moban.setText("模版 "+(position+1));
//            viewHolder.imageView.setImageBitmap(ImageUtil.decodeSampledBitmapFromResource(context.getResources(),datas[position],dw/3,dh/3));
//            viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//
//                    if (hasFocus) {
//                        viewHolder.itemView.setBackgroundResource(R.drawable.zidonghuoqu7);
//                        AnimatorSet animatorSet6 = new AnimatorSet();
//                        animatorSet6.playTogether(
//                                //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
//                                ObjectAnimator.ofFloat(viewHolder.itemView,"scaleX",1.0f,1.2f,1.0f),
//                                ObjectAnimator.ofFloat(viewHolder.itemView,"scaleY",1.0f,1.2f,1.0f)
//                        );
//                        //animatorSet.setInterpolator(new DescelerateInterpolator());
//                        animatorSet6.setDuration(300);
//                        animatorSet6.addListener(new AnimatorListenerAdapter(){
//                            @Override public void onAnimationEnd(Animator animation) {
//
//                            }
//                        });
//                        animatorSet6.start();
//                    } else {
//
//                        viewHolder.itemView.setBackgroundResource(R.drawable.zidonghuoqu1);
//                    }
//                }
//            });
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    viewHolder.itemView.setBackgroundResource(R.drawable.zidonghuoqu7);
//                    AnimatorSet animatorSet6 = new AnimatorSet();
//                    animatorSet6.playTogether(
//                            //	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
//                            ObjectAnimator.ofFloat(viewHolder.itemView,"scaleX",1.0f,1.2f,1.0f),
//                            ObjectAnimator.ofFloat(viewHolder.itemView,"scaleY",1.0f,1.2f,1.0f)
//                    );
//                    //animatorSet.setInterpolator(new DescelerateInterpolator());
//                    animatorSet6.setDuration(300);
//                    animatorSet6.addListener(new AnimatorListenerAdapter(){
//                        @Override public void onAnimationEnd(Animator animation) {
//                            baoCunBean=baoCunBeanDao.load(123456L);
//                            baoCunBean.setMoban(position);
//                            baoCunBeanDao.update(baoCunBean);
//                            dismiss();
//                            TastyToast.makeText(context,"已设置为模版"+(position+1),TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
//                        }
//                    });
//                    animatorSet6.start();
//                }
//            });
//
//            RecyclerView.LayoutParams lp= (RecyclerView.LayoutParams) viewHolder.relativeLayout.getLayoutParams();
//            lp.width=dw/3;
//            lp.height=dh/3;
//            viewHolder.relativeLayout.setLayoutParams(lp);
//            viewHolder.relativeLayout.invalidate();
//
//
//        }
//        //获取数据的数量
//        @Override
//        public int getItemCount() {
//            return datas.length;
//        }
//        //自定义的ViewHolder，持有每个Item的的所有界面元素
//        public   class ViewHolder extends RecyclerView.ViewHolder {
//            private ImageView imageView;
//            private RelativeLayout relativeLayout;
//            private TextView moban;
//
//            private ViewHolder(View view){
//                super(view);
//                imageView = (ImageView) view.findViewById(R.id.imageView_moban);
//                relativeLayout= (RelativeLayout) view.findViewById(R.id.top_rl_mo);
//                moban= (TextView) view.findViewById(R.id.moban);
//            }
//        }
//    }
//
//    @Override
//    public void setContentView(int layoutResID) {
//    }
//
//    @Override
//    public void setContentView(View view, LayoutParams params) {
//    }
//
//    @Override
//    public void setContentView(View view) {
//    }
//
////    /**
////     * 确定键监听器
////     * @param listener
////     */
////    public void setOnPositiveListener(View.OnClickListener listener){
////        positiveButton.setOnClickListener(listener);
////    }
////    /**
////     * 取消键监听器
////     * @param listener
////     */
////    public void setOnQuXiaoListener(View.OnClickListener listener){
////        quxiao.setOnClickListener(listener);
////    }
//}
