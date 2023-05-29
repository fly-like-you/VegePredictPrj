import React, {useState} from 'react';
import styled from "styled-components";

const ButtonDiv = styled.div`
  width: 200px;
`;
function DropDown({ options, onSelect }) {
    console.log("DropDown", options);
    const [selectedOption, setSelectedOption] = useState(null);
    const handleClick = (event, option) => {
        event.preventDefault();
        setSelectedOption(option);
        onSelect(option);
    };

    return (

        <div className="dropdown">
            <button
                className="btn btn-primary dropdown-toggle"
                type="button"
                id="dropdownMenuButton"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
                style={{ width: '160px' }}
            >
                {selectedOption}
            </button>
            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                {options?.map((option, index) => (
                    <a
                        key={index}
                        className="dropdown-item"
                        href="#"
                        onClick={(event) => handleClick(event, option)}
                    >
                        {option}
                    </a>
                ))}
            </div>
        </div>
    );
}

export default DropDown;