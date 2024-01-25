@extends('layout')

@section('content')
    <h1>Tasks</h1>
    <ul>
        @foreach ($tasks as $task)
            <li> <a href="/tasks/{{$task->id}}">{{$task->title}}</a> </li>
        @endforeach
    </ul>
@endsection
