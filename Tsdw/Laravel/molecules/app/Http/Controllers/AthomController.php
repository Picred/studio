<?php

namespace App\Http\Controllers;

use App\Models\Athom;
use Illuminate\Http\Request;

class AthomController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        $molecule_id = request('molecule_id');
        return view('athoms.create', compact('molecule_id'));
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $athom = new Athom;
        $athom->name = request('name');
        $athom->ath_num = request('ath_num');
        $athom->molecule_id = request('molecule_id');
        $athom->save();
        return redirect('/molecules');
    }

    /**
     * Display the specified resource.
     */
    public function show(Athom $athom)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Athom $athom)
    {
        return view('athoms.edit', compact('athom'));
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Athom $athom)
    {
        $athom->name = request('name');
        $athom->ath_num = request('ath_num');
        $athom->save();
        return redirect('/molecules');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Athom $athom)
    {
        $athom->delete();
        return redirect('/molecules');
    }
}
