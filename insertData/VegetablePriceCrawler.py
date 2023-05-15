# 그룹 코드 그룹명 품목 코드	품목명	품종 코드	품종명
# 200	 채소류	 242	 풋고추	  00	풋고추
# 200 	 채소류	 242	 풋고추	  02	꽈리고추
# 200 	 채소류	 242 	 풋고추	  03	청양고추
# 200 	 채소류	 253	 깻잎	  00	깻잎
# 200	 채소류	 258	 깐마늘(국산) 01	깐마늘(국산)
# 200	 채소류	 245	 양파	  00 	 양파
# 200	 채소류	 226	 딸기	  00	 딸기
# 200	 채소류	 256	 파프리카  00	     파프리카
# 200	 채소류	 262	 양상추	   01	 양상추
# 200	 채소류	 254	 부추	  00	 부추
# 200	 채소류	 213	 시금치	  00	 시금치
# 200	 채소류	 224	 호박	  01	 애호박
# 200	 채소류	 224	 호박	  02 	 쥬키니
import requests
from datetime import datetime

vegetables = {
    "풋고추": {
        "p_itemcode": "242",
        "p_kindcode": "00"
    },
    "꽈리고추": {
        "p_itemcode": "242",
        "p_kindcode": "02"
    },
    "청양고추": {
        "p_itemcode": "242",
        "p_kindcode": "03"
    },
    "깻잎": {
        "p_itemcode": "253",
        "p_kindcode": "00"
    },
    "깐마늘": {
        "p_itemcode": "244",
        "p_kindcode": "04"
    },
    "양파": {
        "p_itemcode": "245",
        "p_kindcode": "00"
    },
    "딸기": {
        "p_itemcode": "226",
        "p_kindcode": "00"
    },
    "파프리카": {
        "p_itemcode": "256",
        "p_kindcode": "00"
    },
    "양상추": {
        "p_itemcode": "262",
        "p_kindcode": "01"
    },
    "부추": {
        "p_itemcode": "254",
        "p_kindcode": "00"
    },
    "시금치": {
        "p_itemcode": "213",
        "p_kindcode": "00"
    },
    "애호박": {
        "p_itemcode": "224",
        "p_kindcode": "01"
    },
    "쥬키니": {
        "p_itemcode": "224",
        "p_kindcode": "02"
    }
}


class VegetablePriceCrawler:
    def __init__(self, start_date, end_date, url="http://www.kamis.or.kr/service/price/xml.do?action=periodProductList"):
        self.__start_date = start_date
        self.__end_date = end_date
        self.__url = url

    def get_vegetable_data(self, vegetable):
        params = self.__get_params(vegetable)
        data = self.__call_api(params)
        if data is None:
            print(f"상태 코드 이상")
            return None  # 상태 코드 200이외의 값이 나온 경우

        try:  # 상태 코드가 200이지만 데이터가 없는 경우
            error_code = data['data']['error_code']
        except:
            print(f"상태 코드가 200이지만 {vegetable}의 데이터가 존재하지 않습니다.")
            return []

        result = []
        for item in data['data']['item']:
            if not item['itemname']:
                continue

            result.append({
                'kindname': item['kindname'],
                'date': datetime.strptime(f"{item['yyyy']}-{item['regday']}", "%Y-%m/%d").strftime("%Y-%m-%d"),
                'price': int(item['price'].replace(',', ''))
            })
        return result

    def get_vegetables_data(self):
        ve = {}
        for vegetable in vegetables.keys():
            data = self.get_vegetable_data(vegetable)
            if data is not None:
                ve[vegetable] = data
        return ve

    def __call_api(self, params):
        headers = {"User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Whale/3.19.166.16 Safari/537.36"}
        response = requests.get(self.__url, params=params, headers=headers)
        if response.status_code == 200:
            return response.json()
        else:
            print("API 호출 실패")
            return None

    def __get_params(self, vegetable):
        global vegetables
        if vegetable not in vegetables:
            raise ValueError(f"{vegetable}은(는) 유효하지 않은 채소명입니다.")

        try:
            datetime.strptime(self.__start_date, '%Y-%m-%d')
            datetime.strptime(self.__end_date, '%Y-%m-%d')
        except ValueError:
            raise ValueError("올바르지 않은 날짜 형식입니다. (YYYY-MM-DD)")

        params = {
            "p_cert_key": "81bcbdc4-d802-4ef4-8cf9-12bd55f5e29d",
            "p_cert_id": "qkrwnsgh71w",
            "p_returntype": "json",
            "p_startday": self.__start_date,
            "p_endday": self.__end_date,
            "p_productclscode": "02",
            "p_itemcategorycode": "200",
            "p_itemcode": vegetables[vegetable]['p_itemcode'],
            "p_kindcode": vegetables[vegetable]['p_kindcode'],
            "p_countrycode": "1101",
            "p_convert_kg_yn": "Y"
        }

        return params
