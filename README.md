# TACS1C2017Grupo2

### Openshift:

Para acceder a la version hosteada en openshift simplemente accedo a la url:
*http://frontend-tacs.7e14.starter-us-west-2.openshiftapps.com/*

### Servidor REST local:

Pasos para ejecutar en consola:

1. Ubicarse en el directorio del proyecto git.
2. Escribir *mvn clean install*
3. Escribir *mvn spring-boot:run*
4. El servidor REST se encuentra en *http://localhost:8080*

Lista de endpoints: 
Se adjunta "listaEndpoints.txt", que contiene los path.
Se adjunta una coleccion v2 de PostMan con todos los requests correspondientes.
	
*http://localhost:8080/home*

### FrontEnd local:

1. Tener instalado el node.js
2. Abrir la terminal
3. Ubicarse en "src/main/webapp/"
4. Ejecutar "npm install"
5. Ejecutar "npm start"
6. Abrira automaticamente el frontend en el browser, sino ir a la siguiente ruta: *http://localhost:3000*

### Cuentas existentes:

	user: Alvaro
	password: 1234
	
	user: Guille
	password: 1234
	
	user: Martin
	password: 1234
	
	user: Julio
	password: 1234
	
	user: Jon
	password: 1234
	
Sino se ofrece la posibilidad de registrar cuentas nuevas, siendo la url REST: *http://{baseurl}/usuarios* con operacion POST.