import java.lang.invoke.ConstantCallSite;

public class GBN {
	static int SEND_WIND_SIZE = 10;// ���ʹ��ڴ�СΪ 10�� GBN ��Ӧ���� W + 1 <=N��W Ϊ���ʹ��ڴ�С��
									// N Ϊ���кŸ�����
	// ����ȡ���к� 0...19 �� 20 ��
	// ��������ڴ�С��Ϊ 1����Ϊͣ-��Э��
	static int SEQ_SIZE = 20; // ���кŵĸ������� 0~19 ���� 20 ��
	// ���ڷ������ݵ�һ���ֽ����ֵΪ 0�������ݻᷢ��ʧ��
	// ��˽��ն����к�Ϊ 1~20���뷢�Ͷ�һһ��Ӧ
	static boolean[] ack = new boolean[SEQ_SIZE];// �յ� ack �������Ӧ 0~19 �� ack
	static int curSeq;// ��ǰ���ݰ��� seq
	static int curAck;// ��ǰ�ȴ�ȷ�ϵ� ack
	static int totalSeq;// �յ��İ�������
	static int totalPacket;// ��Ҫ���͵İ�����

	public static boolean seqIsAvailable() {
		int step;
		step = curSeq - curAck;
		step = step >= 0 ? step : step + SEQ_SIZE;
		// ���к��Ƿ��ڵ�ǰ���ʹ���֮��
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
	// Qualifier: �յ� ack���ۻ�ȷ�ϣ�ȡ����֡�ĵ�һ���ֽ�
	// ���ڷ�������ʱ����һ���ֽڣ����кţ�Ϊ 0��ASCII��ʱ����ʧ�ܣ���˼�һ
	// Parameter: char c
	// ************************************
	public static void ackHandler(byte c) {
		byte index = (byte) (c - 1); // ���кż�һ
		System.out.println("Recv a ack of" + index);
		if (curAck <= index) {
			for (int i = curAck; i <= index; ++i) {
				ack[i] = true;
			}
			curAck = (index + 1) % SEQ_SIZE;
		} else {
			// ack ���������ֵ���ص��� curAck �����
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
