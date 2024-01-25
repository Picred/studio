<?php

namespace App\Http\Controllers;

use App\Models\Magazzon;
use Illuminate\Http\Request;

class MagazzonController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $products = Magazzon::all();
        return view('magazzon.read', compact('products'));
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        return view('magazzon.create');
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $magazzon = new Magazzon;
        $magazzon->nome_prodotto = request('nome_prodotto');
        $magazzon->giacenza = request('giacenza');
        $magazzon->prezzo = request('prezzo');
        $magazzon->save();
        return redirect('/');
    }

    /**
     * Display the specified resource.
     */
    public function show(Magazzon $magazzon)
    {
        //
    }

    /**
     * Acquista una quantità.
     */
    public function edit(Magazzon $magazzon)
    {
        $magazzon->giacenza = $magazzon->giacenza-1;
        $magazzon->save();
        return redirect('/');
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Magazzon $magazzon)
    {
        $magazzon->giacenza -= request('quantita');
        $magazzon->save();
        return redirect('/');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Magazzon $magazzon)
    {
        $magazzon->delete();
        return redirect('/');
    }
}
