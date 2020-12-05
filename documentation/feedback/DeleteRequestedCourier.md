# Delete requested Couriers

Used by Admin users to delete a courier request from the list of requested Couriers.

**URL** : `/api/feedback/courier/`

**Method** : DELETE

**Authentication required** : Yes

**Permissions required** : Admin

## Request Body

```json
{
    "id": "String"
}
```

## Success Response

**Code** : 200

```json
{
    "code": 200,
    "message": "String"
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

___

**Scenario** : Request not found - invalid ID

**Code** : 400

```json
{
    "code": 400,
    "message": "String"
}
```

___

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

