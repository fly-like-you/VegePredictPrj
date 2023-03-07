import { useState, useEffect } from "react";
import axios from "axios";
import ChartComponent from "./ChartComponent";


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
        <ChartComponent key={veggie} data={chartData[veggie]} title={veggie}/>
    );
}

export default Chart;

