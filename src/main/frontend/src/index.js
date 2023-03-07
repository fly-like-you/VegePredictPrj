import React, {useState} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './vendor/fontawesome-free/css/all.min.css';
import './css/sb-admin-2.min.css';
import reportWebVitals from './reportWebVitals';
import ScrollButton from "./components/scrollbutton/ScrollButton";
import ContentWrapper from "./components/wrapper/ContentWrapper";



ReactDOM.render(
    <React.StrictMode>
        <div id="wrapper">
            <ContentWrapper>
            </ContentWrapper>
        </div>
        <ScrollButton></ScrollButton>

    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();
