package com.zgan.community.activity;

import com.zgan.community.R;
import com.zgan.community.baidu.ZganCommunityMapShow;
import com.zgan.community.tools.MainAcitivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CommunityServiceActivityFragment extends Fragment {

	private Button back;           //����
	private TextView title;        //����
	
	private Button move;           //���
	private Button unlock;         //��������
	private Button plumber;        //ˮ��ά��
	private Button pcrepair;       //����ά��
	private Button recycle;        //���û���
	private Button pet;            //����֮��
	//private Button psychological;  //������
	//private Button health;         //������ѯ
	//private Button law;            //������ѯ
	private Button pipe;           //��ͨ�ܵ�
	private Button matron;         //��ɩ
	private Button cleaning;       //����
	//private Button nanny;          //��ķ
	
	private Context con;
	private Intent intent=null;    //װ����ת����� ��ͼ

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_community_service, container,false);
		
		back=(Button) view.findViewById(R.id.back);
		title=(TextView) view.findViewById(R.id.title);
		//title.setText(R.string.community_service);    //���� ��������
		title.setBackgroundResource(R.drawable.title_shequfuwu);
		
		move=(Button) view.findViewById(R.id.buttonMove);
		unlock=(Button) view.findViewById(R.id.buttonUnlock);
		plumber=(Button) view.findViewById(R.id.buttonPlumber);
		pcrepair=(Button) view.findViewById(R.id.buttonPCrepair);
		recycle=(Button) view.findViewById(R.id.buttonRecycle);
		pet=(Button) view.findViewById(R.id.buttonPet);
		pipe=(Button) view.findViewById(R.id.buttonPipe);
		matron=(Button) view.findViewById(R.id.buttonMatron);
		cleaning=(Button) view.findViewById(R.id.buttonCleaning);
		
		con=this.getActivity();                     //��ʼ��Context
		
        ButtonClickListener l=new ButtonClickListener();       //��ť�����������ʼ��
		
        back.setOnClickListener(l);                            //��ťע�������
        move.setOnClickListener(l);
        unlock.setOnClickListener(l);
        plumber.setOnClickListener(l);
        pcrepair.setOnClickListener(l);
        recycle.setOnClickListener(l);
        pet.setOnClickListener(l);
        pipe.setOnClickListener(l);
        matron.setOnClickListener(l);
        cleaning.setOnClickListener(l);
		return view;
	}

	public class ButtonClickListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				//���ص����Ӧ�¼�
				((Activity) con).finish();
				break;
            case R.id.buttonMove:
            	//��ҵ����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��������");
				startActivity(intent);
				break;
            case R.id.buttonUnlock:
            	//������������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "ˮ��ά��");
				startActivity(intent);
				break;
            case R.id.buttonPlumber:
            	//ˮ��ά�޵����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��������");
				startActivity(intent);
				break;
             case R.id.buttonPCrepair:
            	//����ά�޵����Ӧ�¼�
            	 intent=new Intent(con,ZganCommunityMapShow.class);
             	intent.putExtra("button_key", "��ҹ�˾");
 				startActivity(intent);
 				break;
            case R.id.buttonRecycle:
            	//���û��յ����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��ɩ��ķ");
				startActivity(intent);
				break;
            case R.id.buttonPet:
            	//����֮�ҵ����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "���ֻ���");
				startActivity(intent);
				break;
            case R.id.buttonPipe:
            	//��ͨ�ܵ������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "�ҵ�ά��");
				startActivity(intent);
				break;
            case R.id.buttonMatron:
            	//��ɩ�����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "�ܵ���ͨ");
				startActivity(intent);
				break;
            case R.id.buttonCleaning:
            	//��������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "ϴ�·���");
				startActivity(intent);
				break;

			default:
				break;
			}
		}
		
	}

}
