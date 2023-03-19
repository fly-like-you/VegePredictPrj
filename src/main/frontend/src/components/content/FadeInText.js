import React from 'react';
import '../../css/FadeInText.css';

function FadeInText(props) {
    return (
        <div className="fade-in">
            <h1>{props.text}</h1>
        </div>
    );
}

export default FadeInText;
