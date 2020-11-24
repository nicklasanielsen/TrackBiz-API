# Register

Used by non existing users to create a user.

**URL** : `/api/auth/register/`

**Method** : `POST`

**Authentication required** : No

**Permissions required** : None

## Request Body

```json
{
    "userName": "String",
    "firstName": "String",
    "lastName": "String",
    "password": "String"
}
```

## Success Response

**Code** : 200

```json
{
    "userName": "String",
    "token": "String"
}
```

## Bad Requests

**Scenario** : Missing request body.

**Code** : 400

```
{
    "code": 400,
    "message": "String"
}
```

------

**Scenario** : Invalid request body.

**Code** : 400

```
{
    "code": 400,
    "message": "String"
}
```

------

**Scenario** : Username already in use.

**Code** : 400

```
{
    "code": 400,
    "message": "String"
}
```

------

**Scenario** : One and/or more of the required credentials was not provided.

**Code** : 400

```
{
    "code": 400,
    "message": "String"
}
```

## Notes

- When a new user is created, the user is automatically logged in.