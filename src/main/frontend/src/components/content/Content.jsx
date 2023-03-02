import React from 'react'
import Chart from "../chart/Chart";
import TopBar from "../topbar/TopBar";

function Content() {
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <TopBar></TopBar>
            <Chart></Chart>
        </div>

    );
}

export default Content;
