# Demoblaze — Pruebas automatizadas con Serenity BDD + Screenplay + Cucumber

Pruebas end-to-end automatizadas para la tienda [Demoblaze](https://www.demoblaze.com/),
construidas con **Serenity BDD**, el **patrón Screenplay**, **Cucumber** (Gherkin) y **Maven**.

El test cubre el flujo completo de compra:
agregar dos productos al carrito → visualizar el carrito → completar el formulario de compra → confirmar la compra.

---

## Tabla de contenidos

1. [Prerrequisitos](#prerrequisitos)
2. [Clonar el repositorio](#clonar-el-repositorio)
3. [Estructura del proyecto](#estructura-del-proyecto)
4. [Cómo ejecutar las pruebas](#cómo-ejecutar-las-pruebas)
5. [Cómo funciona todo — paso a paso](#cómo-funciona-todo--paso-a-paso)

---

## Prerrequisitos

| Requisito     | Versión                    | Notas                                                          |
| ------------- | -------------------------- | -------------------------------------------------------------- |
| Java JDK      | 17 o superior              | Verificar con `java -version`                                  |
| Apache Maven  | 3.8 o superior             | Verificar con `mvn -version`                                   |
| Google Chrome | Cualquier versión reciente | El test abre una ventana real de Chrome                        |
| ChromeDriver  | **No es necesario**        | Serenity lo descarga automáticamente con `autodownload = true` |

> **ChromeDriver se gestiona automáticamente.**
> Serenity BDD incluye WebDriverManager, que detecta la versión de Chrome instalada
> y descarga el ChromeDriver correspondiente antes de que corra el primer test.
> No necesitas descargar ni configurar ningún driver de forma manual.

---

## Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/EjercicioSerenityBDD.git
cd EjercicioSerenityBDD
```

No se necesita ninguna configuración adicional. Maven descarga todas las dependencias en la primera ejecución.

---

## Estructura del proyecto

```
src/
└── test/
    ├── java/org/santi/
    │   ├── abilities/
    │   │   └── NavegarLaWeb.java          # Le da al Actor la habilidad de usar Chrome
    │   ├── ui/
    │   │   └── DemoblazePage.java         # Todos los localizadores CSS/XPath en un solo lugar
    │   ├── models/
    │   │   └── PurchaseData.java          # Objeto de datos para el formulario de compra
    │   ├── tasks/
    │   │   ├── OpenDemoblaze.java         # Abre la tienda y espera que los productos carguen
    │   │   ├── AddProductToCart.java      # Hace clic en un producto, lo agrega y regresa al Home
    │   │   ├── GoToCart.java              # Navega a la página del carrito
    │   │   ├── PlaceOrder.java            # Hace clic en Place Order y abre el modal del formulario
    │   │   └── FillPurchaseForm.java      # Llena los campos del formulario y hace clic en Purchase
    │   ├── questions/
    │   │   └── ConfirmationMessage.java   # Lee el texto de confirmación de la pantalla
    │   ├── stepdefinitions/
    │   │   └── DemoblazeSteps.java        # Glue entre los pasos Gherkin y Screenplay
    │   └── features/
    │       └── CucumberTestSuiteIT.java   # Runner de Cucumber — punto de entrada para Maven
    └── resources/
        ├── features/
        │   └── compra/
        │       └── purchase_demoblaze.feature  # El escenario de prueba en inglés natural
        ├── serenity.conf                        # Configuración del navegador y capturas de pantalla
        └── junit-platform.properties            # Configuración del plugin y glue de Cucumber
```

---

## Cómo ejecutar las pruebas

```bash
mvn clean verify
```

Cuando la ejecución termine, abre el reporte HTML en tu navegador:

```
target/site/serenity/index.html
```

---

## Cómo funciona todo — paso a paso

Esta sección traza el camino completo desde el momento en que escribes `mvn clean verify`
hasta que el navegador se cierra y el reporte queda generado.

### Paso 1 — Maven inicia el ciclo de vida

`mvn clean verify` dispara las siguientes fases de Maven en orden:

```
clean  →  compile  →  test-compile  →  integration-test  →  post-integration-test  →  verify
```

- **clean**: elimina la carpeta `target/` para que no queden resultados anteriores.
- **test-compile**: compila todos los archivos Java bajo `src/test/java/`.
- **integration-test**: el plugin `maven-failsafe-plugin` encuentra `CucumberTestSuiteIT.java`
  y lo lanza como punto de entrada de las pruebas.
- **post-integration-test**: el plugin `serenity-maven-plugin` lee todos los archivos JSON
  producidos por Serenity durante la ejecución y genera el reporte HTML.
- **verify**: Failsafe revisa si algún test falló y rompe el build en ese caso.

---

### Paso 2 — Cucumber descubre el archivo feature

`CucumberTestSuiteIT.java` le indica a Cucumber dónde encontrar todo:

```java

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")           // busca archivos .feature aquí
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
        value = "org.santi.stepdefinitions")       // busca los step definitions aquí
public class CucumberTestSuiteIT {
}
```

Cucumber lee `purchase_demoblaze.feature` y convierte cada escenario en una lista
de pasos que necesitan ser conectados a métodos Java.

---

### Paso 3 — El feature file: el escenario de negocio en lenguaje natural

```gherkin
Feature: Purchase products on Demoblaze

  Scenario: Add two products to the cart and complete the purchase
    Given Customer opens the Demoblaze store
    When Customer adds the product "Samsung galaxy s6" to the cart
    And Customer adds the product "Nokia lumia 1520" to the cart
    And Customer goes to the cart
    And Customer completes the order form with the following details:
      | name     | country  | city   | card             | month | year |
      | Santi QA | Colombia | Bogota | 1234567890123456 | June  | 2026 |
    Then Customer should see the message "Thank you for your purchase!"
```

Este archivo está escrito en **Gherkin** — una sintaxis de lenguaje natural estructurado.
No tiene Java, no tiene detalles técnicos. Un analista de negocio o product owner
puede leerlo y entender exactamente qué se está probando.

Cada línea que empieza con `Given`, `When`, `And` o `Then` es un **paso**
que Cucumber conectará con un método en los step definitions.

La tabla bajo `And Customer completes the order form` es un **DataTable** —
una funcionalidad de Cucumber que permite pasar datos estructurados directamente
desde el escenario al código Java sin hardcodear valores.

---

### Paso 4 — El glue: DemoblazeSteps conecta Gherkin con Screenplay

`DemoblazeSteps.java` es el puente entre el feature file y las Tasks de Screenplay.
Cada método está anotado con el texto exacto de un paso Gherkin:

```java

@Given("Customer opens the Demoblaze store")
public void customerOpensDemoblaze() {
    customer = Actor.named("Customer");
    customer.whoCan(NavegarLaWeb.conChrome());
    givenThat(customer).attemptsTo(OpenDemoblaze.inTheBrowser());
}
```

Este método:

1. Crea el **Actor** llamado "Customer"
2. Le otorga la **Ability** de navegar con Chrome
3. Le indica al Actor que **intente** realizar la Task `OpenDemoblaze`

El step definition no sabe _cómo_ se abre el navegador — solo sabe _que_
el Actor debe abrir la tienda. Todos los detalles técnicos viven dentro de la Task.

---

### Paso 5 — Las Tasks ejecutan las interacciones reales con el navegador

Cada **Task** encapsula un comportamiento cohesivo del usuario.
Cuando el step definition llama a `customer.attemptsTo(OpenDemoblaze.inTheBrowser())`,
Serenity invoca el método `performAs(actor)` de la Task:

```java
// OpenDemoblaze.java
public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
            Open.url("https://www.demoblaze.com/"),
            WaitUntil.the(DemoblazePage.FIRST_PRODUCT, isVisible())
                    .forNoMoreThan(15).seconds()
    );
}
```

`Open.url()` y `WaitUntil` son **Interactions** — las unidades atómicas de Screenplay.
Ellas hablan directamente con el WebDriver. Las Tasks componen Interactions en
acciones de negocio con significado.

La cadena completa de Tasks para este escenario es:

```
OpenDemoblaze          → abre https://www.demoblaze.com, espera que los productos carguen
AddProductToCart ×2    → clic en producto → clic en Add to cart → maneja el alert → regresa al Home
GoToCart               → clic en Cart → espera el botón Place Order
PlaceOrder             → clic en Place Order → espera que el modal del formulario se abra
FillPurchaseForm       → llena los 6 campos → clic en Purchase → espera la confirmación
```

---

### Paso 6 — Los localizadores: página en DemoblazePage

Todos los selectores CSS y XPath del proyecto viven en `DemoblazePage.java`:

```java
public static final Target ADD_TO_CART_BUTTON =
        Target.the("Add to cart button")
                .located(By.cssSelector("#tbodyid > div.row > div > a"));
```

`Target` es el wrapper de Serenity alrededor de un localizador de Selenium. El texto
`"Add to cart button"` es un nombre legible para humanos que aparece en el reporte HTML,
facilitando entender con qué elemento se interactuó en cada paso.

Si Demoblaze cambia su estructura HTML, solo actualizas `DemoblazePage.java` —
ninguna Task, Question o StepDefinition necesita modificarse.

---

### Paso 7 — La Question lee el resultado

Después de que se envía la compra, Serenity necesita verificar el resultado.
Una **Question** observa el estado actual de la aplicación y devuelve un valor:

```java
// ConfirmationMessage.java
public String answeredBy(Actor actor) {
    return Text.of(DemoblazePage.CONFIRMATION_MESSAGE).answeredBy(actor);
}
```

El step definition luego compara ese valor con un Matcher de Hamcrest:

```java
then(customer).

should(
        seeThat(ConfirmationMessage.onScreen(),containsString("Thank you for your purchase!"))
        );
```

Si el texto coincide → el escenario pasa ✅  
Si no coincide → Serenity lo marca como fallido ❌, toma una captura de pantalla
y registra el valor real vs el esperado en el reporte.

---

### Paso 8 — Serenity genera el reporte HTML

Después de que todos los tests terminan, el `serenity-maven-plugin` agrega los archivos
JSON de resultados producidos durante la ejecución y genera un reporte HTML completo en:

```
target/site/serenity/index.html
```

El reporte muestra:

- ✅ / ❌ resultado por escenario y por paso
- Captura de pantalla en cada paso (configurado con `take.screenshots = AFTER_EACH_STEP`)
- El nombre de cada `Target` con el que se interactuó
- El valor real devuelto por cada `Question`
- Stack trace completo para cualquier fallo
