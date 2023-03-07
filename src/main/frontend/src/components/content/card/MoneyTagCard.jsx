import React from "react";

function PercentageTagCard({veggie}) {
    return (
        <div className="col-xl-3 col-md-6 mb-4">
            <div className="card border-left-success shadow h-100 py-2">
                <div className="card-body">
                    <div className="row no-gutters align-items-center">
                        <div className="col mr-2">
                            <div className="text-xs font-weight-bold text-success text-uppercase mb-1">
                                오늘의 {veggie}가격
                            </div>
                            <div className="h5 mb-0 font-weight-bold text-gray-800">13,520원</div>
                        </div>
                        <div className="col-auto">
                            <i className="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default PercentageTagCard;
