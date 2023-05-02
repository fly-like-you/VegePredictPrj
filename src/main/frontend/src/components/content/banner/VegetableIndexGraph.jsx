import React, { useRef, useEffect } from 'react';
import Chart from 'chart.js/auto';

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

const VegetableIndexGraph = ({ data }) => {
    const chartRef = useRef(null);

    useEffect(() => {
        if (!data) return;

        const labels = [];
        const productIndexData = [];

        data.forEach((item) => {
            const {id, date, productIndex} = item;
            labels.push(date);
            productIndexData.push(productIndex);
        });
        console.log(labels, productIndexData);
            const chartData = {
                labels: labels,
                datasets: [
                    {
                        label: 'Vegetable Index',
                        data: productIndexData,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderWidth: 2,
                        lineTension: 0.3,
                        pointRadius: 3,
                    },
                ],
            };

            const myChart = new Chart(chartRef.current, {
                type: 'line',
                data: chartData,
                options: getChartOptions(),

            });
        //
        //     return () => {
        //         myChart.destroy();
        //     };
        }, [data]);

        return <canvas ref={chartRef} />;
}

export default VegetableIndexGraph;
