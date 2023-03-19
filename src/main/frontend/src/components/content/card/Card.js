import React from 'react';
import styled from 'styled-components';

const CardWrapper = styled.div`
  width: 640px;
  height: 380px;
  background-color: #000;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);

`;

const ImageWrapper = styled.div`
  width: 100%;
  height: 100%;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;

`;

const Image = styled.img`
  border-radius: 30px 0 0 30px;
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;

`;

const TextWrapper = styled.div`
  flex: 1;
  padding: 1.25rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  opacity: inherit

`;

const Title = styled.h5`
  margin-bottom: 0.5rem;
`;

const Info = styled.p`
  margin: 0;
  margin-top: auto;
`;

function Card(props) {
    const { imgSrc, title, text, info, lastUpdated } = props;

    return (
        <CardWrapper className={"row no-gutters"}>
            <div className="col-4">
                <ImageWrapper>
                    <Image src={imgSrc} alt={title} />
                </ImageWrapper>
            </div>
            <div className="col-8">
                <TextWrapper>
                    <Title>{title}</Title>
                    <p>{text}</p>
                    <Info>{info}</Info>
                    <p><small>Last updated {lastUpdated}</small></p>
                </TextWrapper>
            </div>
        </CardWrapper>
    );
}

export default Card;
