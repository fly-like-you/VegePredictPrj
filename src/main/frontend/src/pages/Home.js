import React from 'react'
import TopBar from "../components/topbar/TopBar";
import Footer from "../components/content/footer/Footer";
import Banner from "../components/content/Banner";
import Card from "../components/content/card/Card";
import styled from "styled-components";

const ContentWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 70%;
    margin: 0 auto;
    min-width: 1250px;
    position: relative;

`;


const CardWrapper = styled.div`
    display: flex;
    justify-content: center;
    flex-direction: row;
    align-items: center;
    flex-wrap: wrap;
    width: 1080px;
    position: absolute;
    top: 300px;

`;


function Home() {
    return(
        <div>
            <TopBar></TopBar>

            <ContentWrapper>
                <Banner imageSrc="/images/banner.jpg" title="banner"/>
                <CardWrapper>
                    <Card
                        imgSrc="/images/red-pepper.jpg"
                        title="Card Title"
                        text="Some quick example text to build on the card title and make up the bulk of the card's content."
                        info="More info here."
                        lastUpdated="3 mins ago"
                    />
                    <Card
                        imgSrc="/images/red-pepper.jpg"
                        title="Card Title"
                        text="Some quick example text to build on the card title and make up the bulk of the card's content."
                        info="More info here."
                        lastUpdated="3 mins ago"
                    />
                    <Card
                        imgSrc="/images/red-pepper.jpg"
                        title="Card Title"
                        text="Some quick example text to build on the card title and make up the bulk of the card's content."
                        info="More info here."
                        lastUpdated="3 mins ago"
                    />
                    <Card
                        imgSrc="/images/red-pepper.jpg"
                        title="Card Title"
                        text="Some quick example text to build on the card title and make up the bulk of the card's content."
                        info="More info here."
                        lastUpdated="3 mins ago"
                    />


                </CardWrapper>
            </ContentWrapper>

            <Footer></Footer>

        </div>


    )
}

export default Home;