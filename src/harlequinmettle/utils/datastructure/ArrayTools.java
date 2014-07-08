package harlequinmettle.utils.datastructure;

public class ArrayTools {

	public static String[] concat(String[] A, String[] B) {
		int aLen = A.length;
		int bLen = B.length;
		String[] C = new String[aLen + bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}
	public static Object[] concat(Object[] A, Object[] B) {
		int aLen = A.length;
		int bLen = B.length;
		Object[] C = new Object[aLen + bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}
 
}
