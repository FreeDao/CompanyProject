package com.zgan.community.activity;

import com.zgan.community.R;
import com.zgan.community.R.layout;
import com.zgan.community.R.menu;
import com.zgan.community.activity.CommunityPolicitalActivity.ButtonClickListener;
import com.zgan.community.tools.MainAcitivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CommunitySetting extends MainAcitivity {

	private Button back;
	private TextView title;
	private Context con;
	
	private LinearLayout about;//����
	private LinearLayout feedback;//�������
	private Button messageSwitch;//��Ϣ���Ϳ���
	private Button passwordChange;//�����޸�
	private RadioGroup sexRadioGroup;//�Ա�sexRadioGroup
	private EditText birthText;//������ʾ��
	private Button birthChange;//�����޸İ�ť
	private EditText nicknameText;//�ǳ���ʾ��
	private Button nicknameChange;//�ǳ��޸İ�ť
	private TextView balcony;//¥����
	private TextView phone;//�ֻ�����
	
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
		messageSwitch=(Button) findViewById(R.id.messageSwitch);//��Ϣ���Ϳ���
		passwordChange=(Button) findViewById(R.id.passwordChange);//�����޸�
		sexRadioGroup=(RadioGroup) findViewById(R.id.sexRadioGroup);//�Ա�sexRadioGroup
		birthText=(EditText) findViewById(R.id.birthText);//������ʾ��
		birthChange=(Button) findViewById(R.id.birthChange);//�����޸İ�ť
		nicknameText=(EditText) findViewById(R.id.nicknameText);//�ǳ���ʾ��
		nicknameChange=(Button) findViewById(R.id.nicknameChange);//�ǳ��޸İ�ť
		balcony=(TextView) findViewById(R.id.balcony);//¥����
		phone=(TextView) findViewById(R.id.phone);//�ֻ�����
				
		back.setOnClickListener(l);
		passwordChange.setOnClickListener(l);
		feedback.setOnClickListener(l);
		about.setOnClickListener(l);
		nicknameChange.setOnClickListener(l);
		
	}
	
	
	private OnClickListener l=new OnClickListener() {
		
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
				break;
			case R.id.about:
				//����
				
				break;
			case R.id.nicknameChange:
				//�޸��ǳ�
				buttonToggleAction(nicknameChange,nicknameText);
				
				break;

			default:
				break;
			}
		}
	};

	
	public void buttonToggleAction(Button button,EditText editText)
	{
		if(!button.getText().toString().equals("ȷ��"))
		{
			editText.setEnabled(true);
			editText.requestFocus();
			button.setText("ȷ��");
		}else
		{
			editText.setEnabled(false);
			editText.clearFocus();
			button.setText("�޸�");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_community_setting, menu);
		return false;
	}

}
