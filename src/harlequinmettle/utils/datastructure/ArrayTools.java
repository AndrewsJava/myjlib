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

	public static void reverse(final String[] array) {
		if (array == null) {
			return;
		}
		int i = 0;
		int j = array.length - 1;
		String tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}
}
