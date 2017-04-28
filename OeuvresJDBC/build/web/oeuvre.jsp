<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div  class="col-md-8 col-md-offset-1">
    <h1 align='center'>${titre}${Id}</h1>
    <form class="form-signin form-horizontal" role="form"  action="enregistrer.oe?Id=${oeuvre.getId_oeuvre()}" method="post">
        <div class="form-group">
            <label class="col-md-3 control-label">Titre : </label>
            <div class="col-md-6">
                <input type="text" name="txtTitre" value="${oeuvre.getTitre()}" class="form-control" placeholder="Titre de l'oeuvre" required autofocus>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Prix : </label>
            <div class="col-md-3">
                <input type="text" name="txtPrix" value="${oeuvre.getPrix()}"  class="form-control" placeholder="Prix de l'oeuvre" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Proprietaire : </label>
            <div class="col-sm-6 col-md-3">
                <select class='form-control' name='lProprietaires' required>
                    <c:forEach items="${lProprietaires}" var="proprietaire" >
                        <option value="${proprietaire.getId_proprietaire()}" <c:if test="${proprietaire.getId_proprietaire() == oeuvre.getId_proprietaire()}"> SELECTED</c:if> >${proprietaire.getNom_proprietaire()} ${proprietaire.getPrenom_proprietaire()} </option>
                    </c:forEach>
                </select>
            </div>
        </div>             
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <button type="submit" class="btn btn-default btn-primary"><span class="glyphicon glyphicon-log-in"></span>Valider</button>
            </div>
        </div>
    </form>
</div>