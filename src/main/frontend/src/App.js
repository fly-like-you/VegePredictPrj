// src/main/frontend/src/App.js
// React의 주요 로직을 구현
import React, {useEffect, useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button } from 'react-bootstrap';
import Sidebar from './components/sidebar/Sidebar'

function App() {

    return (
        <Sidebar />
    )


}

export default App;