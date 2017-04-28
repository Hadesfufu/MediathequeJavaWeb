<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div  class="col-md-11 col-md-offset-1">
    <h1 align='center'>Liste des réservations</h1>             
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Titre</td>
                <td>Date</td>
                <td>Statut</td>
                <td>Prénom adhérent</td>
                <td>Nom adhérent</td>
                <td>Confirmer</td>
                <td>Supprimer</td>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${lReservationsR}" var="resa">
                <tr>
                    <td> ${resa.getOeuvre().getTitre()}</td>
                    <td> ${resa.getOeuvre().getTitre()}</td>
                    <td>${resa.getStatut()}</td>
                    <td>${resa.getAdherent().getPrenom_adherent()}</td>
                    <td>${resa.getAdherent().getNom_adherent()}</td>
                    <td><a class="btn btn-primary" onclick="javascript:if (confirm('Confirmer la réservation ?')){ window.location='confirmerReservation.res?idOeuvre=${resa.getId_oeuvre()}&idAdherent=${resa.getId_adherent()}';}" >Confirmer</a></td>
                    <td><a class="btn btn-primary" onclick="javascript:if (confirm('Suppression confirmée ?')){ window.location='supprimerReservation.res?idOeuvre=${resa.getId_oeuvre()}&idAdherent=${resa.getId_adherent()}';}" >Supprimer</a></td>                
                </tr>
            </c:forEach>                     
        </tbody>
    </table>              
</div>