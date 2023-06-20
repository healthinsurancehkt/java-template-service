CREATE TABLE WebFluxExample.Employee (
	id bigint unsigned NOT NULL AUTO_INCREMENT,
	name varchar(100) NULL,
	CONSTRAINT Employee_PK PRIMARY KEY (id)
);
INSERT INTO WebFluxExample.Employee(id, name) VALUES(1, 'Indra');
INSERT INTO WebFluxExample.Employee(id, name) VALUES(2, 'Juan');
INSERT INTO WebFluxExample.Employee(id, name) VALUES(3, 'Julia');
INSERT INTO WebFluxExample.Employee(id, name) VALUES(4, 'Romero');


