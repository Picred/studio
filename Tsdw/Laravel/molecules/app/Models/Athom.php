<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Athom extends Model
{
    use HasFactory;

    public function molecule(){
        return $this->belongsTo(Molecule::class);
    }
}
