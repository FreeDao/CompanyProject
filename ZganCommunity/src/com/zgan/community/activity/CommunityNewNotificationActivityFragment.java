package com.zgan.community.activity;

import java.util.ArrayList;
import java.util.List;

import com.zgan.community.R;
import com.zgan.community.adapter.CommunityNewsAdapter;
import com.zgan.community.data.News;
import com.zgan.community.jsontool.AppConstants;
import com.zgan.community.jsontool.DialogUtil;
import com.zgan.community.jsontool.GsonUtil;
import com.zgan.community.jsontool.HttpAndroidTask;
import com.zgan.community.jsontool.HttpClientService;
import com.zgan.community.jsontool.HttpPreExecuteHandler;
import com.zgan.community.jsontool.HttpResponseHandler;
import com.zgan.community.jsontool.JsonEntity;
import com.zgan.community.tools.MyProgressDialog;
import com.zgan.community.tools.ZganCommunityStaticData;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class CommunityNewNotificationActivityFragment extends Fragment {

	private Button back;
	private TextView title;

	private ListView list;
	private ListView list2;
	// private List<String> dList;//װ�����ݵ�List

	private Context con;
	private TabHost tabHost;
	private Handler handler;
	private MyProgressDialog dialog;
	int did = 1;
	int sid = 1;

	private List<News> newsList=new ArrayList<News>();
	private List<News> newsList_s=new ArrayList<News>();
	private MyProgressDialog pdialog;
	private CommunityNewsAdapter communityNewsAdapter;
	
	private RadioGroup group;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_community_policital_news, container,false);
		back = (Button) view.findViewById(R.id.back);
		title = (TextView) view.findViewById(R.id.title);
		//title.setText(R.string.community_notification_title);
		title.setBackgroundResource(R.drawable.title_tongzhigonggao);
		
		group=(RadioGroup) view.findViewById(R.id.group);
		con = this.getActivity();
		list = (ListView) view.findViewById(R.id.listViewPolitical);
		list2 = (ListView) view.findViewById(R.id.listViewPolitical2);
		list.setDividerHeight(0);
		list2.setDividerHeight(0);
		ButtonClickListener l = new ButtonClickListener();
		back.setOnClickListener(l);
		group.setOnCheckedChangeListener(listener);

		handler = new Handler();
		dialog = new MyProgressDialog(con);
		dialog.start("�����У����Ժ�...");

		initTabHost(view); // ��ʼ��TabHost
		getData();// ��ȡ��������   ��ҵ֪ͨ
		return view;
	}

	private OnCheckedChangeListener listener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.wuyetongzhi:
				if(newsList.size()>0)
				{
					showData(1);
				}else
				{
					getData();
				}
				tabHost.setCurrentTabByTag("tab1");
				break;
				
            case R.id.shequgonggao:
            	if(newsList_s.size()>0)
				{
					showData(2);
				}else
				{
					getData_s();
				}
            	tabHost.setCurrentTabByTag("tab2");
				break; 

			default:
				break;
			}
		}
	};

	/**
	 * ��ʼ��TabHost
	 */
	public void initTabHost(View view) {
		tabHost = (TabHost) view.findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.getTabWidget();

		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator(null,
						null).setContent(R.id.listViewPolitical));

		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator(null, null)
				.setContent(R.id.listViewPolitical2));

	}

	public class ButtonClickListener implements View.OnClickListener {
		Intent intent = null;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				((Activity) con).finish();
				break;

			default:
				break;
			}
		}

	}

	private void showData(int i) {
		// dList=new ArrayList<String>();
		if (i == 1) {
			communityNewsAdapter=new CommunityNewsAdapter(con, newsList);
			list.setDivider(null);// ����ListViewû�зָ���
			list.setAdapter(communityNewsAdapter);
			communityNewsAdapter.notifyDataSetChanged();
		} else {
			communityNewsAdapter=new CommunityNewsAdapter(con, newsList_s);
			list2.setDivider(null);// ����ListViewû�зָ���
			list2.setAdapter(communityNewsAdapter);
			communityNewsAdapter.notifyDataSetChanged();
		}

	}

	// ��ȡ��������
	private void getData() {
		// TODO Auto-generated method stub
		//newsList = new ArrayList<News>();

		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zgannews.aspx");//"http://community1.zgantech.com/ZganNews.aspx?did=15923258890"
		//����
		svr.addParameter("did",ZganCommunityStaticData.User_Number);
		svr.addParameter("method","news_wy");
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						pdialog.stop();
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							handler.post(none);
						} else if (jsonEntity.getStatus() == 0) {
								newsList=(List<News>) GsonUtil.getData(
										jsonEntity,AppConstants.type_newsList);	
								
								if(newsList.size()>0)
				                {
				                	//�����ݵ�ʱ�����
									handler.post(r);
				                }else
				                {
				                	//û������ʱ����ʾ
				                	handler.post(none);
				                }														
						}														
					}
				}, new HttpPreExecuteHandler() {
					public void onPreExecute(Context context) {
						pdialog = new MyProgressDialog(context);
						DialogUtil.setAttr4progressDialog(pdialog);
					}
				});
		task.execute(new String[] {});	
	}
	
	//������������
	private void getData_s() {
		// TODO Auto-generated method stub
		//newsList = new ArrayList<News>();

		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zgannews.aspx");//"http://community1.zgantech.com/ZganNews.aspx?did=15923258890"
		//����
		svr.addParameter("did",ZganCommunityStaticData.User_Number);
		svr.addParameter("method","news_cq");
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						pdialog.stop();
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							handler.post(none_s);
						} else if (jsonEntity.getStatus() == 0) {
								newsList_s=(List<News>) GsonUtil.getData(
										jsonEntity,AppConstants.type_newsList);	
								
								if(newsList_s.size()>0)
				                {
				                	//�����ݵ�ʱ�����
									handler.post(r_s);
				                }else
				                {
				                	//û������ʱ����ʾ
				                	handler.post(none_s);
				                }														
						}														
					}
				}, new HttpPreExecuteHandler() {
					public void onPreExecute(Context context) {
						pdialog = new MyProgressDialog(context);
						DialogUtil.setAttr4progressDialog(pdialog);
					}
				});
		task.execute(new String[] {});	
	}
	
	
	// ���ݼ�����֮��Ĳ���
			Runnable r_s = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// dialog.dismiss();
					//dialog.stop();
					if (newsList_s.size() <= 0) {
						Toast.makeText(con, "��������������", 1).show();					
					} else {
						//tabHost.setCurrentTabByTag("tab2");// ѡ�е�һ��Tab
						showData(2);// ��ʼ������
					}

				}
			};

		// �����ݴ������
		Runnable none_s = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// dialog.dismiss();
				//dialog.stop();
				Toast.makeText(con, "��������������", 2).show();
			}
		};

		
	// �����ݴ������
		Runnable none = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// dialog.dismiss();
				//dialog.stop();
				Toast.makeText(con, "û�пɹ����ص�����", 2).show();
			}
		};

		
	// ���ݼ�����֮��Ĳ���
	Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// dialog.dismiss();
			//dialog.stop();
			if (newsList.size() <= 0) {
				Toast.makeText(con, "û������", 2).show();
			} else {
				tabHost.setCurrentTabByTag("tab1");// ѡ�е�һ��Tab
				showData(1);// ��ʼ������
			}

		}
	};

	public boolean isNotNull(String str) {
		return ((str != null) && (str != "") && (!str.equals(null)) && (!str
				.equals("")));
	}
}

