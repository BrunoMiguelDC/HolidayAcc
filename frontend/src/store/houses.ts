import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import { ApartmentListDTO } from '../types'

export interface State {
    houses: ApartmentListDTO[],
    filter: string,
    loading: boolean,
}

// Define the initial state using that type
const initialState: State = {
    houses: [], 
    filter: '',
    loading: false,
}

export const slice = createSlice({
    name: 'houses',
    initialState,
    reducers: {
        setFilter: (state, action:PayloadAction<string>) => {
            state.filter = action.payload
        },
        setLoading: (state, action:PayloadAction<boolean>) => {
            state.loading = action.payload
        },
        setHouses: (state, action:PayloadAction<ApartmentListDTO[]>) => {
            state.houses = action.payload
            state.loading = false
        }
    }
})

export const {  setFilter, setLoading, setHouses } = slice.actions

export const loadHouses = () => (dispatch: any) => {
    dispatch(setLoading(true))
    fetch('houses.json')
    .then( response => response.json())
    .then( houses => dispatch(setHouses(houses)))
}

// Other code such as selectors can use the imported `RootState` type
// export const selectCount = (state: RootState) => state.counter.value

export default slice.reducer