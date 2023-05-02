import React from 'react';
import styled from 'styled-components';

const StyledGraphContainer = styled.div`
  display: flex;
  flex-direction: column;
  background-color: #f8f9fc;
  border-radius: 5px;
  padding: 1rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2), 0 1px 2px rgba(0, 0, 0, 0.12);
  margin-bottom: 1rem;

  h3 {
    margin-bottom: 1rem;
    color: #5a5c69;
    text-align: center;
  }


`;

const GraphContainer = ({ title, children }) => {
    return (
        <StyledGraphContainer>
            <h3>{title}</h3>
            <div className="graph">{children}</div>
        </StyledGraphContainer>
    );
};

export default GraphContainer;
