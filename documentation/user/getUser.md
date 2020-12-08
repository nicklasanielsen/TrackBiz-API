# Get User

Used by registered users to get their information.

**URL** : `/api/user/`

**Method** : GET

**Authentication required** : Yes

**Permissions required** : User

## Success Response

**Code** : 200

```json
{
    "userName": "String",
    "fullName": "String",
    "created": "yyyy-MM-dd HH:mm:ss Time zone",
    "roleList":[
        {
            "roleName": "String"
        }
    ]
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

