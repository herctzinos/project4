<%-- 
    Document   : lyricsjsp
    Created on : 24-Oct-2018, 00:04:28
    Author     : Herc
--%>


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
                color:crimson;
            }
            tr:nth-child(even){background-color: #f2f2f2}
        </style>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MP3 analysis</title>
    </head>
    <body style="background-image: url( <%=request.getAttribute("cover")%>)">
        <table >
            <tr>
                <th>Title</th>
                <th>Artist</th>
                <th>Album</th>
                <th>Year</th>
                <th>Lyrics</th>
            </tr>
            <tr>
                <td><%=request.getAttribute("title")%></td>
                <td><%=request.getAttribute("artist")%></td>
                <td><%=request.getAttribute("album")%></td>
                <td><%=request.getAttribute("year")%></td>
                <td><%=request.getAttribute("lyrics")%></td>
            </tr>
        </table>
    </body>
</html>
