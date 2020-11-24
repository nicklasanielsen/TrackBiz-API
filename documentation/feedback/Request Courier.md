# Request Courier

Used by users to request additional couriers.

**URL** : `/api/feedback/courier/`

**Method** : POST

**Authentication required** : No

**Permissions required** : None

## Request Body

```json
{
    "name": "String",
    "url": "String",
    "message": "String"
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

