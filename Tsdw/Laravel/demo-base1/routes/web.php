<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ProjectController;
use App\Http\Controllers\TaskController;

Route::view('/', 'home');

Route::resource('/projects', ProjectController::class);
Route::post('/projects/help_show', [ProjectController::class, 'help_show']);
Route::delete('/projects', [ProjectController::class, 'destroyAll']);

Route::resource('/tasks', TaskController::class);
Route::post('/tasks/create', [TaskController::class, 'help_create']);
Route::post('/tasks/help_show', [TaskController::class, 'help_show']);
Route::delete('/tasks', [TaskController::class, 'destroyAll']);

Route::get('/README.md', function () {
// $contents = Illuminate\Mail\Markdown::parse(file_get_contents(base_path() . '/README.md'));   
// return $contents;
   return response()->file(base_path() . '/README.md');
});
