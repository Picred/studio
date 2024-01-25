@extends('layout')
@section('content')

<h1>Modifica il libro <code>{{$book->title}}</code></h1>

<form action="/books/{{$book->id}}" method="post">
    @csrf
    @method('PATCH')
    <input type="text" id="title" name="title" value="{{$book->title}}">
    <input type="text" id="author" name="author" value="{{$book->author}}">
    <input type="submit" value="Aggiorna modifiche del libro">
</form>