import React from 'react'
import Footer from "../content/footer/Footer";
import Content from "../content/Content";
import TopBar from "../topbar/TopBar";
import Banner from "../content/Banner";
import PercentageTagCard from "../content/card/PercentageTagCard";
import styled from "styled-components";
import MoneyTagCard from "../content/card/MoneyTagCard";



const TagCardSlider = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
`;
function ContentWrapper() {
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <TopBar></TopBar>
            <Banner imageSrc="/images/red-pepper.jpg" title="Red Pepper" />

            <marquee behavior="scroll" direction="right" scrollamount="5">
                <TagCardSlider>
                    <PercentageTagCard percentage={60}></PercentageTagCard>
                    <PercentageTagCard percentage={50}></PercentageTagCard>
                    <PercentageTagCard percentage={40}></PercentageTagCard>
                    <MoneyTagCard></MoneyTagCard>
                </TagCardSlider>

            </marquee>
            <Content></Content>
            <Footer></Footer>
        </div>

    );
}

export default ContentWrapper;
