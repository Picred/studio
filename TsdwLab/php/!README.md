# Directory di file di esempio PHP

## Per visualizzare i file in un browser

1. Aprire una shell in questa cartella (essa è la document root del server PHP e contiene anche php.ini)

1. Lanciare: `php -c . -S localhost:8888`

      NB: per default php usa il php.ini di sistema, per rendersene conto,
      lanciare `php --ini` e poi `php -c . --ini`

      NB: php.ini dovrebbe essere quello della versione di PHP utilizzata (copiarlo da .../etc/php/)

1. Puntare il browser su: <http://localhost:8888>
   [Potete usare un altro port al posto di 8888]

## Rigenerare l'indice degli esempi

* il comando `./\!noindx.sh` mostra se l'indice `!index.csv` è disallineato
      rispetto ai file sorgente .php e .html in questa directory

* Aggiornato eventualmente (a mano) `!index.csv`, si aggiorni di conseguenza `index.html`
  eseguendo da una shell: `./\!mkindx [-w]`

##  Andare ora all'indice degli esempi: http://localhost:8888/
