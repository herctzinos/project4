<%-- 
    Document   : lyricsjsp
    Created on : 24-Oct-2018, 00:04:28
    Author     : Herc
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MP3 analysis</title>
    </head>
    <body>

        <%=request.getAttribute("lyrics")%>
        <br> <%=request.getAttribute("title")%>
        <br>   <%=request.getAttribute("artist")%>
        <br>    <%=request.getAttribute("album")%>
        <br>     <%=request.getAttribute("year")%>
        <br>     <%=request.getAttribute("coverart")%>


    </body>
</html>
