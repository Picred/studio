@extends('layout')
@section('content')

<h1>Crea una nuova lireria</h1>

<form action="/libraries" method="post">
    @csrf
    <input type="text" id="name" name="name" placeholder="Nome libreria" required>
    <input type="text" id="description" name="description" placeholder="Descrizione" required>
    <input type="submit" value="Crea">
</form>