import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import {User, UserInfo} from '../types'


export type State = User

// Define the initial state using that type
const initialState: State = {
    name: undefined,
    username: undefined,
    userToken: undefined,
    isOwner: false
}

export const slice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<UserInfo>) => {
            state.name = action.payload.name
            state.username = action.payload.username
            state.userToken = action.payload.userToken
        },
        setUserOwner: (state, action: PayloadAction<boolean>) => {
            state.isOwner = action.payload
        }
    }
})

export const { setUser, setUserOwner } = slice.actions

export default slice.reducer