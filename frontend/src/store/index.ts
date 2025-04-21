import { configureStore } from '@reduxjs/toolkit';
import { logger } from 'redux-logger'
import houseReducer, { loadHouses } from './houses';
import userReducer, { setUser } from './user';
import houseIdentifierReducer, { setHouse } from './HouseIdentifier';
import reservationIdentifierReducer, { setReservationId, setReservationState } from './ReservationIdentifier';


export const store = configureStore({
    reducer: {
        houses: houseReducer,
        user: userReducer,
        houseIdentifier: houseIdentifierReducer,
        reservationIdentifier: reservationIdentifierReducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
});

export type State = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

//store.dispatch(setUser({name: "John Doe", username: "user1", userToken: undefined}))
store.dispatch(loadHouses())

