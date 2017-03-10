
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="well">
    <div class="text-center titre">
        <h2>/* A compl�ter */</h2>
    </div>
    <form class="form-horizontal" role="form" name="employeeForm" action="/* A compl�ter */" method="post">

        <div class="form-group">
            <label class="col-md-3 control-label">Nom : </label>
            <div class="col-md-5">
                <input type="text" name="txtNom" value="/* A compl�ter */" class="form-control" placeholder="Nom" required autofocus>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Pr�nom : </label>
            <div class="col-md-3">
                 <input type="text"  name="txtPrenom" value="/* A compl�ter */" class="form-control" placeholder="Pr�nom" required autofocus>               
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-3 control-label">Adresse : </label>
            <div class="col-md-6">   
                 <input type="text" name="txtAdresse" value="/* A compl�ter */" class="form-control" placeholder="Adresse" required autofocus>
            </div>
        </div>    
        <div class="form-group">
            <label class="col-md-3 control-label">Login : </label>
            <div class="col-md-2">
                <input type="text" name="txtLogin" value="/* A compl�ter */" class="form-control" placeholder="Login" required autofocus>
            </div>
        </div>     
        <div class="form-group">
            <label class="col-md-3 control-label">Mot de passe : </label>
            <div class="col-md-2">
                <input type="password" name="txtPwd" value="/* A compl�ter */" class="form-control" placeholder="Mot de passe" required autofocus>
            </div>
        </div>             
            
        <div class="form-group">
            <label class="col-md-3 control-label">Cat�gorie : </label>
            <div class="col-md-3">
                <select class='form-control' name='lstCategories' required>
                    <option value = "">S�lectionner une cat�gorie</option>
                    /* A compl�ter */
                </select>
            </div>
        </div>        
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <button type="submit" class="btn btn-default btn-primary"><span class="glyphicon glyphicon-ok"></span> Valider</button>
                &nbsp;
                <button type="button" class="btn btn-default btn-primary" onclick="window.location='index.user';return false;"><span class="glyphicon glyphicon-remove"></span> Annuler</button>
            </div>           
        </div>       

    </form>
</div>
