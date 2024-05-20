import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class ProcesamientoCriptografico {

    public static void main(String[] args) {
        String texto = "A Silvia\n"
                + "Bien puede el mundo entero conjurarse\n"
                + "contra mi dulce amor y mi ternura,\n"
                + "y el odio infame y tiranía dura\n"
                + "de todo su rigor contra mí armarse.\n"
                + "Bien puede el tiempo rápido cebarse\n"
                + "en la gracia y primor de su hermosura,\n"
                + "para que cual si fuese llama impura\n"
                + "pueda el fuego de amor en mí acabarse.\n"
                + "Bien puede en fin la suerte vacilante,\n"
                + "que eleva, abate, ensalza y atropella,\n"
                + "alzarme o abatirme en un instante;\n"
                + "Que el mundo, al tiempo y a mi varia estrella,\n"
                + "más fino cada vez y más constante,\n"
                + "les diré: «Silvia es mía y yo soy de ella».";

        texto = sustituirCaracteres(texto);
        //System.out.println("Texto después de sustituciones:\n" + texto);
        texto = eliminarTildes(texto);
        //System.out.println("Texto después de eliminar tildes:\n" + texto);
        texto = convertirMayusculas(texto);
        //System.out.println("Texto después de convertir a mayúsculas:\n" + texto);
        texto = eliminarEspaciosPuntuacion(texto);
        //System.out.println("Texto después de eliminar espacios y puntuación:\n" + texto);
        alfabeto_longitud(texto);
        //guardarEnArchivo("PRACTICA1_PRE.TXT", texto);
        
        //String textoUnicode = preprocesarUnicode(texto);
        //System.out.println("Texto preprocesado según UNICODE-8:\n" + textoUnicode);
        
        String textoAlfabeto = preprocesarConAlfabetoYEPIS(texto, "EPIS");
        System.out.println("Texto preprocesado con EPIS y padding:\n" + textoAlfabeto);
        //Map<Character, Integer> frecuencias = calcularFrecuencias("PRACTICA1_PRE.TXT");
        //System.out.println("Frecuencias de letras:\n" + frecuencias);
        //Map<String, Integer> trigramasDistancias = encontrarTrigramas(texto);
        //System.out.println("Trigramas y distancias:\n" + trigramasDistancias);

    }

    public static String sustituirCaracteres(String texto) {
        return texto.replaceAll("a", "u").replaceAll("h", "t").replaceAll("ñ", "e")
                .replaceAll("k", "l").replaceAll("v", "f").replaceAll("w", "b")
                .replaceAll("z", "y").replaceAll("r", "p")
                .replaceAll("A", "U").replaceAll("H", "T").replaceAll("Ñ", "E")
                .replaceAll("K", "L").replaceAll("V", "F").replaceAll("W", "B")
                .replaceAll("Z", "Y").replaceAll("R", "P");
    }

    public static String eliminarTildes(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    public static String convertirMayusculas(String texto) {
        return texto.toUpperCase();
    }

    public static String eliminarEspaciosPuntuacion(String texto) {
        return texto.replaceAll("\\s+|\\p{Punct}", "");
    }

    public static void alfabeto_longitud(String texto) {
        ArrayList<Character> alfabeto = new ArrayList<>();
        for (char c : texto.toCharArray()) {
            if (!alfabeto.contains(c)) {
                alfabeto.add(c);
            }
        }
        System.out.println("Alfabeto resultante: " + alfabeto);
        System.out.println("Longitud del alfabeto: " + alfabeto.size());

    }

    public static void guardarEnArchivo(String nombreArchivo, String texto) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Character, Integer> calcularFrecuencias(String nombreArchivo) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            int c;
            while ((c = reader.read()) != -1) {
                char caracter = (char) c;
                frecuencias.put(caracter, frecuencias.getOrDefault(caracter, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frecuencias;
    }

    public static Map<String, Integer> encontrarTrigramas(String texto) {
        Map<String, Integer> trigramas = new HashMap<>();
        for (int i = 0; i < texto.length() - 2; i++) {
            String trigrama = texto.substring(i, i + 3);
            trigramas.put(trigrama, trigramas.getOrDefault(trigrama, 0) + 1);
        }
        return trigramas;
    }

    public static String preprocesarUnicode(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            resultado.append(String.format("%04X", (int) c));
        }
        return resultado.toString();
    }

     public static String preprocesarConAlfabetoYEPIS(String texto, String cadena) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            resultado.append(String.format("%02d", (int) c));
        }
        String textoModificado = resultado.toString();
        StringBuilder conCadena = new StringBuilder();
        for (int i = 0; i < textoModificado.length(); i += 15) {
            conCadena.append(textoModificado, i, Math.min(i + 15, textoModificado.length())).append(cadena);
        }
        while (conCadena.length() % 7 != 0) {
            conCadena.append('Z');
        }
        return conCadena.toString();
    }
}
