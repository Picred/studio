<?php

namespace App\Http\Controllers;

use App\Models\Task;
use App\Models\Project;
use Illuminate\Http\Request;

class TaskController extends Controller
{
    public function index()
    {
        $tasks = Task::all();
        return view('tasks.index', compact('tasks'));
    }

    public function create($project_id = 0)
    {
        return view('tasks.create', compact('project_id'));
    }

    public function store()
    {
        $task = new Task;
        $task->title = request('title');
        $task->description = request('description');
        $task->project_id = request('project_id');
        Project::findOrFail($task->project_id);
        $task->save();
        return redirect('/');
    }

    public function show(Task $task)
    {
        return view('tasks.show', compact('task'));
    }

    public function edit(Task $task)
    {
        return view('tasks.edit', compact('task'));
    }

    public function update(Task $task)
    {
        $task->title = request('title');
        $task->description = request('description');
        $task->project_id = request('project_id');
        Project::findOrFail($task->project_id);
        $task->save();
        return redirect('/');
    }

    public function destroy(Task $task)
    {
        $task->delete();
        return redirect('/');
    }

    public function help_create()
    {
        $project_id = request('project_id');
        return $this->create($project_id);
    }

    public function help_show()
    {
        $id = request('id');
        return redirect("/tasks/$id");
    }

    public function destroyAll()
    {
        $tasks = Task::all();
        foreach($tasks as $task)
            $task->delete();
        return redirect('/');
    }
}
