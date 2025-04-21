import { useEffect, useState } from "react"
import { useInput } from "../util"
import { Card, Col, Form, ListGroup, Row } from "react-bootstrap"
import { MyPagination } from "../layout"
import { useNavigate } from "react-router-dom"
import { store } from "../../store"
import {setReservationId, setReservationState} from "../../store/ReservationIdentifier"
import {useHouseIdentifierSelector, useUserSelector} from "../../store/hooks";
import {apartmentsAPI, usersAPI} from "../../app/App";
import {ReservationApartmentsListDTO, ReservationStateCreateDTO, ReservationUsersListDTO} from "../../api";
import {setHouse} from "../../store/HouseIdentifier";
import NameEnum = ReservationStateCreateDTO.NameEnum;

const ReservationsListView = ({reservations}:{reservations: ReservationUsersListDTO[] | ReservationApartmentsListDTO[]}) => {
    const navigate = useNavigate();
    const user = useUserSelector(state => state)
    const isOwner = user.isOwner
    const apartmentId = useHouseIdentifierSelector(state => state.houseId)
    const selectCard = (reservationId : string, reservationState: NameEnum) => {
        store.dispatch(setReservationId(reservationId))
        store.dispatch(setReservationState(reservationState))
        navigate('/reservationDetails');
    }
    const onClickReservation = (r:ReservationUsersListDTO | ReservationApartmentsListDTO) =>{
        const reservationId = r.reservationNum.toString() + "-" +
            (isOwner? (r as ReservationApartmentsListDTO).client.username:user.username) + "-" +
            r.periodNum.toString() + "-" +
            (isOwner? apartmentId:(r as ReservationUsersListDTO).apartment.apartmentId.toString())
        selectCard(reservationId, r.state.name)
    }

    return <div>
     <Row className="row gx-4 gy-4" lg={4} md={3} sm={4}>
      {reservations.map((r, i) => 
        <Col key={i}>
        <Card className=" h-100" onClick={() => onClickReservation(r)} style={{ cursor: "pointer" }}>
            {!isOwner && <Card.Img variant={"top"} src={(r as ReservationUsersListDTO).apartment.picture} />}
            <Card.Body>
                <ListGroup variant="flush">
                    <ListGroup.Item>
                        {!isOwner && <Card.Title>{(r as ReservationUsersListDTO).apartment.name}</Card.Title>}
                        {!isOwner && <Card.Subtitle className="mb-2 text-muted">{(r as ReservationUsersListDTO).apartment.location}</Card.Subtitle>}
                        {isOwner && <Card.Title>{(r as ReservationApartmentsListDTO).client.name}</Card.Title>}
                        {isOwner && <Card.Subtitle className="mb-2 text-muted">{(r as ReservationApartmentsListDTO).client.email}</Card.Subtitle>}
                    </ListGroup.Item>
                    <ListGroup.Item>
                        <Row className="g-0">
                            <Col >
                                <b>Check in </b><br/>
                                <b>Exit </b>
                            </Col>
                            <Col >
                                {r.startDate}<br/>
                                {r.endDate}
                            </Col>
                        </Row>
                    </ListGroup.Item>    
                </ListGroup>
            </Card.Body>
            <Card.Footer>
                <b>State: </b>{" "}
                <b>{r.state.name}</b>
            </Card.Footer>
        </Card>
        </Col>
      )}
     </Row>
    </div>
}


const ReservationList = () => {
    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(0)
    const recordsPerPage = 8


    const [reservations, setReservations] = useState<ReservationUsersListDTO[] | ReservationApartmentsListDTO[]>([])

    const [inputName, searchName, setSearchName] = useInput("", "Search House Name", "search")
    const [inputLocation, searchLocation, setSearchLocation] = useInput("", "Search House Location", "search")

    const user = useUserSelector(state => state)
    const houseId = useHouseIdentifierSelector(state => state.houseId)


    /*
    const loadReservations = () => {
        fetch(`/userReservations.json`)
            .then(response => response.json())
            .then(data => setReservations(data.slice((currentPage-1)*recordsPerPage, currentPage*recordsPerPage)))
    }*/


    const loadReservations = () => {
        if(user.isOwner && houseId){
            apartmentsAPI.searchApartmentReservations(houseId, currentPage-1, recordsPerPage,
                undefined, undefined, undefined,undefined,
                {headers:{"Authorization":user.userToken}})
                .then(data => {
                    setReservations(data.list)
                    setTotalPages(data.numPages)
            })
        } else if(user.username){
            usersAPI.searchUserReservations(user.username, currentPage-1, recordsPerPage,
                undefined, undefined, undefined, undefined,
                {headers:{"Authorization":user.userToken}})
                .then(data => {
                    setReservations(data.list)
                    setTotalPages(data.numPages)
            })
        }
    }

    useEffect(() => {
        loadReservations()
    }, [currentPage, searchName, searchLocation])

    //const filteredReservations = reservations.filter(r => r.apartmentShortDTO.name.includes(searchName)
    // && r.apartmentShortDTO.location.includes(searchLocation))

    return <div className="container-fluid">
        <Row className="justify-content-md-center" md={2}>
            <Form className="d-flex">
            {inputName}
            {inputLocation}
            </Form>
        </Row>    
        <div><span>&nbsp;</span></div>
        <ReservationsListView reservations={reservations}/><p/><p/>
        <MyPagination currentPage={currentPage} totalPages={totalPages} setCurrentPage={setCurrentPage}/>
    </div>
    
} 

export default ReservationList