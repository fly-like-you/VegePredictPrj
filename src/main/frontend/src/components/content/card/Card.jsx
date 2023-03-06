import styled from "styled-components";
import React from "react";
import Chart from '../chart/Chart.js'

function Card({veggie}) {
    return (
        <div className="card shadow mb-4">
            <div className="card-header py-3">
                <h6 className="m-0 font-weight-bold text-primary">{veggie} 일간 가격 변화 그래프</h6>
            </div>

            <div className="card-body">
                <Chart veggie={veggie}/>

            </div>
        </div>
    );
}

export default Card;
