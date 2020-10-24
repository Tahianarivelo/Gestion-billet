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
                    <h1 class="text-center">Chiffre d'affaire du vol:${vol_detail.num}</h1>
                </div>
            </div>
        <div class="form-group">
            <label ><B>Trajet: </B>${vol_detail.origin} => ${vol_detail.destination}</label><br>
            <label ><B>Datede depart: </B> ${vol_detail.dateDep}</label><br>
            <label ><B>Horaire:</B>${vol_detail.horaire}</label><br>
            <label ><B>Prix: </B>  ${vol_detail.prix } Ar</label><br><br>        
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
                               <th scope="col" class="text-center">Etat</th>
                          </tr>
                        </thead>
                        <tbody>
                            <c:forEach  var="bi" items="${billet}">
                                  <tr>
                                      <th scope="row">${bi.id}</th>
                                      <td>${bi.nomClient}</td>
                                      <td class="text-right">${bi.age}</td>
                                      <td>${bi.num_vol}</td>
                                      <td class="text-center">
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
                                      <td class="text-center">
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
                                      <td  class="text-right">
                                          <c:choose> 
                                              <c:when test="${bi.etat == 10}">
                                                  ${bi.montant}
                                                </c:when>
                                                  <c:when test="${bi.etat == 20}">
                                                  ${bi.montant_remboursement}
                                                </c:when>
                                                  <c:when test="${bi.etat == 11 && bi.modifiable == 1}">
                                                    0.0
                                                </c:when>
                                                 <c:when test="${bi.etat == 11 && bi.modifiable != 1 }">
                                                    ${bi.montant*(penalite/100)}
                                                </c:when>
                                          </c:choose>
                                      </td>
                                      <td>
                                           <c:choose> 
                                              <c:when test="${bi.etat == 10}">
                                                  Validé
                                                </c:when>
                                                  <c:when test="${bi.etat == 20}">
                                                  Remboursé
                                                </c:when>
                                                  <c:when test="${bi.etat == 11}">
                                                  Non validé
                                                </c:when>
                                          </c:choose>
                                      </td>
                                  </tr>
                            </c:forEach>
                        </tbody>
                </table>
             <label ><B>Chiffre d'affaire: </B>  ${chiffre_affaire} Ar</label>
          </div>
    </body>
</html>