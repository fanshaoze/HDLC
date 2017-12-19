import java.util.Arrays;

public class utils {

	/**
	 * ��byteת��Ϊһ������Ϊ8��byte���飬����ÿ��ֵ����bit
	 */
	public static byte[] getBooleanArray(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	public static byte[] leftMove(byte[] b) {
		byte[] array = new byte[b.length];
		int i = 0;
		for (i = 1; i < b.length; i++) {
			array[i - 1] = b[i];
		}
		array[b.length - 1] = 0;
		return array;
	}

	public static byte[] bitesXor(byte[] a, byte[] b) {
		byte[] array = new byte[b.length];
		int i = 0;
		for (i = 1; i < b.length; i++) {
			if (a[i] == b[i])
				array[i] = 0;
			else
				array[i] = 1;
		}
		return array;
	}

	/**
	 * ��byteתΪ�ַ�����bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}
	
	public static byte BitToByte(String byteStr) {  
	    int re, len;  
	    if (null == byteStr) {  
	        return 0;  
	    }  
	    len = byteStr.length();  
	    if (len != 4 && len != 8) {  
	        return 0;  
	    }  
	    if (len == 8) {// 8 bit����  
	        if (byteStr.charAt(0) == '0') {// ����  
	            re = Integer.parseInt(byteStr, 2);  
	        } else {// ����  
	            re = Integer.parseInt(byteStr, 2) - 256;  
	        }  
	    } else {//4 bit����  
	        re = Integer.parseInt(byteStr, 2);  
	    }  
	    return (byte) re;  
	} 
	public static String bitToBitString(byte[] Bits,int start,int end) {  
	String BitsString = "";
	int i = 0;
		for (i = start;i<end;i++){
			if(Bits[i] == 0){
				BitsString +=0;
			}
			else BitsString +=1;
			
		}
		return BitsString;
	}
	public static void main(String[] args) {
		byte b = 0x35; // 0011 0101
		// ��� [0, 0, 1, 1, 0, 1, 0, 1]
		System.out.println(Arrays.toString(getBooleanArray(b)));
		// ��� 00110101
		System.out.println(byteToBit(b));
		// JDK�Դ��ķ����������ǰ��� 0
		System.out.println(Integer.toBinaryString(0x35));
	}
}