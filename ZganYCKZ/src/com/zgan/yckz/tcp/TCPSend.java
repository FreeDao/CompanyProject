package com.zgan.yckz.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;

public class TCPSend implements Runnable {
	private SocketChannel socketChannel;
	private Queue<byte[]> send_Queue = new LinkedList<byte[]>();

	public TCPSend(String strServerIP, int intPort, Selector _selector,
			Queue _queue, Queue _rqueue) {

		this.send_Queue = _queue;

		try {
			// �򿪼����ŵ�������Ϊ������ģʽ
			socketChannel = SocketChannel.open(new InetSocketAddress(
					strServerIP, intPort));
			socketChannel.configureBlocking(false);

			// �򿪲�ע��ѡ�������ŵ�
			socketChannel.register(_selector, SelectionKey.OP_READ);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ���������߳�
		TCPReceive tr = new TCPReceive(_selector, _rqueue);
		TCPPing tp = new TCPPing();
	}

	@Override
	public void run() {
		while (true) {
			if (send_Queue.size() > 0) {
				byte[] sendByte = null;

				sendByte = send_Queue.poll();

				ByteBuffer writeBuffer = ByteBuffer.wrap(sendByte);

				try {
					//ֹͣ����
					FrameTools.Thread_Ping=false;					
					FrameTools.Thread_PingTime=0;

					socketChannel.write(writeBuffer);

					FrameTools.Thread_Ping=true;

					Log.i("TCPSend", "��������......");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
