package frames;


public class Iframes {
	byte mark;
	byte[] address;
	byte control;
	byte[] information;
	byte[] CRC16;
	byte end;
	
	public Iframes(byte mark,byte[] address,byte control,byte[] information,byte[] CRC16,byte end) {
		this.mark =mark;
		System.arraycopy(address, 0, this.address, 0, this.address.length);
		this.control = control;
		System.arraycopy(information, 0, this.information, 0, this.information.length);
		System.arraycopy(CRC16, 0, this.CRC16, 0, this.CRC16.length);
		this.end = end;
		// TODO Auto-generated constructor stub
	}
	public int getLength() {
		return 1+address.length+1+information.length+CRC16.length+1;
	}
	
	public byte getmark() {
		return mark;
	}

	public void setmark(byte mark) {
		this.mark = mark;
	}
	public byte[] getaddress() {
		return address;
	}

	public void setaddress(byte[] address) {
		System.arraycopy(address, 0, this.address, 0, this.address.length);
	}
	
	public byte getcontrol() {
		return control;
	}

	public void setcontrol(byte control) {
		this.control = control;
	}
	public byte[] getinformation() {
		return information;
	}

	public void setinformation(byte[] information) {
		System.arraycopy(information, 0, this.information, 0, this.information.length);
	}
	public byte[] getCRC16() {
		return CRC16;
	}

	public void setCRC16(byte[] CRC16) {
		System.arraycopy(CRC16, 0, this.CRC16, 0, this.CRC16.length);
	}
	public byte getend() {
		return end;
	}

	public void setend(byte end) {
		this.end = end;
	}
	
}
