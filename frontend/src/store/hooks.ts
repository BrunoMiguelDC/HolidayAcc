import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux'
import type { State, AppDispatch } from '.'
import type { State as UserState } from './user'
import type { State as HousesState } from './houses'
import type { State as HouseDetailsState } from './HouseIdentifier'
import type { State as ReservationDetailsState } from './ReservationIdentifier'


// Typed versions of useDispatch and useSelector hooks
export const useAppDispatch: () => AppDispatch = useDispatch
export const useAppSelector: TypedUseSelectorHook<State> = useSelector


export const useUserSelector: TypedUseSelectorHook<UserState> = 
    <T>(f:(state:UserState) => T) => useAppSelector((state:State) => f(state.user))
export const useHouseSelector: TypedUseSelectorHook<HousesState> =
    <T>(f:(state:HousesState) => T) => useAppSelector((state:State) => f(state.houses))
    
export const useHouseIdentifierSelector: TypedUseSelectorHook<HouseDetailsState> = 
    <T>(f:(state:HouseDetailsState) => T) => useAppSelector((state:State) => f(state.houseIdentifier))
export const useReservationIdentifierSelector: TypedUseSelectorHook<ReservationDetailsState> = 
    <T>(f:(state:ReservationDetailsState) => T) => useAppSelector((state:State) => f(state.reservationIdentifier))
