
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
import javax.servlet.http.Part;
import javax.sql.DataSource;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1_1;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utils {

//    public static void findtags(Part mp3) throws IOException, TagException {
//        File sourceFile;
//        MP3File mp3file = new MP3File(sourceFile);
//        // MP3File mp3file = new MP3File((MP3File) mp3);   perpei na apothikeftei sto disko afto arxika gia na treksei
//        ID3v1_1 tag = new ID3v1_1((ID3v1_1) mp3);
//        System.out.println(tag);
//    }
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

    public static void blob(Part file, String name, String album, String composer, String year, String lyrics) throws NamingException, SQLException, IOException {
        Connection con1 = Utils.getConnection();
        PreparedStatement pstm = null;

        String sql = "INSERT INTO mp3 (title,song,album,artist,year,lyrics) VALUES(?,?,?,?,?,?)";
        pstm = con1.prepareStatement(sql);

        pstm.setString(1, name);
        pstm.setBlob(2, file.getInputStream());
        pstm.setString(3, album);
        pstm.setString(4, composer);
        pstm.setString(5, year);
        pstm.setString(6, lyrics);

        pstm.executeUpdate();

    }

    public static String lyrics(String artist, String title) throws MalformedURLException, IOException, ParseException {

        String api = "https://api.lyrics.ovh/v1/";
        String api1 = "";
        String api2 = "";

        String tl = title;
        String[] words1 = tl.split("\\s");
        for (String w : words1) {
            api1 = api1.concat(w + "%20");
        }

        String art = artist;
        String[] words2 = art.split("\\s");
        for (String w : words2) {
            api2 = api2.concat(w + "%20");
        }

        URL url = new URL(api + api1 + "/" + api2);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed:HTTP error code:" + conn.getResponseCode());
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

}
