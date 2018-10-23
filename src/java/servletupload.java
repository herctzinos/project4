
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.farng.mp3.TagException;

@WebServlet(name = "FileUpload", urlPatterns = {"/servletupload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 5 * 5)
public class servletupload extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TagException, NamingException, SQLException {
        //   response.setContentType("text/html;charset=UTF-8");

        Part filepart = request.getPart("file");
        String filename = filepart.getSubmittedFileName();
        Utils.blob(filepart, filename);
        //Utils.findtags(filepart);
        RequestDispatcher rd = request.getRequestDispatcher("/lyricsjsp.jsp");
        rd.include(request, response);

        //gia na kanoume setBlob tha valoume filepart.getInputStream
        //     filepart.write("C:\\Users\\Herc\\Desktop\\Test\\" + filename); edw to apothikevoume se arxeio ston skliro
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (TagException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (TagException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
