import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './css/sb-admin-2.min.css';
import reportWebVitals from './reportWebVitals';
import App from "./App";
import {BrowserRouter} from "react-router-dom";



ReactDOM.render(
    <React.StrictMode>
        <BrowserRouter>
            <App>
            </App>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();
