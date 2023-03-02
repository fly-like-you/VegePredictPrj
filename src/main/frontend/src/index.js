import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './vendor/fontawesome-free/css/all.min.css';
import './css/sb-admin-2.min.css';

import App from './App';
import reportWebVitals from './reportWebVitals';
import Chart from "./components/chart/Chart";
import Sidebar from "./components/sidebar/Sidebar";
import ScrollButton from "./components/scrollbutton/ScrollButton";
import Footer from "./components/footer/Footer";
import ContentWrapper from "./components/wrapper/ContentWrapper";

// <div className="row">
//     <div className="col-3">
//         <Sidebar />
//     </div>
//     <div className="col-9">
//         <Chart></Chart>
//     </div>
// </div>

ReactDOM.render(
    <React.StrictMode>
        <div id="wrapper">
            <Sidebar></Sidebar>
            <ContentWrapper>

            </ContentWrapper>
        </div>
        <ScrollButton></ScrollButton>

    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
