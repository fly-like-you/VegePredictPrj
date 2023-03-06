import styled from "styled-components";
import React from "react";
import Chart from './chart/Chart.js'

function Card() {
    return (
        <div class="card shadow mb-4">
            <a href="#collapseCardExample" class="d-block card-header py-3" data-toggle="collapse"
               role="button" aria-expanded="true" aria-controls="collapseCardExample">
                <h6 class="m-0 font-weight-bold text-primary">Collapsable Card Example</h6>
            </a>
            <div class="collapse show" id="collapseCardExample">
                <div class="card-body">
                    <Chart veggie={'cucumber'}/>
                </div>
            </div>
        </div>
    );
}

export default Card;
