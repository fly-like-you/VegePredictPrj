import React from "react";

function PercentageTagCard({percentage}) {
    console.log("Percentage");
    let round = Math.round(percentage * 10) / 10;

    return (
        <div className="col-xl-3 mb-4" style={{width: '50%'}}  >
            <div className="card border-left-info shadow h-100 py-2">
                <div className="card-body">
                    <div className="row no-gutters align-items-center">
                        <div className="col mr-2">
                            <div className="text-xs font-weight-bold text-info text-uppercase mb-1">전날과 비교해서 {Math.abs(round)} % {round < 0 ? '내렸어요 ㅠㅠ' : '올랐어요'}
                            </div>
                            <div className="row no-gutters align-items-center">
                                <div className="col-auto">
                                    <div className="h5 mb-0 mr-3 font-weight-bold text-gray-800">{Math.abs(round)}%</div>
                                </div>
                                <div className="col">
                                    <div className="progress progress-sm mr-2">
                                        <div className="progress-bar bg-info" role="progressbar"
                                             style={{width: `${Math.abs(round)}%`}} aria-valuenow=" `${percentage}`" aria-valuemin="0"
                                             aria-valuemax="100">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-auto">
                            <i className="fas fa-clipboard-list fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default PercentageTagCard;
