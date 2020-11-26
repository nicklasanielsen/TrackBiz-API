# Track

Used by users to track shipments.

**URL** : `/api/tracking/{courier}/{tracking number}/`

**Method** : GET

**Authentication required** : No

**Permissions required** : None

## Success Response

**Code** : 200

```json
[
    {
        "courier": "String",
        "trackingNumber": "String",
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

**Scenario** : Unsupported Courier

**Code** : 501

```json
{
    "code": 501,
    "message": "String"
}
```

___

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

```
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

```
{
    "code": 500,
    "message": "String"
}
```

## Notes

* Path parameter for Courier represents the Courier, if a Courier isn't specified the string `any` must be used.
* Path parameter for tracking number must be specified. A tracking number can include numbers and letters and must be provided as a string.