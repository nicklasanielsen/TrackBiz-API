# Untrack shipment

Used by registered users to untrack a tracked shipment.

**URL** : `/api/user/shipments/`

**Method** : DELETE

**Authentication required** : Yes

**Permissions required** : User

## Request Body

```json
{
    "courier": "String",
    "shippingNumber": "String"
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

```json
{
    "code": 400,
    "message": "String"
}
```

___

**Scenario** : Invalid request body.

**Code** : 400

```json
{
    "code": 400,
    "message": "String"
}
```

___

**Scenario** : One and/or more of the required credentials was not provided.

**Code** : 400

```json
{
    "code": 400,
    "message": "String"
}
```

___

**Scenario** : Shipment not found.

**Code** : 400

```json
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

