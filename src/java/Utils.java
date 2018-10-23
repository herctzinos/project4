
import java.io.File;
import java.io.IOException;
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

public class Utils {

    public static void findtags(Part mp3) throws IOException, TagException {
        // MP3File mp3file = new MP3File((MP3File) mp3);   perpei na apothikeftei sto disko afto arxika gia na treksei
        ID3v1_1 tag = new ID3v1_1((ID3v1_1) mp3);
        System.out.println(tag);
    }

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

    public static void blob(Part file, String name) throws NamingException, SQLException, IOException {
        Connection con1 = Utils.getConnection();
        PreparedStatement pstm = null;

        String sql = "INSERT INTO mp3 (title,song) VALUES(?,?)";// and password=? to ekane o spiros
        pstm = con1.prepareStatement(sql);

        pstm.setString(1, name);
        pstm.setBlob(2, file.getInputStream());

        pstm.executeUpdate();

    }
}
