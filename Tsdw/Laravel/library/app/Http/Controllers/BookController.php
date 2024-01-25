<?php

namespace App\Http\Controllers;

use App\Models\Book;
use App\Models\Library;
use Illuminate\Http\Request;

class BookController extends Controller
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
        $library_id = request('library_id');
        return view('books.create', compact('library_id'));
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $book = new Book;
        $book->title = request('title');
        $book->author = request('author');
        $book->library_id = request('library_id');
        Library::findOrFail($book->library_id);
        $book->save();
        return redirect('/libraries');
    }

    /**
     * Display the specified resource.
     */
    public function show(Book $book)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Book $book)
    {
        return view('books.edit', compact('book'));
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, Book $book)
    {
        $book->title = request('title');
        $book->author = request('author');
        $book->save();
        return redirect('/libraries');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Book $book)
    {
        $book->delete();
        return redirect('/libraries');
    }
}
