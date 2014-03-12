package com.zgan.community.activity;

import java.util.ArrayList;
import java.util.List;

import com.zgan.community.R;
import com.zgan.community.data.News;
import com.zgan.community.data.User;
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
import com.zgan.community.tools.SlipButton;
import com.zgan.community.tools.ZganCommunityStaticData;
import com.zgan.community.tools.SlipButton.OnChangedListener;

import android.os.Bundle;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CommunitySetting extends MainAcitivity {

	private Button back;
	private TextView title;
	private Context con;
	
	private LinearLayout about;//����
	private LinearLayout feedback;//�������
	//private Button messageSwitch;
	private Button passwordChange;//�����޸�
	private EditText nicknameText;//�ǳ���ʾ��
	private ToggleButton nicknameChange;//�ǳ��޸İ�ť
	private TextView balcony;//¥����
	private TextView phone;//�ֻ�����
	//private SlipButton messageSwitch;//��Ϣ���Ϳ���
	
	private boolean clicked=false;
	
	//����һ����������ʶDatePickerDialog  
    private static final int DATE_PICKER_ID = 3;
    private MyProgressDialog pdialog;
    
    private List<User> userInfo=new ArrayList<User>();
    private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_setting);
		
		back = (Button) findViewById(R.id.back);
		title = (TextView) findViewById(R.id.title);
		title.setText(R.string.personal_settings);
		con = CommunitySetting.this;
		
		about=(LinearLayout) findViewById(R.id.about);//����
		feedback=(LinearLayout) findViewById(R.id.feedback);//�������
		passwordChange=(Button) findViewById(R.id.passwordChange);//�����޸�
		nicknameText=(EditText) findViewById(R.id.nicknameText);//�ǳ���ʾ��
		nicknameChange=(ToggleButton) findViewById(R.id.nicknameChange);//�ǳ��޸İ�ť
		balcony=(TextView) findViewById(R.id.balcony);//¥����
		phone=(TextView) findViewById(R.id.phone);//�ֻ�����
		//messageSwitch=(SlipButton) findViewById(R.id.slipbutton);
		
		handler=new Handler();
				
		back.setOnClickListener(l);
		passwordChange.setOnClickListener(l);
		feedback.setOnClickListener(l);
		about.setOnClickListener(l);
		//nicknameChange.setOnClickListener(l);		
		nicknameChange.setOnCheckedChangeListener(listener);
		/*messageSwitch.SetOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(boolean CheckState) {
				Toast.makeText(con, ""+CheckState, Toast.LENGTH_SHORT).show();
			}
		});*/
		getUserInfo();//��ȡҵ����Ϣ
	}
	
	OnCheckedChangeListener listener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked)
			{
				nicknameText.setEnabled(true);
				nicknameText.requestFocus();
				buttonView.setBackgroundResource(R.drawable.make_sure);
			}else
			{
				nicknameText.setEnabled(false);
				nicknameText.clearFocus();
				String name=nicknameText.getText().toString().trim();
				if(name!=null&&!name.equals(""))
				{
					//�����ǳ�
					modifyNickName(nicknameText.getText().toString());
					buttonView.setBackgroundResource(R.drawable.change_btn);
				}else
				{
					Toast.makeText(con, "�ǳƲ���Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	
	
	private OnClickListener l=new OnClickListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.passwordChange:
				 //�����޸Ĳ���
				Intent intent=new Intent(con,CommunityModifyPasswordActivity.class);
				startActivity(intent);
				break;
			case R.id.feedback:
				//�������
				Intent i=new Intent(con,CommunitySettingFeedBack.class);
				startActivity(i);
				break;
			case R.id.about:
				//����
				Intent aboutI=new Intent(con,About_Detail_Activity.class);
				startActivity(aboutI);
				break;

			default:
				break;
			}
		}
	};

	
	public void buttonToggleAction(Button button,EditText editText)
	{
		if(clicked)
		{
			clicked=false;
		}else
		{
			clicked=true;
		}
		if(clicked)
		{
			editText.setEnabled(true);
			editText.requestFocus();
		}else
		{
			editText.setEnabled(false);
			editText.clearFocus();
			String name=editText.getText().toString().trim();
			if(name!=null&&!name.equals(""))
			{
				//�����ǳ�
				modifyNickName(editText.getText().toString());
			}else
			{
				Toast.makeText(con, "�ǳƲ���Ϊ�գ�", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
	//�޸�ҵ���ǳ�
	private void modifyNickName(final String newName){
		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zganuserinfo.aspx");
		//����
		svr.addParameter("did",ZganCommunityStaticData.User_Number);
		svr.addParameter("method","nickname");
		svr.addParameter("name",newName);
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						pdialog.stop();
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							Toast.makeText(con, "ҵ���ǳƸ���ʧ�ܣ�", Toast.LENGTH_SHORT).show();
						} else if (jsonEntity.getStatus() == 0) {							
							handler.post(updateUIRunnable(newName));																							
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
	
	// ��ȡҵ����Ϣ
	private void getUserInfo() {
		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zganuserinfo.aspx");
		//����
		svr.addParameter("did",ZganCommunityStaticData.User_Number);
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						pdialog.stop();
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							Toast.makeText(con, "ҵ����Ϣ��ȡʧ�ܣ�", Toast.LENGTH_SHORT).show();
						} else if (jsonEntity.getStatus() == 0) {
							userInfo=(List<User>) GsonUtil.getData(
										jsonEntity,AppConstants.type_userList);	
							handler.post(updateUIRunnable(userInfo.get(0)));																							
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
	
	/**
	 * ����ҵ���ǳƺ����UI Runnable
	 * @param newName
	 * @return
	 */
	private Runnable updateUIRunnable(final String newName) {
        Runnable r=new Runnable() {
			
			@Override
			public void run() {
				nicknameText.setText(newName);
				Toast.makeText(con, "���³ɹ���", Toast.LENGTH_SHORT).show();
			}
		};
		
		return r;
	}
	
	/**
	 * ��ȡҵ����Ϣ�����UI Runnable
	 * @param user
	 * @return
	 */
	private Runnable updateUIRunnable(final User user) {
		Runnable r=new Runnable() {
			
			@Override
			public void run() {
					if(!user.equals(null))
					{
						phone.setText(user.getUserName());
						nicknameText.setText(user.getNickname());
						balcony.setText(user.getLocation());
					}				
			}
		};
		return r;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_community_setting, menu);
		return false;
	}

}
