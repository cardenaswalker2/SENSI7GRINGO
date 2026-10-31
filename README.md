# Sensi7gringo 🎮🔥

Plataforma web de generación inteligente y calibración milimétrica de sensibilidades para **Free Fire**, desarrollada con **Spring Boot 3, Thymeleaf, MongoDB, CSS3 Vanilla Premium y JavaScript Vanilla**.

Inspirada en la estética y estructura de *Sabor Fanta Sensi*, elevada a una identidad deportiva moderna con paleta de **Negro Carbón, Blanco puro y Naranja Neón**.

---

## 🚀 Características Principales

1. **Generador Maestro de Sensibilidad**:
   - Selector interactivo de Sistema Operativo (**Android / iOS**).
   - Filtrado dinámico de marcas (**Samsung, Xiaomi, Apple, Motorola, Realme, Huawei, Infinix, Tecno**).
   - Selección de modelo exacto con tasa de refresco (Hz) y resolución física.
   - Nivel de experiencia (*Iniciado, Intermedio, Pro*).
   - Tipo de sensibilidad (*Baja, Media, Alta*).
   - Configuración con DPI o sin DPI.
   - Estilo de enfrentamiento (*Precisión, Velocidad/Rusheo, Equilibrio*).
   - Algoritmo de calibración matemática que calcula: General, Punto Rojo, Mira 2X, Mira 4X, Francotirador (AWM), Cámara 360 / Olhadinha, Botón de Disparo (%) y DPI sugerido.
2. **Performance & Diagnóstico Real**:
   - Diagnóstico del navegador y pantalla en tiempo real mediante consola interactiva.
   - Guía técnica y recomendaciones oficiales para optimizar 90Hz/120Hz y respuesta táctil.
3. **Aim & Calibración**:
   - Técnicas de arrastre de mira (arrastre en "J", levantamiento progresivo).
   - Calibración de botones y recomendaciones de armas.
4. **Pro Players Verificados**:
   - Configuraciones documentadas de figuras competitivas (*Nobru, Fantasma, Blackn444, Two9, Bak, Apelapato*).
   - Botón directo para adaptar la configuración de un pro player al dispositivo del usuario.
5. **Sensi Especial VIP**:
   - Perfil optimizado para armas de un tiro (Desert Eagle, M1887, AC80).
6. **Ranking de la Comunidad & Historial Persistente**:
   - Historial de sensibilidades generadas por sesión con opción de consultar, copiar y eliminar.
   - Ranking de mejores configuraciones.
7. **Alta Disponibilidad y Resiliencia**:
   - Soporta MongoDB nativo y cuenta con **InMemoryDataStore** de fallback automático: arranca y funciona al 100% incluso si la base de datos no está levantada.

---

## 🛠️ Requisitos del Sistema

- **Java**: OpenJDK 17 o superior.
- **Maven**: 3.8+ (incluido en el sistema).
- **MongoDB**: (Opcional) Local en `mongodb://localhost:27017/sensi7gringo` o cluster en MongoDB Atlas.

---

## 💻 Ejecución del Proyecto

### 1. Desde la Terminal

```bash
# Compilar y ejecutar pruebas
mvn clean test

# Iniciar la aplicación
mvn spring-boot:run
```

Una vez iniciada, abre tu navegador en:
👉 `http://localhost:8080/`

### 2. Ejecución desde Visual Studio Code o IntelliJ IDEA

- **IntelliJ IDEA**: Abre la carpeta `Generadorsensi`, espera que Maven sincronice las dependencias y ejecuta la clase principal `com.sensi7gringo.Sensi7gringoApplication`.
- **VS Code**: Con la extensión *Extension Pack for Java* o *Spring Boot Dashboard*, ejecuta o depura la aplicación con un solo clic.

---

## 📡 Endpoints de la API REST

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/dispositivos/marcas?sistemaOperativo={so}` | Obtiene marcas filtradas por Android o iOS |
| `GET` | `/api/dispositivos/modelos/{marca}` | Obtiene los modelos exactos de una marca |
| `GET` | `/api/dispositivos/buscar?q={query}` | Búsqueda rápida de dispositivos |
| `POST` | `/api/sensibilidades/generar` | Procesa y calcula la sensibilidad para el dispositivo |
| `POST` | `/api/sensibilidades/guardar` | Persiste una configuración |
| `GET` | `/api/sensibilidades/historial?sessionId={sid}` | Lista el historial de la sesión |
| `GET` | `/api/sensibilidades/ranking` | Lista el ranking de la comunidad |
| `GET` | `/api/sensibilidades/{id}` | Consulta una sensibilidad específica |
| `DELETE` | `/api/sensibilidades/{id}` | Elimina una sensibilidad |
| `POST` | `/api/sensibilidades/{id}/favorito` | Alterna el estado de favorito |

---

## 📁 Estructura del Código

```
src/
├── main/
│   ├── java/com/sensi7gringo/
│   │   ├── Sensi7gringoApplication.java
│   │   ├── config/              # Configuración resiliente de MongoDB
│   │   ├── controllers/         # WebViewController (Thymeleaf) y REST Controllers
│   │   ├── dto/                 # Request & ApiResponse wrappers con validación
│   │   ├── models/              # Dispositivo, Sensibilidad, ProPlayer
│   │   ├── repositories/        # Repositorios Mongo y Fallback InMemoryDataStore
│   │   ├── services/            # Algoritmo de calibración y lógica de negocio
│   │   └── utils/
│   └── resources/
│       ├── application.properties
│       ├── static/
│       │   ├── css/main.css     # Estilos CSS3 puros con paleta neón/dark
│       │   └── js/main.js       # Interactividad, API fetch, consola en vivo
│       └── templates/
│           └── index.html       # Vista Thymeleaf con todos los módulos
└── test/                        # Pruebas unitarias e integración (100% pasando)
```

---

© 2026 Sensi7gringo.
