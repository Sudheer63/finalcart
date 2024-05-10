package dao;

import dbcon.DB_Properties;
import dbcon.StoreDAO;

public class DAObridge {
	private static StoreDAO s = null;

	public static StoreDAO get() {
		if (s == null) {
			s = new DB_Properties();
		}
		return s;

	}
}
