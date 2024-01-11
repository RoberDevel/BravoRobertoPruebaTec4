# Agencia Turismo 🌟

## Documentación 📖
<br>
<details>
<summary> <h2 style="display:inline">Comenzando 🚀 </h2></summary>

### BBDD

Importa la bbdd ya creada que encontrarás dentro de la carpeta
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

### Seguridad

Para realizar el __login:__

* __Usuario:__ hackaboss

* __Contraseña:__ 123456
</details>
<br>
<details>
<summary><h2 style="display:inline">Funcionalidades 📚</h2></summary>



### Historias de Usuario

#### Historias de usuario de Hoteles para usuarios autenticados y no autenticados

__1. Obtener un listado de todos los hoteles registrados:__ <br>Los usuarios pueden ver un listado de todos los hoteles registrados en la aplicación.
 <br>
<br>
__Path:__ 

> localhost:8080/agency/hotels

__2. Obtener un listado de todos los hoteles disponibles en un determinado rango de fechas y según la ciudad seleccionada:__ <br>
Los usuarios pueden ver un listado de todos los hoteles disponibles en un determinado rango de fechas y según la ciudad seleccionada.
<br><br> __Path:__ 
> localhost:8080/agency/hotels?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"

__3. Realizar una reserva de un hotel, indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. Obtener como respuesta el monto total de la reserva realizada:__ <br>
Los usuarios pueden realizar una reserva de un hotel, 
indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. Se obtendrá como respuesta el monto total de la reserva realizada.
<br><br>
__Path:__ 
> localhost:8080/agency/hotel-booking/new

* Ejemplo __json__ para crear una reserva de hotel:
```yaml
{
 //Para crear la reserva de hotel, se debe indicar el hotelCode de este.
 "hotelCode": "TM-0000002",
 "checkInDate": "2024-07-01",
 "checkOutDate": "2024-08-01",
 //Tipos de habitaciones; SINGLE, DOBLE, TRIPLE, MULTIPLE
 "roomType": "DOUBLE",
 "guests": [
  {
   "name": "John",
   "lastName": "Doe",
   "email": "john.doe@example.com",
   "phone": "123-456-7890",
   "dni": "12345698G"
  },
  {
   "name": "Jane",
   "lastName": "Doe",
   "email": "jane.doe@example.com",
   "phone": "098-765-4321",
   "dni": "98563214K"
  }
 ]
}
```
<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueban que las fechas de entrada y salida sean correctas.
<br>
 Ejemplo de respuesta en caso de que las fechas no sean validas:

  ![invalidDates](GestionTurismo\doc\hotelResInvDates.png)

2. Se comprueba que el hotel exista.
<br>
 Ejemplo de respuesta en caso de que el hotel no exista:

  ![invalidHotel](GestionTurismo\doc\hotelResInvHotel.png)

3. Se comprueba que el hotel esté dado de alta.
<br>
 Ejemplo de respuesta en caso de que el hotel no esté dado de alta:

  ![invalidHotel](GestionTurismo\doc\hotelResUnHotel.png)

4. Se comprueba que haya huespedes en la reserva.
<br>
 Ejemplo de respuesta en caso de que no haya huespedes en la reserva:

  ![invalidGuests](GestionTurismo\doc\hotelResInvGuests.png)

5. Se comprueba que la habitación esté disponible.
<br>
 Ejemplo de respuesta en caso de que la habitación no esté disponible:

  ![invalidRoom](GestionTurismo\doc\hotelResInvRoom.png)

6. Se comprueba que el numero de huespedes no supere la capacidad de la habitación.
<br>
 Ejemplo de respuesta en caso de que el numero de huespedes supere la capacidad de la habitación:

  ![invalidGuests](GestionTurismo\doc\hotelResInvGuestsRoom.png)

</details>

__4. Obtener un hotel buscado por su Id:__
Los usuarios pueden realizar una busqueda de un hotel por el Id de este.
<br><br>
__Path:__ 
> localhost:8080/agency/hotels/{id}

#### Historias de usuario de Hoteles para usuarios autenticados

__1. Crear un hotel:__ Los usuarios autenticados pueden crear un hotel.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/new

* Ejemplo __json__ para crear un hotel:
```yaml
{
"name": "Hotel Cristoforo Colombo",
"city": "Buenos Aires"
}
```

__2. Crear una habitación:__ Los usuarios autenticados pueden crear una habitación para un determinado hotel, usando el __hotelCode__.
<br><br>__Path:__ 
> localhost:8080/agency/rooms/new/{hotelCode}

* Ejemplo __json__ para crear una habitación:
```yaml
{
 "roomType": "TRIPLE",
 "availableFrom": "2024-01-01",
 "availableTo": "2024-12-31",
 "pricePerNight": 450.00
}
```

__3. Modificar un hotel:__ Los usuarios autenticados pueden modificar cualquier campo de un hotel buscándolo por su __hotelCode__.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/edit/{hotelCode}
> 
* Ejemplo __json__ para modificar solamente la ciudad del hotel:
```yaml
{
  "city":"Barcelona"
}
```
<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>


1. Se comprueba que el hotel exista.
   <br>
   Ejemplo de respuesta en caso de que el hotel no exista:

![invalidHotel](GestionTurismo\doc\hotelEditInvHotel.png)

</details>

__4. Modificar un hotel:__ Los usuarios autenticados pueden modificar __"name"__ y __"city"__ de un hotel pasándolos como parámetro y buscándolo por su __id__.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/edit/{id}

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que el hotel exista.
<br>
 Ejemplo de respuesta en caso de que el hotel no exista:

  ![invalidHotel](GestionTurismo\doc\hotelEditInvHotel.png)

</details>


__5. Dar de baja un hotel:__ Los usuarios autenticados pueden dar de __baja__ o __alta__ pasándo un booleano como parámetro llamado __isActive__, un hotel buscándolo por su __hotelCode__.
<br><br>__Path:__ 
> localhost:8080/agency/hotels/edit/{hotelCode}

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que el hotel exista.
<br>
 Ejemplo de respuesta en caso de que el hotel no exista:

  ![invalidHotel](GestionTurismo\doc\hotelStatusInvHotel.png)

</details>

__6. Cancelar una reserva de hotel:__ Los usuarios autenticados pueden cancelar la reserva de un hotel, buscándola por su __id__.
<br><br>__Path:__ 
> localhost:8080/agency/hotel-booking/cancel/{id}

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>
1. Se comprueba que la reserva exista.
<br>
 Ejemplo de respuesta en caso de que la reserva no exista:

  ![invalidHotel](GestionTurismo\doc\hotelResCancelInvRes.png)

</details>

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

* Ejemplo __json__ para crear una reserva de vuelo para ir y volver:
```yaml
{
 "flightToCode": "MABA-2501000003",
 "flightBackCode": "BAMA-2601000004",
 "dateFlightTo": "2024-01-25",
 "dateFlightBack": "2024-01-26",
 "seatTypeFlightTo": "BUSINESS",
 "seatTypeFlightBack": "TOURIST",
 "passengers": [
  {
   "name": "Lucrecia",
   "lastName": "Miramontes",
   "email": "lucrecia.miramontes@example.com",
   "phone": "32421563",
   "dni": "16942378F"
  }
 ]
}
```
* Ejemplo __json__ para crear una reserva de vuelo sólo de ida:
```yaml
{
 "flightToCode": "MABA-2501000003",
 "flightBackCode": "",
 "dateFlightTo": "2024-01-25",
 "dateFlightBack": "",
 "seatTypeFlightTo": "BUSINESS",
 "seatTypeFlightBack": "",
 "passengers": [
  {
   "name": "Lucrecia",
   "lastName": "Miramontes",
   "email": "lucrecia.miramontes@example.com",
   "phone": "32421563",
   "dni": "16942378F"
  }
 ]
}
```

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que el vuelo de ida exista.
<br>
 Ejemplo de respuesta en caso de que el vuelo de ida no exista:

  ![invalidFlight](GestionTurismo\doc\flightToResInvFlight.png)

2. Se comprueba que exista el vuelo en la fecha indicada.
<br>
 Ejemplo de respuesta en caso de que el vuelo no exista en la fecha indicada:

  ![invalidFlight](GestionTurismo\doc\flightToResInvDate.png)

3. Se comprueba que haya suficientes asientos para la cantidad de pasajeros indicada en la reserva.
<br>
 Ejemplo de respuesta en caso de que no haya suficientes asientos:

  ![invalidFlight](GestionTurismo\doc\flightToResInsQSeats.png)
4. Se comprueba si existe una reserva realizada por una de las personas que se quiere reservar.
<br>
 Ejemplo de respuesta en caso de que exista una reserva realizada por una de las personas que se quiere reservar:

  ![invalidFlight](GestionTurismo\doc\flightToResInsRes.png)

</details>

#### Historias de usuario de Vuelos para usuarios autenticados

__1. Crear un vuelo:__ Los usuarios autenticados pueden crear un vuelo.
<br><br>__Path:__ 
>localhost:8080/agency/flights/new

* Ejemplo __json__ para crear un vuelo:
```yaml
{
 "origin": "Buenos Aires",
 "destination": "Madrid",
 "seatTypePrices": {
  "TOURIST": 200.00,
  "PREMIUM_TOURIST": 350.00,
  "BUSINESS": 500.00
 },
 "date": "2024-01-26",
 "totalSeats": 10,
 "isActive": true
}
```

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que la fecha introducida no sea anterior al dia de hoy, y que el vuelo no exista.
<br>
 Ejemplo de respuesta en caso de que la fecha introducida no sea anterior al dia de hoy, y que el vuelo no exista:

  ![invalidFlight](GestionTurismo\doc\flightNewInv.png)

</details>

__2. Modificar un vuelo:__ Los usuarios autenticados pueden modificar cualquier campo de un vuelo buscándolo por su __flightNumber__.
<br><br>__Path:__ 
> localhost:8080/agency/flights/edit/{flightNumber}

* Ejemplo __json__ para modificar solamente el destino del vuelo y la fecha:
```yaml
{
  "destination":"Barcelona",
  "date": "2024-01-26"
}
```

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que el vuelo exista.
<br>
 Ejemplo de respuesta en caso de que el vuelo no exista:

  ![invalidFlight](GestionTurismo\doc\flightEditInvFlight.png)

</details>

__3. Modificar un vuelo:__ Los usuarios autenticados pueden modificar el origen, destino, el tipo de asiento, la cantidad de asientos y la fecha de un vuelo buscándolo por su __Id__.
<br><br>__Path:__ 
> localhost:8080/agency/flights/edit/{id}

* Ejemplo __json__ para modificar el vuelo:
```yaml
{
 "origin": "Oviedo",
 "destination": "Paris",
 "seatTypePrices": {
  "TOURIST": 120.00,
  "PREMIUM_TOURIST": 200.00,
  "BUSINESS": 350.00
 },
 "date": "2024-08-15",
 "totalSeats": 150,
 "isActive": true
}
```

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que el vuelo exista.
   <br>
   Ejemplo de respuesta en caso de que el vuelo no exista:

![invalidFlight](GestionTurismo\doc\flightEditInvFlight.png)

</details>

__4. Cancelar una reserva de vuelo:__ Los usuarios autenticados pueden cancelar la reserva de un vuelo, buscándolo por su __id__.
<br><br>__Path:__ 
> localhost:8080/agency/flight-booking/cancel/{id}

<details>
<summary><h4 style="display:inline">Validaciones ❗ </h4>
</summary>

1. Se comprueba que la reserva exista.
<br>
 Ejemplo de respuesta en caso de que la reserva no exista:

  ![invalidFlight](GestionTurismo\doc\flightResCancelInvRes.png)

</details>

__5. Obtener todas las reservas de vuelo:__ Los usuarios autenticados pueden obtener un listado de todas las reservas de todos los vuelos.
<br><br>__Path:__ 
> localhost:8080/agency/flight-booking/all

</details>