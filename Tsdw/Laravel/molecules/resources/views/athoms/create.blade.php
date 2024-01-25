@extends('layout')
@section('content')

<h1>Crea un atomo</h1>

<form action="/athoms" method="post">
    @csrf
    <input type="text" id="name" name="name" placeholder="Nome atomo">
    <input type="text" id="ath_num" name="ath_num" placeholder="Numero atomico">
    <input type="hidden" id="molecule_id" name="molecule_id" value="{{$molecule_id}}">
    <input type="submit" value="Crea atomo">
</form>