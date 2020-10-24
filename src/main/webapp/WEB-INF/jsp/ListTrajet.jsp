<%-- 
    Document   : ListBillet
    Created on : 13 oct. 2020, 14:45:04
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
                    <h1 class="text-center">List de trajets</h1>
                </div>
            </div>
            <div class="row contenu_recherche">
                <table class="table">
                        <thead class="thead-dark">
                          <tr>
                              <th scope="col" class="text-center">#Id</th>
                               <th scope="col" class="text-center">Origin</th>
                               <th scope="col" class="text-center">Desctination</th>
                               <th scope="col" class="text-center">Distance (km)</th>
                               <th scope="col" class="text-center">Date</th>
                               <th scope="col" class="text-center">Prix (Ar)</th>
                               <th scope="col" class="text-center">Option</th>
                          </tr>
                        </thead>
                        <tbody>
                            <c:forEach  var="tr" items="${trajet_detail}">
                                  <tr>
                                      <th scope="row">${tr.id}</th>
                                      <td>${tr.origin}</td>
                                      <td >${tr.destination}</td>
                                      <td class="text-right">${tr.distance}</td>
                                      <td class="text-center">${tr.dateCreation}</td>
                                      <td class="text-right">${tr.prix}</td>
                                      <td class="text-right" >                     
                                          <button class="btn btn-warning" onclick=window.location.href='ModifTrajet_Gest?idTrajet=${tr.id}'; >
                                            <!--Modifier-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                            </svg>
                                        </button>    
                                         <button class="btn btn-danger" onclick=window.location.href='SuppreTrajet?idTrajet=${tr.id}'; >
                                            <!--Supprimer-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                          </svg>
                                        </button>         
                                      </td>
                                  </tr>
                            </c:forEach>
                        </tbody>
                </table>
        </div>
    </form>
</body>
</html>
