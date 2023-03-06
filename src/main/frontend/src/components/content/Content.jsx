import React from 'react'
import styled from 'styled-components';
import Card from "./card/Card";
import PercentageTagCard from "./card/PercentageTagCard";

const ContentWrapper = styled.div`
    border: 1px solid black;
    flex-direction: column;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 70%;
    margin: 0 auto;
`;

function Content() {
    return (
        <ContentWrapper>
            <Card veggie={'red pepper'}/>

            <Card veggie={'cucumber'}/>
        </ContentWrapper>

    );
}


export default Content;
