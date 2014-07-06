package harlequinmettle.utils.filetools.sqlite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTools {
	public static final int SQL_STRING_ADD = 1234;
	public static final int SQL_FLOAT_ADD = 14141414;
	public static final int SQL_BYTES_ADD = 7897979;

	public static Connection establishSQLiteConnection(String dbName) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection establishSQLiteConnection(File dbFile) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");

			conn = DriverManager.getConnection("jdbc:sqlite:"
					+ dbFile.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Statement reinitializeTable(Connection conn,
			String tableName, String[] tableColumns, String[] tableColumnTypes) {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists " + tableName + ";");
			String sqlStatement = "(";
			for (int i = 0; i < tableColumns.length; i++) {
				sqlStatement += "db_"
						+ tableColumns[i].replaceAll("[^A-Za-z0-9]", "_") + " "
						+ tableColumnTypes[i] + ", ";
			}
			sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
			sqlStatement = "create table " + tableName + " " + sqlStatement
					+ ");";
			System.out.println(sqlStatement);
			stat.executeUpdate(sqlStatement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public static void executeStatement(Connection conn, PreparedStatement prep) {
		try {
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PreparedStatement initPreparedStatement(Connection conn,
			int numberOfValues, String tableName) {
		PreparedStatement prep = null;
		try {
			String prepStatement = "insert into " + tableName + " values (";
			for (int i = 0; i < numberOfValues; i++) {
				prepStatement += "?, ";
			}

			prepStatement = prepStatement.substring(0,
					prepStatement.length() - 2);

			prepStatement += ");";
			prep = conn.prepareStatement(prepStatement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prep;
	}

	public static PreparedStatement buildSQLStatement(PreparedStatement prep,
			List values,ArrayList<Integer> types) {
 
		try {
			for (int i = 0; i < values.size() ; i++) {
				switch (types.get(i)) {
				case SQL_STRING_ADD: 
					prep.setString(i + 1, "" + values.get(i));
					break;
				case SQL_FLOAT_ADD: 
					prep.setFloat(i + 1, (Float.parseFloat(values.get(i).toString()) ));
					break;
				case SQL_BYTES_ADD: 
					byte[] bytes =  serialize(values.get(i));
					//System.out.println(Arrays.toString(bytes));
					prep.setBytes(i + 1,bytes);
					break; 
				default:
					break;
				}

			}
			prep.addBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prep;
	}

	// public static Statement reinitializeTable(Connection conn,
	// String tableName, String[] tableColumns) {
	// Statement stat = null;
	// try {
	// stat = conn.createStatement();
	// stat.executeUpdate("drop table if exists " + tableName + ";");
	// String tableDef = Arrays.toString(tableColumns).replace("[", "(")
	// .replace("]", ")").replaceAll(",", ", real");
	// stat.executeUpdate("create table " + tableName + " " + tableDef
	// + ";");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return stat;
	// }

	public static void enterRecord(PreparedStatement prep, String[] tableEntries) {

		try {
			for (int i = 0; i < tableEntries.length; i++) {
				prep.setString(i, tableEntries[i]);
			}
			prep.addBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void executeSQLiteStatement(Connection conn,
			PreparedStatement prep) {
		try {
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] serialize(Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(obj);
			bytes = b.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static Object deserialize(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream b = new ByteArrayInputStream(bytes);
			ObjectInputStream o = new ObjectInputStream(b);
			obj = o.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
