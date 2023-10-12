# Esercizio sulla distribuzione binomiale
from scipy.special import binom

p = 0.05
q = 1.-p

# dispaly(p) su jupter serve per mostrare il risultato di p dopo aver "eseguito" con shift+enter

# punto 1

p_es1 = binom(4,1)*p*q**3; # q**3 == q^3 oppure pow(q,3)
#display(p_es1)

# punto 2
p0 = binom(4,0)*q**4;
p1 = binom(4,1)*p*q**3;
p2 = binom(4,2)*p**2.*q**2
p_es2 = p0+p1+p2
# display(p_es2)

media = 40*p
# display(media)