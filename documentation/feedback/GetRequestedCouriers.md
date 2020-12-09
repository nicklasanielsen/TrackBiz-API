# Get requested Couriers

Used by Admin users to get a list of requested Couriers.

**URL** : `/api/feedback/courier/`

**Method** : GET

**Authentication required** : Yes

**Permissions required** : Admin

## Success Response

**Code** : 200

```json
[
    {
        "id": "String",
        "name": "String",
        "url": "String",
        "message": "String"
     }
]
```

## Bad Requests

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

