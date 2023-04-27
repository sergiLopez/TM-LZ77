# Algorisme LZ77 

Autors: Jordi Bujaldón y Sergio Lopez
## Com hem implementat l'algorisme?

Per implementar l'algorisme, el que hem fet ha sigut agafar tants bits com ens marca Mdest i Ment. Aquestes dos variables de tipus enter, ens permetran crear dos substrings per anar fent comparacions entre ells. Si el String petit, que es el que ens marca la variable Ment, es troba dins del String gran, marcat per la variable Mdest, crearem una tupla que contindra la longitud i la posició. Si no es troba el string petit en el gran, el que farem serà reduir un bit de l'String petit i tornar a fer la comparació, reduint el String si no es troba una coincidencia, on finalment quedarà com a (1,1) que significarà que el string s'ha reduit fins a un bit. Aquestes tuples les anirem guardant i les passarem a binari. Amb això el que tindrem serà el nostre missatge codificat.

Per a descodificar, el que farem serà agafar les tuples una a una, ens mourem les posicions que ens digui i agafarem els bits determinats per la longitud. Amb això el que haurem aconseguit serà decodificar el nostre missatge. 

## Mòduls/classes/llibreries utilitzats:

Per a la implementació d'aquest algorisme, no em necessitat cap mòdul ni llibreria, simplement hem creat una classe anomenada `Main.java`, que conté la major part de l'algorisme, i una classe `LDNode.java` que ens ha servit per guardar les tuples.

**IMPORTANT:** La versió de Java utilizada és la 1.8.

## Com s'executa?
Per ejecutar l'algorisme hi ha dues maneres:
- La primera que seria arrossegant els dos arxius, el Main.java i el LDNode.java en un IDE i executant el main, cal tenir en compte que nosaltres em utilitzat java 1.8
- La segona manera, seria compilant tots els arxius .java i executant el main: 
```bash
javac Main.java LDNode.java
java Main
```
## Apartats

#### Utilizando el programa anterior, investigad si es posible (ajustando los valores de Mdes y Ment) comprimir datos aleatorios mediante LZ77 (es decir, que la cadena de datos originales sea más larga que la cadena comprimida).¿Por qué?

Si que és possible que la cadena de dades originals sigui més llarga que la cadena comprimida, perque aquest algorisme, es eficaç quan hi ha molta redundancia, però si el que estem utilitzan són dades aleatories, aquest algorisme no funcionarà bé i com a conseqüencia tindrem un factor de compressió més baix que 1. Si que és cert que podem millorar això retocant els valors de ment i mdest, però com em dit anteriorment, el LZ7 s'aprofita bàsicament de la redundància de les dades. 

#### ¿Cuál es la máxima compresión que lográis? ¿Con qué valores?
Con Mdest=2048 y Ment=64, un factor de compresión de 1,34. Es un valor bajo, porque estamos cogiendo valores aleatorios. 

#### Representad gráficament como varia el factor de compresión al variar Mdes y Ment.

Malauradament, per certs valors de Ment i Mdest, el nostre algorisme no acaba de funcionar correctament. Després d'uns dies intentant arreglar-ho, em sigut incapaços d'arreglar el problema, així doncs, el que em fet es agafar els valors de compressió que tenim correctes, i fer una predicció de quin valor de compressió agafaria per valors de Ment i Mdest que no acaben de funcionar. 