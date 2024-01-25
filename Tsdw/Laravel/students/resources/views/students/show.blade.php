@extends('layout')
@section('content')
<h1>Informazioni su {{$student->nome}}  {{$student->cognome}}</h1>

<h3>
    Id: {{$student->id}} <br>
    Matricola: {{$student->matricola}} <br>
    Corso di laurea: {{$student->corso_di_laurea}} <br>
</h3>

<form action="/students/{{$student->id}}/edit" method="get">
    <input type="submit" value="Aggiorna dati">
</form>

<form action="/students/{{$student->id}}" method="post">
    @csrf
    @method('DELETE')
    <input type="submit" value="Elimina studente">
</form>