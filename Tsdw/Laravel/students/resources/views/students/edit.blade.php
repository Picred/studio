@extends('layout')
@section('content')

<h1>Aggiorna i dati di {{$student->nome}} {{$student->cognome}} </h1>

<form action="/students/{{$student->id}}" method="post">
    @csrf
    @method('PATCH')
    Nome: <input type="text" id="nome" name="nome" value="{{$student->nome}}" required> <br>
    Cognome: <input type="text" id="cognome" name="cognome" value="{{$student->cognome}}" required> <br>
    Matricola: <input type="text" id="matricola" name="matricola" value="{{$student->matricola}}" required> <br>
    Corso di laurea: <input type="text" id="corso_di_laurea" name="corso_di_laurea" value="{{$student->corso_di_laurea}}" required> <br>
    <input type="submit" value="Aggiorna studente">
</form>