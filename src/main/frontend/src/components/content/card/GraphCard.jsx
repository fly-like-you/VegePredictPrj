import React from "react";
import Chart from '../chart/Chart.js'

import styled from "styled-components";

const CardWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 0 50px 0 50px;
  width: 100%;
  height: 400px;
  max-width: 600px;
`;

function GraphCard({ veggie }) {
    return (
        <CardWrapper className="card shadow mb-4">
            <div className="card-header py-3">
                <h6 className="m-0 font-weight-bold text-primary">{veggie} 일간 가격 변화 그래프</h6>
            </div>
            <div className="card-body">
                <Chart veggie={veggie}/>
            </div>
        </CardWrapper>
    );
}


export default GraphCard;
