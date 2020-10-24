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
            <form action="RechercheVol" method="get" > 
                <div class="row" >
                    <div class="col">
                        <h1 class="text-center">Recherche de vol <span class="glyphicon glyphicon-envelope"></span></h1>
                    </div>
                </div>
                <div class="row  barRecherche" >
                        <input type="hidden" name="page" value="1" />
                        <input type="hidden" name="colonne" value="num" />
                        <div class="col form-group">
                             <label >Date avant</label>
                            <input type="date" name="date1" class="form-control"/>
                        </div>
                        <div class="col form-group">
                             <label >Date après</label>
                            <input type="date" name="date2" class="form-control" />
                        </div>
                        <div class="col form-group">
                             <label >Horaire</label>
                            <input type="time" name="horaire" class="form-control"/>
                        </div>
                        <div class="col form-group" >
                             <label >Trajet</label>
                            <select name="idTrajet" class="form-control" >
                                <option value="Tout" >Tout</option>
                                <c:forEach  var="tr" items="${trajet_detail}">
                                    <option value="${tr.id}">${tr.origin} a ${tr.destination}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col form-group" >
                            <label >Avion</label>
                          <select name="idAvion" class="form-control" >
                                <option value="Tout">Tout</option>
                                 <c:forEach  var="av" items="${avion}">
                                    <option value="${av.id}">${av.nom}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-1 form-group">
                            <label >Valider</label>
                            <input type="submit" value="Recherche" class="form-control btn btn-dark" />
                        </div>
                </div>
            </form>
            <div class="row contenu_recherche">
                <table class="table">
                        <thead class="thead-dark">
                          <tr>
                              <th scope="col" class="text-center"><a href="RechercheVol?page=${page_current}&colonne=num&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'num'}"> class="text-white" </c:if> >#Numero</a></th>
                              <th scope="col" class="text-center"><a href="RechercheVol?page=${page_current}&colonne=origin&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'origin'}"> class="text-white" </c:if> >Lieu de départ</a></th>
                            <th scope="col" class="text-center" ><a href="RechercheVol?page=${page_current}&colonne=destination&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'destination'}"> class="text-white" </c:if> >Lieu d'arriée</a></th>
                            <th scope="col" class="text-center" ><a href="RechercheVol?page=${page_current}&colonne=nom_avion&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'nom_avion'}"> class="text-white" </c:if> >Avion</a></th>
                            <th scope="col" class="text-center" ><a href="RechercheVol?page=${page_current}&colonne=datedep&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'datedep'}"> class="text-white" </c:if> >Date</a></th>
                            <th scope="col" class="text-center"><a href="RechercheVol?page=${page_current}&colonne=horaire&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'horaire'}"> class="text-white" </c:if> >Horaire</a></th>
                            <th scope="col" class="text-center" ><a href="RechercheVol?page=${page_current}&colonne=prix&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'prix'}"> class="text-white" </c:if> >Tarif(Ar)</a></th>
                            <th scope="col" class="text-center" ><a href="RechercheVol?page=${page_current}&colonne=reste_place&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'reste_place'}"> class="text-white" </c:if> >Reste de place</a></th>
                            <th scope="col" class="text-center" ><a href="RechercheVol?page=${page_current}&colonne=etat_string&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}" <c:if test="${colonne != 'etat_string'}"> class="text-white" </c:if> >Etat</a></th>
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
                                        <button class="btn btn-success" onclick=window.location.href='AchatBillet_Gest?idVol=${vol.id}'; <c:if test="${ vol.etat ==  10}">disabled</c:if>>
                                            <!--Achat-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-cart-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm7 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"/>
                                             </svg>
                                        </button>                   
                                        <button class="btn btn-warning" onclick=window.location.href='ModifVol_Gest?idVol=${vol.id}'; <c:if test="${ vol.etat ==  10}">disabled</c:if>>
                                            <!--Modifier-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                            </svg>
                                        </button>                                   
                                        <button class="btn btn-danger" onclick=window.location.href='SuppreVol?idVol=${vol.id}'; <c:if test="${ vol.etat ==  10}">disabled</c:if>>
                                            <!--Supprimer-->
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                          </svg>
                                        </button>                                     
                                         <button class="btn btn-secondary" onclick=window.location.href='ListBillet_Gest?idVol=${vol.id}'; >
                                             <!--Billets-->
                                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-stickies-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M0 1.5A1.5 1.5 0 0 1 1.5 0H13a1 1 0 0 1 1 1H1.5a.5.5 0 0 0-.5.5V14a1 1 0 0 1-1-1V1.5z"/>
                                                <path fill-rule="evenodd" d="M3.5 2A1.5 1.5 0 0 0 2 3.5v11A1.5 1.5 0 0 0 3.5 16h6.086a1.5 1.5 0 0 0 1.06-.44l4.915-4.914A1.5 1.5 0 0 0 16 9.586V3.5A1.5 1.5 0 0 0 14.5 2h-11zm6 8.5v4.396c0 .223.27.335.427.177l5.146-5.146a.25.25 0 0 0-.177-.427H10.5a1 1 0 0 0-1 1z"/>
                                              </svg>
                                         </button>                                                                          
                                         <button class="btn btn-success" onclick=window.location.href='Validation_vol_Gest?idVol=${vol.id}'; <c:if test="${ vol.etat ==  10}">disabled</c:if> >
                                             <!--Valider-->
                                             <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-check-square-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm10.03 4.97a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                                              </svg>
                                         </button>         
                                         <button class="btn btn-primary" onclick=window.location.href='Chiffre_Affaire?idVol=${vol.id}'; <c:if test="${ vol.etat !=  10}">disabled</c:if> >
                                             <!--Chiffre d'affaire-->
                                           <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-wallet2" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M12.136.326A1.5 1.5 0 0 1 14 1.78V3h.5A1.5 1.5 0 0 1 16 4.5v9a1.5 1.5 0 0 1-1.5 1.5h-13A1.5 1.5 0 0 1 0 13.5v-9a1.5 1.5 0 0 1 1.432-1.499L12.136.326zM5.562 3H13V1.78a.5.5 0 0 0-.621-.484L5.562 3zM1.5 4a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h13a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-13z"/>
                                            </svg>
                                         </button>     
                                    </td>
                                  </tr>
                            </c:forEach>
                        </tbody>
                </table>
            </div>
                <div class="row">
                <c:if test="${nb_page > 1}" >
                 <div class="button_pagination">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                           <c:if test="${page_current > 1}" >
                            <li class="page-item"><a class="page-link text-dark " href="RechercheVol?page=${page_current - 1}&colonne=${colonne}&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}"><<</a></li>
                           </c:if>
                          <c:forEach    var="i" begin="1" end="${nb_page}" >
                              <c:choose>
                                 <c:when test="${page_current == i}">
                                        <li class="page-item"><a class="page-link active_pagination" href="RechercheVol?page=${i}&colonne=${colonne}&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}">${i}</a></li>
                                  </c:when>
                                    <c:when test="${page_current != i}">
                                        <li class="page-item"><a class="page-link text-dark" href="RechercheVol?page=${i}&colonne=${colonne}&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}">${i}</a></li>
                                  </c:when>
                               </c:choose>
                            </c:forEach>
                            <c:if test="${page_current < nb_page}" >
                                <li class="page-item"><a class="page-link text-dark" href="RechercheVol?page=${page_current + 1}&colonne=${colonne}&date1=${date1}&date2=${date2}&horaire=${horaire}&idTrajet=${idTrajet}&idAvion=${idAvion}">>></a></li>
                            </c:if>
                        </ul>
                      </nav>
                </div>
            </c:if>
            </div>
        </body>
    </html>