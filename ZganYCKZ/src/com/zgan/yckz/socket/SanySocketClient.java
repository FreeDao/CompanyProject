package com.zgan.yckz.socket;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
/****************
 * wangjian 
 * 2013-12-24
 */
/**
 * �����Ǻ�ҵ���޹ص�Socket����
 * �ŵ�:
 * 1. ֱ��ʹ�ö�����Byte��Ϊͨ�Žӿ�, Ч�ʸ�
 * 2. ���еĽӿڲ���װ����ģʽ, ��������ֻ��Ҫ�޸���ײ�Ĵ���, ����ά��
 *
 */
public class SanySocketClient implements Serializable {

	private static final long serialVersionUID = -5320160305195473688L;

	public Socket clientSocket;
	
	private InetSocketAddress tcpAddress;
	
	private int timeOut = 30000;  //��ʱ����, Ĭ��һ����
	
	private OutputStream out;
	
	private InputStream in;
	
	private long sendTime;
	
	private final int receiveMaxSize = 1024 * 1024; // ����һ�ν������ݵĴ�С, ���������,Ĭ��Ϊ1M
	private byte[] resultByte=null;
	private int nIndex ;
	//�ͻ���Ψһ��ʶ����, ������HTTP��Session
	private long clientID = -1;
	private int ServerID = 0;
	private int platfromID = 0;
	private byte cbVersion = 0;
	
	private Handler lhandler = null;
	
	private String strLinkip;
	private int nport;
	
	public SanySocketClient(String ip, int port,Handler handler)
	{
		strLinkip = ip;
		nport = port;
	//	resultByte = new byte[receiveMaxSize];
   //     nIndex = 0; 
		lhandler = handler;
		tcpAddress = new InetSocketAddress(ip,port);
		
	}
	
	/**
	 * ���ó�ʱʱ��, �ͻ��˱ر���ҩ, ����, ����˲�����, ����Ҫ�ȵ���ĵ�����, ��Ϊ�����������ģʽ
	 * @param tm
	 */
	public void setTimeOut(int tm)
	{
		timeOut = tm;
	}
	/***
	 * setHandler  �������ݷ��ش���
	 */
	public void setHandler(Handler handler)
	{
		lhandler = handler;
	}
	
	/**
	 * �Է���˿ڵ�����
	 * @return true�ɹ�, false��ʾʧ��
	 */
	public boolean connect()
	{
		try
		{
			if(clientSocket != null && clientSocket.isConnected()) 
			{
				out.close();
				in.close();
				clientSocket.close();
				clientSocket = null;
			}
			clientSocket = new Socket();
			clientSocket.connect(tcpAddress);
			
			if( clientSocket.isConnected() )
			{
				clientSocket.setKeepAlive(true);  
				clientSocket.setSoTimeout(timeOut);
				out = clientSocket.getOutputStream();
				in = clientSocket.getInputStream();
				sendTime = System.currentTimeMillis();
				new Thread(new SanyReciveInfoThread()).start();
				
				return true;
			}
		}
		catch(IOException ex)
		{
		}
		clientSocket = null;
		out = null;
		in = null;
		return false;
	}
	
	/**
	 * �����ض������String�������
	 * @param sendString
	 * @param charset ָ���ı����ʽ
	 * @throws Exception 
	 */
	public void send(String sendString, String charset) throws Exception
	{
		byte[] datas = sendString.getBytes(charset);
		send(datas);
	}
	/**************
	 * ����������
	 * @param cbVersion
	 * @return
	 */
	public boolean SendHeartInfo(){
		byte[] Databuffer;
		Databuffer = new byte[12];
        
        Databuffer[0] = '$';
        Databuffer[1] = 'Z';
        Databuffer[2] = 'G';
    	Databuffer[3] = '&';
    	//��������
    	Databuffer[4] = (byte)(platfromID>>8);
    	Databuffer[5] = (byte)platfromID;

    	Databuffer[6] = cbVersion;
    	Databuffer[7] = 0;
    	Databuffer[8] = 0;
    	Databuffer[9] = 0;
    	Databuffer[10] = 0;	
    	//����У��
    	byte cbXOR = 0;
    	for(int i = 4;i<11 ;i++)
    	{
    		cbXOR ^= Databuffer[i];
    	}
    	Databuffer[11] = cbXOR;
    	if(clientSocket.isConnected()){
    		try {
				boolean bSend =  send(Databuffer);
				//System.out.println("NewSocketInfo  SendData(byte[] cbInfo)  "+getServerID());
				return bSend;
			} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					//System.out.println("NewSocketInfo  "+getServerID()+"  socket closed!   " + e.getMessage());
					clientSocket = null;
					out = null;
					in = null;
					return false;
			}
    	}
    	return false;
	}
	/***********
	 * �������ͻ���
	 * @param cbData
	 * @param nDataSize
	 * @param cbMainCmdID
	 * @param cbSubCmdID
	 * @param cbMessageVer
	 * @return
	 */
    public boolean CrateSendBuffer(byte[] cbData, int nDataSize, byte cbMainCmdID, byte cbSubCmdID, byte cbMessageVer)
	{
		if (nDataSize>receiveMaxSize) return false;
		if(clientSocket.isConnected()){
			//cbVersion = cbMessageVer;
			byte[] Databuffer;
			Databuffer = new byte[nDataSize+12];
	        
	        Databuffer[0] = '$';
	        Databuffer[1] = 'Z';
	        Databuffer[2] = 'G';
	    	Databuffer[3] = '&';
	    	//��������
	    	Databuffer[4] = (byte)(platfromID>>8);
	    	Databuffer[5] = (byte)platfromID;

	    	Databuffer[6] = cbMessageVer;
	    	Databuffer[7] = cbMainCmdID;
	    	Databuffer[8] = cbSubCmdID;
	    	if(nDataSize>0)
	    	{
	    		Databuffer[9] = (byte)(nDataSize>>8);
	    		Databuffer[10] = (byte)(nDataSize);
	    		
	    		System.arraycopy(cbData,0,Databuffer,11,nDataSize);
	    	}
	    	else
	    	{
	    		Databuffer[9] = 0;
	    		Databuffer[10] = 0;
	    	}	
	    	//����У��
	    	byte cbXOR = 0;
	    	for(int i = 4;i< nDataSize+11 ;i++)
	    	{
	    		cbXOR ^= Databuffer[i];
	    	}
	    	Databuffer[11+nDataSize] = cbXOR;
	    	
	    	System.out.println(Databuffer);
	    	if(clientSocket.isConnected()){
	    		try {
					boolean bSend =  send(Databuffer);
					//System.out.println("NewSocketInfo   SendData(byte[] cbInfo)  "+ cbMainCmdID+"   "+ cbSubCmdID +"   "+Databuffer);
					return bSend;
				} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						clientSocket = null;
						out = null;
						in = null;
						//System.out.println("NewSocketInfo  "+getServerID()+"  socket closed!   " + e.getMessage());
						return false;
				}
	    	}
		}
		return false;
	}

	/**
	 * ���Ͷ����������������, ������ܺ�����˵, �����Ҷ����ֽ���
	 * ����Ӧ������Ϊ���ֽ����Ľӿ�
	 * @param datas
	 * @throws Exception 
	 */
	public boolean send(byte[] datas) throws Exception
	{
		if( null == clientSocket || clientSocket.isClosed()) 
		{
			clientSocket = null;
			out = null;
			in = null;
			System.out.println("NewSocketInfo  socket closed!");
			return false;
		}
		out.write(datas);
		out.flush();
		sendTime = System.currentTimeMillis(); //��ȡ��ǰʱ��);
		return true;
	}

	/**
	 * �õ�����ʱ��
	 * @return
	 */
	public long getSendTime() {
		return sendTime;
	}
	
	public void close()
	{
		try {
			clientSocket.close();
			in.close();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	public byte getVersion() {
		return cbVersion;
	}

	public void setVersion(byte cbVersion) {
		this.cbVersion = cbVersion;
	}
	
	public int getServerID() {
		return ServerID;
	}
	public void SetServerID(int ServerID) {
		this.ServerID = ServerID;
	}
	public void setPlatfrom(int platfromID) {
		this.platfromID = platfromID;
	}
	public int GetPlatfrom() {
		return platfromID;
	}
	public String getServerIp() {
		return strLinkip;
	}
	public int getServerport() {
		return nport;
	}
	public Handler getServerHandler() {
		return lhandler;
	}
	/**
	 * �Ƚ�����Ľ��շ�ʽ, ���簴��״̬���Ľ��շ�ʽ,  ��Ҫ���ýӿڱ�¶������, �����ⲿ�ϴ�����ɶ�
	 * ԭ���Ǵ󲿷����ݽ����Ƿ�����, �����н�����ʾ
	 * @return
	 */
	public InputStream getInputStream()
	{
		return in;
	}
	
	
	public OutputStream getOutputStream()
	{
		return out;
	}
	
	private class SanyReciveInfoThread  implements Runnable{//extends Thread {
		
		public SanyReciveInfoThread() throws IOException{
			resultByte = new byte[receiveMaxSize];
	        nIndex = 0; 
	        
		}
		/**
		 * ����Server�˵���Ϣ, �������ض������String ����
		 * ��ѵķ�ʽ, �ǽ�����һ��receive������װ, ����ά������Ľӿ�.
		 * @param charset
		 * @return
		 * @throws Exception 
		 */
		public String receive(String charset) throws Exception
		{
			byte[] receiveData = receive();
			if (receiveData == null)
			{
				return null;
			}
			
			String sData = new String(receiveData, charset);
			return sData.trim();
		}
		/**
		 * ����Server�˵���Ϣ, ������byte ����
		 * ��ѵķ�ʽ, �ǽ�����һ��receive������װ, ����ά������Ľӿ�.
		 * @param charset
		 * @return
		 * @throws Exception 
		 */
		public byte[] receivebytes() throws Exception
		{
			byte[] receiveData = receive();
			if (receiveData == null)
			{
				return null;
			}
			
			return receiveData;
		}
		
		/**
		 * ����ָ��λ�õ���Ϣ, ����byte[]�ķ�ʽ�洢.
		 * ʵ����ʲôʱ�����һ����Ϣ, �Ǹ�ҵ�����.
		 * ����ѭ����������ȡ���Ǹ��õķ�ʽ
		 * @return
		 * @throws Exception 
		 */
		public byte[] receive() throws Exception
		{
			if( null == clientSocket || clientSocket.isClosed()) 
			{
				clientSocket = null;
				out = null;
				in = null;
				//System.out.println("NewSocketInfo"+"   null == clientSocket || clientSocket.isClosed() ");
				throw new Exception("socket closed!");
			}
			byte[] bufIn = new byte[receiveMaxSize];
			Thread.sleep(1000);
			int bytesLen = in.available();
			int totalCount = 0;
			
			while( bytesLen > 0)
			{
				try
				{
					bytesLen = in.read(bufIn, totalCount, bytesLen);
					totalCount += bytesLen;
					bytesLen = in.available();
				}catch(SocketTimeoutException e)
				{
					//��ʱ�׳��쳣, ���������ж�read����
					break;
				}
				
			}
			
			byte[] stores = new byte[totalCount];
			
			System.arraycopy(bufIn, 0, stores, 0, totalCount);
			return stores;
		}
		
		public int threadreceive(byte[] recbytes,int nCount) throws Exception
		{
			if( null == clientSocket || clientSocket.isClosed()) 
			{
				clientSocket = null;
				out = null;
				in = null;
				//System.out.println("NewSocketInfo"+"   null == clientSocket || clientSocket.isClosed() ");
				return -2;//throw new Exception("socket closed!");
			}
			byte[] bufIn = new byte[receiveMaxSize];
			Thread.sleep(1000);
			int bytesLen = in.available();
			int totalCount = 0;
			
			while( bytesLen > 0)
			{
				try
				{
					bytesLen = in.read(bufIn, totalCount, bytesLen);
					totalCount += bytesLen;
					bytesLen = in.available();
				}catch(SocketTimeoutException e)
				{
					//��ʱ�׳��쳣, ���������ж�read����
					//System.out.println("NewSocketInfo"+"   threadreceive(byte[] recbytes,int nCount) "+ e.getMessage());
					break;
				}
			}
			
			if(totalCount>0)
			{
				sendTime = System.currentTimeMillis();
				System.arraycopy(bufIn, 0, recbytes, nCount, totalCount);       
			}
			return totalCount;
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub	
			//System.out.println("NewSocketInfo"+"   Message msg = new Message()");	
			while(clientSocket != null && clientSocket.isConnected())
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				long nowTime = System.currentTimeMillis(); 
				if(nowTime - sendTime > timeOut && getServerID()>1){
					if(!SendHeartInfo()) 
						break;
				}
				int nCount = 0;
				try {
					nCount = threadreceive(resultByte,nIndex);
					if(nCount == -2)
					{
						break;
					}
					if(nCount >0 ){
						nIndex += nCount;
						//System.out.println("NewSocketInfo"+"   nIndex += nCount   "+ nCount);
						//��������
						while(nIndex>=12){
							if(resultByte[0] == '$' && resultByte[1] == 'Z' && resultByte[2] == 'G' && resultByte[3] == '&'){
								int nDataLength = (resultByte[9] > 0 ? (int)resultByte[9]<<8 : (int)resultByte[9]) ;
//resultByte[9]&0xff00;//
	    						
	    						System.out.println("NewSocketInfo   "+  nDataLength+"      "+resultByte[9]);
	    						
	    						nDataLength += resultByte[10]&0xff;//resultByte[10];
	    	
	    						System.out.println("NewSocketInfo   "+  nDataLength+"      "+resultByte[10]);
	    				
	    						//System.out.println("NewSocketInfo   "+ getServerID() + "  resultByte[0] == '$' && resultByte[1] == 'Z' && resultByte[2] == 'G' && resultByte[3] == '&'" + "#################"+nDataLength+"&&&&&&&&&&&&&&&&&"+nIndex);
	    						
								if(nDataLength+12<=nIndex){//������ DATASUCCESS
									byte[] Databuffer;
									Databuffer = new byte[nDataLength+8];
							        
							        System.arraycopy(resultByte,4,Databuffer,0,nDataLength+8);
							        //У��У���� CHECKCODEERROR
							        byte bCheckCode = 0;
							        byte bXor=Databuffer[nDataLength+8-1];
							        for(int i = 0;i<nDataLength+8-1 ;i++){
							        	bCheckCode ^= Databuffer[i];
							        }
							        Log.i("bCheckCode", ""+bCheckCode);
							        Log.i("bXor", ""+bXor);
							        if(bCheckCode == bXor)
							        {
										if(lhandler != null) {
											Message msg = lhandler.obtainMessage();
										   	msg.what = Constant.DATASUCCESS; 
										   	msg.obj = Databuffer; 	
											lhandler.sendMessage(msg); 
										}
							        }
									else{
										if(lhandler != null) {
											Message msg = lhandler.obtainMessage();
											msg.what = Constant.CHECKCODEERROR; 
											msg.obj = "У�������"; 	
											lhandler.sendMessage(msg); 	
										}
									}
							        
							        nIndex -= nDataLength;
							        nIndex -= 12;
							        
							        //�Ƴ�������Ϣ
							        if(nIndex>0){
							        	System.arraycopy(resultByte,nDataLength+12,resultByte,0,nIndex);
							        	//System.out.println("NewSocketInfo"+"��������������������������"+resultByte);
							        } 
								}
								else if(nDataLength+12>receiveMaxSize){//���ݰ�����
									if(lhandler != null) {
										Message msg = lhandler.obtainMessage();
										msg.what = Constant.DATAERROR; 
										msg.obj = "����������쳣��"; 	
										lhandler.sendMessage(msg); 
									}
									nIndex = 0;
								}
								if(nDataLength+12>nIndex){//����ѭ����������
									System.out.println("NewSocketInfo"+" nDataLength+12>nIndex");
									break;
								}
							}
							else{
								System.arraycopy(resultByte,1,resultByte,0,nIndex-1); //�Ƴ���һλ�����ж�
								nIndex -= 1;
							}
						}
					}
				} 
				catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					//System.out.println("NewSocketInfo  " + getServerID() +"  �����쳣��������������������"+e.getMessage());

					if(lhandler != null) {
						Message msg = lhandler.obtainMessage();
						msg.what = Constant.NETERROR; 
						msg.obj = "�����쳣��"; 
						lhandler.sendMessage(msg); 
					}
				}
			}

			if(lhandler != null) {
				clientSocket = null;
				out = null;
				in = null;
				//System.out.println("NewSocketInfo  " + getServerID() +"  �������Ͽ����ӣ���������������������");
				Message msg = lhandler.obtainMessage();
				msg.what = Constant.SERVERERROR; 
				msg.obj = "�������Ͽ����ӣ�"; 
				lhandler.sendMessage(msg); 
			}
		}
	}
}
