@extends('layout')

@section('content')
    <h1>Project {{$project->id}}</h1>
    <b>TITLE:</b> {{$project->title}} <br>
    <b>DESCRIPTION:</b> {{$project->description}} <br>
    @if ($project->tasks->count())
        <b>TASKS:</b>
        <ul>
            @foreach ($project->tasks as $task)
                <li> <a href="/tasks/{{$task->id}}">{{$task->title}}</a> </li>
            @endforeach
        </ul>
    @endif
    <br>
    <form action="/projects/{{$project->id}}/edit" method="get">
        <input type="submit" value="Edit/Delete Project"> <br><br>
    </form>
    <form action="/tasks/create" method="post">
        @csrf
        <input type="hidden" name="project_id" id="project_id" value="{{$project->id}}">
        <input type="submit" value="Create Task in this Project">
    </form>
@endsection
