import React from 'react'
import Footer from "../content/footer/Footer";
import Content from "../content/Content";
import TopBar from "../topbar/TopBar";
import Banner from "../content/Banner";
import Card from "../content/Card";

function ContentWrapper() {
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <TopBar></TopBar>
            <Banner imageSrc="/images/red-pepper.jpg" title="Red Pepper" />
            <Content></Content>
            <Footer></Footer>
        </div>

    );
}

export default ContentWrapper;
