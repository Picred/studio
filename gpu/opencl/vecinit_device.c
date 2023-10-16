// vecinit della parte device, è opencl e l'estensione dovrebbe essere .cl
// device hanno memoria globale (per i buffer, come se fosse heap, disponibile a tutti i kernel del prog in esec.), memoria costante (piccola di sola lettura dai kernel, ottimizzabile per broadcasting) e quella locale che esiste durante l'esecuzione per lo scambio di dati fra work item
//  
void kernel vecinit(global int* array){
    int i= get_global_id(0); // 0 significa = indice della dimensione (ci possono essere griglie di lancio multidimensionale) della griglia.
    array[i] = i;
}
