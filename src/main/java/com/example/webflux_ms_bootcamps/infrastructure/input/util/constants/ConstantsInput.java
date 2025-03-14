package com.example.webflux_ms_bootcamps.infrastructure.input.util.constants;

public class ConstantsInput {
    // Paths
    public static final String PATH_BOOTCAMPS = "/bootcamps";

    // Métodos
    public static final String METHOD_CREATE = "createBootcamp";
    public static final String METHOD_GET = "getBootcamps";

    // Operaciones
    public static final String OP_CREATE_BOOTCAMP = "createBootcamp";
    public static final String OP_GET_BOOTCAMPS = "getBootcamps";

    // Resumen y descripciones
    public static final String SUMMARY_CREATE_BOOTCAMP = "Crear un bootcamp";
    public static final String DESC_CREATE_BOOTCAMP = "Registra un nuevo bootcamp en el sistema con nombre, descripción y hasta 4 capacidades";
    public static final String SUMMARY_GET_BOOTCAMPS = "Obtener bootcamps";
    public static final String DESC_GET_BOOTCAMPS = "Lista todos los bootcamps disponibles con paginación y ordenamiento";

    // Parámetros de consulta
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_ASC = "asc";
    public static final String PARAM_SORT_BY = "sortBy";

    public static final String DESC_PAGE = "Número de la página (por defecto 0)";
    public static final String DESC_SIZE = "Cantidad de bootcamps por página (por defecto 10)";
    public static final String DESC_ASC = "Orden ascendente (true) o descendente (false)";
    public static final String DESC_SORT_BY = "Campo por el cual ordenar (ejemplo: 'name' o 'capabilityCount')";

    public static final String EXAMPLE_PAGE = "0";
    public static final String EXAMPLE_SIZE = "10";
    public static final String EXAMPLE_ASC = "true";
    public static final String EXAMPLE_SORT_BY = "name";

    // Códigos de respuesta
    public static final String CODE_200 = "200";
    public static final String CODE_201 = "201";
    public static final String CODE_400 = "400";
    public static final String CODE_409 = "409";
    public static final String CODE_500 = "500";

    // Mensajes de respuesta
    public static final String RESP_BOOTCAMP_LIST = "Lista de bootcamps disponibles";
    public static final String RESP_BOOTCAMP_CREATED = "Bootcamp creado exitosamente";
    public static final String RESP_ERROR_VALIDATION = "Error en la validación de datos (campo vacío, más de 4 capacidades, etc.)";
    public static final String RESP_ERROR_EXISTS = "El bootcamp ya existe";
    public static final String RESP_ERROR_SERVER = "Error interno del servidor";
    public static final String RESP_BAD_REQUEST = "Solicitud incorrecta debido a parámetros inválidos";

    // Ejemplos JSON
    public static final String EXAMPLE_NAME_CREATE = "Ejemplo de creación de bootcamp";
    public static final String EXAMPLE_BOOTCAMP_CREATE = "{ \"name\": \"Java Bootcamp\", \"description\": \"Bootcamp intensivo de Java\", \"capabilities\": [1, 2, 3] }";
    public static final String EXAMPLE_ERROR_VALIDATION = "{ \"error\": \"name: es requerido, capabilities: no puede superar 4 elementos\" }";
    public static final String EXAMPLE_ERROR_EXISTS = "{ \"error\": \"El bootcamp ya existe\" }";

    public static final String ERROR = "error";
}
