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
                    <h1 class="text-center">Remboursement billet: ${billet.id}</h1>
                </div>
            </div>
            <form action="RembourserBillet" method="post" >
              <input type="hidden" value="${idVol}" name="idVol" />
              <input type="hidden" value="${billet.id}" name="idBillet" />
            <div class="bloc_formulaire">
                <c:if  test="${Erreur != null}" >
                    <div class="alert alert-danger" role="alert">
                       ${Erreur}
                    </div>
                </c:if>     
                <div class="form-group">
                    <label ><B>Nom client: </B>${billet.nomClient}</label><br>
                    <label ><B>Prix: </B> ${billet.montant} Ar</label><br>
                    <label ><B>Penalite: </B>${penalite}%</label><br>
                    <label ><B>Montant a payer:</B> ${montant_payer} Ar</label><br>
                </div>
                <div class="form-group">
                    <label >Date de remboursement</label>
                    <input type="date" name="date" class="form-control" required/>
               </div>
                <div class="form-group">
                   <input type="submit"  class="btn btn-dark form-control" value="Valider"/>
               </div>
            </div>
            </form>
    </body>
</html>