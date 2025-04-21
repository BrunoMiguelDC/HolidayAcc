import { useEffect, useState } from 'react'
import { useHouseIdentifierSelector} from '../store/hooks'
import {
  HouseGallery,
  HouseReviews,
  HouseSubmission,
  ReservationCalendar,
} from '../components/HouseDetails'
import { Col, Row } from 'react-bootstrap'
import {BookingCalendarProps, Period, Reserved} from '../components/BookingCalendar/types'
import {ApartmentDTO} from "../api";
import {apartmentsAPI} from "../app/App";


const HouseDetail = () => {
  const [house, setHouse] = useState<ApartmentDTO>()
  const [periods, setPeriods] = useState<Period[]>()
  const [reservations, setReservations] = useState<Reserved[]>()

  const [calendarProps, setCalendarProps] = useState<BookingCalendarProps>({
    selectedStart: null,
    selectedEnd: null,
    isStart: true,
  });

  const houseId = useHouseIdentifierSelector(state => state.houseId)

  /*
  const loadHouse = () => {
    fetch(`/houseDetails.json`) 
        .then(response => response.json())
        .then(data => setHouse(data))
  }*/

  const loadHouse = () => {
    if (houseId) {
      apartmentsAPI.getApartment(houseId).then(data => {
        setHouse(data)
      })

      apartmentsAPI.searchApartmentReservations(houseId,0, 10).then(data => {
        setReservations(data.list.map( (p) => {
            return {startDate: new Date(p.startDate), endDate: new Date(p.endDate)}}))
      })

      apartmentsAPI.searchPeriods(houseId,0, 10).then(data => {
        setPeriods( data.map( (p) => {
          console.log("START DATE "+p.startDate)
          console.log("END DATE "+p.endDate)
          return {startDate: new Date(p.startDate), endDate: new Date(p.endDate)}}) )
      })
    }
  }

  useEffect(() => {
    loadHouse()
  }, [])


  return (
    <div className='container-fluid'>
      <div className="col-xs-12" style={{height:"15px"}}></div>
      <Row className='p-2'>
        <Col xs={12} md={6}>
        <HouseGallery images={house?.pictures} />
        </Col>
        <Col xs={12} md={6} style={{ minHeight: '400px'}}>
        <ReservationCalendar calendarProps = {calendarProps} setCalendarProps = {setCalendarProps} periods = {periods} reservations = {reservations}/>
        </Col>
      </Row>

      <Row className='p-2' style={{textAlign: 'left'}}>
        <Col xs={12} md={6}>
          <span className="fs-2"><b>{house?.name}</b></span> 
          &nbsp;&nbsp;&nbsp;
          <span className="fs-4" style={{color: house?.available ? "green" : "red", fontWeight: "bold"}}>
                      {house?.available? 'Available' : 'Not Available'}
          </span>
          &nbsp;&nbsp;&nbsp;
          <b className="fs-5">{house?.price} $</b>
          <p className="fs-6 "><b>Owner:</b> {house?.owner.name}</p>
          <p className="fs-5 "><b>Amenities:</b> {house?.amenities.join(', ')}</p>
        </Col>
        <Col>
          <HouseSubmission calendarProps = {calendarProps} loadHouse = {loadHouse} />
        </Col>
        <Row>
          <Col>
            <hr/>
            <p className="fs-5 "><b>Description:</b> {house?.description}</p>
          </Col>
        </Row>
      </Row>
      <p className="fs-2 "><b>Reviews</b></p>
      <HouseReviews/>
    </div>
  );
}






export default HouseDetail