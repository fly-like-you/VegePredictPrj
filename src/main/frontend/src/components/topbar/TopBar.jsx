import React from 'react'
import { Navbar, Nav } from 'react-bootstrap';



function TopBar() {
    const navStyle = {
        color: "white",
    };

    const gradientStyle = {
        backgroundImage: 'linear-gradient(to right, #4E73DF, #254CC0)',
    };

    return (
        <Navbar style={gradientStyle} variant="dark" expand="lg">
            <Navbar.Brand style={navStyle} href="#">React Bootstrap Navbar</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    <Nav.Link href="#">Link</Nav.Link>
                    <Nav.Link href="#">Link</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
}

export default TopBar;
