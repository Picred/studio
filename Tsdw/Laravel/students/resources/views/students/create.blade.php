@extends('layout')

@section('content')

@endsection

<form action="/students" method="post">
    @csrf
    Nome: <input type="text" id="nome" name="nome" required> <br>
    Cognome: <input type="text" id="cognome" name="cognome" required> <br>
    Matricola: <input type="text" id="matricola" name="matricola" required> <br>
    Corso di laurea: <input type="text" id="corso_di_laurea" name="corso_di_laurea" required> <br>
    <input type="submit" value="Crea studente">
</form>