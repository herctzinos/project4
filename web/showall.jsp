<%-- 
    Document   : showall
    Created on : 28-Oct-2018, 22:45:44
    Author     : Herc
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page import="Controllers.Utils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <tr>
                <th>Name</th>
                <th>Surname</th>
            </tr>
            <%
                Connection con = null;
                PreparedStatement pstm = null;
                ResultSet rs = null;
                String sql = "";
                try {
                     con = Utils.getConnection();
                    sql = "SELECT * FROM songs.mp3";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {%>
            <tr>
                <td><%=rs.getString("artist")%></td>
                <td><%=rs.getString("title")%></td>
            </tr>
            <%}
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                } finally {
                    try {
                        pstm.close();
                    } catch (Exception e) {
                    }
                    try {
                        rs.close();
                    } catch (Exception e) {
                    }
                    try {
                        if (null != con) {
                            con.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace(System.out);
                    }
                }
            %>
        </table>       
    </body>
</html>
