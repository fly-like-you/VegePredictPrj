import React from 'react'
import Footer from "../components/content/footer/Footer";
import Content from "../components/content/Content";
import TopBar from "../components/topbar/TopBar";
import Banner from "../components/content/Banner";
import PercentageTagCard from "../components/content/card/PercentageTagCard";
import styled from "styled-components";
import MoneyTagCard from "../components/content/card/MoneyTagCard";



const TagCardSlider = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
`;
function Vegetable( { vegetable }) {
    console.log(vegetable)
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <TopBar></TopBar>
            <Banner imageSrc={`/images/${vegetable}.jpg`} title={vegetable} />

            <marquee behavior="scroll" direction="right" scrollamount="5">
                <TagCardSlider>
                    <PercentageTagCard percentage={50}></PercentageTagCard>
                    <PercentageTagCard percentage={40}></PercentageTagCard>
                    <MoneyTagCard></MoneyTagCard>
                </TagCardSlider>

            </marquee>
            <Content vegetable={vegetable}></Content>
            <Footer></Footer>
        </div>


    );
}

export default Vegetable;
