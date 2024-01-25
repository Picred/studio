<?php

namespace App\Http\Controllers;

use App\Models\Library;
use Illuminate\Http\Request;

class LibraryController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $libraries = Library::all();
        return view('libraries.index', compact('libraries'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('libraries.create');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $library = new Library;
        $library->name = request('name');
        $library->description = request('description');
        $library->save();
        return redirect('/libraries');
    }

    /**
     * Display the specified resource.
     */
    public function show(Library $library)
    {
        return view('libraries.details', compact('library'));
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Library $library)
    {
        return view('libraries.edit', compact('library'));
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Library $library)
    {
        $library->name = request('name');
        $library->description = request('description');
        $library->save();
        return redirect('/libraries');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Library $library)
    {
        $library->delete();
        return redirect('/libraries');
    }
}
