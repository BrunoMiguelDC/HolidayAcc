# HolidayAcc
The project consists of the development of a Spring application to manage apartments in a local holiday accommodation
company. The project is done on behalf of the
Internet Application Development and Implementation course.

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/uP38PorS)
## Group members
| Name        | Number | email                      |
|-------------|--------|----------------------------| 
| Sahil Kumar | 57449  | ss.kumar@campus.fct.unl.pt |
| Bruno Carmo | 57418  | bm.carmo@campus.fct.unl.pt |

## API Reference <!-- omit in toc -->

- [Users](#users)
    - [Create a user](#create-a-user)
    - [Get a user](#get-a-user)
    - [Update a user](#update-a-user)
    - [Delete a user](#delete-a-user)
    - [Search users](#search-users)
    - [Search reservations of a user](#search-reservations-of-a-user)
    - [Search reviews of a user](#search-reviews-of-a-user)
- [Apartments](#apartments)
    - [Create an apartment](#create-an-apartment)
    - [Get an apartment](#get-an-apartment)
    - [Update an apartment](#update-an-apartment)
    - [Delete an apartment](#delete-an-apartment)
    - [Search apartments](#search-apartments)
    - [Create a period for an apartment](#create-a-period-for-an-apartment)
    - [Get a period for an apartment](#get-a-period-of-an-apartment)
    - [Update a period of an apartment](#update-a-period-of-an-apartment)
    - [Delete a period of an apartment](#delete-a-period-of-an-apartment)
    - [Search periods of an apartment](#search-periods-of-an-apartment)
    - [Create a new reservation for an apartment](#create-a-new-reservation-for-an-apartment)
    - [Search reservations of an apartment](#search-reservations-of-an-apartment)
    - [Search reviews of an apartment](#search-reviews-of-an-apartment)
- [Reservations](#reservations)
    - [Get a reservation](#get-a-reservation)
    - [Cancel a reservation](#cancel-a-reservation)
    - [Update the state of a reservation](#update-the-state-of-a-reservation)
    - [List state history of a reservation](#list-state-history-of-a-reservation)
    - [Create review for a reservation](#create-a-review-for-a-reservation)


### Users

#### Create a user

```HTTP
POST /api/users
```

Creates a user in the application system and returns a user id.

<br>

#### Get a user

```HTTP
GET /api/users/{username}
```

Returns the user whose username is **username**.

<br>

#### Update a user

```HTTP
PUT /api/users/{username}
```

Updates the user whose username is **username**.

<br>

#### Delete a user

```HTTP
DELETE /api/users/{username}
```

Deletes the user whose username is **username**.

<br>

#### Search users

```HTTP
GET /api/users
```

Returns a list of users in the application system.

**Query parameters**

| Param | Optional | Type     | Description                                                                                                                                 |
|:------|----------|:---------|:--------------------------------------------------------------------------------------------------------------------------------------------|
| name  | yes      | `String` | The name of the user. Searches for users whose name matches a substring beginning from the start of the given name.                         |
| email | yes      | `String` | The email of the user. Searches for users whose email matches a substring beginning from the start of the given email.                      |
| phone | yes      | `String` | The phone number of the user. Searches for users whose phone number matches a substring beginning from the start of the given phone number. |

<br>

#### Search reservations of a user

```HTTP
GET /api/users/{username}/reservations
```

Returns a list of reservations of a user whose username is **username**.

**Query parameters**

| Param     | Optional | Type     | Description                                                                                                                                                                                                                       |
|:----------|----------|:---------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| startDate | yes      | `Date`   | The start date of the reservation. Searches for reservations that started after the given date. If `endDate` is given, then searches for reservations that are in the interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`. |
| endDate   | yes      | `Date`   | The end date of the reservation. Searches for reservations that ended before the given date. If `startDate` is given, then searches for reservations that are in interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`.      |
| apartment | yes      | `String` | The id of the apartment. Searches for reservations made in the given apartment.                                                                                                                                                   |
| states    | yes      | `enum`   | Comma separated list of states for reservations. Searches for reservations that are in at least one of the states. Possible states are: `under_consideration`, `booked`, `occupied`, `awaiting_review`, `closed`.                 |

<br>

#### Search reviews of a user

```HTTP
GET /api/users/{username}/reviews
```

Returns a list of reviews of a user whose username is **username**.

**Query parameters**

| Param     | Optional | Type     | Description                                                                                                                                                         |
|:----------|----------|:---------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| rating    | yes      | `Int`    | The rating of the review. Searches for reviews that have a rating equal to given rating when converted to `Int`. Possible values are: `0`, `1`, `2`, `3`, `4`, `5`. |
| apartment | yes      | `String` | The id of the apartment. Searches for reviews made for the given apartment.                                                                                         |

<br>

### Apartments

#### Create an apartment

```HTTP
POST /api/apartments
```

Creates an apartment in the application system and returns an apartment id.

<br>

#### Get an apartment

```HTTP
GET /api/apartments/{apartmentId}
```

Returns the apartment whose id is **apartmentId**.

<br>

#### Update an apartment

```HTTP
PUT /api/apartments/{apartmentId}
```

Updates the apartment whose id is **apartmentId**.

<br>

#### Delete an apartment

```HTTP
DELETE /api/apartments/{apartmentId}
```

Deletes the apartment whose id is **apartmentId**.

<br>

#### Search apartments

```HTTP
GET /api/apartments
```

Returns a list of apartments in the application system.

**Query parameters**

| Param       | Optional | Type      | Description                                                                                                                                                                                                                              |
|:------------|----------|:----------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| name        | yes      | `String`  | The name of the apartment. Searches for apartments whose name matches a substring beginning from the start of the given name.                                                                                                            |
| description | yes      | `String`  | Comma separated list of keywords for a description of an apartment. Searches for apartments that have at least one of the keywords in the their description.                                                                             |
| location    | yes      | `String`  | The location of the apartment. Searches for apartments in the given locations.                                                                                                                                                           |
| amenities   | yes      | `String`  | Comma separated list of amenities of an apartment. Searches for apartments that have at least one of the amenities in the given list.                                                                                                    |
| price       | yes      | `Float`   | The price per night of an apartment. Searches for apartments that have a lower price per night than the price given.                                                                                                                     |
| startDate   | yes      | `Date`    | The start date of the period. Searches for apartments whose periods start after the given date. If `endDate` is given, then searches for apartments whose periods are in the interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`. |
| endDate     | yes      | `Date`    | The end date of the period. Searches for apartments whose periods end before the given date. If `startDate` is given, then searches for apartments whose periods are in interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`.      |
| isAvailable | yes      | `Boolean` | The availability of an apartment. Searches for apartments that have available periods for reserving.                                                                                                                                     |
| owner       | yes      | `String`  | The id of the owner of the apartment. Searches for apartments owned by the given owner.                                                                                                                                                  |

<br>

#### Create a period for an apartment

```HTTP
POST /api/apartments/{apartmentId}/periods
```

Creates a period for an apartment whose id is **apartmentId** and returns the period number.

<br>

#### Get a period of an apartment

```HTTP
GET /api/apartments/{apartmentId}/periods/{periodNum}
```

Returns a period (whose id is **periodNum**) of an apartment whose id is **apartmentId**.

<br>

#### Update a period of an apartment

```HTTP
PUT /api/apartments/{apartmentId}/periods/{periodNum}
```

Updates a period (whose id is **periodNum**) of an apartment whose id is **apartmentId**.

<br>

#### Delete a period of an apartment

```HTTP
DELETE /api/apartments/{apartmentId}/periods/{periodNum}
```

Deletes a period (whose id is **periodNum**) of an apartment whose id is **apartmentId**.

<br>

#### Search periods of an apartment

```HTTP
GET /api/apartments/{apartmentId}/periods
```

Returns a list of the periods of an apartment whose id is **apartmentId**.

**Query parameters**

| Param     | Optional | Type   | Description                                                                                                                                                                                                      |
|:----------|----------|:-------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| startDate | yes      | `Date` | The start date of the period. Searches for periods that start after the given date. If `endDate` is given, then searches for periods that are in the interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`. |
| endDate   | yes      | `Date` | The end date of the period. Searches for periods that end before the given date. If `startDate` is given, then searches for periods that are in interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`.      |


<br>

#### Create a new reservation for an apartment

```HTTP
POST /api/apartments/{apartmentId}/periods/{periodNum}
```

Creates a new reservation for a period (whose id is **periodNum**) of an apartment whose id is **apartmentId** and
returns the reservation id.

<br>

#### Search reservations of an apartment

```HTTP
GET /api/apartments/{apartmentId}/reservations
```

Returns a list of the reservations of an apartment whose id is **apartmentId**.

**Query parameters**

| Param     | Optional | Type     | Description                                                                                                                                                                                                                       |
|:----------|----------|:---------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| startDate | yes      | `Date`   | The start date of the reservation. Searches for reservations that started after the given date. If `endDate` is given, then searches for reservations that are in the interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`. |
| endDate   | yes      | `Date`   | The end date of the reservation. Searches for reservations that ended before the given date. If `startDate` is given, then searches for reservations that are in interval `[startDate, endDate]`. Date format: `yyyy-MM-dd`.      |
| client    | yes      | `String` | The id of the client. Searches for reservations made by the given client.                                                                                                                                                         |
| states    | yes      | `enum`   | Comma separated list of states for reservations. Searches for reservations that are in at least one of the states. Possible states are: `under_consideration`, `booked`, `occupied`, `awaiting_review`, `closed`.                 |

<br>

#### Search reviews of an apartment

```HTTP
GET /api/apartments/{apartmentId}/reviews
```

Returns a list of the reviews of an apartment whose id is **apartmentId**.

**Query parameters**

| Param  | Optional | Type     | Description                                                                                                                                                         |
|:-------|----------|:---------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| rating | yes      | `Int`    | The rating of the review. Searches for reviews that have a rating equal to given rating when converted to `Int`. Possible values are: `0`, `1`, `2`, `3`, `4`, `5`. |
| author | yes      | `String` | The id of the author. Searches for reviews made by the given author.                                                                                                |

<br>

## Reservations

#### Get a reservation

```HTTP
GET /api/reservations/{reservationId}
```

Returns a reservation whose id is **reservationId**.

<br>

#### Cancel a reservation

```HTTP
DELETE /api/reservations/{reservationId}
```

Deletes a reservation whose id is **reservationId**.

<br>

#### Update the state of a reservation

```HTTP
PUT /api/reservations/{reservationId}/states
```

Updates the state of a reservation whose id is **reservationId**.

<br>

#### List state history of a reservation

```HTTP
GET /api/reservations/{reservationId}/states
```

Returns a list of the states of a reservation whose id is **reservationId**.

<br>

#### Create a review for a reservation

```HTTP
POST /api/reservations/{reservationId}
```

Creates a review for a reservation whose id is **reservationId** and is awaiting review.

<br>
