import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunkcieDatabazy {
	public static void nacitatKnihy() {
		Connection conn = DBConnection.getDBConnection();

		try (PreparedStatement prStmt = conn.prepareStatement("SELECT * FROM books");
				ResultSet rs = prStmt.executeQuery()) {

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ulozKnihu(String nazov, int rokVydania, String autor, String zaner, int rocnik, String stav) {
		Connection conn = DBConnection.getDBConnection();
		String sql = "INSERT INTO books (id, name, year, author, genre, grade, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement prStmt = conn.prepareStatement(sql)) {
			prStmt.setInt(1, 1);
			prStmt.setString(2, nazov);
			prStmt.setInt(3, rokVydania);
			prStmt.setString(4, autor);
			prStmt.setString(5, zaner);
			prStmt.setInt(6, rocnik);
			prStmt.setString(7, stav);

			prStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
