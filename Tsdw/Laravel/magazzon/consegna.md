data la tabella mySql magazzino con i seguenti campi:
```bash
id (chiave primaria, autoincrement)
nome_prodotto (stringa)
giacenza (intero)
prezzo (decimale)
```

Creare una o più pagine in php/html che implementino le funzioni CRUD.

#### CR (obbligatorio)
1.1) inserimento di un nuovo prodotto

1.2) stampa dell'elenco dei prodotti con giacenza > 0

#### UD (facoltativo)
2.1) aggiungere alla lista (punto 1.2) un link o un bottone "compra" che permette di acquistare il prodotto. Una volta acquistato, la giacenza del prodotto verrà decrementata.

2.2) aggiungere alla lista (punto 1.2) un link o un bottone "elimina" che permette di cancellare l'intero record

>Bonus Aggiungere un "compra tot quantità" e l'acquisto viene effettuato se la giacenza è disponibile.

```sql
CREATE DATABASE magazzino;
```



```sql
USE magazzino;
```


```sql
CREATE TABLE prodotti
(
    id INT(6) UNSIGNED AUTO_INCREMENT,
    nome_prodotto VARCHAR(30) NOT NULL,
    giacenza SMALLINT NOT NULL,
    prezzo FLOAT NOT NULL,
    primary key (id)
);
```

