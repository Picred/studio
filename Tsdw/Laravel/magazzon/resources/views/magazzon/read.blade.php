@extends('layout')
@section('content')

<h1>Prodotti con giacenza > 0 </h1>

<ul>
    @foreach ($products as $product)
        @if ($product->giacenza > 0)
            <li>
                Nome: {{$product->nome_prodotto}}, Giacenza: {{$product->giacenza}}, Prezzo: {{$product->prezzo}}
                <br>
                <form action="/magazzon/{{$product->id}}/edit" method="get">
                    <input type="submit" value="Compra una quantita'">
                </form>
                <form action="/magazzon/{{$product->id}}" method="post">
                    @csrf
                    @method('DELETE')
                    <input type="submit" value="Elimina prodotto">
                </form>

                <form action="/magazzon/{{$product->id}}" method="post">
                    @csrf
                    @method('PATCH')
                    Compra n° prodotti: <input type="number" id="quantita" name="quantita">
                    <input type="submit" value="Compra quantita selezionata">
                </form>
            </li>
        @endif
    @endforeach
</ul>

