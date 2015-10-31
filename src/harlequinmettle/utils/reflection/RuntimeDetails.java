// Oct 24, 2015 8:39:51 AM
package harlequinmettle.utils.reflection;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class RuntimeDetails {
	private static final Set<Class<?>> NUMBER_WRAPPER_TYPES = getWrapperTypes();

	public static void main(String[] args) {
		// Oct 24, 2015 8:39:51 AM

	}

	public static String getPrintMethodInfo() {

		StackTraceElement[] names = Thread.currentThread().getStackTrace();

		// 0 = runtime
		// 1 = this method: getMethodInfo
		// 2 = method calling this addProgramExecutionDetails (DSLog.log and
		// object_asBlob_toDatastore...)
		// 3 =
		int i = 3;
		StackTraceElement st = names[i];
		String nextLog = "[" + st.getFileName().replaceAll("java", st.getMethodName()) + "]" + "{" + st.getLineNumber() + "}               ";
		System.out.println(nextLog);
		return nextLog;
		// logMessage += nextLog + System.lineSeparator();

	}

	public static String getPrintClassInfo(Object obj) {
		String STRING_STRING_FORMAT = "                         %1$-65s %2$-25s ";
		// String STRING_NUMBER_FORMAT = "|%1$-25s|$%2$-10.2f ";
		try {

			StringBuffer sb = new StringBuffer();
			sb.append(System.lineSeparator() + obj.getClass().getSimpleName() + System.lineSeparator());

			System.out.println(sb);
			Class<?> objClass = obj.getClass();

			Field[] fields = objClass.getFields();
			for (Field field : fields) {
				String name = field.getName();
				Object value = field.get(obj);
				String valueString = "";
				if (isPrimitiveNumber(value.getClass()))
					valueString = value.toString();
				else
					valueString = getLabelOrUnqualifiedClass(value);
				System.out.format(STRING_STRING_FORMAT, name, valueString);
				System.out.println();
				sb.append("                " + name + ": " + valueString + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getLabelOrUnqualifiedClass(Object obj) {
		// Oct 25, 2015 10:42:53 AM
		String result = "";
		try {
			Class<?> objClass = obj.getClass();

			Field[] fields = objClass.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String name = field.getName();
				// System.out.println("------ " + name);
				// System.out.println("++++++ " +
				// "artificialNeuralNetComponentLabel");
				if (!name.equals("artificialNeuralNetComponentLabel"))
					continue;

				return field.get(obj).toString();
			}
			result = obj.toString().split("@")[1];
		} catch (Exception e) {
			// System.out.println("unidentifialble" + obj.toString());
			// e.printStackTrace();
			return null;
		}
		return result;
	}

	public static boolean isPrimitiveNumber(Class<?> clazz) {
		return NUMBER_WRAPPER_TYPES.contains(clazz);
	}

	private static Set<Class<?>> getWrapperTypes() {
		Set<Class<?>> ret = new HashSet<Class<?>>();

		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		return ret;
	}
}
