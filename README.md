# BddObject Class

The `BddObject` class is a generic Java class that provides basic CRUD (Create, Read, Update, Delete) operations for interacting with a database. It uses reflection to dynamically interact with database tables.

## Table of Contents

- [Save (Insert) Operation](#save-insert-operation)
- [Delete Operations](#delete-operations)
  - [Delete by Primary Key](#delete-by-primary-key)
  - [Delete by Specified ID](#delete-by-specified-id)
  - [Delete with Custom Condition](#delete-with-custom-condition)
- [Update Operation](#update-operation)
  - [Update Record by Primary Key](#find-record-by-primary-key)
  - [Update Records with Custom Condition]
- [Select Operations](#select-operations)
  - [Find All Records](#find-all-records)
  - [Find Record by Primary Key](#find-record-by-primary-key)
  - [Find Records with Custom Condition](#find-records-with-custom-condition)
- [Other Operations](#other-operations)
  - [Execute Update with Custom Query](#execute-update-with-custom-query)
  - [Execute Query with Custom Query and Object Mapping](#execute-query-with-custom-query-and-object-mapping)
