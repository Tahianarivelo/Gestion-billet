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
        <form action="ValiderVol" method="post">
            <input type="hidden" name="idVol" value="${vol.id}" />
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
                               <th scope="col" class="text-center">Présent</th>
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
                                       <td>
                                           <input type="checkbox" name="present" value="${bi.id}"/>
                                      </td>
                                  </tr>
                            </c:forEach>
                        </tbody>
                </table>
            </div>
            <c:if test="${vol.etat == 0}">
                <div style="padding:5px 100px;" >
                <input type="submit" class="btn btn-dark form-control" value="Valider" />
            </div>
            </c:if>
        </form>
    </body>
</html>
