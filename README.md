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

//--------------------------------------------------------------------------------------------




















