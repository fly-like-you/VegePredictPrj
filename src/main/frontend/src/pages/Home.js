import React, {useEffect, useState} from 'react'
import TopBar from "../components/topbar/TopBar";
import Footer from "../components/content/footer/Footer";
import Banner from "../components/content/Banner";
import Card from "../components/content/card/Card";
import styled from "styled-components";
import {Link} from "react-router-dom";

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
    const [vegetableCards, setVegetableCards] = useState([]);

    useEffect(() => {
        fetch('/api/vegetable-cards')
            .then((response) => response.json())
            .then((data) => setVegetableCards(data));
    }, []);

    return(
        <div>
            <TopBar></TopBar>

            <ContentWrapper>
                <Banner imageSrc="/images/banner.jpg" title="농산물 가격예측"/>

                <CardWrapper>
                    {vegetableCards.map((card) => (
                        <Link to={`/${card.vegetableName}`}>

                        <Card
                            imgSrc={`/images/${card.vegetableName}.jpg`}
                            title={card.vegetableName}
                            text={
                                card.pricePercentage < 0
                                    ? `전날과 대비하여 ${Math.abs(card.pricePercentage).toFixed(2)}% 하락했습니다.`
                                    : `전날과 대비하여 ${card.pricePercentage.toFixed(2)}% 상승하였습니다.`
                            }
                            info={`내일 예측: ${card.isHigherThanToday ? '높음' : '낮음'}`}
                        />
                        </Link>
                    ))}

                </CardWrapper>
            </ContentWrapper>

            <Footer></Footer>

        </div>


    )
}

export default Home;