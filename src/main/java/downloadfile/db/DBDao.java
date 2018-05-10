package downloadfile.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBDao {
	public static String url;
	public static Connection getConnection(){
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "jdbc:oracle:thin:jl_yuqing_getter/jl_yuqing_getter@192.168.174.18:1521:orcl";
		try {
			con = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
