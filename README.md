# Epicentro Gourmet — TP Grupal 2026

Sistema de gestión para el predio ferial "Epicentro Gourmet", desarrollado con Java + Hibernate (mapeo XML) + MySQL.

##Grupo 10 | integrantes :  ##  
####### Cecilia Livia Mendez | github: liimendez |CU 01: TestLiiMendezReporteEjecutivo | CU 02: TestConsultaFestival. 
####### Valentin Franco Hegele | github FrancoHegele | TestFrancoHegele
####### Santiago Sosa | github: santisosa-gh | TestConsultaMontajeMin, TestConsultaElectricidad, TestConsultaPrecioMaxFT.

```
### Reporte Ejecutivo - Lii Mendez - cu 01
Muestra el Festival TOP (el que más recaudó) y su desglose.

sql
SELECT f.id, f.nombre, SUM(dp.cantidad * pl.precio_venta) AS total 
FROM pedido pe 
JOIN unidad_venta uv ON pe.unidad_venta_id = uv.id 
JOIN festival f ON uv.festival_id = f.id 
JOIN detalle_pedido dp ON dp.pedido_id = pe.id 
JOIN plato pl ON dp.plato_id = pl.id 
GROUP BY f.id, f.nombre 
ORDER BY total DESC;

#### 2 . Unidad que más recaudó en Festival 4 (verifica Puesto 20)

SELECT uv.id, uv.nombre_comercial, SUM(dp.cantidad * pl.precio_venta) AS total 
FROM pedido pe 
JOIN unidad_venta uv ON pe.unidad_venta_id = uv.id 
JOIN detalle_pedido dp ON dp.pedido_id = pe.id 
JOIN plato pl ON dp.plato_id = pl.id 
WHERE uv.festival_id = 4 
GROUP BY uv.id, uv.nombre_comercial 
ORDER BY total DESC;

#### 3. Plato más vendido y más rentable en Festival 4 (verifica Ramen U34)

SELECT pl.id, pl.nombre, SUM(dp.cantidad) AS cant, SUM(dp.cantidad * pl.precio_venta) AS recaudado 
FROM pedido pe 
JOIN unidad_venta uv ON pe.unidad_venta_id = uv.id 
JOIN detalle_pedido dp ON dp.pedido_id = pe.id 
JOIN plato pl ON dp.plato_id = pl.id 
WHERE uv.festival_id = 4 
GROUP BY pl.id, pl.nombre 
ORDER BY cant DESC;

#### 4. Cajero que más recaudó en Festival 4 (verifica Facu Sosa ID 13)


SELECT c.id, per.nombre, per.apellido, SUM(dp.cantidad * pl.precio_venta) AS total 
FROM pedido pe 
JOIN unidad_venta uv ON pe.unidad_venta_id = uv.id 
JOIN cajero c ON pe.cajero_id = c.id 
JOIN personal per ON per.id = c.id 
JOIN detalle_pedido dp ON dp.pedido_id = pe.id 
JOIN plato pl ON dp.plato_id = pl.id 
WHERE uv.festival_id = 4 
GROUP BY c.id, per.nombre, per.apellido 
ORDER BY total DESC;

**----->
CU-01: Generar Reporte Ejecutivo de Recaudación - Festival TOP
Es un Caso de Uso de Consulta / Reporte Gerencial.

Nombre: Reporte Ejecutivo - Festival que más recaudó

Actor: Administrador / Gerente del Predio

Objetivo: Permitir al gerente saber qué festival dejó más plata, qué puesto/foodtruck dentro de ese festival vendió más, qué plato fue el más vendido y el más rentable, y qué cajero recaudó más.

Flujo:

El sistema calcula la recaudación total de cada Festival (SUM cantidad * precioVenta)
Determina el Festival TOP
Dentro de ese Festival, determina:
UnidadVenta TOP
Plato más vendido
Plato más rentable
Cajero TOP
Clases que toca: Festival, UnidadVenta (herencia PuestoDesarmable/FoodTruck), Pedido, DetallePedido, Plato, Cajero (herencia Personal).

------------------------------------------------------------------------------------------------>
CU-02: Consultar Distribución de Unidades por Festival y Ranking por Superficie - relacion: uno a muchos - herencia 
Tipo: Caso de Uso de Consulta - Organización del predio

Actor: Administrador del Predio / Encargado de Logística

Objetivo: Saber cómo están distribuidas las unidades de venta dentro de cada festival y cuáles son las más grandes/chicas para asignar espacios y costos de alquiler.

Flujo:

Listar todas las unidades ordenadas por Festival (para ver cuántos puestos tiene cada festival)
Traer la unidad con mayor superficie (para cobrar más alquiler)
Traer la unidad con menor superficie
Mostrar ranking completo de mayor a menor

1. Unidades ordenadas por Festival (lo que hace traerOrdenadasPorFestival()):

SELECT id, nombre_comercial, festival_id, superficie_m2 
FROM unidad_venta 
ORDER BY festival_id ASC, id ASC;

2. Unidad con MAYOR superficie (traerUnidadConMayorSuperficie()):

SELECT id, nombre_comercial, superficie_m2, festival_id 
FROM unidad_venta 
ORDER BY superficie_m2 DESC 
LIMIT 1;

3. Unidad con MENOR superficie (traerUnidadConMenorSuperficie()):

SELECT id, nombre_comercial, superficie_m2, festival_id 
FROM unidad_venta 
ORDER BY superficie_m2 ASC 
LIMIT 1;

4. Ranking completo por superficie de mayor a menor (traerOrdenadasPorMayorSuperficie()):

SELECT id, nombre_comercial, superficie_m2 
FROM unidad_venta 
ORDER BY superficie_m2 DESC;

//-------------------------------------------------------------------------------------------------------------------------------------------------

SQL TestConsultaMontajeMin: santisosa-gh
CONSULTA 1: Trae todos los PuestoDesarmable de un Festival que su tiempo de montaje(tiempoMontajeMin) este en un rango indicado.

USE epicentro_gourmet;

SELECT 
    f.id AS idFestival,
    f.nombre AS festival_nombre,
    uv.id AS idUnidadVenta,
    uv.nombre_comercial,
    uv.codigo_unico,
    pd.tiempo_montaje_min,
    pd.cantidad_carpas
FROM festival f
INNER JOIN unidad_venta uv ON f.id = uv.festival_id
INNER JOIN puesto_desarmable pd ON uv.id = pd.id
WHERE f.id = 2
  AND pd.tiempo_montaje_min BETWEEN 20 AND 60;

SQL TestConsultaElectricidad: santisosa-gh
CONSULTA 2: Trae todos los FoodTruck de un Festival dependiendo si requiere conexion electrica o no(requiereConexionElectrica = TRUE or FALSE).

USE epicentro_gourmet;

SELECT 
    f.id AS idFestival,
    f.nombre AS festival_nombre,
    uv.id AS idUnidadVenta,
    uv.nombre_comercial,
    uv.codigo_unico,
    ft.patente,
    ft.requiere_conexion_electrica
FROM festival f
INNER JOIN unidad_venta uv ON f.id = uv.festival_id
INNER JOIN food_truck ft ON uv.id = ft.id
WHERE f.id = 2
  AND ft.requiere_conexion_electrica = TRUE;

SQL TestConsultaPrecioMaxFT: santisosa-gh
CONSULTA 3: Trae todos los FoodTruck de un Festival que ofrezcan al menos un Plato con precio de venta menor o igual a un valor indicado (precioMaximo) y al traer esos FoodTruck solo mostrara los platos que no superen dicho valor indicado(precioMaximo).

USE epicentro_gourmet;

SELECT DISTINCT
    f.id AS festival_id,
    f.nombre AS festival_nombre,
    uv.id AS unidad_venta_id,
    uv.nombre_comercial,
    uv.codigo_unico,
    ft.patente,
    ft.requiere_conexion_electrica,
    p.id AS plato_id,
    p.nombre AS plato_nombre,
    p.precio_venta
FROM festival f
INNER JOIN unidad_venta uv ON f.id = uv.festival_id
INNER JOIN food_truck ft ON uv.id = ft.id              
INNER JOIN plato p ON uv.id = p.unidad_venta_id         
WHERE f.id = 2
  AND p.precio_venta <= 7500.0
ORDER BY uv.id, p.precio_venta ASC;
