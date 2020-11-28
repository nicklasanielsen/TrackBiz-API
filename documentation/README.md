# Documentation

To see all the documentation about a specific endpoint, access the  documentation by clicking the link next to the endpoint in question.

## Open Endpoints

Open endpoints require no Authentication.

### Authentication related

* [Login](authentication/Login.md) : `POST /api/auth/login/`
* [Register](authentication/Register.md) : `POST /api/auth/register/`

### Tracking related

* [Track](tracking/Track.md) : `GET /api/tracking/{courier}/{tracking number}/`

## Feedback related

* [Request Courier](feedback/RequestCourier.md) : `POST /api/feedback/courier/`

## Closed Endpoints

Closed endpoints require a valid Token to be included in the header  of the request. A Token can be acquired from the open endpoints above.

The token must be provided under header-key `x-access-token`.

### User related

* [Edit](user/EditUser.md) : `PUT /api/user/`
* [Delete](user/DeleteUser.md) : `DELETE /api/user/`
* [Get tracked shipments](user/GetTrackedShipments.md) : `GET /api/user/shipments/`
* [Track shipment](user/TrackShipment.md) : `POST /api/user/shipments/`
* [Untrack shipment](user/UntrackShipment.md) : `DELETE /api/user/shipments/`

## Feedback related

* [Get requested Couriers](feedback/GetRequestedCouriers.md) : `GET /api/feedback/courier/`
* [Delete requested Courier](feedback/DeleteRequestedCourier.md) : `DELETE /api/feedback/courier/`