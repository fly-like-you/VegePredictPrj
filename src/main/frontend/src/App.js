import React from 'react'
import {Routes, Route} from "react-router-dom";
import Home from "./pages/Home";
import Vegetable from "./pages/Vegetable";


function App() {
    return(
        <Routes>
            <Route path={"/"} element={<Home/>}/>
            <Route path={"/vegetables/:vegetable"} element={<Vegetable></Vegetable>}/>
        </Routes>
    );
}

export default App;
