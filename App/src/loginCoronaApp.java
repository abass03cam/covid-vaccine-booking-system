import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginVrai")
public class loginCoronaApp extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	
	// R�cup�ration des param�tres de la requ�te
	String username = request.getParameter("username");
	String password = request.getParameter("password");

	if (username != null && username.isEmpty() && password != null && password.isEmpty()) {
        response.sendRedirect("login.html?error=missingFields");
        return;
    }
	// Chargement du Driver JDBC
	try {
	  Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	  System.out.println("La classe Driver n'existe pas");
	  e.printStackTrace();
	}

	// Cr�ation de la connexion � la base de donn�es
	try (
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/jordan", "root",
	        "1708")) {

	  // V�rification des informations de connexion
	  String sql = "SELECT * FROM usersApp WHERE username=? AND password=?";
	  try (PreparedStatement statement = conn.prepareStatement(sql)) {
	    statement.setString(1, username);
	    statement.setString(2, password);
	    ResultSet rs = statement.executeQuery();

	    if (rs.next()) {
	      // Cr�ation de la session utilisateur
	      HttpSession session = request.getSession();
	      
	      session.setAttribute("username", username);
	      session.setAttribute("firstName", rs.getString("first_name"));
	      session.setAttribute("lastName", rs.getString("last_name"));
	      session.setAttribute("email", rs.getString("email"));
	      session.setAttribute("city", rs.getString("city"));
	      session.setAttribute("postalCode", rs.getString("postal_code"));
	      
	      response.sendRedirect("central.html?username=" +username);
	    
	    } else {
	      response.sendRedirect("login.html?error=invalidLogin");
	    }
	  }
	} catch (SQLException e) {
	  System.out.println("Echec");
	  e.printStackTrace();
	}
}
}