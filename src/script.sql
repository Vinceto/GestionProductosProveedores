-- Proveedores
INSERT INTO proveedores (nombre_proveedor) VALUES ('Proveedor 1');
INSERT INTO proveedores (nombre_proveedor) VALUES ('Proveedor 2');
INSERT INTO proveedores (nombre_proveedor) VALUES ('Proveedor 3');

-- Productos
INSERT INTO productos (nombre_producto, proveedor_id) VALUES ('Producto 1', 1);
INSERT INTO productos (nombre_producto, proveedor_id) VALUES ('Producto 2', 1);
INSERT INTO productos (nombre_producto, proveedor_id) VALUES ('Producto 3', 2);
INSERT INTO productos (nombre_producto, proveedor_id) VALUES ('Producto 4', 2);
INSERT INTO productos (nombre_producto, proveedor_id) VALUES ('Producto 5', 3);

-- Ordenes
INSERT INTO ordenes (fecha, cliente) VALUES ('2023-07-29', 'Cliente 1');
INSERT INTO ordenes (fecha, cliente) VALUES ('2023-07-30', 'Cliente 2');

-- OrdenesProductos
INSERT INTO ordenes_productos (orden_id, producto_id, cantidad) VALUES (1, 1, 5);
INSERT INTO ordenes_productos (orden_id, producto_id, cantidad) VALUES (1, 2, 3);
INSERT INTO ordenes_productos (orden_id, producto_id, cantidad) VALUES (2, 3, 2);
INSERT INTO ordenes_productos (orden_id, producto_id, cantidad) VALUES (2, 4, 7);
INSERT INTO ordenes_productos (orden_id, producto_id, cantidad) VALUES (2, 5, 1);