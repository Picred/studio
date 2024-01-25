@extends('layout')

@section('content')
    <h1>Create Task</h1>
    <form action="/tasks" method="post">
        @csrf
        <label for="title">Title</label> <br>
        <input type="text" name="title" id="title"> <br><br>
        <label for="description">Description</label> <br>
        <textarea name="description" id="description" cols="30" rows="10"></textarea> <br><br>
        <label for="project_id">Project ID</label> <br>
        <input type="number" name="project_id" id="project_id" value="{{$project_id}}"> <br><br>
        <input type="submit" value="Create Task">
    </form>
@endsection
