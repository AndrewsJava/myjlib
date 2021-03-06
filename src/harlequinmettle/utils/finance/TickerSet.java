package harlequinmettle.utils.finance;

import java.util.ArrayList;
import java.util.Arrays;

public class TickerSet {
	public static final ArrayList<String> TICKERS = new ArrayList<String>(
			Arrays.asList(concat(Qi.QQ, Yi.YY)));

	static String[] concat(String[] A, String[] B) {
		int aLen = A.length;
		int bLen = B.length;
		String[] C = new String[aLen + bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}

}
