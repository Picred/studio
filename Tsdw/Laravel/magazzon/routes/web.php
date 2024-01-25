<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\MagazzonController;


Route::resource('/magazzon', MagazzonController::class);

Route::get('/', function(){
    return view('homepage');
});