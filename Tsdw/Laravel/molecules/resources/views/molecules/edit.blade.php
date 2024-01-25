@extends('layout')
@section('content')

<h1>Modifica la molecola <code>{{$molecule->name}}</code></h1>
<form action="/molecules/{{$molecule->id}}" method="post">
    @csrf
    @method('PATCH')
    <input type="text" id="name" name="name" value="{{$molecule->name}}">
    <input type="text" id="athom_combination" name="athom_combination" value="{{$molecule->athom_combination}}">
    <input type="submit" value="Modifica molecola">
</form>