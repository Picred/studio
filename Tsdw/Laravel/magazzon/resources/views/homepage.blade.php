@extends('layout')
@section('content')

@endsection

<!DOCTYPE HTML>
<html>
    <body>
        <h1>Benvenuto</h1>
        <h3>Clicca uno dei tasti sottostanti</h3>
        <ul>
            <li>
                <form action="/magazzon/create" method="get">
                    <input type="submit" value="Inserisci un nuovo prodotto">
                </form>
            </li>
            <br>
            <li>
                <form action="/magazzon" method="get">
                    <input type="submit" value="Vedi prodotti con giacenza > 0 ">
                </form>
            </li>
            
        </ul>
    </body>
</html>