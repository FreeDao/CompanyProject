package com.zgan.community.activity;

import com.zgan.community.R;
import com.zgan.community.R.layout;
import com.zgan.community.R.menu;
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
import android.widget.Button;
import android.widget.TextView;

public class CommunityTradeActivityFragment extends Fragment {

	private Button back;
	private TextView title;
	
	private Button supermarket;  //С����
	private Button snack;        //���
	private Button pasta;        //��ʳ
	private Button barbecue;     //�տ�
	private Button hairdressing; //��������
	private Button dryclean;     //��ϴ
	private Button leatherware;  //Ƥ�߱���
	private Button fondue;       //���
	private Button chinesemeal;  //�в�
	
	private Context con;
	private Intent intent=null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_community_trade, container, false);
		back=(Button) view.findViewById(R.id.back);
		title=(TextView) view.findViewById(R.id.title);
		//title.setText(R.string.community_trade);
		title.setBackgroundResource(R.drawable.title_shequshangquan);
		
		supermarket=(Button) view.findViewById(R.id.buttonSupermarket);
		snack=(Button) view.findViewById(R.id.buttonSnack);
		pasta=(Button) view.findViewById(R.id.buttonPasta);
		barbecue=(Button) view.findViewById(R.id.buttonBarbecue);
		hairdressing=(Button) view.findViewById(R.id.buttonHairdressing);
		dryclean=(Button) view.findViewById(R.id.buttonDryclean);
		leatherware=(Button) view.findViewById(R.id.buttonLeatherware);
		fondue=(Button) view.findViewById(R.id.buttonFondue);
		chinesemeal=(Button) view.findViewById(R.id.buttonChinesemeal);
		
		con = this.getActivity();        //��ʼ��һ��ȫ�ֵ�Context
		
        ButtonClickListener l=new ButtonClickListener();       //��ť�����������ʼ��
		
        supermarket.setOnClickListener(l);                //��ťע�������
        snack.setOnClickListener(l);
        pasta.setOnClickListener(l);
        barbecue.setOnClickListener(l);
        hairdressing.setOnClickListener(l);                //��ťע�������
        dryclean.setOnClickListener(l);
        leatherware.setOnClickListener(l);
        fondue.setOnClickListener(l);
        chinesemeal.setOnClickListener(l);                //��ťע�������
        back.setOnClickListener(l);
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
            case R.id.buttonSupermarket:
            	//С���е����Ӧ�¼�
            	
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "������ʳ");
				startActivity(intent);
				break;
            case R.id.buttonSnack:
            	//��͵����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��������");
				startActivity(intent);
  				break;
            case R.id.buttonPasta:
            	//��ʳ�����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��������");
				startActivity(intent);
  				break;
            case R.id.buttonBarbecue:
            	//�տ������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��������");
				startActivity(intent);
  				break;
            case R.id.buttonHairdressing:
             	//�������������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "ҩ������");
				startActivity(intent);
  				break;
            case R.id.buttonDryclean:
             	//��ϴ�����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "��������");
				startActivity(intent);
  				break;
            case R.id.buttonLeatherware:
             	//Ƥ�߱��������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "ĸӤ��Ʒ");
				startActivity(intent);
  				break;
            case R.id.buttonFondue:
             	//��������Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "�ʻ���ֳ");
				startActivity(intent);
  				break;
            case R.id.buttonChinesemeal:
              	//�в͵����Ӧ�¼�
            	intent=new Intent(con,ZganCommunityMapShow.class);
            	intent.putExtra("button_key", "������ѵ");
				startActivity(intent);
  				break;

			default:
				break;
			}
		}
		
	}

}
