
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v2_2;
import org.farng.mp3.id3.ID3v2_3;
import org.json.simple.parser.ParseException;

@WebServlet(name = "FileUpload", urlPatterns = {"/servletupload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 5 * 5)
public class servletupload extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TagException, NamingException, SQLException, MalformedURLException, ParseException {
        //   response.setContentType("text/html;charset=UTF-8");

        Part filepart = request.getPart("file");

        String filename = filepart.getSubmittedFileName();
        filepart.write("C:\\Users\\Herc\\Desktop\\Test\\" + filename);
        File sourceFile = new File("C:\\Users\\Herc\\Desktop\\Test\\" + filename);
        MP3File mp3file = new MP3File(sourceFile);
        ID3v2_2 tag1 = (ID3v2_2) mp3file.getID3v2Tag();
        ID3v2_3 tag2 = (ID3v2_3) mp3file.getID3v2Tag();

        String lyrics = Utils.lyrics(tag1.getSongTitle(), tag1.getLeadArtist());

        Utils.blob(filepart, tag1.getSongTitle(), tag1.getAlbumTitle(), tag1.getLeadArtist(), tag1.getYearReleased(),lyrics);
        //  Utils.lyrics(tag1.getLeadArtist(), tag1.getSongTitle());
        //Utils.lyrics(tag2.getLeadArtist(), tag2.getSongTitle());

        RequestDispatcher rd = request.getRequestDispatcher("/lyricsjsp.jsp");
        //    request.setAttribute("title", tag1.getSongTitle());
        //  request.setAttribute("artist", tag1.getLeadArtist());
        request.setAttribute("lyrics", lyrics);

        rd.include(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException {
        try {
            processRequest(request, response);
        } catch (TagException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException {
        try {
            processRequest(request, response);
        } catch (TagException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
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
