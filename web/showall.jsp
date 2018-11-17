<%-- 
    Document   : showall
    Created on : 28-Oct-2018, 22:45:44
    Author     : Herc
--%>

<%@page import="Helpers.Utils"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            table {
                
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                text-align: left;
                padding: 8px;
                
            }
            tr:nth-child(even){background-color: #f2f2f2}
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Library</title>
    </head>
    <body>
        <table>
            <tr>
                <th>Title</th>
                <th>Artist</th>
                <th>Album</th>
                <th>Year</th>
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
                <td><%=rs.getString("title")%></td>
                <td><%=rs.getString("artist")%></td>
                <td><%=rs.getString("album")%></td>
                <td><%=rs.getString("year")%></td>
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
