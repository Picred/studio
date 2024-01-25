@extends('layout')
@section('content')


<h1>Modifica l'atomo <code>{{$athom->name}}</code></h1>

<form action="/athoms/{{$athom->id}}" method="post">
    @csrf
    @method('PATCH')
    <input type="text" id="name" name="name" value="{{$athom->name}}">
    <input type="number" id="ath_num" name="ath_num" value="{{$athom->ath_num}}">
    <input type="submit" value="Modifica atomo">
</form>