package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbcon {
	private static Connection con = null;

	public static Connection getDbcon() {
		if (con == null) {
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://192.168.110.48:5432/postgres",
						"plf_training_admin", "pff123");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;

	}
}
