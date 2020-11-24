# Delete User

Used by registered users to delete their account.

**URL** : `/api/user/`

**Method** : DELETE

**Authentication required** : Yes

**Permissions required** : User

## Success Response

**Code** : 200

```JSON
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

