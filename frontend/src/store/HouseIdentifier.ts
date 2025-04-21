import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import { HouseIdentifier } from '../types'

export type State = HouseIdentifier

// Define the initial state using that type
const initialState: State = {
    houseId: undefined
}

export const slice = createSlice({
    name: 'houseDetails',
    initialState,
    reducers: {
        setHouse: (state, action: PayloadAction<number>) => {
            state.houseId = action.payload
        },
    }
})

export const { setHouse } = slice.actions

export default slice.reducer