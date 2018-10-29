package Controllers;

import java.io.File;
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
import javax.xml.parsers.ParserConfigurationException;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v2_2;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "FileUpload", urlPatterns = {"/servletupload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 5 * 5)
public class servletupload extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TagException, NamingException, SQLException, MalformedURLException, ParseException, ParserConfigurationException, SAXException {

        Part data = request.getPart("file");
        String filename = data.getSubmittedFileName();
        InputStream input = data.getInputStream();
        data.write("C:\\Mp3\\" + filename);
        input.close();

        File sourceFile = new File("C:\\Mp3\\" + filename);
        MP3File mp3file = new MP3File(sourceFile);
        ID3v2_2 tag1 = (ID3v2_2) mp3file.getID3v2Tag();

        String lyrics = Utils.lyrics(tag1.getSongTitle(), tag1.getLeadArtist());
        String coverart = Utils.coverart(tag1.getSongTitle(), tag1.getLeadArtist());
        Utils.blob(data, tag1.getSongTitle(), tag1.getAlbumTitle(), tag1.getLeadArtist(), tag1.getYearReleased(), lyrics, coverart);

        request = Utils.req(tag1, lyrics, coverart, request);
        RequestDispatcher rd = request.getRequestDispatcher("/lyricsjsp.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException {
        try {
            processRequest(request, response);
        } catch (TagException | NamingException | SQLException | ParseException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MalformedURLException {
        try {
            processRequest(request, response);
        } catch (TagException | NamingException | SQLException | ParseException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(servletupload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
