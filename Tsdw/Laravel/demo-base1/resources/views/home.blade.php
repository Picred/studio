@extends('layout')

@section('content')
    <h1>Projects Operations</h1>

    <form action="/projects/create" method="get">
        <input type="submit" value="Create Project"> <br><br>
    </form>

    <form action="/projects" method="get">
        <input type="submit" value="Show All Projects"> <br><br>
    </form>

    <form action="/projects/help_show" method="post">
        <input type="submit" value="Search Project by numeric ID">
        @csrf
        <input type="number" name="id" id="id"><br><br>
    </form>

    <form action="/projects" method="post">
        @csrf
        @method('DELETE')
        <input type="submit" value="Delete All Projects"> <br><br>
    </form>

    <h1>Tasks Operations</h1>

    <form action="/tasks/create" method="get">
        <input type="submit" value="Create Task"> <br><br>
    </form>

    <form action="/tasks" method="get">
        <input type="submit" value="Show All Tasks"> <br><br>
    </form>

    <form action="/tasks/help_show" method="post">
        @csrf
        <input type="submit" value="Search Task by numeric ID">
        <input type="number" name="id" id="id"> <br><br>
    </form>

    <form action="/tasks" method="post">
        @csrf
        @method('DELETE')
        <input type="submit" value="Delete All Tasks">
    </form>
    <br>
@endsection
