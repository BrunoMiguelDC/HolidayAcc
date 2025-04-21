import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import { ReservationIdentifier } from '../types'
import {ReservationStateCreateDTO} from "../api";
import NameEnum = ReservationStateCreateDTO.NameEnum;

export type State = ReservationIdentifier

// Define the initial state using that type
const initialState: State = {
    reservationId: undefined,
    reservationState: undefined
}

export const slice = createSlice({
    name: 'reservationDetails',
    initialState,
    reducers: {
        setReservationId: (state, action: PayloadAction<string>) => {
            state.reservationId = action.payload
        },
        setReservationState: (state, action: PayloadAction<NameEnum>) => {
            state.reservationState = action.payload
        },
    }
})

export const { setReservationId, setReservationState } = slice.actions

export default slice.reducer