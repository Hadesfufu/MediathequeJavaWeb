<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div  class="col-md-11 col-md-offset-1">
    <h1 align='center'>Catalogue des oeuvres</h1>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Titre</td>
                <td>Prix</td>
                <td>Prénom propriétaire</td>
                <td>Nom propriétaire</td>
                <td>Réserver</td>
                <td>Modifier</td>
                <td>Supprimer</td>                
            </tr>  
        </thead>
        <tbody>
            <c:forEach items="${lOeuvres}" var="oeuvre">
                <tr>
                    <td>${oeuvre.getTitre()}</td>
                    <td>${oeuvre.getPrix()}</td>
                    <td>${getProprietaireByID(oeuvre.getProprietaire())}</td>
                    <td>${oeuvre.getProprietaire().getNom_proprietaire()}</td>
                    <td><a class="btn btn-primary" href="">Réserver</a></td>
                    <td><a class="btn btn-primary" href="">Modifier</a></td> 
                    <td><a class="btn btn-primary" onclick="javascript:if (confirm('Suppression confirmée ?')){ window.location='';}">Supprimer</a></td>                     
                </tr>
            </c:forEach>
        </tbody>
    </table>          
</div>
