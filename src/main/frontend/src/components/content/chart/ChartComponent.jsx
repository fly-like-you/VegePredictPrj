import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';
import styled from 'styled-components';

const StyledCanvas = styled.canvas`
  width: 100%;
  height: 100%;
`;
function getChartColor(vegetable) {
    const colors = [
        'rgb(255, 99, 132)', // 깻잎(일반)
        'rgb(54, 162, 235)', // 미나리(일반)
        'rgb(255, 205, 86)', // 시금치(일반)
        'rgb(75, 192, 192)', // 양파(일반)
        'rgb(255, 159, 64)', // 청양
        'rgb(153, 102, 255)', // 토마토(일반)
        'rgb(255, 99, 64)', // 파프리카(일반)
        'rgb(54, 162, 99)' // 풋고추(전체)
    ];

    const index = [
        '깻잎(일반)',
        '미나리(일반)',
        '시금치(일반)',
        '양파(일반)',
        '청양',
        '토마토(일반)',
        '파프리카(일반)',
        '풋고추(전체)'
    ].indexOf(vegetable);

    if (index !== -1) {
        return colors[index];
    } else {
        // 농산물에 해당하는 색상이 없을 경우, 기본 색상을 반환하거나 예외 처리를 진행할 수 있습니다.
        return 'rgb(0, 0, 0)'; // 예시로 검은색 반환
    }
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

    return <StyledCanvas ref={chartRef} />;
}





export default ChartComponent;

