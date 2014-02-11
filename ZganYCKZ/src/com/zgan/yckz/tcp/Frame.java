package com.zgan.yckz.tcp;


public class Frame {
	
	/**
	 * ƽ̨���� 
	 * ���磺�ն�Platform = 3
	 * */
	public int Platform=0;
	
	/**
	 * Э��汾
	 * ��ʼ�汾Ϊ1���Ժ���������ش��ܱ�����޸�Э��汾�������֧���Ա���չ��֧��ǰ�ڷ���
	 * */
	public int Version=1;
	
	/**
	 * ������������ 
	 * ��½Ϊ0x01�����ķ���������Ϊ0x0c���������Ϊ0x0e���ն���Ϣ��
	 * */
	public byte MainCmd=0;
	
	/**
	 * �ӹ���������
	 * */
	public int SubCmd=0;	

	/**
	 * Data����
	 * */
	public String strData="";
	
	/**
	 * Data����
	 * */
	public byte[] aryData=null;
    
    public Frame(){
    	
    }
    
    public Frame(byte[] Buff){
    	FrameTools.getByteToFrame(Buff, this);
    }      
}
