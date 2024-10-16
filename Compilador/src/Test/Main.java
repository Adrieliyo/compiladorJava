package Test;

public class Main {
    public static void main(String[] args) {
        String rutaCarpeta = "D:\\CompiladorJava\\Compilador\\src\\Test";
        String archivoEntrada = "Tilin.txt";
        String archivoSalida = "Tilin_Procesado.txt";
        Preprocesador.preprocesarArchivo(rutaCarpeta, archivoEntrada, archivoSalida);
        System.out.println("Archivo procesado y guardado correctamente.");
        System.out.println("Contenido del archivo procesado:");
        Preprocesador.recorrerArchivo(rutaCarpeta, archivoSalida);
    }
}
