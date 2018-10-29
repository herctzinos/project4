package Controllers;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.farng.mp3.id3.ID3v2_2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Utils {

    public static Connection getConnection() throws NamingException, SQLException {
        Connection conn = null;

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/SongsDB");
            conn = ds.getConnection();

        } catch (NamingException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return conn;
    }

    public static void blob(Part file, String name, String album, String composer, String year, String lyrics, String coverart) throws NamingException, SQLException, IOException {
        Connection con1 = Utils.getConnection();
        PreparedStatement pstm = null;

        String sql = "INSERT INTO mp3 (title,song,album,artist,year,lyrics,photo_url) VALUES(?,?,?,?,?,?,?)";
        pstm = con1.prepareStatement(sql);

        pstm.setString(1, name);
        pstm.setBlob(2, file.getInputStream());
        pstm.setString(3, album);
        pstm.setString(4, composer);
        pstm.setString(5, year);
        pstm.setString(6, lyrics);
        pstm.setString(7, coverart);

        pstm.executeUpdate();
    }

    public static String titlefixer(String sentence) {
        String fix = "";
        String[] words1 = sentence.split("\\s");
        for (String w : words1) {
            fix = fix.concat(w + "%20");

        }
        return fix.substring(0, fix.length() - 3);
    }

    public static String lyrics(String artist, String title) throws MalformedURLException, IOException, ParseException {

        String fixedtitle = titlefixer(title);
        String fixedartist = titlefixer(artist);
        String api = "https://api.lyrics.ovh/v1/";
        URL url = new URL(api + fixedtitle + "/" + fixedartist);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("The selected song was not found");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String st = "";

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(br);
            JSONObject jsonObject = (JSONObject) obj;
            st = (String) (jsonObject.get("lyrics"));

        } catch (FileNotFoundException e) {
        } catch (IOException ioe) {
        }
        return st;
    }

    public static String coverart(String artist, String title) throws MalformedURLException, IOException, ParseException, ParserConfigurationException, SAXException {

        String fixedtitle = titlefixer(title);
        String fixedartist = titlefixer(artist);
        String api = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=58e788f053087a5e48d73a38bee121f3&artist=";
        URL url = new URL(api + fixedtitle + "&track=" + fixedartist);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/xml");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("The selected song was not found");
        }
        String st;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(url.openStream());
        st = doc.getElementsByTagName("image").item(3).getTextContent();
        return st;
    }

    public static HttpServletRequest req(ID3v2_2 tag1, String lyrics, String coverart, HttpServletRequest request) {

        request.setAttribute("title", tag1.getSongTitle());
        request.setAttribute("artist", tag1.getLeadArtist());
        request.setAttribute("album", tag1.getAlbumTitle());
        request.setAttribute("year", tag1.getYearReleased());
        request.setAttribute("lyrics", lyrics);
        request.setAttribute("cover", coverart);

        return request;
    }

    


}
