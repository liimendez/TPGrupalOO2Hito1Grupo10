# Epicentro Gourmet — TP Grupal 2026

Sistema de gestión para el predio ferial "Epicentro Gourmet", desarrollado con
Java + Hibernate (mapeo XML) + MySQL.

Grupo 10

Cecilia Livia Mendez - liimendez - testConsultaPedido - detallePedido 1 - * pedido.
Valentin Franco Hegele - FrancoHegele - TestConsultaCocinero - Cocinero - Personal: Herencia 1 a *.
Santiago Sosa - santisosa-gh - TestConsultaFestival - Festival - UnidadVenta: Uno a Muchos.




## Requisitos previos

- MySQL corriendo (Wamp) con usuario `root`
- La base `epicentro_gourmet` **no necesita existir de antemano** — se crea
  sola en la primera ejecución gracias a `createDatabaseIfNotExist=true` +
  `hibernate.hbm2ddl.auto=update` en `hibernate.cfg.xml`
- Revisar que `hibernate.connection.username` / `password` en
  `src/main/resources/hibernate.cfg.xml` coincidan con tu MySQL local

## Orden de ejecución de los tests

**Importante:** los tests de "Agregar" deben correrse en este orden la
primera vez, porque cada uno depende de que existan los datos cargados por
el anterior (por ejemplo, no se puede crear un `FoodTruck` sin que ya exista
un `Festival` y un `Personal` responsable):

1. **`TestAgregarFestival`** — primera ejecución: crea la base de datos y
   todas las tablas automáticamente (`hbm2ddl.auto=update`), además de
   insertar los festivales de prueba
2. **`TestAgregarFoodTruck`**
3. **`TestAgregarPuestoDesarmable`**
4. **`TestAgregarPlato`**

Una vez cargados los datos, se pueden correr las consultas en cualquier
orden:

5. **`TestConsultaFestival`**
6. **`TestConsultaUnidadVenta`**
7. **`TestConsultaFoodTruck`**
8. **`TestConsultaPuestoDesarmable`**

## Notas

- Cada corrida de un test de "Agregar" inserta datos nuevos (no
  sobrescribe), así que si lo corrés varias veces vas a acumular registros
  duplicados — tenerlo en cuenta al revisar resultados en las consultas
- Si es la primera vez que corrés el proyecto en tu máquina, revisá que
  MySQL esté corriendo y que la contraseña en `hibernate.cfg.xml` sea la
  correcta para tu instalación local
  
  
  
  
  
  
  
  