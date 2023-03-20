import React from 'react'
import {Routes, Route, useParams} from "react-router-dom";
import Home from "./pages/Home";
import Vegetable from "./pages/Vegetable";

function App() {

    return(
        <Routes>
            <Route path={"/"} element={<Home/>}/>
            <Route path={"/:vegetable"} element={<VegetablePage />} />
            <Route path={"*"} element={<NotFound />} />
        </Routes>
    );
}
function VegetablePage() {
    const { vegetable } = useParams();
    return <Vegetable vegetable={vegetable} />;
}

function NotFound() {
    return <h1>404 Not Found</h1>;
}
export default App;
