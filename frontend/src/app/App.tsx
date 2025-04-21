import {  NavigationBar } from '../components/layout'
import { BrowserRouter, Routes, Route } from "react-router-dom";


import { Provider } from 'react-redux'
import { store } from '../store'

import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import HouseDetail from '../pages/HouseDetails';
import ReservationList from '../pages/Reservations';
import Houses from '../pages/Houses';
import ReservationDetails from '../pages/ReservationDetails';
import OwnerHouses from '../pages/OwnerHouses';
import HouseReservations from '../pages/HouseReservations';
import {ApartmentsApi, UsersApi, AuthApi, ReservationsApi} from "../api";

export const usersAPI : UsersApi = new UsersApi()
export const apartmentsAPI : ApartmentsApi = new ApartmentsApi()
export const reservationsAPI : ReservationsApi = new ReservationsApi()
export const authAPI : AuthApi = new AuthApi()


function App() {
  return (
    <BrowserRouter>
    <div className="App">
      <NavigationBar menuName={'Search Houses'}/>
        <Routes>
          <Route path="/" element={<Houses/>}/>
          <Route path="/houseDetails" element={<HouseDetail/>}/>
          <Route path="/reservations" element={<ReservationList/>}/>
          <Route path="/reservationDetails" element={<ReservationDetails/>}/>
          <Route path="/ownerHouses" element={<OwnerHouses/>}/>
          <Route path="/houseReservations" element={<HouseReservations/>}/>
      </Routes>
    </div>
    </BrowserRouter>
  );
}

const RdxApp = () => 
  <Provider store={store}>
    <App/>
  </Provider>



export default RdxApp;
