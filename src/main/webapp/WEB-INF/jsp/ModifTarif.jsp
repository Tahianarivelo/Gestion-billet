<%-- 
    Document   : ModifTarif
    Created on : 12 oct. 2020, 15:22:43
    Author     : tahiana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <%@ include file="NavBar.jsp" %>
        <div class="row" >
                <div class="col">
                    <h1 class="text-center">Modification de tarif: ${tarif.id}</h1>
                </div>
            </div>
            <form action="ModifTarif" method="post" >
            <input type="hidden" name="id" value="${tarif.id}" />
            <div class="bloc_formulaire">
                <c:if  test="${Erreur  != null}" >
                    <div class="alert alert-danger" role="alert">
                       ${Erreur}
                    </div>
                </c:if>
                <div class="form-group">
                    <label >Trajet</label>  
                    <select name="idTrajet" class="form-control" >
                        <c:forEach  var="tr" items="${trajet_detail}">
                            <option value="${tr.id}" <c:if test="${tarif.getIdTrajet() == tr.id}">selected</c:if> >${tr.origin} a ${tr.destination}</option>
                        </c:forEach>
                    </select>
               </div>
                <div class="form-group">
                    <label >Prix (Ar)</label>
                    <input type="number" name="prix" value="${tarif.getPrix()}" class="form-control" required/>
               </div>
                <div class="form-group">
                   <input type="submit"  class="btn btn-dark form-control" value="Valider"/>
               </div>
            </div>
            </form>
    </body>
</html>

