import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

import frames.Iframes;
import frames.Sframes;
import frames.Uframes;


public class client {
	static DatagramSocket socket=null;
	static Timer sendData = new Timer();
	static Timer receiveData = new Timer();
	static byte mark = 126;
	static byte[] caddress;
	static byte control;
	static byte[] information = {0,0};
	static byte[] CRC;
	static byte end = 127;
	static byte[] crcdiv = {-128,5};
	public static byte[] formIframe(byte[] address,byte control,byte[] information,byte[] CRC) {

		Iframes iframes = new Iframes(mark, address, control, information, CRC, end);
		byte[] iframe = Frame.organizeIframe(iframes);
		byte[] dividends = new byte[iframe.length-1];
		System.arraycopy(iframe, 0, dividends, 0, dividends.length);
		CRC = CRC16.crc16(dividends,crcdiv);
		Iframes r_iframe = new Iframes(mark, address, control, information, CRC, end);
		iframe = Frame.organizeIframe(r_iframe);
		return iframe;
	}
	public static byte[] formSframe(byte[] address,byte control,byte[] CRC) {
		address[0] = 0;
		address[1] = 1;
		control = 0;
		CRC[0] = 0;
		CRC[1] = 0;
		Sframes sframes = new Sframes(mark, address, control, CRC, end);
		byte[] sframe = Frame.organizeSframe(sframes);
		byte[] dividends = new byte[sframe.length-1];
		System.arraycopy(sframe, 0, dividends, 0, dividends.length);
		CRC = CRC16.crc16(dividends,crcdiv);
		Sframes r_sframe = new Sframes(mark, address, control, CRC, end);
		sframe = Frame.organizeSframe(r_sframe);
		return sframe;
	}
	public static byte[] formUframe(byte[] address,byte control,byte[] information,byte[] CRC) {
		address[0] = 0;
		address[1] = 1;
		control = 0;
		CRC[0] = 0;
		CRC[1] = 0;
		Uframes uframes = new Uframes(mark, address, control, information, CRC, end);
		byte[] uframe = Frame.organizeUframe(uframes);
		byte[] dividends = new byte[uframe.length-1];
		System.arraycopy(uframe, 0, dividends, 0, dividends.length);
		CRC = CRC16.crc16(dividends,crcdiv);
		Uframes r_uframe = new Uframes(mark, address, control, information, CRC, end);
		uframe = Frame.organizeUframe(r_uframe);
		return uframe;
	}
	public static void client() {
		//客户端
		//1、定义服务器的地址、端口号、数据 
		InetAddress address= null;
		try {
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int port =10010;
		 int i = 0;
		 for (i = 0;i<2;i++) {
			caddress[0] = 0;
			caddress[1] = 1;
			information[0] = 0;
			information[1] = 1;
			control = (byte) (i*16);
			CRC[0] = 0;
			CRC[1] = 0;
			byte[] data = formIframe(caddress,control,information,CRC);
			//byte[] data ="用户名：admin;密码：123".getBytes();
			 
				//2、创建数据报，包含发送的数据信息
			 DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
			 //3、创建DatagramSocket对象
			 
			try {
				socket = new DatagramSocket();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//4、向服务器发送数据
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		}
		
	//接受服务器端响应数据
	//======================================
	//1、创建数据报，用于接受服务器端响应数据
		 int t = 0;
		while(t<2){
			t++;
			byte[] data2 = new byte[1024];
			DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
			//2、接受服务器响应的数据
			try {
				socket.receive(packet2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String reply = new String(data2,0,packet2.getLength());
			System.out.println("我是客户端，服务器说："+reply);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (i = 0;i<1;i++) {
			caddress[0] = 0;
			caddress[1] = 1;
			information[0] = 0;
			information[1] = 1;
			control = (byte) (-125);
			CRC[0] = 0;
			CRC[1] = 0;
			byte[] data = formIframe(caddress,control,information,CRC);
		 //byte[] data ="用户名：admin;密码：123".getBytes();
		 
			//2、创建数据报，包含发送的数据信息
		 DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
		 //3、创建DatagramSocket对象
		 
		try {
			socket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//4、向服务器发送数据
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}
		for (i = 0;i<40;i++){
		//GBN	
		}
		
		//4、关闭资源
		//socket.close();
		
	}
	public static void Close() {
		//关闭资源
		socket.close();
		
	}
	public static void connectProcess() {
		sendData.schedule(new TimerTask() {
		public void run() {
			client();
		}
		},0,3*1000);
		
	}
	
	
	public static void main(String args[]) {
		connectProcess();
		//Receive();
		//Close();
	
	}

}


//CorrectTimer.schedule(new TimerTask() {
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		try {
//
//			CurrentTime currentTime = Util.getCurrentDateTime(Util.getCurrentDateTime());
//			if(Count < MaxCount){
//			nioCorrectTime.sendto(
//					Util.getCorrectTimeMessage2(0x13,currentTime.getHour(), currentTime.getMinute(),
//							currentTime.getSecond()),
//					getSocketAddressByName(parameter.getRootAddr(), CorrectTimePort));
//			Count++;
//			System.out.println("correct time " + currentTime.getSecond());
//			}else{
//				System.out.println("发送跟节点重启指令" + Count);
//				nioCorrectTime.sendto(
//						Util.getCorrectTimeMessage2(0x14,currentTime.getHour(), currentTime.getMinute(),
//								currentTime.getSecond()),
//						getSocketAddressByName(parameter.getRootAddr(), CorrectTimePort));
//				Count = 0;
//			}
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}, 0, 1000 * currect_rate);
