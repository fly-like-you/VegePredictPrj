import React from 'react'
import Chart from "./chart/Chart";
import styled from 'styled-components';
import Banner from "./Banner";
import Card from "./Card";

const ContentWrapper = styled.div`
    flex-direction: column;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid black;
    width: 60%;
    margin: 0 auto;
`;

function Content() {
    return (
        <ContentWrapper>
            <div style={{ display: "flex", flexDirection: "column" }}>
            </div>
            <Card>

            </Card>

        </ContentWrapper>
    );
}


export default Content;
