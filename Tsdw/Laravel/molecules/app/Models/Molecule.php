<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Molecule extends Model
{
    use HasFactory;

    public function athoms(){
        return $this->hasMany(Athom::class);
    }
}
