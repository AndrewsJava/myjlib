package harlequinmettle.utils.filetools.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;

public class SQLiteTools {
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

	public static Statement reinitializeTable(Connection conn,
			String tableName, String[] tableColumns, String[] tableColumnTypes) {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists " + tableName + ";");
			String tableDef = "(";
			for (int i = 0; i < tableColumns.length; i++) {
				tableDef += tableColumns[i] + " " + tableColumnTypes[i] + ", ";
			}
			tableDef = tableDef.substring(0, tableDef.length() - 2);

			stat.executeUpdate("create table " + tableName + " " + tableDef
					+ ");");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

	public static Statement reinitializeTable(Connection conn,
			String tableName, String[] tableColumns) {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists " + tableName + ";");
			String tableDef = Arrays.toString(tableColumns).replace("[", "(")
					.replace("]", ")").replaceAll(",", ", real");
			stat.executeUpdate("create table " + tableName + " " + tableDef
					+ ";");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stat;
	}

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

}
