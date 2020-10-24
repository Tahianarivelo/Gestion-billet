<%-- 
    Document   : AchatBillet
    Created on : 13 oct. 2020, 06:38:32
    Author     : tahiana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
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
                        <h1 class="text-center">Achet de billet par le vol numero: ${vol.num}</h1>
                    </div>
                </div>
            <form action="AchatBillet" method="post" >
                <input type="hidden" name="idVol" value="${vol.id}" class="form-control" />
            <div class="bloc_formulaire">
                <c:if  test="${Erreur != null}" >
                    <div class="alert alert-danger" role="alert">
                       ${Erreur}
                    </div>
                </c:if>
                <div class="form-group">
                    <label >Nom du client</label>
                    <input type="text" name="nom" value="" class="form-control" required/>
               </div>
               <div class="form-group">
                    <label >Age du client</label>
                    <input type="date" name="date" value="" class="form-control" required/>
               </div>
                <div class="form-group">
                    <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="modifiable" value="true" checked>
                    <label class="form-check-label">
                      Modifiable:
                    </label>
                </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="true" name="remboursable" checked>
                    <label class="form-check-label">
                      Remboursable:
                    </label>
                </div>
               </div>
               
                <div class="form-group">
                   <input type="submit"  class="btn btn-dark form-control" value="Valider"/>
               </div>
            </div>
            </form>
        </body>
    </html>
</f:view>
