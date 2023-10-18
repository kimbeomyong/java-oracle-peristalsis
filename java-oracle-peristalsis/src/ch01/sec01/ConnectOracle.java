package ch01.sec01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectOracle {
	public static Connection makeConnection() {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521/xe";
		String user = "hr";
		String pwd = "hr";
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("오라클적재실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("오라클 접속실패");
		}
		return con;
	}

	public static void main(String[] args) {
		Connection con = makeConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from phone order by phone_id asc");
			// 4.ResultSet 화면출력
			while (rs.next()) {
				int phoneId = rs.getInt("PHONE_ID");
				String phoneName = rs.getString("PHONE_NAME");
				String phoneMade = rs.getString("PHONE_MADE");
				int phoneYear = rs.getInt("PHONE_YEAR");
				int price = rs.getInt("PRICE");
				String data = String.format("%3d \t %10s \t %10s \t %s \t %d", phoneId, phoneName, phoneMade, phoneYear,
						price);
				System.out.println(data);
			}

		} catch (SQLException e) {
			System.out.println("statement 오류");
		}

	}
}