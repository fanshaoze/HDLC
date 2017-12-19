

public class CRC16 {

	public static byte[] strm2div(byte[] dividends, byte[] divisor) {
		byte[] remaind = new byte[2];
		byte[] quotient = new byte[2];
		int denLength = dividends.length;
		int divLength = divisor.length;
		int L = denLength + divLength;
		int i;
		byte[] crc16 = new byte[L];
		System.arraycopy(dividends, 0, crc16, 0, denLength);
		for (i = denLength; i < L; i++) {
			crc16[i] = 0;
		}
		System.arraycopy(crc16, 0, remaind, 0, divLength);
		byte[] crc16Bits = new byte[crc16.length * 8+1];
		for (i = 0; i < crc16.length; i++) {
			System.arraycopy(utils.getBooleanArray(crc16[i]), 0, crc16Bits,8 * i, 8);
		}
		//crc16Bits[crc16Bits.length] = 0;
		byte[] remaindBits = new byte[remaind.length*8];
		for (i = 0; i < remaindBits.length; i++) {
			System.arraycopy(utils.getBooleanArray(remaind[i]), 0, remaindBits,8 * i, 8);
		}
		byte[] divisorBits = new byte[remaind.length*8];
		for (i = 0; i < divisor.length; i++) {
			System.arraycopy(utils.getBooleanArray(divisor[i]), 0, divisorBits,8 * i, 8);
		}
		
		for (i = 0; i < 8 * divLength; i++) {
			if (remaind[0] == 0) {
				quotient[i] = 0;
			}
			else {
				System.arraycopy(utils.bitesXor(remaind, divisor), 0, remaindBits, 0, remaindBits.length);;
			}
			remaindBits[remaindBits.length-1] = crc16Bits[8 * divLength + i];
		}
		String BitsString_a = "";
		String BitsString_b = "";
		for (i = 0;i<8;i++){
			if(remaindBits[i] == 0){
				BitsString_a +="0";
			}
			else BitsString_a += "1";
			
		}
		BitsString_a = utils.bitToBitString(remaindBits,0,8);
		BitsString_b = utils.bitToBitString(remaindBits,8,16);
		byte[] resultBites = new byte[remaindBits.length-1];
		System.arraycopy(utils.byteToBit(utils.BitToByte(BitsString_a)), 0, resultBites, 0, 8);
		System.arraycopy(utils.byteToBit(utils.BitToByte(BitsString_b)), 0, resultBites, 8, 7);
		return resultBites;
	}

	public static byte[] crc16(byte[] dividends, byte[] CRC16code) {
		byte[] resultbits = new byte[16];
		System.arraycopy(strm2div(dividends,CRC16code), 0, resultbits, 0, strm2div(dividends, CRC16code).length);
		resultbits[resultbits.length-1] = 0;
		String BitsString_a = utils.bitToBitString(resultbits,0,8);
		String BitsString_b = utils.bitToBitString(resultbits,8,16);
		byte[] result = new byte[2];
		result[0] = utils.BitToByte(BitsString_a);
		result[1] = utils.BitToByte(BitsString_b);
		return result;
		// TODO Auto-generated constructor stub
		
	}

	public static void main(String args[]) {

	}
}
