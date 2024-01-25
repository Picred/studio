<?php

namespace App\Http\Controllers;

use App\Models\Molecule;
use Illuminate\Http\Request;

class MoleculeController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $molecules = Molecule::all();
        return view('molecules.index', compact('molecules'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('molecules.create');        
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $molecule = new Molecule;
        $molecule->name = request('name');
        $molecule->athom_combination = request('athom_combination');
        $molecule->save();
        return redirect('/molecules');
    }

    /**
     * Display the specified resource.
     */
    public function show(Molecule $molecule)
    {
        return view('molecules.details', compact('molecule'));
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Molecule $molecule)
    {
        return view('molecules.edit', compact('molecule'));

    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Molecule $molecule)
    {
        $molecule->name = request('name');
        $molecule->athom_combination = request('athom_combination');
        $molecule->save();
        return redirect('/molecules');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Molecule $molecule)
    {
        $molecule->delete();
        return redirect('/molecules');

    }
}
