@extends('layout')
@section('content')

<h1>Dettagli del progetto {{$project->name}}</h1>

{{$project->description}}

