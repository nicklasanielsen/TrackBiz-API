# Login

Used by existing users to login.

**URL** : `/api/auth/login/`

**Method** : `POST`

**Authentication required** : No

**Permissions required** : None

## Request Body

```json
{
    "userName": "String",
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

```json
{
    "code": 400,
    "message": "String"
}
```

------

**Scenario** : Invalid request body.

**Code** : 400

```json
{
    "code": 400,
    "message": "String"
}
```

------

**Scenario** : Incorrect username.

**Code** : 403

```json
{
    "code": 403,
    "message": "String"
}
```

------

**Scenario** : Incorrect password.

**Code** : 403

```json
{
    "code": 403,
    "message": "String"
}
```