import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import axios from "axios";
import styled from "styled-components";
import { Bar } from 'react-chartjs-2';

const CardWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 0 50px 0 50px;
  width: 100%;
  height: 400px;
  max-width: 600px;
`;
const rainbowColors = [
    { r: 255, g: 127, b: 127 },   // Light Red
    { r: 255, g: 196, b: 127 },   // Light Orange
    { r: 255, g: 255, b: 153 },   // Light Yellow
    { r: 178, g: 255, b: 102 },   // Light Green
    { r: 102, g: 178, b: 255 },   // Light Blue
    { r: 191, g: 153, b: 255 },   // Light Indigo
    { r: 255, g: 153, b: 204 },   // Light Violet
];

const ErrorChart = ({ data }) => {
    const chartData = {
        labels: ['Day 1 Error', 'Day 2 Error', 'Day 3 Error', 'Day 4 Error', 'Day 5 Error', 'Day 6 Error', 'Day 7 Error'],
        datasets: data.map((item, index) => {
            const color = rainbowColors[index % rainbowColors.length];
            return {
                label: item.date,
                data: [
                    item.day1Error,
                    item.day2Error,
                    item.day3Error,
                    item.day4Error,
                    item.day5Error,
                    item.day6Error,
                    item.day7Error,
                ],
                fill: false,
                borderColor: `rgba(${color.r}, ${color.g}, ${color.b}, 1)`,
                backgroundColor: `rgba(${color.r}, ${color.g}, ${color.b}, 0.2)`,
            };
        }),
    };

    const options = {
        responsive: true,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Error by Day',
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Error',
                },
                beginAtZero: true,
            },
        },
    };

    return (
        <div>
            <Line data={chartData} options={options} />
        </div>
    );
};



const ErrorChartContainer = ({ vegetable, otherSiteFlag }) => {
    const [data, setData] = useState(null);
    const chartTitle = otherSiteFlag
        ? "일자별 오차율 그래프"
        : "모델링 사이트 오차율 그래프(비교군)";
    useEffect(() => {
        const apiUrl = otherSiteFlag
            ? `/api/error-rate/other/name/${vegetable}`
            : `/api/error-rate/name/${vegetable}`;


        axios.get(apiUrl)
            .then(response => setData(response.data))
            .catch(error => console.log(error));
    }, [vegetable, otherSiteFlag]);

    if (!data) {
        return <div>Loading...</div>;
    }

    return (
        <CardWrapper className="card shadow mb-4">
            <div className="card-header py-3">
                <h6 className="m-0 font-weight-bold text-primary">{vegetable} {chartTitle}</h6>
            </div>
            <div className="card-body">
                {data.length > 0 ? (
                    <ErrorChart data={data} />
                ) : (
                    <p>Loading chart data...</p>
                )}
            </div>
        </CardWrapper>
    );
};

export default ErrorChartContainer;




