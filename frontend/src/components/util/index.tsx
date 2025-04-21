import { SetStateAction, useState } from 'react'
import { store } from '../../store'
import { useUserSelector } from '../../store/hooks'
import  { setUser, setUserOwner } from '../../store/user'
import {NavDropdown, Form, Row, Col, Button, Modal} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import {authAPI, usersAPI} from "../../app/App";
import {UserPasswordlessDTO} from "../../api";

export const dateFormat = "yyyy-MM-dd";
export const useInput = (
    initialValue: string,
    placeholder: string,
    type: string
): [JSX.Element, string, React.Dispatch<SetStateAction<string>>] => {
    const [text, setText] = useState<string>(initialValue);

    const input = (
        <Form.Control
            type={type}
            placeholder={placeholder}
            className="me-2"
            aria-label="search"
            value={text}
            onChange={(e) => setText(e.target.value)}
        />
    );

    return [input, text, setText];
};

export const useModal = (): [JSX.Element,
    React.Dispatch<SetStateAction<{ title: string, msg: string; show: boolean }>>] => {

    const [modal, setModal] = useState({show: false, title: "", msg: ""});
    const handleClose = () => setModal({show: false, title: "", msg: ""});
    const modalComponent = (
        <Modal centered
               aria-labelledby="contained-modal-title-vcenter"
               show={modal.show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>{modal.title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{modal.msg}</Modal.Body>
        </Modal>
    );
    return [modalComponent, setModal];
};

export const Login = () => {
    const navigate = useNavigate();
    const user = useUserSelector(state => state)

    const [inputUsername, inUsername, setUsername] = useInput("", "Username", "search")
    const [inputPassword, inPassword, setPassword] = useInput("", "Password", "password")

    const [modalComponent, setModal] = useModal()
    const handleShow = (alert:string) => setModal({show: true, title: "Error", msg: alert});

    const handleLogout = (e:any) => {
        e.preventDefault();
        store.dispatch(setUser({name: undefined, username:undefined, userToken:undefined} ))
        store.dispatch(setUserOwner(false))
        navigate('/');
    }

    const handleSignIn = (e:any) => { 
        e.preventDefault();

        authAPI.login( {
            username: inUsername,
            password: inPassword
        }).then((response) =>{
            const userToken = response.headers.get("Authorization")
            usersAPI.getUser(inUsername, {headers:{"Authorization":userToken}}).then(
                data => {
                    store.dispatch(setUser({name: data.name, username:inUsername, userToken: userToken}))
                })
            }
        ).catch((error:Response) => handleShow("Error signing in:  "+ error.statusText))


        setPassword("")
        setUsername("")
    }

    const handleClickReservations = () =>{
        store.dispatch(setUserOwner(false))
        navigate('/reservations');
    }

    const handleClickHouses = () =>{
        store.dispatch(setUserOwner(true))
        navigate('/ownerHouses');
    }


    const logout = <NavDropdown title={<div className='profile-icon'> <span>
        <img  src="https://i.pinimg.com/originals/f2/15/d9/f215d9a827969950271d7d975c91e2a4.png" width="55" height="55"/>
        &nbsp;&nbsp;{user.name}
        </span></div>} 
        id="basic-nav-dropdown">
      <NavDropdown.Item onClick={handleClickReservations}>My Reservations</NavDropdown.Item>
      <NavDropdown.Item onClick={handleClickHouses}>My Houses</NavDropdown.Item>
      <NavDropdown.Divider />
      <NavDropdown.Item onClick={handleLogout}>Sign out</NavDropdown.Item>
      </NavDropdown>


    const login = <div>
    <Form className="d-flex">
        {inputUsername}{" "}
        {inputPassword}{" "}
    <Button type="submit" onClick={handleSignIn} >Login</Button>
    </Form>
        {modalComponent}
    </div>

    return user.username ? logout : login
}


