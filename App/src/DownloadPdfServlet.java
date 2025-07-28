import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/download-pdf")
public class DownloadPdfServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Verification de l'existence de la session

		HttpSession session = request.getSession(false);
		if (session == null) {
			// Si la session n'existe pas, redirection vers la page de connexion
			response.sendRedirect("login.html");
			return;
		}

		// Recuperation des informations de session
		String username = (String) request.getSession().getAttribute("username");
		String firstName = (String) request.getSession().getAttribute("firstName");
		String lastName = (String) request.getSession().getAttribute("lastName");
		String email = (String) request.getSession().getAttribute("email");
		String city = (String) request.getSession().getAttribute("city");
		String postalCode = (String) request.getSession().getAttribute("postalCode");
		LocalDate date1 = LocalDate.parse((String) request.getSession().getAttribute("date1"));
		LocalTime heure1 = LocalTime.parse((String) request.getSession().getAttribute("heure1"));
		LocalDate date2 = LocalDate.parse((String) request.getSession().getAttribute("date2"));
		LocalTime heure2 = LocalTime.parse((String) request.getSession().getAttribute("heure2"));
		String vaccine = (String) request.getSession().getAttribute("vaccine");

		// Creation du document PDF
		Document document = new Document();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, baos);
			document.open();

			// Ajout des informations de l'utilisateur et de son rendez-vous
			document.add(new Paragraph("Termininformationen für " + username));
			document.add(new Paragraph("Name : " + lastName));
			document.add(new Paragraph("Vorname : " + firstName));
			document.add(new Paragraph("Email : " + email));
			document.add(new Paragraph("Stadt : " + city));
			document.add(new Paragraph("Postleitzahl : " + postalCode));
			document.add(new Paragraph("Datum und Uhrzeit des ersten Termins : " + date1 + " um " + heure1));
			document.add(new Paragraph("Datum und Uhrzeit des zweisten Termins : " + date2 + " um " + heure2));
			document.add(new Paragraph("Impfstoff : " + vaccine));

		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}

		// Envoi du document PDF au client
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + username + "Reservation.pdf\"");
		response.getOutputStream().write(baos.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();

	}

}
