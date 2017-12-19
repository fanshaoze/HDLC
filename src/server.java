import java.io.IOException;
import java.net.*;

//import frames.Iframes;
import frames.*;


public class server {
	static byte mark = 126;
	static byte[] caddress = new byte[2];
	static byte control;
	static byte[] information = new byte[2];
	static byte[] CRC = new byte[2];
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
	
	public static void main(String args[]) {
		DatagramSocket socket = null;
		int i = 0;
		try {
			socket = new DatagramSocket(10010);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//2、创建数据报，用于接受客户端发送的数据
		
		//服务器端，实现基于UDP的用户登录
		//1、创建服务器端DatagramSocket，指定端口

		byte[] data =new byte[1024];//
		DatagramPacket packet =new DatagramPacket(data,data.length);
		for (i = 0;i<2;i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//3、接受客户端发送的数据
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//此方法在接受数据报之前会一致阻塞
			//4、读取数据
			String info =new String(data,0,data.length);
			System.out.println("我是服务器，客户端告诉我"+info);
			
			
			System.arraycopy(data, 1, caddress, 0, caddress.length);
			control = data[2];
			System.arraycopy(data, 3, information, 0, information.length);
			System.arraycopy(data, 5, CRC, 0, CRC.length);
			byte[] riframe = formIframe(caddress,control,information,CRC);
			byte[] check = CRC16.crc16(riframe, crcdiv);
			int ch = 0;
			if (check[0] == 0&&check[1] == 0){
				ch = 0;
				System.out.println("crc is right");
			}
			else {
				ch = 1;
				System.out.println("crc is wrong");
			}
		}
		
		for (i = 0;i<3;i++){
			//向客户端响应数据
			InetAddress address = packet.getAddress();
			System.out.println("AAAAA"+address);
			int port = packet.getPort();
			System.out.println("AAAAA"+port);
			caddress[0] = 0;
			caddress[1] = 0;
			information[0] = 0;
			information[1] = 1;
			control = (byte) (i*16+2);
			CRC[0] = 0;
			CRC[1] = 0;
			byte[] data2 = formIframe(caddress,control,information,CRC);
			//byte[] data ="用户名：admin;密码：123".getBytes();
			 
				//2、创建数据报，包含发送的数据信息
			 //DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
			//byte[] data2 = "欢迎您！".getBytes();
			//2、创建数据报，包含响应的数据信息
			System.out.println("sendback");
			DatagramPacket packet2 = new DatagramPacket(data2,data2.length,address,port);
			//3、响应客户端
			try {
				socket.send(packet2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//4、关闭资源
			//socket.close();
		}
		for (i = 0;i<1;i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//3、接受客户端发送的数据
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//此方法在接受数据报之前会一致阻塞
			//4、读取数据
			String info =new String(data,0,data.length);
			System.out.println("我是服务器，客户端告诉我"+info);
			
			
			System.arraycopy(data, 1, caddress, 0, caddress.length);
			control = data[2];
			System.arraycopy(data, 3, information, 0, information.length);
			System.arraycopy(data, 5, CRC, 0, CRC.length);
			byte[] riframe = formIframe(caddress,control,information,CRC);
			byte[] check = CRC16.crc16(riframe, crcdiv);
			int ch = 0;
			if (check[0] == 0&&check[1] == 0){
				ch = 0;
				System.out.println("crc is right");
			}
			else {
				ch = 1;
				System.out.println("crc is wrong");
			}
		}
		while (true) {
			//GBN
		}
	}
}
	
