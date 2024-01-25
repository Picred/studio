@extends('layout')
@section('content')

<h1>Tutti i progetti</h1>

{{-- Lista progetti --}}
<ul>
    @foreach ($projects as $project)
        
    <li>
        Nome: {{$project->name}}
        <a href="/projects/{{$project->id}}">Dettagli</a>
        <a href="/projects/{{$project->id}}/edit">Modifica progetto</a>

        <form action="/projects/{{$project->id}}" method="post">
            @csrf
            @method('DELETE')
            <input type="submit" value="Elimina progetto">
        </form>

    </li>
    @endforeach
</ul>


{{-- Crea un progetto --}}
<a href="/projects/create">Crea progetto</a>
