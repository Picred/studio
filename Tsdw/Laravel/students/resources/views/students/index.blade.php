@extends('layout')

@section('content')

@endsection

<h1>Tutti gli studenti:</h1>

<ul>
    @foreach ($students as $student)
    <li>
        {{-- Id: {{$student->id}} <br> --}}
        <a href="/students/{{$student->id}}"> Nome: {{$student->nome}}
        Cognome: {{$student->cognome}} <br> </a>
        {{-- Matricola: {{$student->matricola}} <br> --}}
    </li>    
    @endforeach
</ul>
<br><br>
<form action="/students/create" method="get">
    {{-- @csrf --}}
    <input type="submit" value="Crea un nuovo studente">
</form>