+++
title = "Updating"
[menu.main]
  parent = "Reference Guides"
  pre = "<i class='fa fa-file-text-o'></i>"
+++

# Introduction

There are two basic ways to update your data: insert/save a whole Entity or issue an update operation. 

# Updating (on the server)

The update method on [`Datastore`]({{< apiref "org/mongodb/morphia/Datastore" >}}) is used to issue a command to the server to change 
existing documents.  The effects of the update command are defined via 
[`UpdateOperations`]({{< apiref "org/mongodb/morphia/query/UpdateOperations" >}}) methods.

## The Field Expression

The field expression, used by all update operations,  can be either a single field name or any dot-notation form (for embedded 
elements). The positional operator ($) can also be used in the field expression for array updates.  To illustrate, consider the 
entity here:

```java
@Entity("hotels")
public class Hotel
{
   @Id
   private ObjectId id;

   private String name;
   private int stars;

   @Embedded
   private Address address;

   List<Integer> roomNumbers = new ArrayList<Integer>();

   // ... optional getters and setters
}

@Embedded
public class Address
{
   private String street;
   private String city;
   private String postalCode;
   private String country;

   // ... optional getters and setters
}
```


### set()/unset()
To change the name of the hotel, one would use something like this:

```java
UpdateOperations ops = datastore
    .createUpdateOperations(Hotel.class)
    .set("name", "Fairmont Chateau Laurier");
datastore.update(updateQuery, ops);
```

This also works for embedded documents.  To change the name of the city in the address, one would use something like this:

```java
UpdateOperations ops = datastore
    .createUpdateOperations(Hotel.class)
    .set("address.city", "Ottawa");
datastore.update(updateQuery, ops);
```

Values can also be removed from documents as shown below:

```java
UpdateOperations ops = datastore
    .createUpdateOperations(Hotel.class)
    .unset("name");
datastore.update(updateQuery, ops);
```

After this update, the name of the hotel would be `null` when the entity is loaded.

### inc()/dec()

To simply increment or decrement values in the database, updates like these would be used:

```java
// increment 'stars' by 4
UpdateOperations ops = datastore
    .createUpdateOperations(Hotel.class)
    .inc("stars");
datastore.update(updateQuery, ops);

// increment 'stars' by 4
ops = datastore
    .createUpdateOperations(Hotel.class)
    .inc("stars", 4);
datastore.update(updateQuery, ops);

// decrement 'stars' by 1
ops = datastore
    .createUpdateOperations(Hotel.class)
    .dec("stars");  // same as .inc("stars", -1)
datastore.update(updateQuery, ops);

// decrement 'stars' by 4
ops = datastore
    .createUpdateOperations(Hotel.class)
    .inc("stars", -4);
datastore.update(updateQuery, ops);
```

### add()/addAll()

`add()` is used to add a value to an array field:
```java
ops = datastore
    .createUpdateOperations(Hotel.class)
    .add("roomNumbers", 11);
datastore.update(updateQuery, ops);
```

This will issue a `$addToSet` operation adding `11` to the list.  This will prevent duplicate values in this field.  If duplicate values are
desired, use the overloaded `add()` that takes a boolean instead:
```java
ops = datastore
    .createUpdateOperations(Hotel.class)
    .add("roomNumbers", 11, true);
datastore.update(updateQuery, ops);
```

`addAll()` takes a `List` of values and behaves similarly.

### removeFirst()/removeLast()/removeAll()
To remove values from a list, use `removeFirst()`, `removeLast()`, or `removeAll()`:
```java
//given roomNumbers = [ 1, 2, 3 ]
ops = datastore
    .createUpdateOperations(Hotel.class)
    .removeFirst("roomNumbers");
datastore.update(updateQuery, ops);  // [ 2, 3 ]

//given roomNumbers = [ 1, 2, 3 ]
ops = datastore
    .createUpdateOperations(Hotel.class)
    .removeLast("roomNumbers");
datastore.update(updateQuery, ops);  // [ 1, 2 ]
ops = datastore
    .createUpdateOperations(Hotel.class)
    .removeLast("roomNumbers");
datastore.update(updateQuery, ops);  // [ 1 ]
ops = datastore
    .createUpdateOperations(Hotel.class)
    .removeLast("roomNumbers");
datastore.update(updateQuery, ops);  // []   empty array

//given roomNumbers = [ 1, 2, 3, 3 ]
ops = datastore
    .createUpdateOperations(Hotel.class)
    .removeAll("roomNumbers", 3);
datastore.update(updateQuery, ops);  // [ 1, 2 ]

//given roomNumbers = [ 1, 2, 3, 3 ]
ops = datastore
    .createUpdateOperations(Hotel.class)
    .removeAll("roomNumbers", Arrays.asList(2, 3));
datastore.update(updateQuery, ops);  // [ 1 ]
```

### updateFirst()

In the default driver and shell this is the default behavior. In Morphia we feel like updating all the results of the query is a better default (see below).

```javascript
    {
        name: "Fairmont", 
        stars: 5
    },
    {
        name: "Last Chance", 
        stars: 3 
    }
```

```java
ops = datastore.createUpdateOperations(Hotel.class).inc("stars", 50);

// morphia exposes a specific updateFirst to update only the first hotel matching the query
datastore
    .updateFirst(datastore
        .find(Hotel.class)
        .order("stars"),
        ops);  // update only Last Chance
datastore
    .updateFirst(datastore
        .find(Hotel.class)
        .order("-stars"),
        ops); // update only Fairmont
```

## Multiple Operations

You can also perform multiple update operations within a single update.

```java
//set city to Ottawa and increment stars by 1
ops = datastore
    .createUpdateOperations(Hotel.class)
    .set("city", "Ottawa")
    .inc("stars");
datastore.update(updateQuery, ops);

//if you perform multiple operations in one command on the same property, results will vary
ops = datastore
    .createUpdateOperations(Hotel.class)
    .inc("stars", 50)
    .inc("stars");  //increments by 1
ops = datastore
    .createUpdateOperations(Hotel.class)
    .inc("stars")
    .inc("stars", 50);  //increments by 50

//you can't apply conflicting operations to the same property
ops = datastore
    .createUpdateOperations(Hotel.class)
    .set("stars", 1)
    .inc("stars", 50); //causes error
```

## createIfMissing (overload parameter)

All of the update methods on `Datastore` are overloaded and accept a `createIfMissing` parameter

```java
ops = datastore
    .createUpdateOperations(Hotel.class)
    .inc("stars", 50);

//update, if not found create it
datastore
    .updateFirst(datastore
        .createQuery(Hotel.class)
        .field("stars").greaterThan(100),
    ops, true);  

// creates { "_id" : ObjectId("4c60629d2f1200000000161d"), "stars" : 50 }
```
