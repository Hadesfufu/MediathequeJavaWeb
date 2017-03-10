
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div  class="col-md-8 col-md-offset-1">
    <h1 align='center'>Liste des utilisateurs</h1>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Nom</td>
                <td>Prénom</td>
                <td>Catégorie</td>
                <td>Modifier</td>
            </tr>  
        </thead>
        <tbody>
            <c:forEach var="user" items="/* A compléter */">
                <tr>
                    /* A compléter */            
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
