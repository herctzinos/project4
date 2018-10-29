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
            body{
               
                color: antiquewhite;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MP3 analysis</title>
    </head>
    <body>
        <table style="background-image: url( <%=request.getAttribute("cover")%>)">
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
