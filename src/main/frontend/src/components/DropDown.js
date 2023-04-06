import React, { useState } from 'react';
import styled from 'styled-components';

const DropdownWrapper = styled.div`
  position: relative;
`;

function DropDown({ options, onSelect }) {
    const [selectedOption, setSelectedOption] = useState(options[0]);

    const handleDropdownChange = (event) => {
        const { value } = event.target.dataset;
        setSelectedOption(value);
        onSelect(value);
    };

    return (
        <DropdownWrapper>
            <button
                className="btn btn-primary dropdown-toggle"
                type="button"
                id="dropdownMenuButton"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
            >
                {selectedOption}
            </button>
            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                {options.map((option, index) => (
                    <a
                        key={index}
                        className="dropdown-item"
                        href="#"
                        data-value={option}
                        onClick={handleDropdownChange}
                    >
                        {option}
                    </a>
                ))}
            </div>
        </DropdownWrapper>
    );
}

export default DropDown;
