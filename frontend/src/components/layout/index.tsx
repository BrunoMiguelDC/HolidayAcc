import { ReactNode } from 'react'
import { Link, useLocation } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Login } from '../util';
import { Pagination } from 'react-bootstrap';
import { store } from '../../store';
import { setUserOwner } from '../../store/user';

const NAVBAR_TEXTS = [{page:"/", text: "Search Houses"}, 
 {page:"/houseDetails", text: "House Details"},
 {page:"/reservations", text: "Reservations"},
 {page:"/reservationDetails", text: "Reservation Details"},
 {page:"/ownerHouses", text: "My houses"}]

interface BannerProps {
    title: string
}
export const Banner = ({ title }: BannerProps) => <div>{ title }</div>

const headerStyle = { backgroundColor: 'lightblue', padding: '10px' }

export const Header = (props: { title:string, children: ReactNode }) =>
    <header style={headerStyle}>
        <Banner title={props.title}/>
        {props.children}
    </header>

export const Container_props = (props: { children?: ReactNode }) => <div>{ props.children }</div>
export const Footer = () => <footer></footer>


export const NavigationBar = (props: { menuName: string }) => {
  const location = useLocation();
  const textToShow = NAVBAR_TEXTS.find(el => el.page === location.pathname)?.text

  const handleClickHome = () => {
    store.dispatch(setUserOwner(false))
  }

    return (
        <Navbar expand="lg" className="bg-secondary p-2 bg-opacity-75" >
          <Container>
            <Navbar.Brand>{textToShow}</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className="me-auto">
                <Nav.Link as={Link} to="/" onClick={() => handleClickHome()}>Home</Nav.Link>
              </Nav>
              <Login/>
            </Navbar.Collapse>
          </Container>
        </Navbar>
      );
}

export const MyPagination = ( { currentPage, totalPages, setCurrentPage }: { currentPage: number, totalPages: number, setCurrentPage: (page:number)=> void } ) => {
  let items = []
  if(currentPage > 1){
      items.push(<Pagination.Prev key="prev" onClick={() => setCurrentPage(currentPage-1)}/>)
  }
  for(let page = 1; page <= totalPages;page++){
      items.push(
          <Pagination.Item key={page} data-page={page} active={page === currentPage} onClick={() => setCurrentPage(page)}> 
          {page} 
          </Pagination.Item>
      )
  }
  if(currentPage < totalPages){
      items.push(<Pagination.Next key="next" onClick={() => setCurrentPage(currentPage + 1)}/>)
  }
  return <div>
  <Pagination className='justify-content-center'>
      {items}
  </Pagination>
  </div>
}
