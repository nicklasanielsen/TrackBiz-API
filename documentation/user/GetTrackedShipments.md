# Get tracked shipments

Used by registered users to get their tracked shipments.

**URL** : `/api/user/shipments/`

**Method** : GET

**Authentication required** : Yes

**Permissions required** : User

## Success Response

**Code** : 200

```json
[
    {
        "courier": "String",
        "consignor": "String",
        "consignee": "String",
        "originCountry": "String",
        "originHub": "String",
        "destinationCountry": "String",
        "destinationHub": "String",
        "weight": "Integer",
        "length": "Integer",
        "height": "Integer",
        "width": "Integer",
        "currentEvent": {
            "timeStamp": "yyyy-MM-dd HH:mm:ss Time zone",
            "status": "String",
            "description": "String",
            "country": "String",
            "hub": "String"
        },
        "events": [
            {
                "timeStamp": "yyyy-MM-dd HH:mm:ss Time zone",
                "status": "String",
                "description": "String",
                "country": "String",
                "hub": "String"
            }
        ]
    }
]
```

## Bad Requests

**Scenario** : No shipments found.

**Code** : 400

```json
{
    "code": 400,
    "message": "String"
}
```

___

**Scenario** : External service timed out.

**Code** : 503

```json
{
    "code": 503,
    "message": "String"
}
```

___

**Scenario** : Internal server error caused by threads.

**Code** : 500

```
{
    "code": 500,
    "message": "String"
}
```

___

**Scenario** : Internal server error caused by invalid JSON received by external service.

**Code** : 500

```json
{
    "code": 500,
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

