package Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Preprocesador {

    public static void recorrerArchivo(String rutaCarpeta, String archivoProcesado) {
        File carpeta = new File(rutaCarpeta);
        File archivo = new File(carpeta, archivoProcesado);
        if (!archivo.exists()) {
            System.err.println("Error: El archivo " + archivoProcesado + " no existe en la carpeta " + rutaCarpeta + ".");
        } else {
            FileReader lector = null;

            try {
                lector = new FileReader(archivo);

                int caracter;
                while((caracter = lector.read()) != -1) {
                    char ch = (char)caracter;
                    System.out.print(ch);
                }
            } catch (IOException var15) {
                IOException e = var15;
                e.printStackTrace();
            } finally {
                try {
                    if (lector != null) {
                        lector.close();
                    }
                } catch (IOException var14) {
                    IOException e = var14;
                    e.printStackTrace();
                }

            }

        }
    }

    public static void preprocesarArchivo(String rutaCarpeta, String archivoEntrada, String archivoSalida) {
        File carpeta = new File(rutaCarpeta);
        File archivo = new File(carpeta, archivoEntrada);
        if (!archivo.exists()) {
            System.err.println("Error: El archivo " + archivoEntrada + " no existe en la carpeta " + rutaCarpeta + ".");
        } else {
            File archivoProcesado = new File(carpeta, archivoSalida);
            FileReader lector = null;
            FileWriter escritor = null;

            try {
                lector = new FileReader(archivo);
                escritor = new FileWriter(archivoProcesado);
                boolean enComentario = false;
                StringBuilder linea = new StringBuilder();

                while (true) {
                    int caracter;
                    while ((caracter = lector.read()) != -1) {
                        char ch = (char) caracter;
                        if (ch == '/') {
                            if ((caracter = lector.read()) == 47) {
                                enComentario = true;
                                continue;
                            }

                            linea.append('/');
                            linea.append((char) caracter);
                        }

                        if (enComentario) {
                            if (ch == '\n') {
                                enComentario = false;
                                procesarLinea(linea.toString(), escritor);
                                linea.setLength(0);
                            }
                        } else if (ch == '\n') {
                            if (linea.length() > 0) {
                                procesarLinea(linea.toString(), escritor);
                                linea.setLength(0);
                            }
                        } else if (ch != '\t' && ch != ' ') {
                            linea.append(ch);
                        }
                    }

                    if (linea.length() > 0) {
                        procesarLinea(linea.toString(), escritor);
                    }
                    break;
                }
            } catch (IOException var20) {
                IOException e = var20;
                e.printStackTrace();
            } finally {
                try {
                    if (lector != null) {
                        lector.close();
                    }

                    if (escritor != null) {
                        escritor.close();
                    }
                } catch (IOException var19) {
                    IOException e = var19;
                    e.printStackTrace();
                }

            }

        }
    }

    private static void procesarLinea(String linea, FileWriter escritor) throws IOException {
        linea = eliminarEspacios(linea);
        if (!linea.isEmpty()) {
            escritor.write(linea);
            escritor.write(10);
        }

    }

    private static String eliminarEspacios(String linea) {
        int start = 0;

        int end;
        for(end = linea.length() - 1; start <= end && Character.isWhitespace(linea.charAt(start)); ++start) {
        }

        while(end >= start && Character.isWhitespace(linea.charAt(end))) {
            --end;
        }

        return start > end ? "" : linea.substring(start, end + 1);
    }

}
