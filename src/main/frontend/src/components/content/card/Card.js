import React from 'react';
import styled from 'styled-components';

const CardWrapper = styled.div`
  height: 250px;
  width: 400px;
  background-color: #aaaaaa;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    margin: 30px 30px 30px 30px;
    


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
  display: flex;
  flex-direction: column;
  justify-content: space-between;

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
                </TextWrapper>
            </div>
        </CardWrapper>
    );
}

export default Card;
