@extends('layout')
@section('content')

<h1>Modifica la libreria <code>{{$library->name}}</code></h1>

<form action="/libraries/{{$library->id}}" method="post">
    @csrf
    @method('PATCH')
    <input type="text" id="name" name="name" value="{{$library->name}}">
    <input type="text" id="description" name="description" value="{{$library->description}}">
    <input type="submit" value="Aggiorna modifiche">
</form>