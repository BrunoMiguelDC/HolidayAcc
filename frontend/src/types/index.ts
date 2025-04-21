import {ApartmentsApi, ReservationStateCreateDTO} from "../api";
import NameEnum = ReservationStateCreateDTO.NameEnum;

export interface ApartmentDTO {
    name: string
    description: string
    location: string
    amenities: string
    pictures:string[]
    price:number
    isAvailable:boolean
    owner:UserShortDTO
}

export interface ApartmentListDTO {
    apartmentId: number
    name: string
    location: string
    picture: string
    description: string
    price: number
    isAvailable: boolean
    owner: UserShortDTO
}

export interface ApartmentShortDTO {
    apartmentId: number
    name: string
    location: string
    picture: string
    price: number
    isAvailable: boolean
}

export interface UserShortDTO{
    username: string
    name: string
}

export interface ReservationStateShortDTO{
    name:string
}

export interface ReservationStateDTO{
    name:string
    startDate:string
    endDate:string
}

export interface PeriodShortDTO{
    periodNum: number
    startDate:string
    endDate:string
    availableFrom: string
}

export interface ReservationDTO{
    reservationNum:number
    startDate:string
    endDate:string
    client:UserShortDTO
    period: PeriodShortDTO
    apartmentShortDTO: ApartmentShortDTO
    state: ReservationStateDTO
    review: ReviewDTO | undefined
}

export interface ReservationListDTO{
    reservationId:number
    startDate:string
    endDate:string
    state: ReservationStateShortDTO
    apartmentShortDTO : ApartmentShortDTO
}

export interface ReviewDTO{
    text: string | undefined
    rating: number | undefined
}

export interface ReviewApartmentsListDTO{
    text: string
    rating: number
    author: UserShortDTO
}


export interface User {
    name: string | undefined
    username: string | undefined
    userToken: string | undefined | null
    isOwner: boolean
}

export interface UserInfo {
    name: string | undefined
    username: string | undefined
    userToken: string | undefined | null
}

export interface HouseIdentifier {
    houseId: number | undefined
}

export interface ReservationIdentifier {
    reservationId: string | undefined,
    reservationState: NameEnum | undefined
}

export interface Api {
    apartmentsApi : ApartmentsApi
}