import React from 'react'
import styled from 'styled-components';
import GraphCard from "./card/GraphCard";
import PercentageTagCard from "./card/PercentageTagCard";

const ContentWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 70%;
    margin: 0 auto;
    min-width: 1250px;

`;

const SectionWrapper = styled.div`
  width: 100%;
  padding: 10px 10px 20px 10px;
`;

const CardWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  height: 450px;
`;

function Content({ vegetable }) {
    return (
        <ContentWrapper>
            <SectionWrapper>
                <h2>가격 예측 그래프</h2>
            </SectionWrapper>
            <CardWrapper>
                <GraphCard veggie={vegetable}  />
            </CardWrapper>
            <SectionWrapper>
                <h2>세부사항</h2>
            </SectionWrapper>
            <CardWrapper>
                <GraphCard veggie={vegetable}  />
            </CardWrapper>
        </ContentWrapper>
    );
}



export default Content;
