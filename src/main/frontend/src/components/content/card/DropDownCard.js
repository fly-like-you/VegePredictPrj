import React, { useState } from 'react';
import styled from 'styled-components';
import DropDown from "../../DropDown";
import Chart from "../chart/Chart";

const CardWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 0 50px 0 50px;
  width: 100%;
  height: 400px;
  max-width: 600px;
`;

const CardHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const OptionDiv = styled.div`
  display: flex;
  align-items: center;
`;

function DropDownCard({ veggie }) {
    const [selectedOption, setSelectedOption] = useState('Radish');

    function handleDropdownSelect(option) {
        setSelectedOption(option);
    }

    return (
        <CardWrapper className="card shadow mb-4">
            <CardHeader className="card-header py-3">
                <h6 className="m-0 font-weight-bold text-primary">
                    {veggie} 일간 가격 변화 그래프
                </h6>
                <OptionDiv>
                    <DropDown
                        options={['Radish', 'RedPepper', 'Cucumber']}
                        onSelect={handleDropdownSelect}
                    />

                    <i
                        className="fas fa-info-circle ml-2"
                        data-toggle="tooltip"
                        data-placement="left"
                        title="This is a tooltip"
                    ></i>
                </OptionDiv>
            </CardHeader>
            <div className="card-body">
                <Chart key={selectedOption} veggie={selectedOption} />
            </div>
        </CardWrapper>
    );
}

export default DropDownCard;