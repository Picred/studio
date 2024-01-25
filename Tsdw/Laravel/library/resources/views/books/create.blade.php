@extends('layout')
@section('content')

<h1>Aggiungi un nuovo libro</h1>

<form action="/books" method="post">
    @csrf
    <input type="text" id="title" name="title" placeholder="Titolo libro" required>
    <input type="text" id="author" name="author" placeholder="Autore" required>
    <input type="hidden" name="library_id" id="library_id" value="{{$library_id}}">

    <input type="submit" value="Crea">
</form>