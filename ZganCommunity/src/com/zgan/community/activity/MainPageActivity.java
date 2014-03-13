package com.zgan.community.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.zgan.community.R;
import com.zgan.community.data.News;
import com.zgan.community.data.Weather;
import com.zgan.community.jsontool.AppConstants;
import com.zgan.community.jsontool.GsonUtil;
import com.zgan.community.jsontool.HttpAndroidTask;
import com.zgan.community.jsontool.HttpClientService;
import com.zgan.community.jsontool.HttpPreExecuteHandler;
import com.zgan.community.jsontool.HttpResponseHandler;
import com.zgan.community.jsontool.JsonEntity;
import com.zgan.community.tools.MainAcitivity;
import com.zgan.community.tools.ZganCommunityStaticData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainPageActivity extends MainAcitivity {

	private Button communityService; // ��������
	//private ImageButton communityCheap; // �ñ���
	private Button communityPay; // ��ֵ�ɷ�
	private Button communityTrade; // ������Ȧ
	private Button communityPolitical; // ��������
	private Button communityRecruit; // �й���Ϣ
	private Button communityLivelihood; // �������
	private Button communityHouseShelter; // ��ͥ��ʿ
	private Button communitySettings; // ��ϵ��ҵ
	private ImageView setting;

	private TextView clock; //ʱ��
	private TextView place; // �ص�
	private TextView date; // ����
	private TextView temperature; // �¶�
	private TextView message;//֪ͨ
	private LinearLayout messageLinear;
	//private Timer timer;
	private Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);

		communityService = (Button) findViewById(R.id.communityService);
		//communityCheap = (ImageButton) findViewById(R.id.communityCheap);
		communityPay = (Button) findViewById(R.id.communityPay);
		communityTrade = (Button) findViewById(R.id.communityTrade);
		communityPolitical = (Button) findViewById(R.id.communityPolicital);
		communityRecruit = (Button) findViewById(R.id.communityRecruit);
		communityLivelihood = (Button) findViewById(R.id.communityLivelihood);
		communityHouseShelter = (Button) findViewById(R.id.communityHouseShelter);
		communitySettings = (Button) findViewById(R.id.communitySettings);
		setting=(ImageView) findViewById(R.id.headIco);
		/*Gallery gallery = (Gallery) findViewById(R.id.advertising);
		ImagAdapter adapter=new ImagAdapter(con);
		gallery.setAdapter(adapter);*/
		con = MainPageActivity.this; // ��ʼ��һ��ȫ�ֵ�Context
		
		clock=(TextView) findViewById(R.id.clock);
		place=(TextView) findViewById(R.id.place);
		date=(TextView) findViewById(R.id.date);
		temperature=(TextView) findViewById(R.id.temperature);
		message=(TextView) findViewById(R.id.message);
		messageLinear=(LinearLayout) findViewById(R.id.meLinearLayout);
		
		ButtonClickListener l = new ButtonClickListener(); // ��ť�����������ʼ��

		communityService.setOnClickListener(l); // ��ťע�������
		//communityCheap.setOnClickListener(l);
		communityPay.setOnClickListener(l);
		communityTrade.setOnClickListener(l);
		communityPolitical.setOnClickListener(l);
		communityRecruit.setOnClickListener(l);
		communityLivelihood.setOnClickListener(l);
		communityHouseShelter.setOnClickListener(l);
		setting.setOnClickListener(l);
		//communitySmartHousing.setOnClickListener(l);
		//communityNeighbor.setOnClickListener(l);
		communitySettings.setOnClickListener(l);
		messageLinear.setOnClickListener(l);
		setMainPagePara();
	}
	
	//������ҳ��һЩʱ�����
	private void setMainPagePara()
	{
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0x1);
			}
			
		}, 0, 30000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(sdf.format(new Date())+"  "+getWeekOfDate());
        getNews();
        getTemperature();
	}
	
	private Handler handler= new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x1) {
				Date date = new Date();
				clock.setText(date.getHours()+":"+date.getMinutes());
			}
		}
	};
	
	public  String getWeekOfDate(){
		String[] weekdays = {"������","����һ","���ڶ�","������","������","������","������"};
		Calendar cal = Calendar.getInstance();
		Date curDate = new Date(System.currentTimeMillis());
		  cal.setTime(curDate);
		    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		    if (w < 0)
		        w = 0;
		    return weekdays[w];
		}

	public class ButtonClickListener implements View.OnClickListener {

		Intent intent = null;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.communityService:
				// ������������Ӧ�¼�
				intent = new Intent(con, MainFragmentActivity.class);
				intent.putExtra("TAG", R.id.radio_button0);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;

			/*case R.id.communityCheap:
				// �ñ��˵����Ӧ�¼�
				intent = new Intent(con, CommunityCheapActivity.class);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;*/
			case R.id.meLinearLayout:
				// ����֪ͨ�����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button2);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communityPay:
				// ��ֵ�ɷѵ����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button6);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communityTrade:
				// ������Ȧ�����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button1);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communityPolicital:
				// ������������Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button5);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communityRecruit:
				// �й���Ϣ�����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button3);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communityLivelihood:
				// ������µ����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button4);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communityHouseShelter:
				// ��ͥ��ʿ�����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button8);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				break;
			case R.id.communitySettings:
				// �ҵ����õ����Ӧ�¼�
				intent = new Intent(con, MainTabActivity.class);
				intent.putExtra("TAG", R.id.radio_button7);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				//finish();
				break;
			case R.id.headIco:
				// �ҵ����õ����Ӧ�¼�
				intent = new Intent(con, CommunitySetting.class);
				startActivity(intent);
				// Acticity�л�����
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				//finish();
				break;

			default:
				break;
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main_page, menu);
		return true;
	}

	//��ȡ��ǰ�¶�
	private void getTemperature() {
		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zganweather.aspx");
		//����
		svr.addParameter("did",ZganCommunityStaticData.User_Number);
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {					
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							temperature.setText("0");
						} else if (jsonEntity.getStatus() == 0) {
								List<Weather> weathers = (List<Weather>) GsonUtil.getData(
										jsonEntity,AppConstants.type_weatherList);	
							    if(weathers.size()>0)
							    {
							    	temperature.setText(weathers.get(0).getWd());
							    }else
							    {
							    	temperature.setText("0");
							    }
						}														
					}					
				}, new HttpPreExecuteHandler() {
					public void onPreExecute(Context context) {
					}
				});
		task.execute(new String[] {});	
	}
	
	//������������
	private void getNews() {
		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zgannews.aspx");
		//����
		svr.addParameter("did",ZganCommunityStaticData.User_Number);
		svr.addParameter("method","news_cq");
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {					
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							handler.post(none_s);
						} else if (jsonEntity.getStatus() == 0) {
								List<News> newsList_s = (List<News>) GsonUtil.getData(
										jsonEntity,AppConstants.type_newsList);	
								
								if(newsList_s.size()>0)
				                {
				                	//�����ݵ�ʱ�����
									handler.post(r(newsList_s.get(0)));
				                }														
						}														
					}					
				}, new HttpPreExecuteHandler() {
					public void onPreExecute(Context context) {
					}
				});
		task.execute(new String[] {});	
	}
	
	//�й����ʱ��
	private Runnable r(final News news) {
		return new Runnable() {
			
			@Override
			public void run() {
				message.setText(news.getTitle());
			}
		};
	}
	
	//�޹����ʱ��
	private Runnable none_s=new Runnable() {
		
		@Override
		public void run() {
			message.setText("�������¹���");
		}
	};
	/*class ImagAdapter extends BaseAdapter {
		private Context context;// ���ڽ��մ��ݹ�����Context����

		public ImagAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.i("im.length", ""+im.length);
			return im.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Log.i("magc", ""+position);

			ImageView iv = new ImageView(MainPageActivity.this);// ������洫�ݹ�����Context������
			//�ڴ��ֹ���
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1 ;
						BitmapFactory.Options opt = new BitmapFactory.Options();  
	        opt.inPreferredConfig = Bitmap.Config.RGB_565;  
	        opt.inPurgeable = true;  
	        opt.inInputShareable = true;  
	        // ��ȡ��ԴͼƬ  
	        Bitmap bitmap = BitmapFactory.decodeResource(MainPageActivity.this.getResources(), im[position]);

			iv.setImageBitmap(bitmap);
	        return iv;
		}*/


}
