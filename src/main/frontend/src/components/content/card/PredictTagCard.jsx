import React from "react";

function PercentageTagCard({flag}) {

    var str = "";
    flag ? str = "내일 가격이 올라요!" : str = "내일 가격이 떨어져요!";
    var subStr = "";
    flag ? subStr = "오늘 사시는건 어떠신가요?" : subStr = "다른 재료를 찾아보시는건 어때요?";

    return (
        <div className="col-xl-3 col-md-6 mb-4">
            <div className="card border-left-warning shadow h-100 py-2">
                <div className="card-body">
                    <div className="row no-gutters align-items-center">
                        <div className="col mr-2">
                            <div className="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                {str}
                            </div>
                            <div className="h6 mb-0 font-weight-bold text-gray-800">{subStr}</div>
                        </div>
                        <div className="col-auto">
                            <i className="fas fa-comments fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default PercentageTagCard;
