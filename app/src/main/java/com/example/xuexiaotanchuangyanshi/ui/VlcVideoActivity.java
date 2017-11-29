package com.example.xuexiaotanchuangyanshi.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xuexiaotanchuangyanshi.MyApplication;
import com.example.xuexiaotanchuangyanshi.R;
import com.example.xuexiaotanchuangyanshi.beans.BaoCunBean;
import com.example.xuexiaotanchuangyanshi.beans.BaoCunBeanDao;
import com.example.xuexiaotanchuangyanshi.beans.ChuanJianUserBean;
import com.example.xuexiaotanchuangyanshi.beans.MoShengRenBean;
import com.example.xuexiaotanchuangyanshi.beans.MoShengRenBean2;
import com.example.xuexiaotanchuangyanshi.beans.MoShengRenBeanDao;
import com.example.xuexiaotanchuangyanshi.beans.ShiBieBean;
import com.example.xuexiaotanchuangyanshi.beans.TanChuangBean;
import com.example.xuexiaotanchuangyanshi.beans.TuPianBean;
import com.example.xuexiaotanchuangyanshi.beans.User;
import com.example.xuexiaotanchuangyanshi.beans.WBBean;
import com.example.xuexiaotanchuangyanshi.beans.WeiShiBieBean;
import com.example.xuexiaotanchuangyanshi.interfaces.RecytviewCash;
import com.example.xuexiaotanchuangyanshi.media.IjkVideoView;
import com.example.xuexiaotanchuangyanshi.media.Settings;
import com.example.xuexiaotanchuangyanshi.service.AlarmReceiver;

import com.example.xuexiaotanchuangyanshi.utils.DateUtils;
import com.example.xuexiaotanchuangyanshi.utils.GsonUtil;
import com.example.xuexiaotanchuangyanshi.utils.Utils;
import com.example.xuexiaotanchuangyanshi.view.WrapContentLinearLayoutManager;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import sun.misc.BASE64Decoder;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class VlcVideoActivity extends BaseActivity implements RecytviewCash, SpeechSynthesizerListener {
	private final static String TAG = "WebsocketPushMsg";
	private IjkVideoView ijkVideoView;
	private MyReceiver myReceiver=null;
	private SurfaceView surfaceview;
	private RecyclerView recyclerView;
	private MyAdapter adapter=null;
	private RecyclerView recyclerView2;
	private MyAdapter2 adapter2=null;
	private MoShengRenBeanDao daoSession=null;
	private SpeechSynthesizer mSpeechSynthesizer;
	private String mSampleDirPath;
	private static final String SAMPLE_DIR_NAME = "baiduTTS";
	private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
	private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
	private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
	//private static final String LICENSE_FILE_NAME = "temp_license";
	private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
	private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
	private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
	private WrapContentLinearLayoutManager manager;
	private WrapContentLinearLayoutManager manager2;
	private static  WebSocketClient webSocketClient=null;
	private MediaPlayer mediaPlayer=null;
	private IVLCVout vlcVout=null;
	private IVLCVout.Callback callback;
	private LibVLC libvlc;
	private Media media;
	private SurfaceHolder mSurfaceHolder;
	private String zhuji=null;
	private static final String zhuji2="http://121.46.3.20";
	private  static Vector<TanChuangBean> lingdaoList=null;
	private  static Vector<TanChuangBean> yuangongList=null;
	private int dw,dh;
	private BaoCunBeanDao baoCunBeanDao=null;
	private BaoCunBean baoCunBean=null;
	private NetWorkStateReceiver netWorkStateReceiver=null;
	private TextView wangluo;
	private boolean isLianJie=false;
	private RelativeLayout top_rl;
	private TextView xiaoshi,riqi,xingqi,tianqi,wendu;


	public  Handler handler=new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(final Message msg) {
			switch (msg.what){
				case 111:
					//更新地址
//					try {
//
//						WebsocketPushMsg websocketPushMsg=new WebsocketPushMsg();
//						if (zhuji_string!=null && shiping_string!=null){
//
//							websocketPushMsg.startConnection(zhuji_string,shiping_string);
//						}else {
//
//							TastyToast.makeText(VlcVideoActivity.this, "请先设置主机和视频流", Toast.LENGTH_LONG,TastyToast.ERROR).show();
//
//						}
//
//					} catch (URISyntaxException e ) {
//
//						Log.d(TAG, e.getMessage());
//					}

					break;
				case 110:
					if (lingdaoList.size()>1) {

						AnimatorSet animatorSet = new AnimatorSet();
						animatorSet.playTogether(
								ObjectAnimator.ofFloat(adapter2.getViewByPosition(recyclerView2,1,R.id.ffflll),"scaleY",1f,0f),
								ObjectAnimator.ofFloat(adapter2.getViewByPosition(recyclerView2,1,R.id.ffflll),"scaleX",1f,0f)
								//	ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
						);
						animatorSet.setDuration(200);
						animatorSet.setInterpolator(new AccelerateInterpolator());
						animatorSet.addListener(new AnimatorListenerAdapter(){
							@Override public void onAnimationEnd(Animator animation) {
								adapter2.notifyItemRemoved(1);
								lingdaoList.remove(1);

							}
						});
						animatorSet.start();

					}


					break;
				case 999:

					if (yuangongList.size()>1) {

						AnimatorSet animatorSet = new AnimatorSet();
						animatorSet.playTogether(
								ObjectAnimator.ofFloat(adapter.getViewByPosition(recyclerView,1,R.id.ffflll),"scaleY",1f,0f),
								ObjectAnimator.ofFloat(adapter.getViewByPosition(recyclerView,1,R.id.ffflll),"scaleX",1f,0f)
								//	ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
						);
						animatorSet.setDuration(200);
						animatorSet.setInterpolator(new AccelerateInterpolator());
						animatorSet.addListener(new AnimatorListenerAdapter(){
							@Override public void onAnimationEnd(Animator animation) {
								adapter.notifyItemRemoved(1);
								yuangongList.remove(1);

							}
						});
						animatorSet.start();

						//adapter.notifyItemChanged(0);
					//	adapter.notifyItemRangeChanged(0,yuangongList.size()+1);
						//adapter.notifyDataSetChanged();
						//manager.scrollToPosition(yuangongList.size() - 1);
					}



					break;
				case 19: //更新识别记录
					Log.d(TAG, "19");

					//adapter2.notifyItemInserted(size-1);
					//manager2.smoothScrollToPosition(recyclerView2,null,size-1);

					break;

			}

			if (msg.arg1==1){
				ShiBieBean.PersonBeanSB dataBean= (ShiBieBean.PersonBeanSB) msg.obj;
				try {

					final TanChuangBean bean=new TanChuangBean();
					bean.setBytes(null);
					bean.setIdid(dataBean.getId());
					bean.setType(dataBean.getSubject_type());
					bean.setName(dataBean.getName());
					bean.setRemark(dataBean.getRemark());
					bean.setTouxiang(dataBean.getAvatar());

					switch (dataBean.getSubject_type()) {
						case 0: //员工
							//Log.d(TAG, "员工k");

							if (dataBean.getRemark().equals("领导")) {
								int a = 0;
								for (int i2 = 0; i2 < lingdaoList.size(); i2++) {
									if (lingdaoList.get(i2).getIdid() == bean.getIdid()) {
										a = 1;
									}
								}
								if (a==0){

								lingdaoList.add(bean);
								int i1 = lingdaoList.size();
								adapter2.notifyItemInserted(i1);
								manager2.scrollToPosition(i1 - 1);
								new Thread(new Runnable() {
									@Override
									public void run() {

										try {
											Thread.sleep(10000);

											Message message = Message.obtain();
											message.what = 110;
											handler.sendMessage(message);


										} catch (InterruptedException e) {
											e.printStackTrace();
										}


									}
								}).start();

								}

							}else {
								int a = 0;
								for (int i2 = 0; i2 < yuangongList.size(); i2++) {
									if (yuangongList.get(i2).getIdid() == bean.getIdid()) {
										a = 1;
									}
								}
								if (a==0){
									yuangongList.add(bean);
									int i1 = yuangongList.size();
									adapter.notifyItemInserted(i1);
									manager.scrollToPosition(i1 - 1);
									new Thread(new Runnable() {
										@Override
										public void run() {

											try {
												Thread.sleep(10000);

												Message message = Message.obtain();
												message.what = 999;
												handler.sendMessage(message);


											} catch (InterruptedException e) {
												e.printStackTrace();
											}


										}
									}).start();
								}


					}
							break;

//						case 1: //普通访客
//							yuangongList.add(bean);
//							int i2=yuangongList.size();
//							adapter.notifyItemInserted(i2);
//							manager.scrollToPosition(i2-1);
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//
//									try {
//										Thread.sleep(10000);
//
//										Message message=Message.obtain();
//										message.what=999;
//										handler.sendMessage(message);
//
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//
//
//								}
//							}).start();
//
//							break;
//						case 2:  //VIP访客
//							yuangongList.add(bean);
//							int i3=yuangongList.size();
//							adapter.notifyItemInserted(i3);
//							manager.scrollToPosition(i3-1);
//
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//
//									try {
//										Thread.sleep(10000);
//										Message message=Message.obtain();
//										message.what=999;
//										handler.sendMessage(message);
//
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//
//
//								}
//							}).start();
//
//
//							break;

					}

				} catch (Exception e) {
					//Log.d("WebsocketPushMsg", e.getMessage());
					e.printStackTrace();
				}


			}else if (msg.arg1==2) {

			final WeiShiBieBean dataBean = (WeiShiBieBean) msg.obj;

				new Thread(new Runnable() {
					@Override
					public void run() {

						try {

							BASE64Decoder decoder = new BASE64Decoder();
							// Base64解码
							final byte[][] b;

							b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
							for (int i = 0; i < b[0].length; ++i) {
								if (b[0][i] < 0) {// 调整异常数据
									b[0][i] += 256;
								}
							}

							TanChuangBean bean = new TanChuangBean();
							bean.setBytes(b[0]);
							bean.setName("陌生人");
							bean.setType(-1);
							bean.setTouxiang(null);
							yuangongList.add(bean);
							final int i3=yuangongList.size();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									adapter.notifyItemInserted(i3);
									manager.scrollToPosition(i3 - 1);
								}
							});

							Thread.sleep(10000);

							Message message = Message.obtain();
							message.what = 999;
							handler.sendMessage(message);


						} catch (Exception e) {

							Log.d(TAG, e.getMessage() + "陌生人解码");
						}

					}
				}).start();
			}

			return false;
		}
	});

//	/*
//     * @param arg0
//     */
//	@Override
//	public void onSynthesizeStart(String utteranceId) {
//	//	toPrint("onSynthesizeStart utteranceId=" + utteranceId);
//	}
//
//	/**
//	 * 合成数据和进度的回调接口，分多次回调
//	 *
//	 * @param
//	 * @param data 合成的音频数据。该音频数据是采样率为16K，2字节精度，单声道的pcm数据。
//	 * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
//	 */
//	@Override
//	public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress) {
//		// toPrint("onSynthesizeDataArrived");
//		//mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_SYNTHES_TEXT_SELECTION, progress, 0));
//	}
//
//	/**
//	 * 合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
//	 *
//	 * @param
//	 */
//	@Override
//	public void onSynthesizeFinish(String utteranceId) {
//		//toPrint("onSynthesizeFinish utteranceId=" + utteranceId);
//	}
//
//	/**
//	 * 播放开始，每句播放开始都会回调
//	 *
//	 * @param
//	 */
//	@Override
//	public void onSpeechStart(String utteranceId) {
//		//toPrint("onSpeechStart utteranceId=" + utteranceId);
//	}
//
//	/**
//	 * 播放进度回调接口，分多次回调
//	 *
//	 * @param
//	 * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
//	 */
//	@Override
//	public void onSpeechProgressChanged(String utteranceId, int progress) {
//		// toPrint("onSpeechProgressChanged");
//		//mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_INPUT_TEXT_SELECTION, progress, 0));
//	}
//
//	/**
//	 * 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
//	 *
//	 * @param utteranceId
//	 */
//	@Override
//	public void onSpeechFinish(String utteranceId) {
//		//toPrint("onSpeechFinish utteranceId=" + utteranceId);
//	}
//
//	/**
//	 * 当合成或者播放过程中出错时回调此接口
//	 *
//	 * @param utteranceId
//	 * @param error 包含错误码和错误信息
//	 */
//	@Override
//	public void onError(String utteranceId, SpeechError error) {
//	//	toPrint("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
//	}

	@Override
	public void reset() {

		//数据重置
		chongzhi();
	}

	private void chongzhi(){
		yuangongList.clear();
		//tanchuangList.clear();
		if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || (!recyclerView.isComputingLayout())) {
			adapter.notifyDataSetChanged();
		}


//
//		TanChuangBean bean=new TanChuangBean();
//		bean.setBytes(null);
//		bean.setName(null);
//		bean.setType(-2);
//		bean.setTouxiang(null);
//		//tanchuangList.add(bean);
//
//		TanChuangBean bean5=new TanChuangBean();
//		bean5.setBytes(null);
//		bean5.setName(null);
//		bean5.setType(-2);
//		bean5.setTouxiang(null);
//		//tanchuangList.add(bean5);
//
//		TanChuangBean bean3=new TanChuangBean();
//		bean3.setBytes(null);
//		bean3.setName(null);
//		bean3.setType(-2);
//		bean3.setTouxiang(null);
//		yuangongList.add(bean3);

	}

//	private class TestLoopAdapter extends LoopPagerAdapter {
//
//		public TestLoopAdapter(RollPagerView viewPager) {
//			super(viewPager);
//		}
//
//		@Override
//		public View getView(ViewGroup container, int position) {
//			ImageView view = new ImageView(container.getContext());
//			try {
//
//				view.setImageDrawable(getImageDrawable(photoPathList.get(position)));
//			} catch (IOException e) {
//				Log.d("test", e.getMessage());
//			}
//
//			view.setScaleType(ImageView.ScaleType.FIT_XY);
//			view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//			return view;
//		}
//
//		@Override
//		public int getRealCount() {
//			return photoPathList.size();
//		}
//	}
//	/**
//	 * 将文件生成位图
//	 * @param
//	 * @return
//	 * @throws
//	 */
//	public BitmapDrawable getImageDrawable(String path)
//			throws IOException
//	{
//		//打开文件
//		File file = new File(path);
//		if(!file.exists())
//		{
//			return null;
//		}
//
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//		byte[] bt = new byte[1024];
//
//		//得到文件的输入流
//		InputStream in = new FileInputStream(file);
//
//		//将文件读出到输出流中
//		int readLength = in.read(bt);
//		while (readLength != -1) {
//			outStream.write(bt, 0, readLength);
//			readLength = in.read(bt);
//		}
//
//		//转换成byte 后 再格式化成位图
//		byte[] data = outStream.toByteArray();
//		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// 生成位图
//		BitmapDrawable bd = new BitmapDrawable(getResources(),bitmap);
//
//		return bd;
//	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//	Log.d(TAG, "创建111");


		baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
		baoCunBean = baoCunBeanDao.load(123456L);
		if (baoCunBean == null) {
			BaoCunBean baoCunBean = new BaoCunBean();
			baoCunBean.setId(123456L);
			baoCunBeanDao.insert(baoCunBean);
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		//DisplayMetrics dm = getResources().getDisplayMetrics();
		dw = Utils.getDisplaySize(VlcVideoActivity.this).x;
		dh = Utils.getDisplaySize(VlcVideoActivity.this).y;

		setContentView(R.layout.activity_video_vlc);
		wangluo = (TextView) findViewById(R.id.wangluo);
		surfaceview= (SurfaceView) findViewById(R.id.surfaceview);
		mSurfaceHolder = surfaceview.getHolder();

		tianqi= (TextView) findViewById(R.id.tianqi);
		wendu= (TextView) findViewById(R.id.wendu);

		xiaoshi= (TextView) findViewById(R.id.shijian);
		riqi= (TextView) findViewById(R.id.riqi);
		xingqi= (TextView) findViewById(R.id.xingqi);
		String time=(System.currentTimeMillis())+"";
		xiaoshi.setText(DateUtils.timeMinute(time));
		riqi.setText(DateUtils.timesTwo(time));
		xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));

//		if (baoCunBean!=null && baoCunBean.getIsHengOrShu()){
//          //  Log.d(TAG, "横屏");
//            isHX=true;
//			/**
//			 * 设置为横屏
//			 */
//			if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//			}
//
//		}else {
//			isHX=false;
//			/**
//			 * 设置为竖屏
//			 */
//			if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                Log.d(TAG, "竖屏");
//			}
//		}

		//	w = dm.widthPixels;
		//	h = dm.heightPixels;


		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					//JPushInterface.setAlias(VlcVideoActivity.this,1,"children");
					//百度语音
					initialEnv();
					initialTts();
				}
			}).start();
		} catch (Exception e) {
			Log.d(TAG, e.getMessage() + "");
		}

		IjkMediaPlayer.loadLibrariesOnce(null);
		IjkMediaPlayer.native_setLogLevel(6);
		IjkMediaPlayer.native_profileBegin("libijkplayer.so");

		lingdaoList=new Vector<>();
		yuangongList = new Vector<>();

		TanChuangBean bean=new TanChuangBean();
		bean.setBytes(null);
		bean.setName(null);
		bean.setType(-2);
		bean.setRemark("学生");
		bean.setIdid(0);
		bean.setTouxiang(null);
		yuangongList.add(bean);

		TanChuangBean bean2=new TanChuangBean();
		bean2.setBytes(null);
		bean2.setName(null);
		bean2.setType(-2);
		bean.setIdid(0);
		bean.setRemark("领导");
		bean2.setTouxiang(null);
		lingdaoList.add(bean2);




		Button button = (Button) findViewById(R.id.dddk);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chongzhi();

				startActivity(new Intent(VlcVideoActivity.this, SheZhiActivity.class));
			}
		});


		//实例化过滤器并设置要过滤的广播
		myReceiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("duanxianchonglian");
		intentFilter.addAction("gxshipingdizhi");
		intentFilter.addAction("shoudongshuaxin");
		intentFilter.addAction("updateGonggao");
		intentFilter.addAction("updateTuPian");
		intentFilter.addAction("updateShiPing");
		intentFilter.addAction("delectShiPing");
		intentFilter.addAction("guanbi");
		intentFilter.addAction(Intent.ACTION_TIME_TICK);

		// 注册广播
		registerReceiver(myReceiver, intentFilter);

		daoSession = MyApplication.getAppContext().getDaoSession().getMoShengRenBeanDao();
		daoSession.deleteAll();
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
//		recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
//			//用来标记是否正在向最后一个滑动
//			boolean isSlidingToLast = false;
//
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//				LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//				// 当不滚动时
//				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//					//获取最后一个完全显示的ItemPosition
//					int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//					int totalItemCount = manager.getItemCount();
//
//					// 判断是否滚动到底部，并且是向右滚动
//					if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//						//加载更多功能的代码
//						manager2.smoothScrollToPosition(recyclerView2,null,0);
//					}
//
//					if (lastVisibleItem==4 && !isSlidingToLast){
//						manager2.smoothScrollToPosition(recyclerView2,null,shiBieJiLuList.size()-1);
//					}
//
//				}
//			}
//
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//
//				//dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//				//大于0表示正在向下滚动
//				//小于等于0表示停止或向上滚动
//				isSlidingToLast = dy > 0;
//			}
//		});


		//	mSurfaceView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//		LinearLayout relativeLayout= (LinearLayout) findViewById(R.id.ffgghh);
//		relativeLayout.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//
//			}
//		});

//		Log.d(TAG, "findViewById(R.id.toptv).getWidth():" + findViewById(R.id.toptv).getWidth());

		//mLoadingView = findViewById(R.id.video_loading);


		//mSurfaceHolder = mSurfaceView.getHolder();
		//mSurfaceHolder.setFormat(PixelFormat.RGBX_8888);
		//mSurfaceHolder.addCallback(this);
		//	mSurfaceView.setKeepScreenOn(true);


		ijkVideoView = (IjkVideoView) findViewById(R.id.vitamio);
		ijkVideoView.setHudView(new TableLayout(this));
		//this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//		mMediaPlayer.setMediaList();
		//		mMediaPlayer.getMediaList().add(new Media(mMediaPlayer, "http://live.3gv.ifeng.com/zixun.m3u8"), false);
		//		mMediaPlayer.playIndex(0);
		//	mMediaPlayer.playMRL("rtsp://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp");

		Settings mSettings = new Settings(this);
		IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
		ijkMediaPlayer.setLogEnabled(false);

		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 60);

		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 0);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "fps", 30);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_YV12);

		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "nobuffer");
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max-buffer-size", 1024);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 10);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", "4096");
		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", "2000000");

//		ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory()+File.separator+"aaa.mp4");
//		ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
//			@Override
//			public void onCompletion(IMediaPlayer iMediaPlayer) {
//
//				ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory()+File.separator+"aaa.mp4");
//				ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//					@Override
//					public void onPrepared(IMediaPlayer iMediaPlayer) {
//						ijkVideoView.start();
//					}
//				});
//
//			}
//		});
//		ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//			@Override
//			public void onPrepared(IMediaPlayer iMediaPlayer) {
//				ijkVideoView.start();
//			}
//		});
//		ijkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
//			@Override
//			public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
//				//Log.d(TAG, "i:" + i);
//				//Log.d(TAG, "i1:" + i1);
//				Toast toast=TastyToast.makeText(VlcVideoActivity.this,"播放视频出错",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//				toast.setGravity(Gravity.CENTER,0,0);
//				toast.show();
//
//
//
//				return true;
//			}
//		});


		manager = new WrapContentLinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(manager);

		manager2 = new WrapContentLinearLayoutManager(this);
		manager2.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView2.setLayoutManager(manager2);
		//recyclerView.addItemDecoration(new RecycleViewDivider(VlcVideoActivity.this, LinearLayoutManager.VERTICAL));
		//recyclerView.addItemDecoration(new MyDecoration(VlcVideoActivity.this, LinearLayoutManager.VERTICAL,20,R.color.transparent));

		adapter = new MyAdapter(R.layout.tanchuang_item, yuangongList);
		recyclerView.setAdapter(adapter);

		adapter2 = new MyAdapter2(R.layout.tanchuang_item2, lingdaoList);
		recyclerView2.setAdapter(adapter2);

		surfaceview.setKeepScreenOn(true);

		libvlc = new LibVLC(this);
		mediaPlayer = new MediaPlayer(libvlc);
		vlcVout = mediaPlayer.getVLCVout();

		callback = new IVLCVout.Callback() {
			@Override
			public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {

			}

			@Override
			public void onSurfacesCreated(IVLCVout ivlcVout) {
				try {

					changeSurfaceSize();

				} catch (Exception e) {
					Log.d("vlc-newlayout", e.toString());
				}
			}

			@Override
			public void onSurfacesDestroyed(IVLCVout ivlcVout) {

			}

			@Override
			public void onHardwareAccelerationError(IVLCVout vlcVout) {

			}
		};

		vlcVout.addCallback(callback);
		vlcVout.setVideoView(surfaceview);
		vlcVout.attachViews();

	}

	private void initialEnv() {
		if (mSampleDirPath == null) {
			String sdcardPath = Environment.getExternalStorageDirectory().toString();
			mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
		}
		Utils.makeDir(mSampleDirPath);
		Utils.copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME,this);
		Utils.copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME,this);
		Utils.copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME,this);
		//Utils.copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME,this);
		Utils.copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
				+ ENGLISH_SPEECH_FEMALE_MODEL_NAME,this);
		Utils.copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
				+ ENGLISH_SPEECH_MALE_MODEL_NAME,this);
		Utils.copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
				+ ENGLISH_TEXT_MODEL_NAME,this);
	}

	private void initialTts() {
		this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
		this.mSpeechSynthesizer.setContext(this);
		this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
		// 文本模型文件路径 (离线引擎使用)
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
				+ TEXT_MODEL_NAME);
		// 声学模型文件路径 (离线引擎使用)
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
				+ SPEECH_FEMALE_MODEL_NAME);
		// 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
		// 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
		//this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
			//	+ LICENSE_FILE_NAME);
		// 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
		this.mSpeechSynthesizer.setAppId("9990556"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
		// 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
		this.mSpeechSynthesizer.setApiKey("0fxLiYpG9gUC9660agtU3rEU",
				"68288f5f80eaa85a6384ac30a14b622e"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
		// 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4");
		// 设置Mix模式的合成策略
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
		//合成引擎速度优化等级，取值范围[0, 2]，值越大速度越快（离线引擎）
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOCODER_OPTIM_LEVEL, "2");

		// 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
		// AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
		AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

		if (authInfo.isSuccess()) {
			//toPrint("auth success");
		} else {
			String errorMsg = authInfo.getTtsError().getDetailMessage();
			//toPrint("auth failed errorMsg=" + errorMsg);
		}

		// 初始化tts
		if (mSpeechSynthesizer!=null){
			mSpeechSynthesizer.initTts(TtsMode.MIX);
			// 加载离线英文资源（提供离线英文合成功能）
			int result =
					mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
							+ "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
			//toPrint("loadEnglishModel result=" + result);
		}


	}



	@Override
	protected void onStart() {
		super.onStart();

	}

	//学生跟陌生人
	public  class MyAdapter extends BaseQuickAdapter<TanChuangBean,BaseViewHolder> {
		private RequestOptions myOptions = null;


		private MyAdapter(int layoutResId, List<TanChuangBean> data) {
			super(layoutResId, data);
			 myOptions = new RequestOptions()
					.circleCrop()
					.diskCacheStrategy(DiskCacheStrategy.NONE);
		}


		@Override
		protected void convert(final BaseViewHolder helper, TanChuangBean item) {
			//Log.d(TAG, "动画执行");
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.playTogether(
					ObjectAnimator.ofFloat(helper.itemView,"scaleY",0f,1f),
					ObjectAnimator.ofFloat(helper.itemView,"scaleX",0f,0f)
					//	ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
			);
			animatorSet.setDuration(200);
			animatorSet.setInterpolator(new AccelerateInterpolator());
			animatorSet.addListener(new AnimatorListenerAdapter(){
				@Override public void onAnimationEnd(Animator animation) {

					AnimatorSet animatorSet2 = new AnimatorSet();
					animatorSet2.playTogether(
							ObjectAnimator.ofFloat(helper.itemView,"scaleX",0f,1f)
							//ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
							//	ObjectAnimator.ofFloat(helper.itemView,"scaleY",1f,0.5f,1f)
					);
					animatorSet2.setInterpolator(new AccelerateInterpolator());
					animatorSet2.setDuration(500);
					animatorSet2.start();

				}
			});
			animatorSet.start();

			ImageView imageView= helper.getView(R.id.touxiang);
			TextView name=helper.getView(R.id.name33);
			TextView zhuangtai=helper.getView(R.id.zhuangtai33);
			LinearLayout toprl=helper.getView(R.id.ggghhh);
			RelativeLayout rl=helper.getView(R.id.ffflll);

			if (helper.getAdapterPosition()==0 ){
				rl.setBackgroundColor(Color.parseColor("#00000000"));
				toprl.setBackgroundColor(Color.parseColor("#00000000"));
				name.setText("");
				zhuangtai.setText("");
			}else {

				switch (item.getType()) {
					case -1:
						//陌生人
						toprl.setBackgroundResource(R.drawable.zidonghuoqu8);

						zhuangtai.setText("陌生人");
						name.setText("陌生人进入,请保安尽快到现场或短信保安预警.");

						mSpeechSynthesizer.speak("陌生人进入,请保安尽快到现场或短信保安预警");

						break;
					case 0:
						//员工
						toprl.setBackgroundResource(R.drawable.yg_bg);
						name.setText(item.getName());
						zhuangtai.setText(item.getRemark());
						mSpeechSynthesizer.speak("欢迎"+item.getName()+"祝你出入平安.");

//						String  zt=item.getRemark();
//						if (zt!=null){
//							if (zt.equals("")){
//								zhuangtai.setText("员工");
//							}else {
//								zhuangtai.setText(zt);
//							}
//						}else {
//							zhuangtai.setText("员工");
//						}

						break;

					case 1:
						//访客
						//toprl.setBackgroundResource(R.drawable.zidonghuoqu15);

						//richeng.setText("");
						//name.setText(item.getName());
						//autoScrollTextView.setText("欢迎你来本公司参观指导。");

						break;
					case 2:
						//VIP访客
						//	toprl.setBackgroundResource(R.drawable.ms_bg);
						//	richeng.setText("");
						//	name.setText(item.getName());
						//autoScrollTextView.setText("欢迎VIP访客 "+item.getName()+" 来本公司指导工作。");

						break;
				}
				if (item.getTouxiang()!=null){

					Glide.with(MyApplication.getAppContext())
							.load(zhuji+item.getTouxiang())
							//	.load("http://121.46.3.20/"+item.getTouxiang())
							.apply(RequestOptions.bitmapTransform(new CircleCrop()))
							//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
							//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
							.into((ImageView) helper.getView(R.id.touxiang));
				}else {
					Glide.with(MyApplication.getAppContext())
							.load(item.getBytes())
							.apply(myOptions)
							//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
							//	.transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
							.into((ImageView) helper.getView(R.id.touxiang));

				}

			}

			RelativeLayout linearLayout_tanchuang = helper.getView(R.id.ffflll);
				ViewGroup.LayoutParams lp =  linearLayout_tanchuang.getLayoutParams();

			    //弹窗的高宽

				lp.width=dw;
				lp.height=(dh/3)-40;
				linearLayout_tanchuang.setLayoutParams(lp);
			    linearLayout_tanchuang.invalidate();

		}

	}


	//领导
	private  class MyAdapter2 extends BaseQuickAdapter<TanChuangBean,BaseViewHolder> {
		private RequestOptions myOptions = null;

		private MyAdapter2(int layoutResId, List<TanChuangBean> data) {
			super(layoutResId, data);
			myOptions = new RequestOptions();
		}


		@Override
		protected void convert(final BaseViewHolder helper, TanChuangBean item) {
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.playTogether(
					ObjectAnimator.ofFloat(helper.itemView,"scaleY",0f,1f),
					ObjectAnimator.ofFloat(helper.itemView,"scaleX",0f,0f)
					//	ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
			);
			animatorSet.setDuration(200);
			animatorSet.setInterpolator(new AccelerateInterpolator());
			animatorSet.addListener(new AnimatorListenerAdapter(){
				@Override public void onAnimationEnd(Animator animation) {

					AnimatorSet animatorSet2 = new AnimatorSet();
					animatorSet2.playTogether(
							ObjectAnimator.ofFloat(helper.itemView,"scaleX",0f,1f)
							//ObjectAnimator.ofFloat(helper.itemView,"alpha",0f,1f)
							//	ObjectAnimator.ofFloat(helper.itemView,"scaleY",1f,0.5f,1f)
					);
					animatorSet2.setInterpolator(new AccelerateInterpolator());
					animatorSet2.setDuration(500);
					animatorSet2.start();

				}
			});
			animatorSet.start();

//			ViewAnimator
//					.animate(helper.itemView)
//				//	.scale(0,1)
//					.alpha(0,1)
//					.duration(1000)
//					.start();

			RelativeLayout toprl= helper.getView(R.id.ffflll);

			TextView tishi_tv= helper.getView(R.id.tishi_tv);
			TextView tishi_tv2= helper.getView(R.id.ddd);

			if (helper.getAdapterPosition()==0 ){
				toprl.setBackgroundColor(Color.parseColor("#00000000"));
				tishi_tv.setText("");
				tishi_tv2.setText("");

			}else {

				switch (item.getType()){
					case -1:
						//陌生人
						//	toprl.setBackgroundResource(R.drawable.tanchuang);


						break;
					case 0:
						//员工
						toprl.setBackgroundResource(R.drawable.datc);
						String sa1="热烈欢迎"+item.getName()+"莅临参观指导";
						StringBuilder sb1=new StringBuilder();
						for(int i=0;i<sa1.length();i++){
							sb1.append((sa1.charAt(i)));//依次加入sb中
							if((i+1)%(8)==0 &&((i+1)!=sa1.length())){
								sb1.append("\n");
							}
						}
						tishi_tv.setText(sb1.toString());
						mSpeechSynthesizer.speak(sb1.toString());
						break;

					case 1:
						//访客

						toprl.setBackgroundResource(R.drawable.datc);
						String sa="热烈欢迎"+item.getName()+"莅临参观指导";
						StringBuilder sb=new StringBuilder();
						for(int i=0;i<sa.length();i++){
							sb.append((sa.charAt(i)));//依次加入sb中
							if((i+1)%(8)==0 &&((i+1)!=sa.length())){
								sb.append("\n");
							}
						}
						tishi_tv.setText(sb.toString());

						break;
					case 2:
						//VIP访客
						toprl.setBackgroundResource(R.drawable.datc);
						String sa2="热烈欢迎"+item.getName()+"莅临参观指导";
						StringBuilder sb2=new StringBuilder();
						for(int i=0;i<sa2.length();i++){
							sb2.append((sa2.charAt(i)));//依次加入sb中
							if((i+1)%(8)==0 &&((i+1)!=sa2.length())){
								sb2.append("\n");
							}
						}
						tishi_tv.setText(sb2.toString());

						break;

				}
				if (item.getTouxiang()!=null){

					Glide.with(MyApplication.getAppContext())
							.load(zhuji+item.getTouxiang())
							.apply(myOptions)
							//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
							//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
							.into((ImageView) helper.getView(R.id.touxiang));
				}else {
					Glide.with(MyApplication.getAppContext())
							.load(item.getBytes())
							.apply(myOptions)
							//.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
							//.transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
							.into((ImageView) helper.getView(R.id.touxiang));
				}
			}


		}

	}

//	/**
//	 * 生成二维码
//	 * @param string 二维码中包含的文本信息
//	 * @param mBitmap logo图片
//	 * @param format  编码格式
//	 * [url=home.php?mod=space&uid=309376]@return[/url] Bitmap 位图
//	 * @throws WriterException
//	 */
//	private static final int IMAGE_HALFWIDTH = 1;//宽度值，影响中间图片大小
//	public Bitmap createCode(String string,Bitmap mBitmap, BarcodeFormat format)
//			throws WriterException {
//		Matrix m = new Matrix();
//		float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
//		float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
//		m.setScale(sx, sy);//设置缩放信息
//		//将logo图片按martix设置的信息缩放
//		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
//				mBitmap.getWidth(), mBitmap.getHeight(), m, false);
//		MultiFormatWriter writer = new MultiFormatWriter();
//		Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
//		hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置字符编码
//		BitMatrix matrix = writer.encode(string, format, 600, 600, hst);//生成二维码矩阵信息
//		int width = matrix.getWidth();//矩阵高度
//		int height = matrix.getHeight();//矩阵宽度
//		int halfW = width/2;
//		int halfH = height/2;
//		int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
//		for (int y = 0; y < height; y++) {//从行开始迭代矩阵
//			for (int x = 0; x < width; x++) {//迭代列
//				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//						&& y > halfH - IMAGE_HALFWIDTH
//						&& y < halfH + IMAGE_HALFWIDTH) {//该位置用于存放图片信息
//			//记录图片每个像素信息
//					pixels[y * width + x] = mBitmap.getPixel(x - halfW
//							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);              } else {
//					if (matrix.get(x, y)) {//如果有黑块点，记录信息
//						pixels[y * width + x] = 0xff000000;//记录黑块信息
//					}
//				}
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		// 通过像素数组生成bitmap
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		return bitmap;
//	}
//	private class MyReceiverFile  extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, final Intent intent) {
//			String action = intent.getAction();
//			if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
//				//USB设备移除，更新UI
//				Log.d(TAG, "设备被移出");
////				if (rollPagerView!=null){
////					if (rollPagerView.isPlaying()){
////						rollPagerView.pause();
////					}
////
////
////				}
//				if (ijkMediaPlayer!=null){
//					Log.d(TAG, "播放暂停");
//					ijkVideoView.pause();
//					ijkVideoView.canPause();
//
//					//ijkVideoView.stopPlayback();
//					//ijkMediaPlayer.stop();
//				}
//			} else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
//				//USB设备挂载，更新UI
//				Log.d(TAG, "设备插入");
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						String usbPath = intent.getDataString();//（usb在手机上的路径）
//
//						getAllFiles(new File(usbPath.split("file:///")[1]+File.separator+"file"));
//						Log.d(TAG, usbPath);
//					}
//				}).start();
//			}
//
//		}
//	}


	private class MyReceiver  extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, final Intent intent) {
			//Log.d(TAG, "intent:" + intent.getAction());

			if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
				String time=(System.currentTimeMillis())+"";
				xiaoshi.setText(DateUtils.timeMinute(time));
				riqi.setText(DateUtils.timesTwo(time));
				xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));

			}
				if (intent.getAction().equals("duanxianchonglian")) {

					//断线重连
					if (webSocketClient!=null){

					//	Log.d(TAG, "进来1");

						if (!isLianJie){
						//	Log.d(TAG, "进来2");
					try {
						baoCunBean=baoCunBeanDao.load(123456L);
						WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
						websocketPushMsg.close();
						if (baoCunBean.getZhujiDiZhi() != null && baoCunBean.getShipingIP() != null) {
							websocketPushMsg.startConnection(baoCunBean.getZhujiDiZhi(), "rtsp://"+baoCunBean.getShipingIP()+":554/user=admin_password=tlJwpbo6_channel=1_stream=0.sdp?real_stream");
						}


					} catch (Exception e) {
						Log.d(TAG, e.getMessage()+"aaa");

					}
						}
				}
				if (intent.getAction().equals("gxshipingdizhi")) {
					//更新视频流地址
					//Log.d(TAG, "收到更新地址广播");
					String a = intent.getStringExtra("gxsp");
					String b = intent.getStringExtra("gxzj");
//					if (!a.equals("")) {
//						shiping_string = a;
//						media = new Media(libvlc, Uri.parse(shiping_string));
//						mediaPlayer.setMedia(media);
//						mediaPlayer.play();
//						//mMediaPlayer.playMRL(shiping_string);
//						startActivity(new Intent(VlcVideoActivity.this, ErWeiMaActivity.class));
//					}
//					if (!b.equals("")) {
//
//						zhuji_string = b;
//						try {
//							String[] a1=zhuji_string.split("//");
//							String[] b1=a1[1].split(":");
//							zhuji="http://"+b1[0];
//
//							WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
//							if (zhuji_string != null && shiping_string != null) {
//								websocketPushMsg.startConnection(zhuji_string, shiping_string);
//							}
//						} catch (URISyntaxException e) {
//							e.printStackTrace();
//
//						}
//
//					}

				}
				if (intent.getAction().equals("shoudongshuaxin")) {
					baoCunBean=baoCunBeanDao.load(123456L);
					if (baoCunBean.getShipingIP() != null && baoCunBean.getZhujiDiZhi() != null) {

						try {
							WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
							websocketPushMsg.close();
							if (baoCunBean.getShipingIP() != null && baoCunBean.getZhujiDiZhi() != null) {
								websocketPushMsg.startConnection(baoCunBean.getZhujiDiZhi(), "rtsp://"+baoCunBean.getShipingIP()+":554/user=admin_password=tlJwpbo6_channel=1_stream=0.sdp?real_stream");
							}
						} catch (Exception e) {
							Log.d(TAG, e.getMessage()+"fghj");
						}



					}

				}

				if (intent.getAction().equals("guanbi")){
					Log.d(TAG, "关闭");
					finish();
				}


			}
	//	}

	}
	}




	// 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
	public static void getAllFiles(File root,String nameaaa){
		File files[] = root.listFiles();

		if(files != null){
			for (File f : files){
				if(f.isDirectory()){
					getAllFiles(f,nameaaa);
				}else{
					String name=f.getName();
					if (name.equals(nameaaa)){
						Log.d(TAG, "视频文件删除:" + f.delete());
					}
				}
			}
		}
	}

//	private void link_gengxing_erweima() {
//
//		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();
//		RequestBody body = new FormBody.Builder()
//				.add("cmd","getUnSignList")
////                .add("subjectId",zhaoPianBean.getId()+"")
////                .add("subjectPhoto",zhaoPianBean.getAvatar())
//				.build();
//		Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.post(body)
//				.url("http://192.168.2.17:8080/sign");
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//
//				Log.d("AllConnects", "请求获取二维码失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				//Log.d("AllConnects", "请求获取二维码成功"+call.request().toString());
//				//获得返回体
//				//List<YongHuBean> yongHuBeanList=new ArrayList<>();
//				//List<MoShengRenBean2> intlist=new ArrayList<>();
//			//	intlist.addAll(moShengRenBean2List);
//				try {
//
//				if (moShengRenBean2List.size()!=0)
//				 moShengRenBean2List.clear();
//				ResponseBody body = response.body();
//				//  Log.d("AllConnects", "aa   "+response.body().string());
//
//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//						int code=jsonObject.get("resultCode").getAsInt();
//						if (code==0){
//					JsonArray array=jsonObject.get("data").getAsJsonArray();
//					int a=array.size();
//					for (int i=0;i<a;i++){
//						YongHuBean zhaoPianBean=gson.fromJson(array.get(i),YongHuBean.class);
//						moShengRenBean2List.add(zhaoPianBean);
//						//Log.d("VlcVideoActivity", zhaoPianBean.getSubjectId());
//					}
//						//	Log.d("VlcVideoActivity", "moShengRenBean2List.size():" + moShengRenBean2List.size());
////					int a1=intlist.size();
////					int b=yongHuBeanList.size();
////
////						for (int i=0;i<a1;i++){
////							for (int j=0;j<b;j++){
////								Log.d("VlcVideoActivity", intlist.get(i).getId()+"ttt");
////								Log.d("VlcVideoActivity", yongHuBeanList.get(j).getSubjectId()+"ttt");
////								if (intlist.get(i).getId().equals(yongHuBeanList.get(j).getSubjectId())){
////									moShengRenBean2List.add(intlist.get(i));
////								}
////							}
////						}
//
//					Message message=Message.obtain();
//					message.what=12;
//					//  message.obj=yongHuBeanList;
//					handler.sendMessage(message);
//					}
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//
//			}
//		});
//
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if( KeyEvent.KEYCODE_MENU == keyCode ){  //如果按下的是菜单
			Log.d(TAG, "按下菜单键 ");
			chongzhi();
			//isTiaoZhuang=false;
			startActivity(new Intent(VlcVideoActivity.this, SheZhiActivity.class));

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {


		if (netWorkStateReceiver == null) {
			netWorkStateReceiver = new NetWorkStateReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			registerReceiver(netWorkStateReceiver, filter);
		}

		baoCunBean=baoCunBeanDao.load(123456L);

		if (baoCunBean!=null && baoCunBean.getZhujiDiZhi()!=null){
			Log.d(TAG, "onResume");
			try {
				String[] a1=baoCunBean.getZhujiDiZhi().split("//");
				String[] b1=a1[1].split(":");
				zhuji="http://"+b1[0];
				WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
				websocketPushMsg.close();
				if (baoCunBean.getShipingIP() != null ) {
					websocketPushMsg.startConnection(baoCunBean.getZhujiDiZhi(), "rtsp://"+baoCunBean.getShipingIP()+":554/user=admin_password=tlJwpbo6_channel=1_stream=0.sdp?real_stream");
				}
			} catch (URISyntaxException e) {
				Log.d(TAG, e.getMessage()+"ddd");

			}
		}else {
			TastyToast.makeText(VlcVideoActivity.this,"请先设置主机地址和摄像头IP",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
		}
		sendBroadcast(new Intent(VlcVideoActivity.this,AlarmReceiver.class));
		super.onResume();
//		if (baoCunBean!=null && baoCunBean.getIsHengOrShu()!=isHX){
//			if (baoCunBean!=null && baoCunBean.getIsHengOrShu()){
//				isHX=true;
//				/**
//				 * 设置为横屏
//				 */
//				if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//				}
//
//			}else {
//				isHX=false;
//				/**
//				 * 设置为竖屏
//				 */
//				if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//				}
//			}
//		}




//		if (ijkVideoView!=null){
//			if (!ijkVideoView.isPlaying()){
//				ijkVideoView.start();
//			}
//		}

//		if (mediaPlayer==null){
//			Log.d(TAG, "onresume执行播放");
//
//			libvlc = LibVLCUtil.getLibVLC(VlcVideoActivity.this);
//			mediaPlayer = new MediaPlayer(libvlc);
//			vlcVout = mediaPlayer.getVLCVout();
//
//			callback = new IVLCVout.Callback() {
//				@Override
//				public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {
//
//				}
//
//				@Override
//				public void onSurfacesCreated(IVLCVout ivlcVout) {
//					try {
//
//						changeSurfaceSize();
//
//					} catch (Exception e) {
//					  Log.d("vlc-newlayout", e.toString());
//					}
//				}
//
//				@Override
//				public void onSurfacesDestroyed(IVLCVout ivlcVout) {
//
//				}
//
//				@Override
//				public void onHardwareAccelerationError(IVLCVout vlcVout) {
//
//				}
//			};
//
//			vlcVout.addCallback(callback);
//			vlcVout.setVideoView(mSurfaceView);
//			vlcVout.attachViews();
//		}

	//	link_gengxing_erweima();
	}


	@Override
	public void onPause() {

		Log.d(TAG, "暂停");


		super.onPause();
	}

	@Override
	protected void onDestroy() {

		handler.removeCallbacksAndMessages(null);
		if (myReceiver!=null)
			unregisterReceiver(myReceiver);
		unregisterReceiver(netWorkStateReceiver);
		super.onDestroy();


//		if (mediaPlayer != null) {
//			mediaPlayer.stop();
//			mediaPlayer.release();
//			mediaPlayer=null;
//			vlcVout=null;
//			libvlc.release();
//		}

		//ijkVideoView.pause();
		//ijkVideoView.stopPlayback();
//		this.mSpeechSynthesizer.release();

		//unregisterReceiver(myReceiverFile);
	}

//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		setSurfaceSize(mVideoWidth, mVideoHeight, mVideoVisibleWidth, mVideoVisibleHeight, mSarNum, mSarDen);
//		super.onConfigurationChanged(newConfig);
//}

//	@Override
//	public void surfaceCreated(SurfaceHolder holder) {
//		if (mMediaPlayer != null) {
//			mSurfaceHolder = holder;
//			mMediaPlayer.attachSurface(holder.getSurface(), this);
//		}
//	}
//
//	@Override
//	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//		mSurfaceHolder = holder;
//		if (mMediaPlayer != null) {
//			mMediaPlayer.attachSurface(holder.getSurface(), this);//, width, height
//		}
//		if (width > 0) {
//			mVideoHeight = height;
//			mVideoWidth = width;
//		}
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		if (mMediaPlayer != null) {
//			mMediaPlayer.detachSurface();
//		}
//	}
//
//	@Override
//	public void setSurfaceSize(int width, int height, int visible_width, int visible_height, int sar_num, int sar_den) {
//
//		mVideoHeight = height;
//		mVideoWidth = width;
//		mVideoVisibleHeight = visible_height;
//		mVideoVisibleWidth = visible_width;
//		mSarNum = sar_num;
//		mSarDen = sar_den;
//		mHandler.removeMessages(HANDLER_SURFACE_SIZE);
//		mHandler.sendEmptyMessage(HANDLER_SURFACE_SIZE);
//	}

	//private static final int HANDLER_BUFFER_START = 1;
//	private static final int HANDLER_BUFFER_END = 2;
	//private static final int HANDLER_SURFACE_SIZE = 3;

//	private static final int SURFACE_BEST_FIT = 0;
//	private static final int SURFACE_FIT_HORIZONTAL = 1;
//	private static final int SURFACE_FIT_VERTICAL = 2;
//	private static final int SURFACE_FILL = 3;
//	private static final int SURFACE_16_9 = 4;
//	private static final int SURFACE_4_3 = 5;
	//private static final int SURFACE_ORIGINAL = 6;
//	private int mCurrentSize = SURFACE_BEST_FIT;

//	private Handler mVlcHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg == null || msg.getData() == null)
//				return;
//
//			switch (msg.getData().getInt("event")) {
//			case EventHandler.MediaPlayerTimeChanged:
//				break;
//			case EventHandler.MediaPlayerPositionChanged:
//				break;
//			case EventHandler.MediaPlayerPlaying:
//				mHandler.removeMessages(HANDLER_BUFFER_END);
//				mHandler.sendEmptyMessage(HANDLER_BUFFER_END);
//				break;
//			case EventHandler.MediaPlayerBuffering:
//				break;
//			case EventHandler.MediaPlayerLengthChanged:
//				break;
//			case EventHandler.MediaPlayerEndReached:
//				//播放完成
//				break;
//			}
//
//		}
//	};

//	private Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case HANDLER_BUFFER_START:
//                showLoading();
//				break;
//			case HANDLER_BUFFER_END:
//                hideLoading();
//				break;
//			case HANDLER_SURFACE_SIZE:
//				changeSurfaceSize();
//				break;
//			}
//		}
//	};


	private void changeSurfaceSize() {
		// get screen size
		int dw = Utils.getDisplaySize(getApplicationContext()).x;
		int dh = Utils.getDisplaySize(getApplicationContext()).y;

//		RelativeLayout.LayoutParams re1 = (RelativeLayout.LayoutParams)surfaceview.getLayoutParams();
//
//		  re1.width=dw/3;
//		  re1.height = dh/5;
//
//		surfaceview.setLayoutParams(re1);
//		surfaceview.invalidate();
		Log.d(TAG, baoCunBean.getShipingIP()+"hhhhh");
		if (mediaPlayer != null) {
			Log.d(TAG, baoCunBean.getShipingIP()+"gggg");

			media = new Media(libvlc, Uri.parse("rtsp://"+baoCunBean.getShipingIP()+"/user=admin&password=&channel=1&stream=0.sdp"));
			mediaPlayer.setMedia(media);
			mediaPlayer.play();

		}

	}
//	/**
//	 * websocket接口返回face.image
//	 * image为base64编码的字符串
//	 * 将字符串转为可以识别的图片
//	 * @param imgStr
//	 * @return
//	 */
//	public Bitmap generateImage(String imgStr, int cont, WBWeiShiBieDATABean dataBean, Context context) throws Exception {
//		// 对字节数组字符串进行Base64解码并生成图片
//		if (imgStr == null) // 图像数据为空
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			final byte[][] b = {decoder.decodeBuffer(imgStr)};
//			for (int i = 0; i < b[0].length; ++i) {
//				if (b[0][i] < 0) {// 调整异常数据
//					b[0][i] += 256;
//				}
//			}
//			MoShengRenBean2 moShengRenBean2=new MoShengRenBean2();
//			moShengRenBean2.setId(dataBean.getTrack());
//			moShengRenBean2.setBytes(b[0]);
//			moShengRenBean2.setUrl("dd");
//
//			moShengRenBean2List.add(moShengRenBean2);
//
//				adapter.notifyDataSetChanged();
//
//
//
//
//
//			//   Bitmap bitmap= BitmapFactory.decodeByteArray(b[0],0, b[0].length);
//
//			//  Log.d("WebsocketPushMsg", "bitmap.getHeight():" + bitmap.getHeight());
//
//			// 生成jpeg图片
//			//  OutputStream out = new FileOutputStream(imgFilePath);
//			//   out.write(b);
//			//  out.flush();
//			//  out.close();
//
//
////			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
////				@Override
////				public void onDismiss(DialogInterface dialog) {
////					Log.d("VlcVideoActivity", "Dialog销毁2");
////					b[0] =null;
////				}
////			});
//			//dialog.show();
//
//
//			return null;
//		} catch (Exception e) {
//			throw e;
//
//		}
//	}

	public  int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}
	public  int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	/**
	 * 识别消息推送
	 * 主机盒子端API ws://[主机ip]:9000/video
	 * 通过 websocket 获取 识别结果
	 * @author Wangshutao
	 */
	private class WebsocketPushMsg {
		/** * 识别消息推送
		 * @param wsUrl websocket接口 例如 ws://192.168.1.50:9000/video
		 * @param rtspUrl 视频流地址 门禁管理-门禁设备-视频流地址
		 *                例如 rtsp://192.168.0.100/live1.sdp
		 *                或者 rtsp://admin:admin12345@192.168.1.64/live1.sdp
		 *                或者 rtsp://192.168.1.103/user=admin&password=&channel=1&stream=0.sdp
		 *                或者 rtsp://192.168.1.100/live1.sdp
		 *                       ?__exper_tuner=lingyun&__exper_tuner_username=admin
		 *                       &__exper_tuner_password=admin&__exper_mentor=motion
		 *                       &__exper_levels=312,1,625,1,1250,1,2500,1,5000,1,5000,2,10000,2,10000,4,10000,8,10000,10
		 *                       &__exper_initlevel=6
		 * @throws URISyntaxException
		 * @throws
		 * @throws
		 *
		 *  ://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp
		 *
		 *   rtsp://192.166.2.55:554/user=admin_password=tljwpbo6_channel=1_stream=0.sdp?real_stream
		 */
		private void startConnection(String wsUrl, String rtspUrl) throws URISyntaxException {
			//当视频流地址中出现&符号时，需要进行进行url编码
			if (rtspUrl.contains("&")){
				try {
					//Log.d("WebsocketPushMsg", "dddddttttttttttttttt"+rtspUrl);
					rtspUrl = URLEncoder.encode(rtspUrl,"UTF-8");

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					//Log.d("WebsocketPushMsg", e.getMessage());
				}
			}

			URI uri = URI.create(wsUrl + "?url=" + rtspUrl);
		//	Log.d("WebsocketPushMsg", "url="+uri);
			  webSocketClient = new WebSocketClient(uri) {
			//	private Vector vector=new Vector();

				@Override
				public void onOpen(ServerHandshake serverHandshake) {
					isLianJie=true;

				}

				@Override
				public void onMessage(String ss) {

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					WBBean wbBean= gson.fromJson(jsonObject, WBBean.class);

					if (wbBean.getType().equals("recognized")) { //识别
						Log.d("WebsocketPushMsg", "识别出了");

						final ShiBieBean dataBean = gson.fromJson(jsonObject, ShiBieBean.class);

							try {

								//mSpeechSynthesizer.speak("欢迎" + dataBean.getPerson().getName() + "来学校接送" + dataBean.getPerson().getRemark());
								MoShengRenBean bean = new MoShengRenBean(dataBean.getPerson().getId(), "sss");

								daoSession.insert(bean);

								Message message2 = Message.obtain();
								message2.arg1 = 1;
								message2.obj = dataBean.getPerson();
								handler.sendMessage(message2);
								Log.d(TAG, "111");

							}catch (Exception e){
								Log.d("WebsocketPushMsg", e.getMessage()+"aaa");
							}finally {
								try {
									Thread.sleep(300);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								try {
									daoSession.deleteByKey(dataBean.getPerson().getId());
								//	Log.d("WebsocketPushMsg", "删除");
								}catch (Exception e){
									Log.d("WebsocketPushMsg", e.getMessage()+"bbb");
								}
							}



					}
             else if (wbBean.getType().equals("unrecognized")) {
						Log.d("WebsocketPushMsg", "识别出了陌生人");

						JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();

						final WeiShiBieBean dataBean = gson.fromJson(jsonObject1, WeiShiBieBean.class);


						try {

							MoShengRenBean bean = new MoShengRenBean(dataBean.getTrack(), "sss");

							daoSession.insert(bean);

							Message message = new Message();
							message.arg1 = 2;
							message.obj = dataBean;
							handler.sendMessage(message);


						} catch (Exception e) {
							Log.d("WebsocketPushMsg", e.getMessage());
							//e.printStackTrace();
						}finally {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							try {
								daoSession.deleteByKey(dataBean.getTrack());
								//Log.d("WebsocketPushMsg", "删除");
							}catch (Exception e){
								Log.d("WebsocketPushMsg", e.getMessage());
							}
						}
					}
				}

				@Override
				public void onClose(int i, String s, boolean b) {
					isLianJie=false;

					//Log.d("WebsocketPushMsg", "onClose"+i);
					runOnUiThread( new Runnable() {
						@Override
						public void run() {
							TastyToast.makeText(VlcVideoActivity.this,"连接已断开,20秒后重新连接", Toast.LENGTH_LONG,TastyToast.ERROR).show();
						}
					});
//
//					if (conntionHandler==null && runnable==null){
//						Looper.prepare();
//						conntionHandler=new Handler();
//						runnable=new Runnable() {
//							@Override
//							public void run() {
//
//								Intent intent=new Intent("duanxianchonglian");
//								sendBroadcast(intent);
//							}
//						};
//						conntionHandler.postDelayed(runnable,13000);
//						Looper.loop();
//					}

				}

				@Override
				public void onError(Exception e) {
					Log.d("WebsocketPushMsg", "onError"+e.getMessage());

				}
			};

			webSocketClient.connect();
		}
		private void close(){
//
//			if (conntionHandler!=null && runnable!=null){
//				conntionHandler.removeCallbacks(runnable);
//				conntionHandler=null;
//				runnable=null;
//
//			}
			if (webSocketClient!=null){

//				if (webSocketClient.isOpen()){
//					webSocketClient.close();
//				}

				webSocketClient=null;
				System.gc();


			}

		}

	}



	private void creatUser(byte[] bytes, Long tt, String age) {
		//Log.d("WebsocketPushMsg", "创建用户");
		String fileName="tong"+System.currentTimeMillis()+".jpg";
		//通过bytes数组创建图片文件
		createFileWithByte(bytes,fileName,tt,age);
		//上传
	//	addPhoto(fileName);
	}

	/**
	 * 根据byte数组生成文件
	 *
	 * @param bytes
	 *            生成文件用到的byte数组
	 * @param age
	 */
	private void createFileWithByte(byte[] bytes, String filename, Long tt, String age) {
		/**
		 * 创建File对象，其中包含文件所在的目录以及文件的命名
		 */
		File file=null;
		String	sdDir = this.getFilesDir().getAbsolutePath();//获取跟目录
		makeRootDirectory(sdDir);

		// 创建FileOutputStream对象
		FileOutputStream outputStream = null;
		// 创建BufferedOutputStream对象
		BufferedOutputStream bufferedOutputStream = null;

		try {
			file = new File(sdDir +File.separator+ filename);
			// 在文件系统中根据路径创建一个新的空文件
		//	file2.createNewFile();
		//	Log.d(TAG, file.createNewFile()+"");

			// 获取FileOutputStream对象
			outputStream = new FileOutputStream(file);
			// 获取BufferedOutputStream对象
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			// 往文件所在的缓冲输出流中写byte数据
			bufferedOutputStream.write(bytes);
			// 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
			bufferedOutputStream.flush();
			//上传文件
			addPhoto(sdDir,filename,bytes,tt,age);

		} catch (Exception e) {
			// 打印异常信息
			//Log.d(TAG, "ssssssssssssssssss"+e.getMessage());
		} finally {
			// 关闭创建的流对象
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

	private void addPhoto(final String path, final String fname, final byte[] b, final Long truck, final String age){

//		if (zhuji_string!=null){
//			String[] a=zhuji_string.split("//");
//			String[] b1=a[1].split(":");
//			zhuji="http://"+b1[0];
//		}

		OkHttpClient okHttpClient= new OkHttpClient();

         /* 第一个要上传的file */
		File file1 = new File(path,fname);
		RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
		final String file1Name = System.currentTimeMillis()+"testFile.jpg";

//    /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
//        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
//        String file2Name = "testFile2.txt";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

		MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
				.addFormDataPart("photo" , file1Name , fileBody1)
                  /* 上传一个普通的String参数 */
				//  .addFormDataPart("subject_id" , subject_id+"")
//                .addFormDataPart("file" , file2Name , fileBody2)
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				// .header("Content-Type", "application/json")
				.post(mBody)
				.url(zhuji2+"/subject/photo");
		//    .url(HOST+"/mobile-admin/subjects");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());
		//step 4: 开始异步请求
		final String finalZhuji = zhuji;
		call.enqueue(new Callback() {
			@Override
			public void onFailure(final Call call, final IOException e) {
			//	Log.d("AllConnects", "照片上传失败"+e.getMessage());

			}

			@Override
			public void onResponse(final Call call, Response response) throws IOException {
				Log.d("AllConnects", "照片上传成功"+call.request().toString());

				try{

				//获得返回体
				ResponseBody body = response.body();
				// Log.d("WebsocketPushMsg", "aa   "+response.body().string());
				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
				Gson gson=new Gson();
				int code=jsonObject.get("code").getAsInt();
				if (code==0){
					JsonObject array=jsonObject.get("data").getAsJsonObject();
					TuPianBean zhaoPianBean=gson.fromJson(array,TuPianBean.class);
					//创建用户
				//	Log.d("VlcVideoActivity", "zhaoPianBean.getId():" + zhaoPianBean.getId());
					link(finalZhuji,"a",zhaoPianBean.getId()+"",b,age);

				}else {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TastyToast.makeText(getApplicationContext(),
									"图片不够清晰，请再来一张", TastyToast.LENGTH_LONG, TastyToast.ERROR);
						}
					});

				}
				//删除照片
				Log.d("VlcVideoActivity", "删除照片:" + VlcVideoActivity.this.deleteFile(fname));

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}


		});


		}


	private void link(String zhuji, String name, String id, final byte[] b, final String age){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		OkHttpClient okHttpClient= new OkHttpClient();

		List<Long> longs=new ArrayList<>();
		longs.add(Long.valueOf(id));
		ChuanJianUserBean bean=new ChuanJianUserBean();
		bean.setName(name);
		bean.setPhoto_ids(longs);
		bean.setSubject_type("0");

		String json = new Gson ().toJson(bean);
		RequestBody requestBody = RequestBody.create(JSON, json);


//		RequestBody body = new FormBody.Builder()
//				.add("name",name)
//				.add("subject_type",0+"")
//				.add("photo_ids","["+id+"]")
//				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(requestBody)
				.url(zhuji2+"/subject");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

				ResponseBody body = response.body();
				//  Log.d("AllConnects", "aa   "+response.body().string());

				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
				Gson gson=new Gson();
				int code=jsonObject.get("code").getAsInt();
				if (code==0){
					JsonObject array=jsonObject.get("data").getAsJsonObject();
					User zhaoPianBean=gson.fromJson(array,User.class);
					link_houtai(zhaoPianBean);
					final MoShengRenBean2 moShengRenBean2 = new MoShengRenBean2();
					moShengRenBean2.setId(zhaoPianBean.getId());
					moShengRenBean2.setAge(age);
					moShengRenBean2.setBytes(b);
				//	moShengRenBean2.setUrl("http://192.168.2.7:8080/sign?cmd=signScan&subjectId="+zhaoPianBean.getId());



				}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}

			}
		});


	}

	private void link_houtai(User zhaoPianBean) {
		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
		OkHttpClient okHttpClient= new OkHttpClient();

		RequestBody body = new FormBody.Builder()
				.add("cmd","addSign")
				.add("subjectId",zhaoPianBean.getId()+"")
				.add("subjectPhoto",zhaoPianBean.getPhotos().get(0).getUrl())
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url("http://192.168.2.17:8080/sign");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求添加陌生人成功"+call.request().toString());
				//获得返回体
				try {

				ResponseBody body = response.body();
			//	Log.d("AllConnects", "aa   "+response.body().string());

				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
				Gson gson=new Gson();
				int code=jsonObject.get("resultCode").getAsInt();
				if (code==0){
//					JsonObject array=jsonObject.get("data").getAsJsonObject();
//					User zhaoPianBean=gson.fromJson(array,User.class);
//					link_houtai(zhaoPianBean);
					//link_gengxing_erweima();
				}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});


		}

	public class NetWorkStateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			//检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

				//获得ConnectivityManager对象
				ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				//获取ConnectivityManager对象对应的NetworkInfo对象
				//以太网
				NetworkInfo wifiNetworkInfo1 = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
				//获取WIFI连接的信息
				NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				//获取移动数据连接的信息
				NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (wifiNetworkInfo1.isConnected() || wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected()){
					wangluo.setVisibility(View.GONE);

				}else {
					isLianJie=false;

					wangluo.setVisibility(View.VISIBLE);
				}


//				if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//					Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
//				} else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//					Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
//				} else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//					Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
//				}
//API大于23时使用下面的方式进行网络监听
			}else {

				Log.d(TAG, "API23");
				//获得ConnectivityManager对象
				ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

				//获取所有网络连接的信息
				Network[] networks = connMgr.getAllNetworks();
				//用于存放网络连接信息
				StringBuilder sb = new StringBuilder();
				//通过循环将网络信息逐个取出来
				Log.d(TAG, "networks.length:" + networks.length);
				if (networks.length==0){
					isLianJie=false;
					wangluo.setVisibility(View.VISIBLE);
				}
				for (int i=0; i < networks.length; i++){
					//获取ConnectivityManager对象对应的NetworkInfo对象
					NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

					if (networkInfo.isConnected()){
						wangluo.setVisibility(View.GONE);

					}
				}

			}
		}
	}


	/*
     * @param arg0
     */
	@Override
	public void onSynthesizeStart(String utteranceId) {
		//toPrint("onSynthesizeStart utteranceId=" + utteranceId);
	}

	/**
	 * 合成数据和进度的回调接口，分多次回调
	 *
	 * @param utteranceId
	 * @param data 合成的音频数据。该音频数据是采样率为16K，2字节精度，单声道的pcm数据。
	 * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
	 */
	@Override
	public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress) {
		// toPrint("onSynthesizeDataArrived");
		//mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_SYNTHES_TEXT_SELECTION, progress, 0));
	}

	/**
	 * 合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
	 *
	 * @param utteranceId
	 */
	@Override
	public void onSynthesizeFinish(String utteranceId) {
		//toPrint("onSynthesizeFinish utteranceId=" + utteranceId);
	}

	/**
	 * 播放开始，每句播放开始都会回调
	 *
	 * @param utteranceId
	 */
	@Override
	public void onSpeechStart(String utteranceId) {
		//toPrint("onSpeechStart utteranceId=" + utteranceId);
	}

	/**
	 * 播放进度回调接口，分多次回调
	 *
	 * @param utteranceId
	 * @param progress 文本按字符划分的进度，比如:你好啊 进度是0-3
	 */
	@Override
	public void onSpeechProgressChanged(String utteranceId, int progress) {
		// toPrint("onSpeechProgressChanged");
		//mHandler.sendMessage(mHandler.obtainMessage(UI_CHANGE_INPUT_TEXT_SELECTION, progress, 0));
	}

	/**
	 * 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
	 *
	 * @param utteranceId
	 */
	@Override
	public void onSpeechFinish(String utteranceId) {
		//toPrint("onSpeechFinish utteranceId=" + utteranceId);
	}

	/**
	 * 当合成或者播放过程中出错时回调此接口
	 *
	 * @param utteranceId
	 * @param error 包含错误码和错误信息
	 */
	@Override
	public void onError(String utteranceId, SpeechError error) {
		//toPrint("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
	}


//	private class DownloadReceiver extends ResultReceiver {
//		public DownloadReceiver(Handler handler) {
//			super(handler);
//		}
//		@Override
//		protected void onReceiveResult(int resultCode, Bundle resultData) {
//			super.onReceiveResult(resultCode, resultData);
//			if (resultCode == DownloadService.UPDATE_PROGRESS) {
//				String ididid=resultData.getString("ididid2");
//				int progress = resultData.getInt("progress");
//
//				if (progress == 100) {
//					try {
//
//						//下载完成
//						//更新状态
//						Log.d(TAG, "ididididididd值："+ididid);
//						if (ididid!=null) {
//							ShiPingBean b = shiPingBeanDao.load(ididid);
//							b.setIsDownload(true);
//							shiPingBeanDao.update(b);
//
//							if (shiPingBeanList.size() > 0) {
//								shiPingBeanList.clear();
//							}
//							shiPingBeanList = shiPingBeanDao.loadAll();
//
//							ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory() + File.separator + "linhefile" + File.separator + b.getId() + "." + b.getVideoType().split("\\/")[1]);
//							ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//								@Override
//								public void onPrepared(IMediaPlayer iMediaPlayer) {
//									ijkVideoView.start();
//								}
//							});
//						}else {
//							Log.d(TAG, "id的值是空");
//						}
//
//					}catch (Exception e){
//						Log.d(TAG, "捕捉到异常onReceiveResult"+e.getMessage());
//					}
//
//					//ijkVideoView.setVideoPath(mFile.getPath());
//					//ijkVideoView.start();
////					Intent install = new Intent();
////					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////					install.setAction(android.content.Intent.ACTION_VIEW);
////					install.setDataAndType(Uri.fromFile(mFile),"application/vnd.android.package-archive");
////					startActivity(install);  //下载完成打开APK
//				}
//			}
//		}
//	}

//	private class DownloadReceiverTuPian extends ResultReceiver {
//		public DownloadReceiverTuPian(Handler handler) {
//			super(handler);
//		}
//		@Override
//		protected void onReceiveResult(int resultCode, Bundle resultData) {
//			super.onReceiveResult(resultCode, resultData);
//			if (resultCode == DownloadTuPianService.UPDATE_PROGRESS2) {
//				int progress = resultData.getInt("progress");
//
//				if (progress == 100) {
//					try {
//						//下载完成
//						// Environment.getExternalStorageDirectory()+ File.separator+"linhefile"+File.separator+"tupian111.jpg"
//						Log.d(TAG, "图片下载完成");
//
//					}catch (Exception e){
//						Log.d(TAG, "捕捉到异常onReceiveResult"+e.getMessage());
//					}
//
//				}
//			}
//		}
//	}

//	public static final int TIMEOUT = 1000 * 60;
//	private void link_chengshi() {
//		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//		OkHttpClient okHttpClient= new OkHttpClient.Builder()
//				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.retryOnConnectionFailure(true)
//				.build();
//
////		RequestBody body = new FormBody.Builder()
////				.add("cityCode","101040100")
////				.add("weatherType","1")
////				.build();
//
//		Request.Builder requestBuilder = new Request.Builder()
//				//.header("Content-Type", "application/json")
//				.get()
//				.url("http://api.map.baidu.com/location/ip?ak=uTTmEt0NeHSsgAKsXGLAMC8mvg6zPNLm" +
//						"&mcode=21:21:DA:F2:00:51:3B:AB:C4:E6:19:18:31:C6:98:CA:D6:7B:44:AE;com.example.xuexiaotanchuangyanshi");
//		//.url("http://wthrcdn.etouch.cn/weather_mini?city=广州市");
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				Log.d("AllConnects", "请求添加陌生人成功"+call.request().toString());
//				//获得返回体
//				try {
//
//					ResponseBody body = response.body();
//				//	Log.d("AllConnects", "aa   "+response.body().string());
//
//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//				//	JsonObject object=jsonObject.get("ContentBean").getAsJsonObject();
//
//					IpAddress zhaoPianBean=gson.fromJson(jsonObject,IpAddress.class);
//
//
//					/**从assets中读取txt
//					 * 按行读取txt
//					 * @param
//					 * @return
//					 * @throws Exception
//					 */
//
//						InputStream is = getAssets().open("tianqi.txt");
//						InputStreamReader reader = new InputStreamReader(is);
//						BufferedReader bufferedReader = new BufferedReader(reader);
//						//StringBuffer buffer = new StringBuffer("");
//						String str;
//						String aa=zhaoPianBean.getContent().getAddress_detail().getCity();
//						if (aa.length()>2){
//							aa=aa.substring(0,2);
//						//	Log.d("VlcVideoActivity", "fffff9"+aa);
//						}
//						while ((str = bufferedReader.readLine()) != null) {
//
//
//							if (str.contains(aa)){
//								//Log.d("VlcVideoActivity", "fffff3"+str);
//								link_tianqi(str);
//								break;
//							}
//						}
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//			}
//		});
//
//
//	}

//	private void link_tianqi(String bean) {
//		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//		OkHttpClient okHttpClient= new OkHttpClient.Builder()
//				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.retryOnConnectionFailure(true)
//				.build();
////		RequestBody body = new FormBody.Builder()
////				.add("cityCode","101040100")
////				.add("weatherType","1")
////				.build();
//
//		Request.Builder requestBuilder = new Request.Builder()
//				//.header("Content-Type", "application/json")
//				.get()
//				//.url("https://api.map.baidu.com/location/ip?ak=uTTmEt0NeHSsgAKsXGLAMC8mvg6zPNLm" +
//					//	"&mcode=21:21:DA:F2:00:51:3B:AB:C4:E6:19:18:31:C6:98:CA:D6:7B:44:AE;com.example.xuexiaotanchuangyanshi");
//
//				.url("http://wthrcdn.etouch.cn/weather_mini?citykey=" + bean.substring(bean.length() - 9, bean.length()));
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				Log.d("AllConnects", "请求天气成功"+call.request().toString());
//				//获得返回体
//				try {
//
//					ResponseBody body = response.body();
//					//Log.d("AllConnects", "aa   "+response.body().string());
//
//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//					//JsonObject object=jsonObject.get("ContentBean").getAsJsonObject();
//
//					final TianQiBean zhaoPianBean=gson.fromJson(jsonObject,TianQiBean.class);
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							tianqi0.setText(zhaoPianBean.getData().getCity());
//							tianqi1.setText(zhaoPianBean.getData().getWendu()+" 度");
//						//	tianqi2.setText(zhaoPianBean.getData().getGanmao());
//						}
//					});
//
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//			}
//		});
//
//
//	}

}
