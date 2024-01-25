@extends('layout')
@section('content')

<h1>Crea una molecola</h1>

<form action="/molecules" method="post">
    @csrf
    <input type="text" id="name" name="name" placeholder="Nome molecola">
    <input type="text" id="athom_combination" name="athom_combination" value="default">
    <input type="submit" value="Crea molecola">
</form>