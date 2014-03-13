package com.zgan.community.activity;

import com.zgan.community.R;
import com.zgan.community.tools.MainAcitivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Life_Pepsi_sonFragment extends Fragment {
	/**
	 * ���ذ�ť
	 */
	Button back;

	TextView top_title;

	Button house_magament;
	Button social_security;
	Button labour_employment;
	Button traffic_management;
	Button consumer_protection;
	Button marriage_registration;
	Button family_plann;
	Button labor_arbitration;
	Button housing;
	Button entry_exit;
	Button other_kid;
	Button military_service;
	private Context con=this.getActivity();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.line_life_pepsi_son, container,false);
		back = (Button) view.findViewById(R.id.back);
		house_magament = (Button) view.findViewById(R.id.house_magament);
		social_security = (Button) view.findViewById(R.id.social_security);
		labour_employment = (Button) view.findViewById(R.id.labour_employment);
		traffic_management = (Button) view.findViewById(R.id.traffic_management);
		consumer_protection = (Button) view.findViewById(R.id.consumer_protection);
		marriage_registration = (Button) view.findViewById(R.id.marriage_registration);
		family_plann = (Button) view.findViewById(R.id.family_plann);
		labor_arbitration = (Button) view.findViewById(R.id.labor_arbitration);
		housing = (Button) view.findViewById(R.id.housing);
		entry_exit = (Button) view.findViewById(R.id.entry_exit);
		other_kid = (Button) view.findViewById(R.id.other_kid);
		military_service = (Button) view.findViewById(R.id.military_service);


		top_title = (TextView) view.findViewById(R.id.title);
		//top_title.setText("����ָ��");
		top_title.setBackgroundResource(R.drawable.title_banshizhinan);
		back.setOnClickListener(l);

		house_magament.setOnClickListener(l);
		social_security.setOnClickListener(l);
		labour_employment.setOnClickListener(l);
		traffic_management.setOnClickListener(l);
		consumer_protection.setOnClickListener(l);
		marriage_registration.setOnClickListener(l);
		family_plann.setOnClickListener(l);
		labor_arbitration.setOnClickListener(l);
		housing.setOnClickListener(l);
		entry_exit.setOnClickListener(l);
		other_kid.setOnClickListener(l);
		military_service.setOnClickListener(l);
		return view;
	}

	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				((Activity) con).finish();
				break;

			case R.id.house_magament:
				Intent intent = new Intent(con,
						GuideActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("button_key", "����");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.social_security:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key","�籣");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.labour_employment:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "��ҵ");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.traffic_management:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "����");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.consumer_protection:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "��֤");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.marriage_registration:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "����");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.family_plann:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "����");
				intent.putExtras(bundle);
				startActivity(intent);
				break;

			case R.id.labor_arbitration:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key","��˰");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.housing:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "ס��");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.entry_exit:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "���뾳");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.other_kid:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key","���ⷿ");
				intent.putExtras(bundle);
				startActivity(intent);
				break;

			case R.id.military_service:
				intent = new Intent(con, GuideActivity.class);
				bundle = new Bundle();
				bundle.putString("button_key", "����");
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			}
		}
	};

}
