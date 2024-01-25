@extends('layout')
@section('content')

<h1>Lista delle molecole</h1>

<ul>
    @foreach ($molecules as $mole)
        <li>
            <b>Id: </b> {{$mole->id}}
            <b>Nome: </b> {{$mole->name}}
            <a href="/molecules/{{$mole->id}}">Vedi dettagli</a>

            <br>
            Atomi in questa molecola:

            <ul>
                @foreach ($mole->athoms as $athom)
                    <li>
                    <b>Id: </b> {{$athom->id}}
                    <b>Nome: </b> {{$athom->name}}
                    <b>Numero atomico: </b> {{$athom->ath_num}}

                    {{-- Modifica --}}
                    <a href="/athoms/{{$athom->id}}/edit">Modifica atomo</a>
                    {{-- Elimina --}}
                    <form action="/athoms/{{$athom->id}}" method="post">
                        @csrf
                        @method('DELETE')
                        <input type="submit" value="Elimina atomo">
                    </form>

                    </li>
                @endforeach

                <form action="/athoms/create" method="get">
                    @csrf
                    <input type="hidden" id="molecule_id" name="molecule_id" value="{{$mole->id}}">
                    <input type="submit" value="Aggiungi atomo">
                </form>
            </ul>

            <form action="/molecules/{{$mole->id}}" method="post">
                @csrf
                @method('DELETE')
                <input type="submit" value="Elimina molecola">
            </form>

            <br>
        </li>
    @endforeach
</ul>

<a href="/molecules/create">Crea una molecola</a>