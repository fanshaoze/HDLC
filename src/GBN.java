import java.lang.invoke.ConstantCallSite;

public class GBN {
	static int SEND_WIND_SIZE = 10;// 发送窗口大小为 10， GBN 中应满足 W + 1 <=N（W 为发送窗口大小，
									// N 为序列号个数）
	// 本例取序列号 0...19 共 20 个
	// 如果将窗口大小设为 1，则为停-等协议
	static int SEQ_SIZE = 20; // 序列号的个数，从 0~19 共计 20 个
	// 由于发送数据第一个字节如果值为 0，则数据会发送失败
	// 因此接收端序列号为 1~20，与发送端一一对应
	static boolean[] ack = new boolean[SEQ_SIZE];// 收到 ack 情况，对应 0~19 的 ack
	static int curSeq;// 当前数据包的 seq
	static int curAck;// 当前等待确认的 ack
	static int totalSeq;// 收到的包的总数
	static int totalPacket;// 需要发送的包总数

	public static boolean seqIsAvailable() {
		int step;
		step = curSeq - curAck;
		step = step >= 0 ? step : step + SEQ_SIZE;
		// 序列号是否在当前发送窗口之内
		if (step >= SEND_WIND_SIZE) {
			return false;
		}
		if (ack[curSeq]) {
			return true;
		}
		return false;
	}

	public static void timeOutHandler() {
		System.out.println("Timer out error.");
		int index;
		for (int i = 0; i < SEND_WIND_SIZE; ++i) {
			index = (i + curAck) % SEQ_SIZE;
			ack[index] = true;
		}
		totalSeq -= SEND_WIND_SIZE;
		curSeq = curAck;
	}

	// ************************************
	// Method: ackHandler
	// FullName: ackHandler
	// Access: public
	// Returns: void
	// Qualifier: 收到 ack，累积确认，取数据帧的第一个字节
	// 由于发送数据时，第一个字节（序列号）为 0（ASCII）时发送失败，因此加一
	// Parameter: char c
	// ************************************
	public static void ackHandler(byte c) {
		byte index = (byte) (c - 1); // 序列号减一
		System.out.println("Recv a ack of" + index);
		if (curAck <= index) {
			for (int i = curAck; i <= index; ++i) {
				ack[i] = true;
			}
			curAck = (index + 1) % SEQ_SIZE;
		} else {
			// ack 超过了最大值，回到了 curAck 的左边
			for (int i = curAck; i < SEQ_SIZE; ++i) {
				ack[i] = true;
			}
			for (int i = 0; i <= index; ++i) {
				ack[i] = true;
			}
			curAck = index + 1;
		}
	}

	public static void main(String[] args) {

	}
}
