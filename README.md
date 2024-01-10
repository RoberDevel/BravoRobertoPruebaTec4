# Agencia Turismo 🌟

## Documentación 📖
<br>
<details>
<summary> <h2 style="display:inline">Comenzando 🚀 </h2></summary>

### BBDD

Importa la bbdd ya creada que está dentro de la carpeta
_GestionTurnos\ **bbdd**_

##### Usuario: __"root"__
##### Contraseña:  __""__

Si tienes otro usuario y contraseña tendrás que ir al archivo pom.xml.
Ve a la pestaña source y añade tu nombre de usuario y tu contraseña:

property name="javax.persistence.jdbc.user" value="tu nombre de usuario"
property name="javax.persistence.jdbc.password" value="tu contraseña"

### Postman

Para probar la aplicación puedes usar Postman.
En la carpeta _GestionTurnos\ **postman**_ hay un archivo llamado __Agencia Turismo.postman_collection__.

</details>
<br>
<details>
<summary><h2 style="display:inline">Funcionalidades 📚</h2></summary>

### Historias de Usuario

#### Historias de usuario de Hoteles para usuarios autenticados y no autenticados

__1. Obtener un listado de todos los hoteles registrados:__ Los usuarios pueden ver un listado de todos los hoteles registrados en la aplicación.
 <br>
<br>
__Path:__ 

> localhost:8080/agency/hotels


__2. Obtener un listado de todos los hoteles disponibles en un determinado rango de fechas y según la ciudad seleccionada:__ 
Los usuarios pueden ver un listado de todos los hoteles disponibles en un determinado rango de fechas y según la ciudad seleccionada.
<br><br> __Path:__ 
> localhost:8080/agency/hotels?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"

__3. Realizar una reserva de un hotel, indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. Obtener como respuesta el monto total de la reserva realizada:__ 
Los usuarios pueden realizar una reserva de un hotel, 
indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. Se obtendrá como respuesta el monto total de la reserva realizada.
<br><br>
__Path:__ 
> localhost:8080/agency/hotel-booking/new

__4. Obtener un hotel buscado por su Id:__
Los usuarios pueden realizar una busqueda de un hotel por el Id de este.
<br><br>
__Path:__ 
> localhost:8080/agency/hotels/{id}

#### Historias de usuario de Hoteles para usuarios autenticados

__1. Crear un hotel:__ Los usuarios autenticados pueden crear un hotel.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/new

__2. Crear una habitación:__ Los usuarios autenticados pueden crear una habitación para un determinado hotel, usando el __hotelCode__.
<br><br>__Path:__ 
> localhost:8080/agency/rooms/new/{hotelCode}

__3. Modificar un hotel:__ Los usuarios autenticados pueden modificar cualquier campo de un hotel buscándolo por su __hotelCode__.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/edit/{hotelCode}

__4. Modificar un hotel:__ Los usuarios autenticados pueden modificar __"name"__ y __"city"__ de un hotel buscándolo por su __id__.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/edit/{id}

__5. Dar de baja un hotel:__ Los usuarios autenticados pueden dar de __baja__ o __alta__ un hotel buscándolo por su __hotelCode__.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/edit/{hotelCode}

__6. Cancelar una reserva de hotel:__ Los usuarios autenticados pueden cancelar la reserva de un hotel, buscándola por su __id__.
<br><br>__Path:__ 
> localhost:8080/agency/hotel-booking/cancel/{id}

__7. Obtener todas las reservas de hotel:__ Los usuarios autenticados pueden obtener un listado de todas las reservas de todos los hoteles.
<br><br>__Path:__ 
> localhost:8080/agency/hotel-booking/all

__8. Obtener todas las habitaciones:__ Los usuarios autenticados pueden obtener un listado de todas las habitaciones de todos los hoteles.
<br><br>__Path:__ 
> localhost:8080/agency/rooms


#### Historias de usuario de Vuelos para usuarios autenticados y no autenticados
__1. Obtener un listado de todos los vuelos registrados:__ Los usuarios pueden obtener un listado con todos los vuelos disponibles.
<br><br>__Path:__ 
>localhost:8080/agency/flights

__2. Obtener un listado de todos los vuelos disponibles en un determinado rango de fechas y según el destino seleccionado:__ Los usuarios pueden obtener un listado con todos los vuelos disponibles en un determinado rango de fechas y según el destino seleccionado..
<br><br>__Path:__ 
>localhost:8080/agency/flights

__3. Obtener un vuelo buscado por su Id:__
Los usuarios pueden realizar una busqueda de un vuelo buscado por el Id de este.
<br><br>__Path:__ 
> localhost:8080/agency/flights/{id}

__4. Realizar la reserva de un vuelo/s, indicando el código de vuelo de ida, la fecha de ida, el asiento y la cantidad de pasajeros. Si quiere reservar la vuelta, indicará el código de vuelo y la fecha de vuelta:__
Los usuarios pueden realizar una reserva de vuelo de ida o de ida y vuelta.
<br><br>__Path:__ 
> localhost:8080/agency/flight-booking/new


#### Historias de usuario de Vuelos para usuarios autenticados

__1. Crear un vuelo:__ Los usuarios autenticados pueden crear un vuelo.
<br><br>__Path:__ 
>localhost:8080/agency/flights/new

__2. Modificar un vuelo:__ Los usuarios autenticados pueden modificar cualquier campo de un vuelo buscándolo por su __flightNumber__.
<br><br>__Path:__ 
> localhost:8080/agency/flights/edit/{flightNumber}

__3. Modificar un vuelo:__ Los usuarios autenticados pueden modificar el origen, destino, el tipo de asiento, la cantidad de asientos y la fecha de un vuelo buscándolo por su __Id__.
<br><br>__Path:__ 
> localhost:8080/agency/flights/edit/{id}

__4. Cancelar una reserva de vuelo:__ Los usuarios autenticados pueden cancelar la reserva de un vuelo, buscándolo por su __id__.
<br><br>__Path:__ 
> localhost:8080/agency/flight-booking/cancel/{id}

__5. Obtener todas las reservas de vuelo:__ Los usuarios autenticados pueden obtener un listado de todas las reservas de todos los vuelos.
<br><br>__Path:__ 
> localhost:8080/agency/flight-booking/all

</details>