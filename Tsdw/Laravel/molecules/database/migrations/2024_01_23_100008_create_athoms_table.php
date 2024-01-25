<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('athoms', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->integer('ath_num');
            $table->timestamps();
        });

        Schema::table('athoms', function (Blueprint $table) {
            $table->foreignId('molecule_id')->constrained('molecules')->onDelete('cascade')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('athoms');
    }
};
