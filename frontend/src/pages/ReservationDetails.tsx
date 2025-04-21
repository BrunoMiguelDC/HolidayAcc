import { useEffect, useState } from "react"
import Image from 'react-bootstrap/Image'
import {useReservationIdentifierSelector, useUserSelector} from "../store/hooks"
import {ReservationDTO, ReviewDTO} from "../api"
import { Col, Row } from "react-bootstrap"
import { ReservationUserControl, UserReview } from "../components/ReservationDetails"
import {reservationsAPI} from "../app/App";


export const ReservationDetails = () => {

    const reservationId = useReservationIdentifierSelector(state => state.reservationId)
    const reservationState = useReservationIdentifierSelector(state => state.reservationState)
    const user = useUserSelector(state => state)

    const [reservation, setReservation] = useState<ReservationDTO>()
    const [review, setReview] = useState<ReviewDTO>()

    /*
    const loadReservation = () => {
    fetch(`/reservationDetails.json`) 
        .then(response => response.json())
        .then(data => setReservation(data))
    }
    */
    const loadReservation = () => {
        if(reservationId){
            reservationsAPI.getReservation(reservationId, {headers:{"Authorization":user.userToken}}).then(data => {
                setReservation(data)
                setReview(data.review)
            })
        }
    }

    useEffect(() => {
        loadReservation()
      }, [])

    return <div className='container-fluid'>
        <Row className='p-2'>
            <Col md={5}>
                <Image src={reservation?.apartment.picture} fluid />
            </Col>
            <Col style={{fontSize: "1.6rem"}} md={3}>
                <br/><b>
                <Row > 
                    <Col >
                        Check in<br/>
                        {reservation?.startDate} 
                    </Col>
                    <Col >
                        Exit<br/>
                        {reservation?.endDate}
                    </Col>
                </Row>
                <br/>
                Status
                <br/>
                {reservationState}</b>
            </Col>
            <Col md={3}>
                <br/><br/>
                <ReservationUserControl/>
            </Col>
        </Row>
        <hr/>
        <Col md={2}>
        <span className="fs-2" ><b>{reservation?.apartment.name}</b></span>
        <p className="fs-6 "><b>Client:</b> {reservation?.client.name}</p>
        </Col>
        <p/>
        <Col md={{span: 6, offset: 3}} >
        <p className="fs-2"><b> Review: </b></p>
        <UserReview review={review} setReview={setReview}/>
        </Col>
    </div>   
}

export default ReservationDetails
