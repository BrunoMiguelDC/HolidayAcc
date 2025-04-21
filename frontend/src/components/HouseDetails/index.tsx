import Carousel from 'react-bootstrap/Carousel';
import {  useEffect, useState } from 'react';
import { Button, Card, Col, Form, Modal, Row } from 'react-bootstrap';
import '../../app/App.css'
import {  format } from "date-fns";
import {useHouseIdentifierSelector, useUserSelector} from '../../store/hooks';
import { BookingCalendar } from '../BookingCalendar';
import { BookingCalendarProps, Period, Reserved } from '../BookingCalendar/types';
import { useNavigate } from 'react-router-dom';
import {dateFormat, useModal} from "../util";
import {apartmentsAPI} from "../../app/App";
import {PeriodCreateDTO, PeriodDTO, ReservationCreateDTO, ReviewApartmentsListDTO} from "../../api";
import {start} from "repl";

export const HouseGallery = ({ images }: {  images?: string[] }) => {
  let items = []
  if(images){
    for(let i = 0; i < images.length ;i++){
      items.push(
        <Carousel.Item key={i}>
        <img src={images[i]} className='rounded w-100' alt='...' />
        </Carousel.Item>
      )
    }
  }
return (
    <Carousel fade data-bs-theme="dark">
      {items}
    </Carousel>
  );
}

export const HouseReviews = () => {
  const [reviews, setReviews] = useState<ReviewApartmentsListDTO[]>([])

  const houseId = useHouseIdentifierSelector(state => state.houseId)


  const loadReviews = () => {
    if(houseId){
      apartmentsAPI.searchApartmentReviews(houseId, 0, 10).then(data => setReviews(data))
    }

  }

    /*
    const loadReviews = () => {
      if (houseId) {
        new ApartmentsApi().searchApartmentReviews(houseId, 0 , 4).then(data => {
          setReviews(data)
        })
      }
    }*/

  useEffect(() => {
    loadReviews()
  }, [])

  return <div>
  <Row className="row gx-4 gy-2" lg={4} md={2} sm={2}>
   {reviews.slice(0,4).map((r, i) => 
     <Col key={i}>
     <Card  className="card mb-3 "> 
        <Card.Body>
          <Card.Title className='d-inline-block'>{r.author.name}</Card.Title>
          <Card.Subtitle className="d-inline-block mb-2 text-muted">&nbsp;{r.rating} stars</Card.Subtitle>
          <Card.Text>{r.text}
             </Card.Text>
         </Card.Body>
     </Card>
     </Col>
   )}
  </Row>
    {(reviews.length > 4) && <Button variant="primary">Show more</Button>}
    {(reviews.length == 0) && <p>Apartment does not have any reviews yet.</p>}
  <p/>
 </div>
}

const reserved: Reserved[] = [
  {
    startDate: new Date(2023, 12, 7),
    endDate: new Date(2023, 12, 11),
  },
];

const period: Period[] = [
  {
    startDate: new Date(2023, 12, 6),
    endDate: new Date(2023, 12, 19),
  },
];

export const ReservationCalendar = ({ calendarProps, setCalendarProps, periods, reservations }: {  calendarProps: BookingCalendarProps,
  setCalendarProps: React.Dispatch<React.SetStateAction<any>>, periods : Period[] | undefined, reservations: Reserved[]| undefined }) => {
  const user = useUserSelector(state => state)
  let isOwner = false
  if(user.username){
    if(user.isOwner){
      isOwner = true
    }
  }
  const [modalComponent, setModal] = useModal()

  const handleShow = (alert:string) => setModal({show: true, title: "Error", msg: alert});
  
  const handleChange = (e: Date | number) => {
    const isStart = calendarProps.isStart;
    setCalendarProps({
      ...calendarProps,
      [isStart ? "selectedStart" : "selectedEnd"]: e,
    });
    
  };

  const handleChangeRange = (e: (Date | number)[]) => {
    setCalendarProps({
      ...calendarProps,
      selectedStart: e[0],
      selectedEnd: e[1],
    });
  };

  return (
    <div className="calendar">
    <BookingCalendar
      isOwner = {isOwner}
      reserved={reservations}
      period={periods}
      onChange={handleChange}
      onChangeRange={handleChangeRange}
      onOverbook={(day, errorType) => handleShow(errorType)}
      dateOfStartMonth= {new Date()}
      rangeMode= {true}
      {...calendarProps}
    />
      {modalComponent}
  </div>
  );
}

export const HouseSubmission = ({ calendarProps , loadHouse}: {  calendarProps: BookingCalendarProps, loadHouse: () => void}) => {

  let startDate
  let endDate

  if(calendarProps.selectedStart instanceof Date){
    startDate = format(calendarProps.selectedStart, dateFormat)
  }
  if(calendarProps.selectedEnd instanceof Date){
    const date = new Date(calendarProps.selectedEnd)
    endDate = format(date.setDate(date.getDate()+1), dateFormat)
    console.log(endDate)
  }

  /*
  const startDate = calendarProps.selectedStart
      ? format(calendarProps.selectedStart, dateFormat)
      : undefined
  const endDate = calendarProps.selectedEnd
      ? format(calendarProps.selectedEnd, dateFormat)
      : undefined
  */

  return(<div>
  <Row className="text-center justify-content-center">  
    <Card style={{ width: '18rem' }}>
      <Card.Header as="h5">Check In</Card.Header>
      <Card.Body>
        <Card.Title>{startDate}
        </Card.Title>
      </Card.Body>
    </Card>&nbsp;&nbsp;
    <Card style={{ width: '18rem' }}>
      <Card.Header as="h5">Exit</Card.Header>
      <Card.Body>
        <Card.Title>{calendarProps.selectedEnd
            ? format(calendarProps.selectedEnd, dateFormat)
            : ""}
        </Card.Title>
      </Card.Body>
    </Card>
  </Row><p/>
  <UserControl startDate={startDate} endDate={endDate} loadHouse={loadHouse}/>
  </div>  
  ) 
}

export const UserControl = ({startDate, endDate, loadHouse}:
                                {startDate: string|undefined, endDate : string|undefined, loadHouse: () => void}) =>{
  const [selectValue, setSelectValue] = useState(1)
  const navigate = useNavigate();
  const houseId = useHouseIdentifierSelector(state => state.houseId)

  const user = useUserSelector(state => state)
  const username = user.username
  const name = user.name
  const isOwner = user.isOwner


  const [modalComponent, setModal] = useModal()
  const handleShow = (title: string, alert:string) => setModal({show: true, title: title, msg: alert});

  const handleNavigateHouseRes = () => {
    navigate('/reservations');
  }


  const handleAddRent = () => {
    if (endDate && startDate && houseId && name && username) {
      const reservation: ReservationCreateDTO = {
        endDate: endDate,
        startDate: startDate,
        numGuests: selectValue,
        clientId: username
      }

      apartmentsAPI.searchPeriods(houseId, 0, 10).then(data => {
        let i = 0
        for (; i < data.length; i++) {
          let period = data[i]
          if (period.startDate < startDate && startDate < period.endDate)
            break
        }
        console.log(i)
        apartmentsAPI.addReservation(reservation, houseId, i, {headers: {"Authorization": user.userToken}})
            .then(() => {
              loadHouse()
              handleShow("Success", "Successfully added a new reservation!")
            })
            .catch((error: Response) => handleShow("Error", "Error adding reservation:  " + error.statusText))
      })
    }
  }
  const handleAddPeriod = () => {
    if(endDate && startDate && houseId){
      const period : PeriodCreateDTO = {
        endDate: endDate,
        startDate: startDate
      }
      apartmentsAPI.addPeriod(period, houseId, {headers:{"Authorization":user.userToken}})
          .then(() => {loadHouse()
            handleShow("Success", "Successfully added a new period!")})
          .catch((error:Response) => handleShow("Error", "Error adding period:  "+ error.statusText))
    }
  }

  const clientControl = <Row className="justify-content-center m-4">
    <Col md={3} sm={6}><b>Number of Guests</b>
      <Form.Select style={{ width: '10rem' }} onChange={ e =>
          setSelectValue(e.target.value as unknown as number)}>
        <option value="1">One</option>
        <option value="2">Two</option>
        <option value="4">Four</option>
      </Form.Select>
    </Col>
    <Col md={3} sm={6}>
      <p/>
      <Button variant="primary" size="lg" onClick={handleAddRent}>Rent Period</Button>{' '}
    </Col>
    {modalComponent}
  </Row>

  const OwnerControl = <Row className="justify-content-center m-4" md={5}>
    <Button variant="success" onClick={handleAddPeriod}>Add Period</Button>&nbsp;&nbsp;
    <Button variant="primary" onClick={handleNavigateHouseRes}>House Reservations</Button>
    {modalComponent}
  </Row>

  return !username? <div/>: isOwner? OwnerControl : clientControl
}

