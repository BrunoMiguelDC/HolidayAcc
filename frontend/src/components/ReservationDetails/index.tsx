import {Button, FloatingLabel, Form, Row} from "react-bootstrap"
import {useReservationIdentifierSelector, useUserSelector} from "../../store/hooks"
import {ReservationStateCreateDTO, ReservationStateDTO, ReservationStateUpdateDTO, ReviewDTO} from "../../api";
import {reservationsAPI} from "../../app/App";
import {format} from "date-fns";
import {dateFormat, useModal} from "../util";
import {store} from "../../store";
import {setReservationState} from "../../store/ReservationIdentifier";
import {ChangeEvent, useState} from "react";
import {Rating} from "react-simple-star-rating";
import NameEnum = ReservationStateCreateDTO.NameEnum;


export const ReservationUserControl = () => {
  //store.dispatch(setUserOwner(true))
  const user = useUserSelector(state => state)
  const username = user.username
  const isOwner = user.isOwner
  const reservationId = useReservationIdentifierSelector(state => state.reservationId)
  const reservationState = useReservationIdentifierSelector(state => state.reservationState)
  const [modalComponent, setModal] = useModal()
  const handleShow = (title: string, alert:string) => setModal({show: true, title: title, msg: alert});

  const handleChangeState = (nextStateName: ReservationStateDTO.NameEnum, successMsg:string, errorMsg:string) => {
    if(reservationId){
      const newState : ReservationStateUpdateDTO = {
        endDate: format(new Date(), dateFormat),
        nextState: {name: nextStateName, startDate: format(new Date(), dateFormat)}
      }
      reservationsAPI.updateState(newState, reservationId, {headers:{"Authorization":user.userToken}})
          .then(data => {
            store.dispatch(setReservationState(data.nextState.name))
            handleShow("Success", successMsg)})
          .catch((error:Response) => handleShow("Error", errorMsg + error.statusText))
    }
  }

  const handleCancel = () => {
    if(reservationId){
      const newState : ReservationStateUpdateDTO = {
        endDate: format(new Date(), dateFormat),
        nextState: {name: NameEnum.CANCELED, startDate: format(new Date(), dateFormat)}
      }
      reservationsAPI.deleteReservation(reservationId, {headers:{"Authorization":user.userToken}})
          .then(data => {
            store.dispatch(setReservationState(NameEnum.CANCELED))
            handleShow("Success", "Successfully canceled reservation")})
          .catch((error:Response) => handleShow("Error", "Error canceling reservation:  "+ error.statusText))

      }
  }

  const clientControl = 
    <Row className="justify-content-center m-4">
        {reservationState == ReservationStateDTO.NameEnum.BOOKED && <Button variant="success" size="lg"
                                                                     onClick={() => handleChangeState(NameEnum.OCCUPIED, "Successfully checked in", "Error doing check in:  ")}>
          Check in</Button>}
        {reservationState == ReservationStateDTO.NameEnum.OCCUPIED && <Button variant="success" size="lg"
                                                                       onClick={() => handleChangeState(NameEnum.AWAITINGREVIEW, "Successfully checked out", "Error doing check out:  ")}>
          Check out</Button>}
        <p/><p/><p/>
        {reservationState == ReservationStateDTO.NameEnum.UNDERCONSIDERATION && <Button variant="danger" size="lg" onClick={handleCancel}>
          Cancel Reservation</Button>}
      {modalComponent}
    </Row>
    
  
  const OwnerControl = 
    <Row className="justify-content-center m-4">
        {reservationState == ReservationStateDTO.NameEnum.UNDERCONSIDERATION && <Button variant="success" size="lg"
                                                                                 onClick={() => handleChangeState(NameEnum.BOOKED, "Successfully checked in", "Error doing check in:  ")}>
          Accept Reservation</Button>}
        <p/><p/><p/>
      {reservationState == ReservationStateDTO.NameEnum.UNDERCONSIDERATION && <Button variant="danger" size="lg" onClick={handleCancel}>
        Cancel Reservation</Button>}
      {modalComponent}
    </Row>

  
  return !username? <div/>: isOwner? OwnerControl : clientControl
}

export const UserReview = ({review, setReview}: {review: ReviewDTO|undefined, setReview:React.Dispatch<React.SetStateAction<ReviewDTO|undefined>>}) => {

  const user = useUserSelector(state => state)
  const username = user.username
  const reservationId = useReservationIdentifierSelector(state => state.reservationId)
  const reservationState = useReservationIdentifierSelector(state => state.reservationState)
  const isOwner = user.isOwner
  const [modalComponent, setModal] = useModal()
  const [inputText, setInputText] = useState("")
  const [ratingValue, setRatingValue] = useState(0)
  const handleShow = (title: string, alert:string) => setModal({show: true, title: title, msg: alert});

  const handleSubmit = () => {
    if(reservationId){
      const reviewCreateDTO = {text:inputText, rating:ratingValue, date:format(new Date(), dateFormat)}
      reservationsAPI.addReview(reviewCreateDTO, reservationId,{headers:{"Authorization":user.userToken}})
        .then(data => {
            setReview({text:inputText, rating:ratingValue})
            store.dispatch(setReservationState(NameEnum.CLOSED))
            handleShow("Success", "Added review")
          })
          .catch((error:Response) =>  handleShow("Error", "Error adding review:  "+ error.statusText))
    }
  }

  const handleRating = (rate: number) => {
    setRatingValue(rate) //rate/2?
  }

  const handleInputTextChange = (e:ChangeEvent<HTMLInputElement>) => {
      setInputText(e.target.value)
  }


  const form = <div>
  <FloatingLabel controlId="floatingTextarea2" label="Write a Review!">
      <Form.Control as="textarea" placeholder="Leave a comment here" style={{ height: '200px'}} onChange={handleInputTextChange} value={inputText}></Form.Control>
  </FloatingLabel><br/>
    <Rating
        allowFraction
        onClick={handleRating}
        showTooltip
        tooltipArray={[
          'Terrible',
          'Terrible+',
          'Bad',
          'Bad+',
          'Average',
          'Average+',
          'Great',
          'Great+',
          'Awesome',
          'Awesome+'
        ]}
        transition
    />
      <p></p>
    <Row><Button variant="primary" className="justify-content-center" onClick={handleSubmit}>Submit</Button></Row>
    <p></p>
    {modalComponent}
  </div>

  return review?.text? <div>{review.text}<p>Rating: <Rating
      allowFraction
      showTooltip
      initialValue={review.rating}
      tooltipArray={[
          'Terrible',
          'Terrible+',
          'Bad',
          'Bad+',
          'Average',
          'Average+',
          'Great',
          'Great+',
          'Awesome',
          'Awesome+'
      ]}
      transition
      readonly={true}
  /></p><p></p></div> : !isOwner && reservationState == NameEnum.AWAITINGREVIEW? form : <div>No review</div>
}