package com.zgan.yckz.activity;

import java.util.List;

import com.zgan.yckz.R;
import com.zgan.yckz.jsontool.AppConstants;
import com.zgan.yckz.jsontool.GsonUtil;
import com.zgan.yckz.jsontool.HttpAndroidTask;
import com.zgan.yckz.jsontool.HttpClientService;
import com.zgan.yckz.jsontool.HttpPreExecuteHandler;
import com.zgan.yckz.jsontool.HttpResponseHandler;
import com.zgan.yckz.jsontool.JsonEntity;
import com.zgan.yckz.jsontool.User;
import com.zgan.yckz.socket.Constant;
import com.zgan.yckz.tcp.Frame;
import com.zgan.yckz.tools.YCKZ_SQLHelper;
import com.zgan.yckz.tools.YCKZ_Static;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UserInfo_Activity extends Activity {
	
	Button back;

	Button logout_btn;

	Builder builder;

	YCKZ_SQLHelper yckz_SQLHelper;

	SQLiteDatabase db;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	private TextView nicknameText;//�ǳ���ʾ��
	private TextView balcony;//¥����
	private TextView phone;//�ֻ�����
	
	private Handler handler;
	private Context con;
	
	Handler SubDatahandler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// super.handleMessage(msg);
			switch (msg.what) {
			case Constant.DATAERROR:
				System.out.println("NewSocketInfo  Constant.DATAERROR  "
						+ msg.obj);
				break;
			case Constant.CHECKCODEERROR:
				System.out.println("NewSocketInfo  Constant.CHECKCODEERROR "
						+ msg.obj);
				break;
			case Constant.NETERROR:
				System.out.println("NewSocketInfo  Constant.NETERROR  "
						+ msg.obj);
				break;
			case Constant.SERVERERROR:
				System.out.println("NewSocketInfo  Constant.SERVERERROR "
						+ msg.obj);
				break;
			case Constant.DATASUCCESS:
				Log.i("index", "1");
				byte[] buffer;
				// buffer = new byte[Constant.MSG_NUM];
				// Arrays.fill(buffer, (byte)0);
				buffer = (byte[]) msg.obj;
				Frame frame = new Frame(buffer);

				System.out.println("��������...ƽ̨����" + frame.Platform);
				System.out.println("��������...�汾��" + frame.Version);
				System.out.println("��������...������������" + frame.MainCmd);
				System.out.println("��������...�ӹ���������" + frame.SubCmd);
				System.out.println("��������...����" + frame.strData);
				System.out.println("NewSocketInfo  Constant.DATASUCCESS "
						+ buffer);

				if (frame.MainCmd == 13 && frame.SubCmd == 3
						&& frame.strData != null) {
					String Port[] = frame.strData.split("\t");
					Index_Activity.alarmstr = Port[1];
					Index_Activity.SendReturn();
					NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					Notification notification = new Notification();
					notification.icon = R.drawable.ic_launcher;
					Intent intent = new Intent(UserInfo_Activity.this,
							Alarm_Activity.class);
					if (db != null) {
						Index_Activity.InSertAlarmSQl(db, Port[2]);

					} else {
						yckz_SQLHelper = new YCKZ_SQLHelper(
								UserInfo_Activity.this, "yckz.db3", 1);
						db = yckz_SQLHelper.getReadableDatabase();
						Index_Activity.InSertAlarmSQl(db, Port[2]);
					}
					PendingIntent pendingIntent = PendingIntent.getActivity(
							UserInfo_Activity.this, 0, intent,
							PendingIntent.FLAG_ONE_SHOT);
					notification.setLatestEventInfo(UserInfo_Activity.this,
							"������Ϣ��", "��һ��������Ϣ", pendingIntent);
					notification.flags = Notification.FLAG_AUTO_CANCEL;// ������Զ���ʧ

					manager.notify(1, notification);

				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (db!=null) {
			db.close();
		}
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		preferences = getSharedPreferences("yckz_user", MODE_PRIVATE);
		editor = preferences.edit();
		builder = new AlertDialog.Builder(this);
		yckz_SQLHelper = new YCKZ_SQLHelper(this, "yckz.db3", 1);
		db = yckz_SQLHelper.getReadableDatabase();

		setContentView(R.layout.userinfo_line);
		con=UserInfo_Activity.this;

		back = (Button) findViewById(R.id.back);
		logout_btn = (Button) findViewById(R.id.logout_btn);
		
		nicknameText=(TextView) findViewById(R.id.nicknameText);//�ǳ���ʾ��
		balcony=(TextView) findViewById(R.id.balcony);//¥����
		phone=(TextView) findViewById(R.id.phone);//�ֻ�����
		handler=new Handler();

		back.setOnClickListener(listener);
		logout_btn.setOnClickListener(listener);
         
		getUserInfo();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;

			case R.id.logout_btn:

				builder.setTitle("��ʾ");
				builder.setMessage("�Ƿ�ע����ǰ�û���");
				builder.setNegativeButton("ȡ��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
							}

						});
				builder.setPositiveButton("ȷ��",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								YCKZ_Static.Phone_number=null;
								YCKZ_Static.USER_password=null;
								editor.clear();
								editor.commit();
								android.os.Process.killProcess(android.os.Process.myPid());
								System.exit(0);

							}

						});
				builder.create().show();

				break;

			}
		}
	};
	
	// ��ȡҵ����Ϣ
	private void getUserInfo() {
		HttpClientService svr = new HttpClientService(
				AppConstants.HttpHostAdress+"zganuserinfo.aspx");
		//����
		svr.addParameter("did",YCKZ_Static.Phone_number);
				
		HttpAndroidTask task = new HttpAndroidTask(con, svr,
				new HttpResponseHandler() {
					// ��Ӧ�¼�
					@SuppressWarnings("unchecked")
					public void onResponse(Object obj) {
						JsonEntity jsonEntity = GsonUtil.parseObj2JsonEntity(
								obj,con,false);
						if (jsonEntity.getStatus() == 1) {
							Toast.makeText(con, "ҵ����Ϣ��ȡʧ�ܣ�", Toast.LENGTH_SHORT).show();
						} else if (jsonEntity.getStatus() == 0) {
							List<User> userInfo=(List<User>) GsonUtil.getData(
										jsonEntity,AppConstants.type_userList);	
							handler.post(updateUIRunnable(userInfo.get(0)));																							
						}														
					}					
				}, new HttpPreExecuteHandler() {
					public void onPreExecute(Context context) {
					}
				});
		task.execute(new String[] {});	
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

}
