import React from 'react'
import { Navbar, Nav } from 'react-bootstrap';
import styled from "styled-components";
import {BrowserRouter as Router, Switch, Route, Link, BrowserRouter} from 'react-router-dom';

const NavWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%;
  margin: 0 auto;
  
`;

function TopBar() {
    const navStyle = {
        color: "white",
    };

    const gradientStyle = {
        backgroundImage: 'linear-gradient(to right, #4E73DF, #254CC0)',
    };

    return (
        <Navbar style={gradientStyle} variant="dark" expand="lg">
            <NavWrapper>
                <Navbar.Brand style={navStyle} href="/">농산물 가격예측 시스템</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <Nav.Link href="#">더보기</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </NavWrapper>
        </Navbar>
    );
}

export default TopBar;
