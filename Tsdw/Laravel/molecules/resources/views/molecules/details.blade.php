@extends('layout')
@section('content')

<h1>Dettagli della molecola <code>{{$molecule->name}}</code></h1>

{{$molecule->athom_combination}}

<a href="/molecules/{{$molecule->id}}/edit">Modifica questa molecola</a>
