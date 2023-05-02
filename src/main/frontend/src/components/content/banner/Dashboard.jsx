import React, { useEffect, useState } from 'react';
import GraphContainer from './GraphContainer';
import VegetableIndexGraph from "./VegetableIndexGraph";
import axios from "axios";
import styled from 'styled-components';

const StyledDashboard = styled.div`
  /* 스타일을 여기에 추가하세요 */
  border-radius: 5px;
  width: 100%;
  
`;

const Dashboard = () => {
    const [graphData, setGraphData] = useState(null);

    useEffect(() => {
        axios
            .get('/api/vegetable-index')
            .then((response) => {
                setGraphData(response.data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }, []);

    if (!graphData) {
        return <div>Loading...</div>;
    }

    console.log("Dashboard.js", graphData);

    return (
        <StyledDashboard>
            <GraphContainer title="농산물 가격 지수">
                <VegetableIndexGraph data={graphData} />
            </GraphContainer>
        </StyledDashboard>
    );
};

export default Dashboard;
