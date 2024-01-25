@extends('layout')

@php
    use App\Models\Project;

    $project = Project::findOrFail($task->project_id);
@endphp

@section('content')
    <h1>Task {{$task->id}}</h1>
    <b>TITLE:</b> {{$task->title}} <br>
    <b>DESCRIPTION:</b> {{$task->description}} <br>
    <b>FROM PROJECT:</b> <a href="/projects/{{$project->id}}">{{$project->title}}</a> <br><br>
    <form action="/tasks/{{$task->id}}/edit" method="get">
        <input type="submit" value="Edit/Delete Task">
    </form>
@endsection
