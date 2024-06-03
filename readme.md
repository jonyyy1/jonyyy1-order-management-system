# Order Management System 🚀✨

Este proyecto es una aplicación de gestión de pedidos para un sistema de comercio electrónico, desarrollada como parte de un postulacion. Permite crear, leer, actualizar y eliminar pedidos, así como listar todos los pedidos y obtener detalles de un pedido específico. Los productos y clientes se almacenan en bases de datos relacionales (PostgreSQL), mientras que los pedidos se almacenan en una base de datos no relacional (MongoDB).

## Integrante 👥👨‍💻

| Nombre                     | Email                        | Usuario de GitHub    |
|----------------------------|------------------------------|----------------------|
| Jonathan Quilca Valenzuela | jqvalenzuela10@gmail.com.com | [jonyyy1](https://github.com/jonyyy1) |

## Descripción del Proyecto 📚📄

La aplicación permite:
- Crear, leer, actualizar y eliminar pedidos.
- Listar todos los pedidos.
- Obtener detalles de un pedido específico.

Un pedido tiene un cliente, una lista de productos y un total. Los productos y clientes se almacenan en PostgreSQL, mientras que los pedidos se almacenan en MongoDB. Las operaciones de creación y actualización de pedidos son transaccionales para asegurar la consistencia entre ambas bases de datos.

## Requisitos Técnicos 💻🔧

- **Base de Datos Relacional (PostgreSQL)**
  - Modelos: Cliente, Producto.
- **Base de Datos No Relacional (MongoDB)**
  - Modelo: Pedido.
- **Transacciones**
  - Las operaciones de creación y actualización de pedidos aseguran consistencia entre ambas bases de datos.
- **Patrones de Diseño**
- **Implementación Reactiva**
  - Usando Spring WebFlux.
- **Buenas Prácticas**
  - Manejo adecuado de errores.
  - Validación de entradas de usuario.
  - Tests unitarios.
- **Docker**
  - Contenedores para la aplicación y bases de datos.

## Instalación 💻🔧

1. Clonar el repositorio:
    ```sh
    git clone https://github.com/tu_usuario/order-management-system.git
    ```
2. Navegar al directorio del proyecto:
    ```sh
    cd order-management-system
    ```
3. En caso no tengas instalado gradle 
        
     ```sh
    brew install gradle
    ```
4. Una ves instalado gradle ejecutar el siguiente comando
    ```sh
      gradle wrapper
    ```
5. Construir el proyecto con Gradle (sin ejecutar pruebas):
    ```sh
      ./gradlew build -x test
    ```
   
5. Ejecutar los contenedores Docker con Docker Compose:
    ```sh
    docker-compose up --build
    ```

## Endpoints de la API 🌐

### Crear un pedido

- **URL**: `POST /api/order`
  - **Request Body**:
  ```json
  {
      "customer": {
          "id": 1
      },
      "itemOrders": [
          {
              "product": {
                  "id": 1
                
              },
              "quantity": 2
          },
          {
              "product": {
                  "id": 2
              },
              "quantity": 1
          }
      ],
      "estado": "estado 1"
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**:
        ```json
        {
            "_id": "60d9f9f4e0e4c8b4d1e7f8b3",
            "cliente": {
                "id": 1,
                "nombre": "Juan Perez",
                "direccion": "Av. Siempre Viva 123",
                "email": "juan.perez@example.com"
            },
            "itemPedidos": [
                {
                    "producto": {
                        "id": 1,
                        "nombre": "Producto A",
                        "precio": 10.99,
                        "stock": 98
                    },
                    "cantidad": 2
                },
                {
                    "producto": {
                        "id": 102,
                        "nombre": "Producto B",
                        "precio": 5.49,
                        "stock": 47
                    },
                    "cantidad": 3
                }
            ],
            "fechaCreacion": "2024-06-01T10:00:00.000Z",
            "fechaActualizacion": "2024-06-01T10:00:00.000Z",
            "fechaEntrega": "2024-06-15",
            "estado": "estado 1"
        }
        ```

### Actualizar un pedido

- **URL**: `PUT /api/order/{id}`
  - **Request Body**:
      ```json
      {
          "customer": {
              "id": 1
          },
          "itemOrders": [
              {
                  "product": {
                      "id": 1
                  },
                  "quantity": 1
              },
              {
                  "product": {
                      "id": 2
                  },
                  "quantity": 1
              }
          ],
          "state": "estado 2"
    }
    ```
- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "_id": "60d9f9f4e0e4c8b4d1e7f8b3",
            "cliente": {
                "id": 1,
                "nombre": "Juan Perez",
                "direccion": "Av. Siempre Viva 123",
                "email": "juan.perez@example.com"
            },
            "itemPedidos": [
                {
                    "producto": {
                        "id": 101,
                        "nombre": "Producto A",
                        "precio": 10.99,
                        "stock": 93
                    },
                    "cantidad": 5
                },
                {
                    "producto": {
                        "id": 103,
                        "nombre": "Producto C",
                        "precio": 7.99,
                        "stock": 19
                    },
                    "cantidad": 1
                }
            ],
            "fechaCreacion": "2024-06-01T10:00:00.000Z",
            "fechaActualizacion": "2024-06-01T12:00:00.000Z",
            "fechaEntrega": "2024-06-20",
            "estado": false
        }
        ```

### Eliminar un pedido

- **URL**: `DELETE /orders/{id}`
- **Response**:
    - **Status**: `204 No Content`

### Obtener un pedido por ID

- **URL**: `GET /orders/{id}`
- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        {
            "_id": "60d9f9f4e0e4c8b4d1e7f8b3",
            "cliente": {
                "id": 1,
                "nombre": "Juan Perez",
                "direccion": "Av. Siempre Viva 123",
                "email": "juan.perez@example.com"
            },
            "itemPedidos": [
                {
                    "producto": {
                        "id": 101,
                        "nombre": "Producto A",
                        "precio": 10.99,
                        "stock": 93
                    },
                    "cantidad": 5
                },
                {
                    "producto": {
                        "id": 103,
                        "nombre": "Producto C",
                        "precio": 7.99,
                        "stock": 19
                    },
                    "cantidad": 1
                }
            ],
            "fechaCreacion": "2024-06-01T10:00:00.000Z",
            "fechaActualizacion": "2024-06-01T12:00:00.000Z",
            "fechaEntrega": "2024-06-20",
            "estado": false
        }
        ```

### Listar todos los pedidos

- **URL**: `GET /orders`
- **Response**:
    - **Status**: `200 OK`
    - **Body**:
        ```json
        [
            {
                "_id": "60d9f9f4e0e4c8b4d1e7f8b3",
                "cliente": {
                    "id": 1,
                    "nombre": "Juan Perez",
                    "direccion": "Av. Siempre Viva 123",
                    "email": "juan.perez@example.com"
                },
                "itemPedidos": [
                    {
                        "producto": {
                            "id": 101,
                            "nombre": "Producto A",
                            "precio": 10.99,
                            "stock": 93
                        },
                        "cantidad": 5
                    },
                    {
                        "producto": {
                            "id": 103,
                            "nombre": "Producto C",
                            "precio": 7.99,
                            "stock": 19
                        },
                        "cantidad": 1
                    }
                ],
                "fechaCreacion": "2024-06-01T10:00:00.000Z",
                "fechaActualizacion": "2024-06-01T12:00:00.000Z",
                "fechaEntrega": "2024-06-20",
                "estado": false
            },
            {
                "_id": "60d9fa2ae0e4c8b4d1e7f8b4",
                "cliente": {
                    "id": 2,
                    "nombre": "Maria Gomez",
                    "direccion": "Calle Falsa 456",
                    "email": "maria.gomez@example.com"
                },
                "itemPedidos": [
                    {
                        "producto": {
                            "id": 104,
                            "nombre": "Producto D",
                            "precio": 12.99,
                            "stock": 30
                        },
                        "cantidad": 2
                    }
                ],
                "fechaCreacion": "2024-06-01T11:00:00.000Z",
                "fechaActualizacion": "2024-06-01T11:00:00.000Z",
                "fechaEntrega": "2024-06-15",
                "estado": true
            }
        ]
        ```
## Estructura del Proyecto 📂

```
    order-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/keola/ordermanagement/
│   │   │       ├── controller/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   ├── resources/
│   │   │   └── db/
│   │   │       └── migration/
│   │   └── application.properties
│   ├── test/
│   └── Dockerfile
│   └── docker-compose.yml
│   └── README.md
```


## Dataset 📊📂

Los datos de productos y clientes se almacenan en PostgreSQL, y los datos de pedidos en MongoDB. Asegúrate de que las bases de datos estén configuradas y en funcionamiento.

## License 📝📜

Este proyecto está licenciado bajo el archivo [LICENSE](LICENSE).

¡Feliz codificación! ✨👩‍💻👨‍💻


Este README.md incluye ejemplos detallados de solicitudes y respuestas para cada uno de los endpoints de la API, lo que debería facilitar la comprensión de cómo interactuar con la API. Asegúrate de ajustar los ejemplos y detalles específicos según tu implementación y estructura del proyecto.
