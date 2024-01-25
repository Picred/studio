@extends('layout')
@section('content')

<h1>Lista delle Librerie</h1>

<ul>
    @foreach ($libraries as $lib)
        <li>
            <b>Id:</b> {{$lib->id}}
            <b>Nome libreria:</b> {{$lib->name}}

            Libri presenti:
            <ul>
                @foreach ($lib->books as $book)
                <li>
                    <b>Id:</b> {{$book->id}}
                    <b>Nome libro:</b> {{$book->title}}
                    <b>Autore:</b> {{$book->author}}

                   <a href="/books/{{$book->id}}/edit">Modifica questo libro</a>
                    <form action="/books/{{$book->id}}" method="post">
                        @csrf
                        @method('DELETE')
                        {{-- <input type="hidden" name="library_id" id="library_id" value="{{$lib->id}}"> --}}
                        <input type="submit" value="Elimina questo libro">
                    </form>

                </li>
                @endforeach
            </ul>

            <form action="/books/create" method="get">
                @csrf
                <input type="hidden" name="library_id" id="library_id" value="{{$lib->id}}">
                <input type="submit" value="Aggiungi un libro">
            </form>


            <a href="/libraries/{{$lib->id}}">Dettagli</a>

            <form action="/libraries/{{$lib->id}}" method="post">
                @csrf
                @method('DELETE')
                <input type="submit" value="Elimina libreria">
            </form>
        </li>
    @endforeach
</ul>