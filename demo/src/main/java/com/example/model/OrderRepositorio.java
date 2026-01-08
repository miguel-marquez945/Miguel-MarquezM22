


package com.example.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OrderRepositorio {

    private static final String NOMBRE_RECURSO = "orders.json";

    // RUTA FIJA DENTRO DE demo/src/main/java/com/example/data
    private static final Path RUTA_FIJA =
            Paths.get("demo", "src", "main", "java", "com", "example", "data", "orders.json");

    private Path rutaFichero;
    private ObjectMapper mapper;

    public OrderRepositorio() {
        this.rutaFichero = RUTA_FIJA;
        this.mapper = new ObjectMapper();

        // Para que no falle si el JSON tiene campos extra
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private void asegurarFicheroExiste() throws IOException {
        if (Files.exists(rutaFichero)) {
            return;
        }

        // Crear carpeta data si no existe
        if (!Files.exists(rutaFichero.getParent())) {
            Files.createDirectories(rutaFichero.getParent());
        }

        // Copiar el JSON inicial desde resources
        InputStream entrada = OrderRepositorio.class
                .getClassLoader()
                .getResourceAsStream(NOMBRE_RECURSO);

        if (entrada == null) {
            throw new IOException("No se ha encontrado '" + NOMBRE_RECURSO + "' en resources.");
        }

        Files.copy(entrada, rutaFichero);
        entrada.close();
    }

    public List<Order> cargarPedidos() throws IOException {
        asegurarFicheroExiste();
        return mapper.readValue(
                rutaFichero.toFile(),
                mapper.getTypeFactory().constructCollectionType(List.class, Order.class)
        );
    }

    public void guardarPedidos(List<Order> pedidos) throws IOException {
        asegurarFicheroExiste();
        mapper.writerWithDefaultPrettyPrinter().writeValue(rutaFichero.toFile(), pedidos);
    }

    public String obtenerRutaAbsoluta() {
        return rutaFichero.toAbsolutePath().toString();
    }
}
   

