<%-- 
    Document   : RechercheVol
    Created on : 12 oct. 2020, 15:21:33
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
                        <h1 class="text-center">Modification du billet: ${billet.id}</h1>
                    </div>
                </div>
            <div class="row contenu_recherche">
                <table class="table">
                        <thead class="thead-dark">
                          <tr>
                              <th scope="col" class="text-center"><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=num" <c:if test="${colonne != 'num'}"> class="text-white" </c:if> >#Numero</a></th>
                              <th scope="col" class="text-center"><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=origin" <c:if test="${colonne != 'origin'}"> class="text-white" </c:if> >Lieu de départ</a></th>
                            <th scope="col" class="text-center" ><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=destination" <c:if test="${colonne != 'destination'}"> class="text-white" </c:if> >Lieu d'arriée</a></th>
                            <th scope="col" class="text-center" ><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=nom_avion" <c:if test="${colonne != 'nom_avion'}"> class="text-white" </c:if> >Avion</a></th>
                            <th scope="col" class="text-center" ><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=datedep" <c:if test="${colonne != 'datedep'}"> class="text-white" </c:if> >Date</a></th>
                            <th scope="col" class="text-center"><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=horaire" <c:if test="${colonne != 'horaire'}"> class="text-white" </c:if> >Horaire</a></th>
                            <th scope="col" class="text-center" ><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=prix" <c:if test="${colonne != 'prix'}"> class="text-white" </c:if> >Tarif(Ar)</a></th>
                            <th scope="col" class="text-center" ><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=reste_place" <c:if test="${colonne != 'reste_place'}"> class="text-white" </c:if> >Reste de place</a></th>
                            <th scope="col" class="text-center" ><a href="ModifBillet_Gest?idBillet=${billet.id}&page=${page_current}&colonne=etat_string" <c:if test="${colonne != 'etat_string'}"> class="text-white" </c:if> >Etat</a></th>
                            <th scope="col" class="text-center">Option</th>
                          </tr>
                        </thead>
                        <tbody>
                            <c:forEach  var="vol" items="${vol_detail}">
                                  <tr>
                                    <th scope="row">${vol.num}</th>
                                    <td>${vol.origin}</td>
                                    <td>${vol.destination}</td>
                                    <td>${vol.nom_avion}</td>
                                    <td>${vol.dateDep}</td>
                                    <td>${vol.horaire}</td>
                                    <td class="text-right">${vol.prix}</td>
                                    <td class="text-right" >${vol.reste_place}</td>
                                    <td >${vol.etat_string}</td>
                                    <td class="text-right">                                                                                                                                                                                                   
                                        <button class="btn btn-success" onclick=window.location.href='ModifBillet?idBillet=${billet.id}&idVol=${vol.id}'; <c:if test="${ vol.etat ==  10}">disabled</c:if> >
                                             <!--Valider-->
                                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-check-square-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm10.03 4.97a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                                              </svg>
                                         </button>         
                                    </td>
                                  </tr>
                            </c:forEach>
                        </tbody>
                </table>
            </div>
        </body>
    </html>