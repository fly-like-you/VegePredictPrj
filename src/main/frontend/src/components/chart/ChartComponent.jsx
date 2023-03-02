import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

function getChartColor(vegetable) {
    switch (vegetable) {
        case 'carrot':
            return 'orange';
        case 'radish':
            return 'red';
        case 'cucumber':
            return 'green';
        default:
            return 'black';
    }
}

function getChartData(data) {
    const chartData = {};

    // 모든 데이터를 순회하며 각 농산물의 가격 변동 내역을 chartData에 추가
    data.forEach(product => {
        const { name, price, date } = product;
        if (!chartData[name]) {
            chartData[name] = { label: name, data: [], fill: false, borderColor: getChartColor(name), borderWidth: 2 };
        }
        chartData[name].data.push({ x: date, y: price });
    });

    return chartData;
}

function getChartOptions() {
    return {
        responsive: true,
        legend: {
            display: true,
            position: 'bottom'
        },
        scales: {
            xAxes: [{
                type: 'time',
                time: {
                    unit: 'day',
                    tooltipFormat: 'YYYY-MM-DD'
                },
                ticks: {
                    autoSkip: true,
                    maxTicksLimit: 20
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Date'
                }
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Price'
                }
            }]
        }
    };
}


function ChartComponent(props) {
    const chartRef = useRef(null);

    useEffect(() => {
        // data 객체를 가공해서 새로운 객체를 만듭니다.
        const chartData = {};
        props.data.forEach((item) => {
            const { date, price } = item;
            if (!chartData[props.title]) {
                chartData[props.title] = [];
            }
            chartData[props.title].push({ x: date, y: price });
        });
        console.log(chartData[props.title])
        // 차트 데이터와 옵션을 설정합니다.
        const myChart = new Chart(chartRef.current, {
            type: "line",
            data: {
                datasets: [
                    {
                        label: props.title,
                        data: chartData[props.title],
                        fill: false,
                        borderColor: getChartColor(props.title),
                        borderWidth: 2,
                    },
                ],
            },
            options: getChartOptions(),
        });

        // 컴포넌트가 언마운트될 때 차트를 제거합니다.
        return () => {
            myChart.destroy();
        };
    }, [props.data, props.title]);

    return <canvas ref={chartRef} />;
}





export default ChartComponent;

