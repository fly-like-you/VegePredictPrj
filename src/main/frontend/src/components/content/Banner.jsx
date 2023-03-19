import styled from "styled-components";
import React from "react";
import FadeInText from "./FadeInText";

const BannerImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: brightness(90%) blur(5px);
`;

const BannerWrapper = styled.div`
  height: 500px;
  width: 100%;
  position: relative;
  margin-bottom: 100px;
`;

const BannerText = styled.h1`
  position: absolute;
  bottom: 70%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
`;

function Banner({ imageSrc, title }) {
    return (
        <BannerWrapper>
            <BannerImage src={imageSrc} alt={title} />

            <BannerText>
                <FadeInText text={title}></FadeInText>
            </BannerText>
        </BannerWrapper>
    );
}
export default Banner;
