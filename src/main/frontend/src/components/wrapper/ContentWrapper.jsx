import React from 'react'
import Footer from "../footer/Footer";
import Content from "../content/Content";

function ContentWrapper() {
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <Content></Content>
            <Footer></Footer>
        </div>

    );
}

export default ContentWrapper;
