import React from 'react'
import Footer from "../footer/Footer";
import Content from "../content/Content";
import TopBar from "../topbar/TopBar";

function ContentWrapper() {
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <TopBar></TopBar>

            <Content></Content>
            <Footer></Footer>
        </div>

    );
}

export default ContentWrapper;
