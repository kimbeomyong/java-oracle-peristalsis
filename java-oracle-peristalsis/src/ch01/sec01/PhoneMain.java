package ch01.sec01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PhoneMain {
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		boolean stopFlag = false;
		while (!stopFlag) {
			System.out.println("1:핸드폰목록, 2:핸드폰목록 추가, 3: 핸드폰 목록 수정, 4:핸드폰 목록 삭제, 5:exit");
			System.out.println(">>");
			int num = scan.nextInt();
			scan.nextLine();
			switch (num) {
			case 1:
				phoneSelect();
				break;
			case 2:
				phoneInsert();
				break;
			case 3:
				phoneUpdate();
				break;
			case 4:
				phoneDelete();
				break;
			case 5:
				stopFlag = true;
				System.out.println("종료!");
			}

		}
	}

	private static void phoneDelete() {
		phoneSelect();

		Connection con = ConnectOracle.makeConnection();
		Statement stmt = null;

		try {
			System.out.println("삭제할아이디입력>>");
			int phoneId = scan.nextInt();
			stmt = con.createStatement();

			String query = String.format("delete from phone where phone_id = %d", phoneId);
			int count = stmt.executeUpdate(query);// 실행한 문장의 갯수를 준다.
			// 4.count check
			if (count == 0) {
				System.out.printf("phoneId = %d delete 삭제대상이 아닙니다.", phoneId);
			} else {
				System.out.println("삭제성공~!");
			}
		} catch (SQLException e) {
			System.out.println("statement 오류");
		}
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void phoneUpdate() {
		phoneSelect();

		System.out.println("수정 phone_Id>>");
		int phoneId = scan.nextInt();
		scan.nextLine();
		Phone phone = phoneSelectPhoneId(phoneId);
		if (phone == null) {
			System.out.printf("Phone_id = %d 아이디는 존재하지 않습니다.\n", phoneId);
			return;
		}

		Connection con = ConnectOracle.makeConnection();
		Statement stmt = null;
		try {
			System.out.printf("(%s)수정 >>", phone.getPhoneName());
			String name = scan.nextLine().trim();
			if (name.equals("")) {
				name = phone.getPhoneName();
			}
			System.out.printf("(%s)수정 >>", phone.getPhoneMade());
			String made = scan.nextLine().trim();
			if (made.equals("")) {
				made = phone.getPhoneMade();
			}
			System.out.printf("(%s) 수정>>", phone.getPhoneYear());
			String year = scan.nextLine().trim();
			if (year.equals("")) {
				year = phone.getPhoneYear();
			}
			System.out.printf("(%d)수정>>", phone.getPrice());
			String s_price = scan.nextLine();
			int price = 0;
			if (s_price.equals("")) {
				price = phone.getPrice();
			} else {
				price = Integer.parseInt(s_price);
			}
			stmt = con.createStatement();
			String query = String.format("update phone set phone_name = '%s',phone_made = '%s', phone_year = '%s',price = %d where phone_id = %d",
					name, made, year, price, phoneId);
			int count = stmt.executeUpdate(query);// 실행한 문장의 갯수를 준다.
			if (count != 1) {
				System.out.printf("phone_id = %d update 발생되지 않음.\n", phoneId);
			} else {
				System.out.printf("phone_id = %d update 성공.\n", phoneId);
			}
		} catch (SQLException e) {
			System.out.println("statement 오류");
		}
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {

		}

	}

	private static Phone phoneSelectPhoneId(int phoneId) {
		Connection con = ConnectOracle.makeConnection();
		Statement stmt = null;
		ResultSet rs = null;
		Phone phone = null;
		try {
			stmt = con.createStatement();
			String query = String.format("select * from phone where phone_id = %d", phoneId);
			rs = stmt.executeQuery(query);
			// 4.ResultSet 화면출력
			if (rs.next()) {
				int _phoneId = rs.getInt("PHONE_ID");
				String phoneName = rs.getString("PHONE_NAME");
				String phoneMade = rs.getString("PHONE_MADE");
				String phoneYear = rs.getString("PHONE_YEAR");
				int price = rs.getInt("PRICE");
				phone = new Phone(_phoneId, phoneName, phoneMade, phoneYear, price);
				String data = String.format("%3d \t %10s \t %10s \t %s \t %d", phoneId, phoneName, phoneMade, phoneYear,
						price);
				System.out.println(data);
			}

		} catch (SQLException e) {
			System.out.println("statement 오류");
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return phone;
	}

	private static void phoneInsert() {
		Connection con = ConnectOracle.makeConnection();
		Statement stmt = null;
		try {
			System.out.printf("핸드폰제품명 입력>>");
			String name = scan.nextLine().trim();
			System.out.println("제조한 나라 입력>>");
			String made = scan.nextLine().trim();
			System.out.println("제조 년도>>");
			String year = scan.nextLine().trim();
			System.out.println("가격>>");
			int price = scan.nextInt();
			scan.nextLine();
			stmt = con.createStatement();

			String query = String.format("insert into phone  VALUES (phone_id_seq.nextval, '%s','%s','%s','%d')", name,
					made, year, price);
			int count = stmt.executeUpdate(query);// 실행한 문장의 갯수를 준다.
			// 4.count check
			if (count != 1) {
				System.out.println("Insert 오류발생");
			} else {
				System.out.println("Insert 성공~!");
			}
		} catch (SQLException e) {
			System.out.println("statement 오류");
		}
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("the end");

	}

	private static void phoneSelect() {
		Connection con = ConnectOracle.makeConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from phone order by phone_id asc");
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