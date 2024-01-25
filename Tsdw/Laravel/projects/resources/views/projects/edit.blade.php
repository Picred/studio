@extends('layout')
@section('content')

<h1>Modifica il progetto {{$project->name}}</h1>


<form action="/projects/{{$project->id}}" method="post">
    @csrf
    @method('PATCH')
    {{-- <input type="hidden" name="id" value="{{$project->id}}"> --}}
    <input type="text" name="name" value="{{$project->name}}" >
    <input type="text" name="description" value="{{$project->description}}" >
    <input type="submit">
</form>