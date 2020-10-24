<%-- 
    Document   : AjoutTrajet
    Created on : 12 oct. 2020, 03:13:07
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
                        <h1 class="text-center">Ajout de trajet</h1>
                    </div>
                </div>
            <form action="AjoutTrajet" method="post" >
            <div class="bloc_formulaire">
                <c:if  test="${Erreur != null}" >
                    <div class="alert alert-danger" role="alert">
                       ${Erreur}
                    </div>
                </c:if>
                <div class="form-group">
                    <label >Ville de départ</label>
                    <select name="ville_dep" class="form-control" >
                        <c:forEach  var="tr" items="${ville}">
                            <option value="${tr.id}" <c:if test="${trajet.getVille_dep() == tr.id}">selected</c:if> >${tr.nom}</option>
                        </c:forEach>
                    </select>
               </div>
                <div class="form-group">
                    <label >Ville d'arrivée</label>
                    <select name="ville_arr" class="form-control" >
                        <c:forEach  var="tr" items="${ville}">
                            <option value="${tr.id}" <c:if test="${trajet.getVille_arr() == tr.id}">selected</c:if> >${tr.nom}</option>
                        </c:forEach>
                    </select>
               </div>
                <div class="form-group">
                    <label >Distance</label>
                   <input type="number" name="distance" value="${trajet.getDistance()}" class="form-control" required/>
               </div>
                <div class="form-group">
                   <input type="submit"  class="btn btn-dark form-control" value="Valider"/>
               </div>
            </div>
            </form>
        </body>
    </html>
