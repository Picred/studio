@extends('layout')

@section('content')
    <h1>Create Project</h1>
    <form action="/projects" method="post">
        @csrf
        <label for="title">Title</label> <br>
        <input type="text" name="title" id="title"> <br><br>
        <label for="description">Description</label> <br>
        <textarea name="description" id="description" cols="30" rows="10"></textarea> <br><br>
        <input type="submit" value="Create Project">
    </form>
@endsection
