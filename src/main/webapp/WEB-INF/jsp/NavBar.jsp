<%-- 
    Document   : NavBar
    Created on : 12 oct. 2020, 16:50:09
    Author     : tahiana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">Back Office CA</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
<!--      <li class="nav-item">
        <a class="nav-link" href="/Dashboard">Dashboard</a>
      </li>-->
        <li class="nav-item">
        <a class="nav-link" href="ListTrajet_Get">List trajet</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="ListTarif_Get">List tarif</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="AjoutVol_Gest">Ajout vol</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="AjoutTrajet_Gest">Ajout trajet</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="AjoutTarif_Gest">Ajout tarif</a>
      </li>
        <li class="nav-item">
        <a class="nav-link" href="RechercheVol_Gest">Recherche vol</a>
      </li>
    </ul>
      <ul class="navbar-nav mr-0 mt-2 mt-lg-0 text-rigth">
       <li class="nav-item">
        <a class="nav-link" href="RechercheVol_Gest">Autre</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="">
            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-door-closed-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" d="M4 1a1 1 0 0 0-1 1v13H1.5a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2a1 1 0 0 0-1-1H4zm2 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
              </svg>
        </a>
      </li>
    </ul>
</nav>
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/bootstrap.min.js" ></script>