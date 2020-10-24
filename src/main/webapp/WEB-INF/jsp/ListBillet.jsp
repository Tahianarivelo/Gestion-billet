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
                    <h1 class="text-center">List des billets</h1>
                </div>
            </div>
            <div class="row contenu_recherche">
                <table class="table">
                        <thead class="thead-dark">
                          <tr>
                              <th scope="col" class="text-center">#Id</th>
                               <th scope="col" class="text-center">Nom</th>
                               <th scope="col" class="text-center">age</th>
                               <th scope="col" class="text-center">Vol</th>
                               <th scope="col" class="text-center">Modifiable</th>
                               <th scope="col" class="text-center">Remboursable</th>
                               <th scope="col" class="text-center">Montant</th>
                               <th scope="col" class="text-center">Date</th>
                               <th scope="col" class="text-center">Option</th>
                          </tr>
                        </thead>
                        <tbody>
                            <c:forEach  var="bi" items="${billet}">
                                  <tr>
                                      <th scope="row">${bi.id}</th>
                                      <td>${bi.nomClient}</td>
                                      <td class="text-right">${bi.age}</td>
                                      <td>${bi.num_vol}</td>
                                      <td>
                                          <!--modifiable-->
                                          <c:choose> 
                                              <c:when test="${bi.modifiable == 1}">
                                                  OUI
                                                </c:when>
                                                  <c:when test="${bi.modifiable == 0}">
                                                  NON
                                                </c:when>
                                          </c:choose>
                                      </td>
                                      <td>
                                          <!--remboursable-->
                                          <c:choose> 
                                              <c:when test="${bi.remboursable == 1}">
                                                  OUI
                                                </c:when>
                                                  <c:when test="${bi.remboursable == 0}">
                                                  NON
                                                </c:when>
                                          </c:choose>
                                      </td>
                                      <td  class="text-right">${bi.montant}</td>
                                      <td>${bi.dateCreation}</td>
                                      <td class="text-right">
                                          <a href="ImprimerBillet?idBillet=${bi.id}" target="_blank" >
                                              <button class="btn btn-secondary">
                                                  <!--PDF-->
                                                  <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-printer-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                    <path d="M5 1a2 2 0 0 0-2 2v1h10V3a2 2 0 0 0-2-2H5z"/>
                                                    <path fill-rule="evenodd" d="M11 9H5a1 1 0 0 0-1 1v3a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1v-3a1 1 0 0 0-1-1z"/>
                                                    <path fill-rule="evenodd" d="M0 7a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v3a2 2 0 0 1-2 2h-1v-2a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v2H2a2 2 0 0 1-2-2V7zm2.5 1a.5.5 0 1 0 0-1 .5.5 0 0 0 0 1z"/>
                                                  </svg>
                                              </button>
                                          </a>                                      
                                        <button class="btn btn-success" onclick=window.location.href='Remboursement_Gest?idBillet=${bi.id}&idVol=${bi.idVol}'; <c:if test="${ bi.remboursable ==  1 && bi.etat == 10 || (bi.remboursable ==  0)}">disabled</c:if>>
                                            <!--Remboursement-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-cash-stack" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M14 3H1a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1h-1z"/>
                                                <path fill-rule="evenodd" d="M15 5H1v8h14V5zM1 4a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V5a1 1 0 0 0-1-1H1z"/>
                                                <path d="M13 5a2 2 0 0 0 2 2V5h-2zM3 5a2 2 0 0 1-2 2V5h2zm10 8a2 2 0 0 1 2-2v2h-2zM3 13a2 2 0 0 0-2-2v2h2zm7-4a2 2 0 1 1-4 0 2 2 0 0 1 4 0z"/>
                                             </svg>
                                        </button>
                                         <button class="btn btn-warning" onclick=window.location.href='ModifBillet_Gest?idBillet=${bi.id}'; <c:if test="${ bi.modifiable ==  1 && bi.etat == 10 || (bi.modifiable ==  0)}">disabled</c:if> >
                                            <!--Modifier-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
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
