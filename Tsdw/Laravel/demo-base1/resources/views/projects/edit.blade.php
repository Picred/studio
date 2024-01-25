@extends('layout')

@section('content')
    <h1>Edit/Delete Project</h1>
    <form action="/projects/{{$project->id}}" method="post">
        @csrf
        @method('PATCH')
        <label for="title">Title</label> <br>
        <input type="text" name="title" id="title" value="{{$project->title}}"> <br><br>
        <label for="description">Description</label> <br>
        <textarea name="description" id="description" cols="30" rows="10">{{$project->description}}</textarea> <br><br>
        <input type="submit" value="Save Project"> <br><br>
    </form>
    <form action="/projects/{{$project->id}}" method="post">
        @csrf
        @method('DELETE')
        <input type="submit" value="Delete Project">
    </form>
@endsection
