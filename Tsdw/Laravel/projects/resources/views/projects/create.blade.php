@extends('layout')
@section('content')

<h1>Crea un nuovo progetto</h1>

<form action="/projects" method="post">
    @csrf
    <input type="text" name="name" placeholder="Nome progetto" required>
    <input type="text" name="description" placeholder="Descrizione" required>
    <input type="submit">
</form>