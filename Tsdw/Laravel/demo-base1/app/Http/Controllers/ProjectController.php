<?php

namespace App\Http\Controllers;

use App\Models\Project;
use Illuminate\Http\Request;

class ProjectController extends Controller
{
    public function index()
    {
        $projects = Project::all();
        return view('projects.index', compact('projects'));
    }

    public function create()
    {
        return view('projects.create');
    }

    public function store()
    {
        $project = new Project;
        $project->title = request('title');
        $project->description = request('description');
        $project->save();
        return redirect('/');
    }

    public function show(Project $project)
    {
        return view('projects.show', compact('project'));
    }

    public function edit(Project $project)
    {
        return view('projects.edit', compact('project'));
    }

    public function update(Project $project)
    {
        $project->title = request('title');
        $project->description = request('description');
        $project->save();
        return redirect('/');
    }

    public function destroy(Project $project)
    {
        $project->delete();
        return redirect('/');
    }

    public function help_show()
    {
        $id = request('id');
        return redirect("/projects/$id");
    }

    public function destroyAll()
    {
        $projects = Project::all();
        foreach($projects as $project)
            $project->delete();
        return redirect('/');
    }
}
