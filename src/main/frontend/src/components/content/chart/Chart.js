import { useState, useEffect } from "react";
import axios from "axios";
import ChartComponent from "./ChartComponent";

import styled from 'styled-components';
import Card from "../card/Card";

const ChartWrapper = styled.div`
  width: 600px;
  height: 100%;
`;

function Chart({ veggie }) {
    const [data, setData] = useState(null);
    useEffect(() => {
        axios.get(`/api/products/name/${veggie}`)
            .then(response => setData(response.data))
            .catch(error => console.log(error));
    }, [veggie]);

    if (!data) {
        return <div>Loading...</div>;
    }

    const chartData = {};
    data.forEach(product => {
        if (!chartData[product.name]) {
            chartData[product.name] = [{date: product.date, price: product.price}];
        } else {
            chartData[product.name].push({date: product.date, price: product.price});
        }
    });

    return (
        <div>

            <ChartWrapper>
                <ChartComponent key={veggie} data={chartData[veggie]} title={veggie}/>
            </ChartWrapper>
        </div>
    );
}

export default Chart;

