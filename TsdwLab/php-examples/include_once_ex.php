<!DOCTYPE html>
<html>
<body>

<h2>Welcome to the <code>include</code>/<code>include_once</code> example!</h2>

<p><code>include()</code> pu&ograve; includere qualsiasi file (non importa l'estensione).
<BR>NB: i tre file <code>footer.php</code>, <code>footer.html</code> e <code>footer.my</code> hanno lo stesso contenuto.
</p>

Esegue <code>include('footer.php');</code>:
<?php include('footer.php');?>

Esegue <code>include_once('footer.html');</code>:
<?php include_once('footer.html');?>

Esegue <code>include('footer.my');</code>:
<?php include('footer.my');?>

<hr>

Da qui in poi si usano le varianti <code>include_once</code> e <code>require_once</code> su file gi&agrave; <code>include</code>d, per cui non si vedr&agrave; in effetti nulla.

<BR><BR>Esegue <code>include_once 'footer.php';</code> (non accade nulla) ...

<?php include_once 'footer.php';?>

<BR><BR>Esegue <code>require_once 'footer.html';</code> (non accade nulla) ...
<?php require_once 'footer.html';?>

<BR><BR>Esegue <code>require_once 'footer.my';</code> (non accade nulla) ...
<?php require_once 'footer.my';?>

</body>
</html>
