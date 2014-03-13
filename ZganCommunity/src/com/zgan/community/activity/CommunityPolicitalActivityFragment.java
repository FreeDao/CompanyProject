package com.zgan.community.activity;

import java.util.ArrayList;
import java.util.List;
import com.zgan.community.R;
import com.zgan.community.adapter.CommunityPolicitalAdapter;
import com.zgan.community.data.ContentData;
import com.zgan.community.data.MSZW_BGDD;
import com.zgan.community.jsontool.AppConstants;
import com.zgan.community.jsontool.DialogUtil;
import com.zgan.community.jsontool.GsonUtil;
import com.zgan.community.jsontool.HttpAndroidTask;
import com.zgan.community.jsontool.HttpClientService;
import com.zgan.community.jsontool.HttpPreExecuteHandler;
import com.zgan.community.jsontool.HttpResponseHandler;
import com.zgan.community.jsontool.JsonEntity;
import com.zgan.community.tools.MainAcitivity;
import com.zgan.community.tools.MyProgressDialog;
import com.zgan.community.tools.ZganCommunityStaticData;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class CommunityPolicitalActivityFragment extends Fragment {

	private Button back;
	private TextView title;

	private ListView list;
	private ListView list2;
	//private List<String> dList;//װ�����ݵ�List

	private Context con;
	private TabHost tabHost;
	private Handler handler;
	private MyProgressDialog dialog;
	int did = 1;
	int sid = 1;

	private List<ContentData> contentDataList = new ArrayList<ContentData>();
	private List<MSZW_BGDD> MSZW_BGDDList = new ArrayList<MSZW_BGDD>();

	private CommunityPolicitalAdapter adapter;
	
	//private LinearLayout category;
	private Button buttonCity; //����������
	private Button buttonCounty;//��������
	private Button buttonCommonService;//��������λ
	private RadioGroup group;	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_community_policital, container,false);
		back = (Button) view.findViewById(R.id.back);
		title = (TextView) view.findViewById(R.id.title);
		//title.setText(R.string.community_policital);
		title.setBackgroundResource(R.drawable.title_mingshengzhengwu);
		
		group=(RadioGroup) view.findViewById(R.id.group);
		con = CommunityPolicitalActivityFragment.this.getActivity();
		list = (ListView) view.findViewById(R.id.listViewPolitical);
		list2 = (ListView) view.findViewById(R.id.listViewPolitical2);
		list.setDividerHeight(0);
		list2.setDividerHeight(0);
		
		//category=(LinearLayout) findViewById(R.id.category);
		buttonCity=(Button) view.findViewById(R.id.buttonCity);
		buttonCounty=(Button) view.findViewById(R.id.buttonCounty);
		buttonCommonService=(Button) view.findViewById(R.id.buttonCommonService);
		
		ButtonClickListener l = new ButtonClickListener();
		back.setOnClickListener(l);
		buttonCity.setOnClickListener(l);
		buttonCounty.setOnClickListener(l);
		buttonCommonService.setOnClickListener(l);
		group.setOnCheckedChangeListener(listener);
		handler = new Handler();
		 		
		initTabHost(view); // ��ʼ��TabHost
		getData();// ��ȡ��������
		return view;
	}	
	
	private OnCheckedChangeListener listener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.zhengwuyaowen:
				if(contentDataList.size()>0)
				{
					showData(1);
				}else
				{
					getData();
				}
				tabHost.setCurrentTabByTag("tab1");
				break;
            case R.id.bangongdidian:
            	getData_b(-1);
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
				.setIndicator(null,null)
			    .setContent(R.id.listViewPolitical));

		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator(null,null)
				.setContent(R.id.linearLayoutPolitical2));
	}

	public class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				((Activity) con).finish();
				break;
			case R.id.buttonCity:
				//���������ŵ���¼�
				 buttonCity.setBackgroundResource(R.drawable.ssbm1);
				 buttonCounty.setBackgroundResource(R.drawable.qxzf);
				 buttonCommonService.setBackgroundResource(R.drawable.ggfwdw);
                 getData_b(0);
				break;
			case R.id.buttonCounty:
				//������������¼�
				buttonCity.setBackgroundResource(R.drawable.ssbm);
				buttonCounty.setBackgroundResource(R.drawable.qxzf1);
				buttonCommonService.setBackgroundResource(R.drawable.ggfwdw);
				getData_b(1);
				break;
			case R.id.buttonCommonService:
				//��������λ����¼�
				buttonCity.setBackgroundResource(R.drawable.ssbm);
				buttonCounty.setBackgroundResource(R.drawable.qxzf);
				buttonCommonService.setBackgroundResource(R.drawable.ggfwdw1);
				getData_b(2);
				break;
			default:
				break;
			}
		}

	}

	private void showData(int i) {
		// dList=new ArrayList<String>();
		if (i == 1) {
			adapter = new CommunityPolicitalAdapter(con, contentDataList, 1);
			list.setDivider(null);// ����ListViewû�зָ���
			list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		} else {
			adapter = new CommunityPolicitalAdapter(con, MSZW_BGDDList, 2);
			list2.setDivider(null);// ����ListViewû�зָ���
			list2.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}

	// ��ȡ��������
	//������������
		private void getData() {
			// TODO Auto-generated method stub
			//newsList = new ArrayList<News>();

			HttpClientService svr = new HttpClientService(
					AppConstants.HttpHostAdress+"zgancontent.aspx");//"http://community1.zgantech.com/ZganNews.aspx?did=15923258890"
			//����
			svr.addParameter("did",ZganCommunityStaticData.User_Number);
					
			HttpAndroidTask task = new HttpAndroidTask(con, svr,
					new HttpResponseHandler() {
						// ��Ӧ�¼�
						@SuppressWarnings("unchecked")
						public void onResponse(Object obj) {
							dialog.stop();
							JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
									obj,con,false);
							if (jsonEntity.getStatus() == 1) {
								handler.post(none);
							} else if (jsonEntity.getStatus() == 0) {
								contentDataList=(List<ContentData>) GsonUtil.getData(
											jsonEntity,AppConstants.type_contentDataList);	
									
									if(contentDataList.size()>0)
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
							dialog = new MyProgressDialog(context);
							DialogUtil.setAttr4progressDialog(dialog);
						}
					});
			task.execute(new String[] {});	
		}
		
		private void getData_b(int flid) {
			// TODO Auto-generated method stub
			//newsList = new ArrayList<News>();

			HttpClientService svr = new HttpClientService(
					AppConstants.HttpHostAdress+"zgancontent.aspx");//"http://community1.zgantech.com/ZganNews.aspx?did=15923258890"
			//����
			svr.addParameter("did",ZganCommunityStaticData.User_Number);
			if(flid==-1)
			{
				svr.addParameter("method","bgdd");
			}else
			{
				svr.addParameter("method","bgddfl");
				svr.addParameter("flid",flid);
			}
			
					
			HttpAndroidTask task = new HttpAndroidTask(con, svr,
					new HttpResponseHandler() {
						// ��Ӧ�¼�
						@SuppressWarnings("unchecked")
						public void onResponse(Object obj) {
							dialog.stop();
							JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
									obj,con,false);
							if (jsonEntity.getStatus() == 1) {
								handler.post(none);
							} else if (jsonEntity.getStatus() == 0) {
								MSZW_BGDDList.clear();
								MSZW_BGDDList=(List<MSZW_BGDD>) GsonUtil.getData(
											jsonEntity,AppConstants.type_mSZW_BGDDList);	
									
									if(MSZW_BGDDList.size()>0)
					                {
					                	//�����ݵ�ʱ�����
										handler.post(r_b);
					                }else
					                {
					                	//û������ʱ����ʾ
					                	handler.post(none_b);
					                }														
							}														
						}
					}, new HttpPreExecuteHandler() {
						public void onPreExecute(Context context) {
							dialog = new MyProgressDialog(context);
							DialogUtil.setAttr4progressDialog(dialog);
						}
					});
			task.execute(new String[] {});	
		}


	// �����ݴ������
	Runnable none = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// dialog.dismiss();
			dialog.stop();
			Toast.makeText(con, "û�пɹ����ص�����", 2).show();
		}
	};

	// ���ݼ�����֮��Ĳ���
	Runnable r = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// dialog.dismiss();
			if (contentDataList.size() <= 0) {
				Toast.makeText(con, "ʵʱ����û�пɼ��ص�����", 2).show();
			} else {
				tabHost.setCurrentTabByTag("tab1");// ѡ�е�һ��Tab
				showData(1);// ��ʼ������
			}

		}
	};
	
	// �����ݴ������
		Runnable none_b = new Runnable() {

			@Override
			public void run() {
				Toast.makeText(con, "û�пɹ����ص�����", 2).show();
			}
		};

		// ���ݼ�����֮��Ĳ���
		Runnable r_b = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// dialog.dismiss();
				if (MSZW_BGDDList.size() <= 0) {
					Toast.makeText(con, "û�пɼ��ص�����", 2).show();
				} else {
					tabHost.setCurrentTabByTag("tab2");// ѡ�е�һ��Tab
					showData(2);// ��ʼ������
				}

			}
		};

	public boolean isNotNull(String str) {
		return ((str != null) && (str != "") && (!str.equals(null)) && (!str
				.equals("")));
	}

}
