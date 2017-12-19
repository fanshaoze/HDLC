import frames.*;
public class Frame {
	public static byte[] organizeIframe(Iframes iframes) {
		// TODO Auto-generated method stub
		byte[] iframesBytes = new byte[iframes.getLength()];
		iframesBytes[0] = iframes.getmark();
		System.arraycopy(iframesBytes, 0, iframes.getaddress(), 0, iframes.getaddress().length);
		iframesBytes[iframes.getaddress().length+1] = iframes.getcontrol();
		System.arraycopy(iframesBytes, iframes.getaddress().length+2, iframes.getinformation(), 0, iframes.getinformation().length);
		System.arraycopy(iframesBytes, iframes.getLength()-iframes.getCRC16().length-2, iframes.getCRC16(), 0, iframes.getCRC16().length);
		iframesBytes[iframesBytes.length-1] = iframes.getend();
		return iframesBytes;
	}
	public static byte[] organizeUframe(Uframes uframes) {
		// TODO Auto-generated method stub
		byte[] uframesBytes = new byte[uframes.getLength()];
		uframesBytes[0] = uframes.getmark();
		System.arraycopy(uframesBytes, 0, uframes.getaddress(), 0, uframes.getaddress().length);
		uframesBytes[uframes.getaddress().length+1] = uframes.getcontrol();
		System.arraycopy(uframesBytes, uframes.getaddress().length+2, uframes.getinformation(), 0, uframes.getinformation().length);
		System.arraycopy(uframesBytes, uframes.getLength()-uframes.getCRC16().length-2, uframes.getCRC16(), 0, uframes.getCRC16().length);
		uframesBytes[uframesBytes.length-1] = uframes.getend();
		return uframesBytes;
	}
	public static byte[] organizeSframe(Sframes sframes) {
		// TODO Auto-generated method stub
		byte[] sframesBytes = new byte[sframes.getLength()];
		sframesBytes[0] = sframes.getmark();
		System.arraycopy(sframesBytes, 0, sframes.getaddress(), 0, sframes.getaddress().length);
		sframesBytes[sframes.getaddress().length+1] = sframes.getcontrol();
		//System.arraycopy(sframesBytes, sframes.getaddress().length+2, sframes.getinformation(), 0, sframes.getinformation().length);
		System.arraycopy(sframesBytes, sframes.getLength()-sframes.getCRC16().length-2, sframes.getCRC16(), 0, sframes.getCRC16().length);
		sframesBytes[sframesBytes.length-1] = sframes.getend();
		return sframesBytes;
	}

}
