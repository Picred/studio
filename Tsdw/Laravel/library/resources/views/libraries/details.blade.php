@extends('layout')
@section('content')

<h1>Dettagli della libreria <code>{{$library->name}}</code></h1>

{{$library->description}}

<br>

<a href="/libraries/{{$library->id}}/edit">Modifica libreria</a>

<br>
