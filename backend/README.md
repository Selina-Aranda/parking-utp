# Backend Java para Parking UTP

## Requisitos
- Java 17+
- MySQL Server
- Maven

## Configuración
1. Crear la base de datos con el script que compartiste.
2. Configurar estas variables de entorno antes de ejecutar:
   - DB_URL: jdbc:mysql://localhost:3306/parkingdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   - DB_USER: root
   - DB_PASSWORD: tu_contraseña

## Ejecutar
```bash
cd backend
mvn compile exec:java -Dexec.mainClass=com.parking.backend.Application
```

## Estructura
- model: clases Java para cada tabla
- dao: acceso a datos con JDBC
- config: conexión a MySQL
