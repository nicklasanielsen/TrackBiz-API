# Delete requested Couriers

Used by Admin users to delete a courier request from the list of requested Couriers.

**URL** : `/api/feedback/courier/`

**Method** : DELETE

**Authentication required** : Yes

**Permissions required** : Admin

## Success Response

**Code** : 200

```json
{
    "code": 200,
    "message": "String"
}
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

