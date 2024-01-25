@extends('layout')
@section('content')

@endsection

<h1>Crea un nuovo prodotto</h1>

<form action="/magazzon" method="post">
    @csrf
    Nome prodotto: <input type="text" id="nome_prodotto" name="nome_prodotto" required> <br>
    Giacenza: <input type="number" id="giacenza" name="giacenza" required> <br>
    Prezzo: <input type="decimal" id="prezzo" name="prezzo" required> <br>
    <input type="submit" value="Crea prodotto">
</form>