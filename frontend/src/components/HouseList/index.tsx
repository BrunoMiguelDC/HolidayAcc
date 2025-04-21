import React, { SetStateAction, useCallback, useEffect, useState } from 'react'
import {Card, Col, Form, Pagination, Row} from 'react-bootstrap'
import { useNavigate } from 'react-router-dom';
import {
    useAppDispatch,
    useHouseIdentifierSelector,
    useHouseSelector,
    useUserSelector
} from '../../store/hooks'
import { loadHouses, setFilter } from '../../store/houses'
import { useInput } from '../util'
import { ApartmentListDTO } from '../../api'
import { store } from '../../store';
import { setHouse } from '../../store/HouseIdentifier';
import { setUserOwner } from '../../store/user';
import { MyPagination } from '../layout';
import {apartmentsAPI} from "../../app/App";

const cardStyle = (selected?:boolean) =>
    ({ width:"200px", 
      backgroundColor: "lightblue", 
      padding: "10px", 
      margin: "10px",
      border : selected ? "2px solid red" : "1px solid black" 
    })


const HouseCardView = ({ idx, house, selected, onClick }: { idx: number, house: ApartmentListDTO, selected?: boolean, onClick: ()=> void }) => 
     <li style={cardStyle(selected)} onClick={onClick}>
        <img width={"100px"} src={house.picture} />
        <p>{house.name}</p>
        <p>by {house.location}</p>
  
    </li> 
    


const HousesList2 = () => { // For the sake of explaining things

    const [selected, setSelected] = useState<number | undefined>(undefined)
    // const [searchTitle, setSearchTitle] = useState<string>("")
    const [inputName, searchName, setSearchName] = useInput("", "Search Name", "")

    // const [searchAuthor, setSearchAuthor] = useState<string>("")
    const [inputLocation, searchLocation, setSearchLocation] = useInput("", "Search Location", "")

    // const [searchServer, setSearchServer] = useState<string>("")
    const [houses, setHouses] = useState<ApartmentListDTO[]>([])

    // const loadBooks = () => { // To search in the server side
    //     fetch(`/books.json${searchServer==""?"":`?q=${searchServer}`}`)
    //     .then( response => response.json())
    //     .then( data => setBooks(data))

    // }
    // useEffect(loadBooks, [searchServer])
    const loadHouses = () => {
        fetch("/houses.json") 
        .then( response => response.json())
        .then( data => setHouses(data))

    }
    useEffect(loadHouses, [])

    const filteredHouses = houses.filter(h => h.name.includes(searchName) && h.location.includes(searchLocation))
    
    return <div>
            {/* <input type="text" 
                   placeholder="Search Server" 
                   value={searchServer} 
                   onChange={e => setSearchServer(e.target.value)}/> */}

            { inputName }
            { inputLocation }
            {/* <input type="text" 
                   placeholder="Search Title" 
                   value={searchTitle} 
                   onChange={e => setSearchTitle(e.target.value)}/> */}
            <HousesListView houses={filteredHouses}/>
            </div>
}


const HousesListView = ({ houses }: { houses: ApartmentListDTO[], }) => {
    const navigate = useNavigate();

    const selectCard = (houseId : number) => {
        store.dispatch(setHouse(houseId))

        navigate('/houseDetails');
    }

    return <div>
     <Row className="row gx-4 gy-4" lg={4} md={3} sm={4}>
      {houses.map((h, i) => 
        <Col key={i}>
        <Card className=" h-100" onClick={() => selectCard(h.apartmentId)} style={{ cursor: "pointer" }}>
            <Card.Img variant={"top"} src={h.picture} />
            <Card.Body>
                <Card.Title>{h.name}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">{h.location}</Card.Subtitle>
                <Card.Text>{h.description.length > 280 ?
                        `${h.description.substring(0, 280)}...` : h.description}
                </Card.Text>
            </Card.Body>
            <Card.Footer>
                <span style={{color: h.available ? "green" : "red", fontWeight: "bold"}}>
                    {h.available? 'Available' : 'Not Available'}
                </span>{" "}
                <b>{h.price} $</b>
            </Card.Footer>
        </Card>
        </Col>
      )}
     </Row>
    </div>
}

const HouseList = () => {
    const user = useUserSelector(state => state)

    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(0)
    const recordsPerPage = 8

    
    const [houses, setHouses] = useState<ApartmentListDTO[]>([])

    const [inputName, searchName, setSearchName] = useInput("", "Search Name", "search")
    const [inputLocation, searchLocation, setSearchLocation] = useInput("", "Search Location", "search")

    // You should use a proxy to avoid CORS issues
    // You should use OpenAPI generated code to access the server!!
    const loadHouses = () => {
         apartmentsAPI.searchApartments(currentPage-1, recordsPerPage, searchName, searchLocation,
            undefined, undefined, undefined, undefined, undefined,
             user.isOwner? user.username: undefined)
            .then(data => {
                setHouses(data.list)
                setTotalPages(data.numPages)
        })
        /*fetch(`/houses.json`)
            .then(response => response.json())
            .then(data => setHouses(data.slice((currentPage-1)*recordsPerPage, currentPage*recordsPerPage)))*/
    }

    useEffect(() => {
        loadHouses()
    }, [currentPage, searchName, searchLocation])

    //const filteredHouses = houses.filter(h => h.name.includes(searchName) && h.location.includes(searchLocation))

    return <div className="container-fluid">
        <Row className="justify-content-md-center" md={2}>
            <Form className="d-flex">
            {inputName}
            {inputLocation}
            </Form>
        </Row>    
        <div><span>&nbsp;</span></div>
        <HousesListView houses={houses}/><p/><p/>
        <MyPagination currentPage={currentPage} totalPages={totalPages} setCurrentPage={setCurrentPage}/>
    </div>
    
}


/*const RdxHousesList = () => {

    const houses = useHouseSelector(state => state.houses)
    const dispatch = useAppDispatch()

    const [selected, setSelected] = useState<number | undefined>(undefined)

    const [inputName, searchName, setSearchName] = useInput("", "Search Name", "search")

    useEffect(() => { dispatch(setFilter(searchName)) }, [searchName])

    const filteredHouses = houses.filter(h => h.name.includes(searchName))

    return <div>
        {inputName}
        <HousesListView houses={filteredHouses}/>
    </div>
}

*/
export default HouseList