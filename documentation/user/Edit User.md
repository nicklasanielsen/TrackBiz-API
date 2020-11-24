# Edit User

Used by registered users to edit their information.

**URL** : `/api/user/`

**Method** : PUT

**Authentication required** : Yes

**Permissions required** : User

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

**Scenario** : Missing authentication.

**Code** : 403

```json
{
    "code": 403,
    "message": "String"
}
```

___

**Scenario** : Missing permissions.

**Code** : 401

```json
{
    "code": 401,
    "message": "String"
}
```

