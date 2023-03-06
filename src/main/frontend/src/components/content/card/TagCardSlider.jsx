import { Carousel } from 'react-bootstrap';
import PercentageTagCard from './PercentageTagCard';

function TagCardSlider({ tagCards }) {
    return (
        <Carousel>
            {tagCards.map((tagCard, index) => (
                <Carousel.Item key={index}>
                    <PercentageTagCard percentage={tagCard.percentage} />
                </Carousel.Item>
            ))}
        </Carousel>
    );
}

export default TagCardSlider;
