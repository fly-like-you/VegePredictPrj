import React, {useEffect, useState} from 'react'
import Footer from "../components/content/footer/Footer";
import Content from "../components/content/Content";
import TopBar from "../components/topbar/TopBar";
import Banner from "../components/content/Banner";
import PercentageTagCard from "../components/content/card/PercentageTagCard";
import styled from "styled-components";
import MoneyTagCard from "../components/content/card/MoneyTagCard";
import axios from "axios";
import PredictTagCard from "../components/content/card/PredictTagCard";



const TagCardSlider = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
`;
function Vegetable( { vegetable }) {
    const [data, setData] = useState(null);
    useEffect(() => {
        axios.get(`/api/vegetable-cards/name/${vegetable}`)
            .then(response => setData(response.data))
            .catch(error => console.log(error));
    }, [vegetable]);

    if (!data) {
        return <div>Loading...</div>;
    }
    return(
        <div id="content-wrapper" className="d-flex flex-column">
            <TopBar></TopBar>
            <Banner imageSrc={`/images/${vegetable}.jpg`} title={vegetable} />

            <marquee behavior="scroll" direction="right" scrollamount="5">
                <TagCardSlider>
                    <PercentageTagCard percentage={data['pricePercentage']}></PercentageTagCard>
                    <MoneyTagCard veggie={data}></MoneyTagCard>
                    <PredictTagCard flag={data['higherThanToday']}></PredictTagCard>
                </TagCardSlider>

            </marquee>
            <Content vegetable={vegetable}></Content>
            <Footer></Footer>
        </div>


    );
}

export default Vegetable;
