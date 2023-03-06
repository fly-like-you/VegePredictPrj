import React from 'react'
import Chart from "../chart/Chart";
import styled from 'styled-components';

const ContentWrapper = styled.div`
  padding: 20px;

  @media (min-width: 1191px) {
    max-width: 90%;
  }
`;
function Content() {
    return (
    <ContentWrapper>
        <Chart />
    </ContentWrapper>
    );

}

export default Content;
