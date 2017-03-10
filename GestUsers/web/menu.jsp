<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-target">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.user">Accueil</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse-target">
            <ul class="nav navbar-nav">
                /* A compl�ter */
                    <li><a href="lister.user">Lister</a></li>
                    <li><a href="creer.user">Ajouter</a></li> 
                /* A compl�ter */
                /* A compl�ter */
                    <li><a href="modifier.user?id=${sessionScope.userId}">Compte</a></li> 
                /* A compl�ter */  
            </ul>   
            <ul class="nav navbar-nav navbar-right"> 
                /* A compl�ter */
                    <li><a href="deconnecter.user">Se d�connecter</a></li>
                /* A compl�ter */  
                /* A compl�ter */                            
                    <li><a href="login.user">Se connecter</a></li>
                /* A compl�ter */  
            </ul> 
        </div>
    </div>
</nav>

